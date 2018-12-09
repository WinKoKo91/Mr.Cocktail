package com.kwin.android.mrcocktail.data.network;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kwin.android.mrcocktail.AppExecutors;
import com.kwin.android.mrcocktail.data.db.entity.CocktailEntry;
import com.kwin.android.mrcocktail.data.network.response.CocktailsResponse;
import com.kwin.android.mrcocktail.data.network.retrofit.ApiClient;
import com.kwin.android.mrcocktail.data.network.retrofit.ApiInterface;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkDataSource {

    private static final String LOG_TAG = NetworkDataSource.class.getSimpleName();

    // LiveData storing the latest downloaded weather forecasts
    private final MutableLiveData<List<CocktailEntry>> mDownloadedCocktails;

    private final MutableLiveData<List<CocktailEntry>> mDownloadedCocktailDetail;


    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static NetworkDataSource sInstance;
    private final Context mContext;

    private final AppExecutors mExecutors;
    private final ApiInterface apiService;


    private NetworkDataSource(Context context, AppExecutors executors) {
        mContext = context;
        mExecutors = executors;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        mDownloadedCocktails = new MutableLiveData<List<CocktailEntry>>();
        mDownloadedCocktailDetail = new MutableLiveData<List<CocktailEntry>>();

    }

    /**
     * Get the singleton for this class
     */
    public static NetworkDataSource getInstance(Context context, AppExecutors executors) {
        Log.d(LOG_TAG, "Getting the network data source");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new NetworkDataSource(context.getApplicationContext(), executors);
                Log.d(LOG_TAG, "Made new network data source");
            }
        }
        return sInstance;
    }


    public LiveData<List<CocktailEntry>> getDetailCocktailEntry() {
        return mDownloadedCocktailDetail;
    }

    public LiveData<List<CocktailEntry>> getCurrentCocktailEntry() {
        return mDownloadedCocktails;
    }

    public void fetchCocktail() {

        Call<CocktailsResponse> call = apiService.getNonAlcoholicDrinks();
        call.enqueue(new Callback<CocktailsResponse>() {
            @Override
            public void onResponse(Call<CocktailsResponse> call, Response<CocktailsResponse> response) {
                if (response.isSuccessful()) {
                    CocktailsResponse result = response.body();
                    mDownloadedCocktails.postValue(result.getCocktails());
                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CocktailsResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadCocktailById(String drinkID) {

        Call<CocktailsResponse> call = apiService.getDrinkById(drinkID);
        call.enqueue(new Callback<CocktailsResponse>() {
            @Override
            public void onResponse(Call<CocktailsResponse> call, Response<CocktailsResponse> response) {
                if (response.isSuccessful()) {
                    CocktailsResponse result = response.body();
                    mDownloadedCocktailDetail.postValue(result.getCocktails());

                } else {
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CocktailsResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startFetchWeatherService() {
        Intent intentToFetch = new Intent(mContext, CocktailSyncIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(LOG_TAG, "Service created");
    }


}
