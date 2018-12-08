package com.kwin.android.mrcocktail.data.network;

import com.kwin.android.mrcocktail.data.network.Response.CocktailsResponse;
import com.kwin.android.mrcocktail.model.Cocktail;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("filter.php?a=Non_Alcoholic")
    Call<CocktailsResponse> getNonAlcoholicDrinks();

    @GET("lookup.php")
    Call<CocktailsResponse> getDrinkById(@Query("i") String idDrink);

}
