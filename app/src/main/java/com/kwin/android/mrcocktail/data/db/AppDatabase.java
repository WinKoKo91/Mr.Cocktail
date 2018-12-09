package com.kwin.android.mrcocktail.data.db;

import android.content.Context;

import com.kwin.android.mrcocktail.data.db.dao.CocktailDao;
import com.kwin.android.mrcocktail.data.db.entity.CocktailEntry;

import androidx.annotation.VisibleForTesting;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CocktailEntry.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {


    @VisibleForTesting
    public static final String DATABASE_NAME = "mr_cocktail_db";

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static volatile AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            AppDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }

    public abstract CocktailDao cocktailDao();

}
