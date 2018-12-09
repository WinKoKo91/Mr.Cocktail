package com.kwin.android.mrcocktail.viewmodel;

import com.kwin.android.mrcocktail.DataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataRepository repository;
    private final String drinkId;

    public DetailViewModelFactory(DataRepository repository, String drinkId) {
        this.repository = repository;
        this.drinkId = drinkId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailActivityViewModel(repository, drinkId);
    }
}
