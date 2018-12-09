package com.kwin.android.mrcocktail.data.db.dao;


import com.kwin.android.mrcocktail.data.db.entity.CocktailEntry;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CocktailDao {

    @Query("SELECT * FROM cocktail")
    LiveData<List<CocktailEntry>> loadCocktail();

    @Query("SELECT * FROM cocktail where idDrink=:id")
    LiveData<CocktailEntry> loadCocktailDetail(String id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CocktailEntry> cocktails);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCocktail(List<CocktailEntry> cocktails);

    @Query("DELETE FROM cocktail")
    void deleteOldCocktail();

}
