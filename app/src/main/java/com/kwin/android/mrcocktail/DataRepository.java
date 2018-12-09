package com.kwin.android.mrcocktail;

import android.util.Log;

import com.kwin.android.mrcocktail.data.db.dao.CocktailDao;
import com.kwin.android.mrcocktail.data.db.entity.CocktailEntry;
import com.kwin.android.mrcocktail.data.network.NetworkDataSource;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class DataRepository {

    private static final String LOG_TAG = DataRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static DataRepository sInstance;
    private final CocktailDao mCocktailDao;
    private final NetworkDataSource mNetworkDataSource;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private DataRepository(CocktailDao cocktailDao,
                           NetworkDataSource networkDataSource,
                           AppExecutors executors) {
        mCocktailDao = cocktailDao;
        mNetworkDataSource = networkDataSource;
        mExecutors = executors;


        LiveData<List<CocktailEntry>> networkData = mNetworkDataSource.getCurrentCocktailEntry();
        networkData.observeForever(new Observer<List<CocktailEntry>>() {
            @Override
            public void onChanged(final List<CocktailEntry> cocktailEntries) {
                mExecutors.diskIO().execute(() -> {
                    // Deletes old historical data
                    deleteOldData();

                    // Insert our new cocktail data into App Data
                    cocktailDao.insertAll(cocktailEntries);
                });
            }
        });


    }

    private void deleteOldData() {
        mCocktailDao.deleteOldCocktail();
    }

    public synchronized static DataRepository getInstance(
            CocktailDao weatherDao, NetworkDataSource weatherNetworkDataSource,
            AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the repository");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new DataRepository(weatherDao, weatherNetworkDataSource,
                        executors);
                Log.d(LOG_TAG, "Made new repository");
            }
        }
        return sInstance;
    }

    /**
     * Creates periodic sync tasks and checks to see if an immediate sync is required. If an
     * immediate sync is required, this method will take care of making sure that sync occurs.
     */
    public synchronized void initializeData() {

        // Only perform initialization once per app lifetime. If initialization has already been
        // performed, we have nothing to do in this method.
        if (mInitialized) return;
        mInitialized = true;

        mExecutors.diskIO().execute(new Runnable() {
            @Override
            public void run() {
                startFetchCocktailService();
            }
        });

    }


    /**
     * Network related operation
     */

    private void startFetchCocktailService() {
        mNetworkDataSource.startFetchWeatherService();
    }

    public LiveData<List<CocktailEntry>> getCocktailList() {
        initializeData();
        return mCocktailDao.loadCocktail();
    }

    public LiveData<CocktailEntry> getCocktailById(String id) {
        mNetworkDataSource.loadCocktailById(id);
        insertCocktailById();
        return mCocktailDao.loadCocktailDetail(id);
    }

    public void insertCocktailById() {
        LiveData<List<CocktailEntry>> networkData = mNetworkDataSource.getDetailCocktailEntry();
        networkData.observeForever(new Observer<List<CocktailEntry>>() {
            @Override
            public void onChanged(List<CocktailEntry> cocktailEntries) {
                mExecutors.diskIO().execute(() -> {
                    // Insert our new cocktail data into App Data
                    mCocktailDao.updateCocktail(cocktailEntries);
                });

            }
        });
    }
}