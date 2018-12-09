package com.kwin.android.mrcocktail.viewmodel;

import com.kwin.android.mrcocktail.DataRepository;
import com.kwin.android.mrcocktail.data.db.entity.CocktailEntry;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class DetailActivityViewModel extends ViewModel {

    private final String drinkId;
    private final DataRepository repository;

    private final LiveData<CocktailEntry> mCocktail;

    public DetailActivityViewModel(DataRepository repository, String drinkId) {
        this.drinkId = drinkId;
        this.repository = repository;
        this.mCocktail = repository.getCocktailById(drinkId);
    }

    public LiveData<CocktailEntry> getCocktail() {
        return mCocktail;
    }
}
