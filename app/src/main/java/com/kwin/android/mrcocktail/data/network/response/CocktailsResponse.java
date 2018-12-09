package com.kwin.android.mrcocktail.data.network.response;

import com.google.gson.annotations.SerializedName;
import com.kwin.android.mrcocktail.data.db.entity.CocktailEntry;

import java.util.List;

public class CocktailsResponse {

    @SerializedName("drinks")
    private List<CocktailEntry> cocktails;

    public List<CocktailEntry> getCocktails() {
        return cocktails;
    }
}
