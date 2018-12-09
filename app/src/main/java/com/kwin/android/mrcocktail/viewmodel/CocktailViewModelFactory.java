package com.kwin.android.mrcocktail.viewmodel;

import com.kwin.android.mrcocktail.DataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CocktailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataRepository mRepository;

    public CocktailViewModelFactory(DataRepository repository) {
        mRepository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CocktailActivityViewModel(mRepository);
    }

}
