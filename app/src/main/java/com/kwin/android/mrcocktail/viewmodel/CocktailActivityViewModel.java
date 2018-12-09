package com.kwin.android.mrcocktail.viewmodel;

import com.kwin.android.mrcocktail.DataRepository;
import com.kwin.android.mrcocktail.data.db.entity.CocktailEntry;
import com.kwin.android.mrcocktail.model.Cocktail;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class CocktailActivityViewModel extends ViewModel {

    private final DataRepository mRepository;
    private final LiveData<List<CocktailEntry>> mCocktail;

    public CocktailActivityViewModel(DataRepository mRepository) {
        this.mRepository = mRepository;
        this.mCocktail = mRepository.getCocktailList();
    }

    public LiveData<List<CocktailEntry>> getCocktail() {
        return mCocktail;
    }


}
