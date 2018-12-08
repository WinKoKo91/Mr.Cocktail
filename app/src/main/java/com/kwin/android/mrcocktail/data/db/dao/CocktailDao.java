package com.kwin.android.mrcocktail.data.db.dao;


import com.kwin.android.mrcocktail.data.db.entity.CocktailEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

public interface CocktailDao {

    @Query("SELECT * FROM cocktail where idDrink=:id")
    LiveData<List<CocktailEntity>> loadCocktail(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CocktailEntity> cocktails);

}
