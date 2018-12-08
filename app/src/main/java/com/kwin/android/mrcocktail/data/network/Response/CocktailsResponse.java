package com.kwin.android.mrcocktail.data.network.Response;

import com.google.gson.annotations.SerializedName;
import com.kwin.android.mrcocktail.data.db.entity.CocktailEntity;
import com.kwin.android.mrcocktail.model.Cocktail;

import java.util.List;

public class CocktailsResponse {

    @SerializedName("drinks")
    private List<CocktailEntity> cocktails;

    public List<CocktailEntity> getCocktails() {
        return cocktails;
    }
}
