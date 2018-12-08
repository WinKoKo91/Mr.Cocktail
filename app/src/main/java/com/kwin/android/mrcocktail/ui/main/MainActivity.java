package com.kwin.android.mrcocktail.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import com.kwin.android.mrcocktail.R;
import com.kwin.android.mrcocktail.data.network.ApiClient;
import com.kwin.android.mrcocktail.data.network.ApiInterface;
import com.kwin.android.mrcocktail.data.network.Response.CocktailsResponse;
import com.kwin.android.mrcocktail.model.Cocktail;
import com.kwin.android.mrcocktail.ui.DetailCocktailActivity;
import com.kwin.android.mrcocktail.util.OnItemClickCallback;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnItemClickCallback {


    private RecyclerView cocktailRV;

    List<Cocktail> cocktails = new ArrayList<>();
    CocktailAdapter adapter;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cocktailRV = findViewById(R.id.rv_cocktail);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        cocktailRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        cocktailRV.setHasFixedSize(true);
        adapter = new CocktailAdapter(getApplicationContext(), cocktails);
        adapter.setListener(this);
        cocktailRV.setAdapter(adapter);

        onLoadCocktail();
    }

    private void onLoadCocktail() {

        Call<CocktailsResponse> call = apiService.getNonAlcoholicDrinks();
        call.enqueue(new Callback<CocktailsResponse>() {
            @Override
            public void onResponse(Call<CocktailsResponse> call, Response<CocktailsResponse> response) {
                if (response.isSuccessful()) {
                    CocktailsResponse result = response.body();
                    cocktails.clear();
                    cocktails.addAll(result.getCocktails());
                    //adapter.uploadData(cocktails);
                    adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CocktailsResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(int position) {
        startActivity(DetailCocktailActivity.instance(this, cocktails.get(position).getIdDrink()));
    }
}
