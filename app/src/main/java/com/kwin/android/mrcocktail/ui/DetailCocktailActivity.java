package com.kwin.android.mrcocktail.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kwin.android.mrcocktail.R;
import com.kwin.android.mrcocktail.ui.main.MainActivity;

import androidx.appcompat.app.AppCompatActivity;

public class DetailCocktailActivity extends AppCompatActivity {

    private final static String DRINK_ID = "id";

    public static Intent instance(Activity activity, String idDrink) {
        Intent intent = new Intent(activity, DetailCocktailActivity.class);
        intent.putExtra(DRINK_ID, idDrink);
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cocktail);
    }
}
