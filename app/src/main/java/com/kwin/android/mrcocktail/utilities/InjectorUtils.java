package com.kwin.android.mrcocktail.utilities;

import android.content.Context;

import com.kwin.android.mrcocktail.AppExecutors;
import com.kwin.android.mrcocktail.DataRepository;
import com.kwin.android.mrcocktail.data.db.AppDatabase;
import com.kwin.android.mrcocktail.data.network.NetworkDataSource;
import com.kwin.android.mrcocktail.viewmodel.CocktailViewModelFactory;
import com.kwin.android.mrcocktail.viewmodel.DetailViewModelFactory;

/**
 * Provides static methods to inject the various classes needed for Sunshine
 */
public class InjectorUtils {

    public static DataRepository provideRepository(Context context) {
        AppDatabase database = AppDatabase.getsInstance(context);
        AppExecutors executors = AppExecutors.getInstance();
        NetworkDataSource networkDataSource =
                NetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return DataRepository.getInstance(database.cocktailDao(), networkDataSource, executors);
    }

    public static NetworkDataSource provideNetworkDataSource(Context applicationContext) {
        provideRepository(applicationContext.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return NetworkDataSource.getInstance(applicationContext.getApplicationContext(), executors);
    }

    public static CocktailViewModelFactory provideCocktailViewHolder(Context context) {
        DataRepository repository = provideRepository(context.getApplicationContext());
        return new CocktailViewModelFactory(repository);
    }

    public static DetailViewModelFactory provideDetailCocktailViewHolder(Context context, String drinkId) {
        DataRepository repository = provideRepository(context);
        return new DetailViewModelFactory(repository, drinkId);
    }
}
