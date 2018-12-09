package com.kwin.android.mrcocktail.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.kwin.android.mrcocktail.R;
import com.kwin.android.mrcocktail.data.network.retrofit.ApiClient;
import com.kwin.android.mrcocktail.data.network.retrofit.ApiInterface;
import com.kwin.android.mrcocktail.model.Cocktail;
import com.kwin.android.mrcocktail.ui.detail.DetailCocktailActivity;
import com.kwin.android.mrcocktail.utilities.InjectorUtils;
import com.kwin.android.mrcocktail.utilities.OnItemClickCallback;
import com.kwin.android.mrcocktail.viewmodel.CocktailViewModelFactory;
import com.kwin.android.mrcocktail.viewmodel.CocktailActivityViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class MainActivity extends AppCompatActivity implements OnItemClickCallback {


    private RecyclerView cocktailRV;
    private int mPosition = RecyclerView.NO_POSITION;
    private ProgressBar mLoadingIndicator;
    CocktailAdapter adapter;

    private CocktailActivityViewModel mViewModel;


    ApiInterface apiService;

    List<Cocktail> cocktails = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        cocktailRV = findViewById(R.id.rv_cocktail);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        cocktailRV.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        cocktailRV.setHasFixedSize(true);
        adapter = new CocktailAdapter(getApplicationContext(), cocktails);
        adapter.setListener(this);

        CocktailViewModelFactory factory = InjectorUtils.provideCocktailViewHolder(this.getApplicationContext());
        mViewModel = ViewModelProviders.of(this, factory).get(CocktailActivityViewModel.class);

        mViewModel.getCocktail().observe(this, cocktailEntries -> {
            adapter.uploadData(cocktailEntries);
           /* if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
            cocktailRV.smoothScrollToPosition(mPosition);*/
            // Show the weather list or the loading screen based on whether the forecast data exists
            // and is loaded
            if (cocktailEntries != null && cocktailEntries.size() != 0) {
                showCocktailDataView();
                cocktails.clear();
                cocktails.addAll(cocktailEntries);
            } else showLoading();


        });
        cocktailRV.setAdapter(adapter);


    }

    private void showCocktailDataView() {
        // First, hide the loading indicator
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Finally, make sure the weather data is visible
        cocktailRV.setVisibility(View.VISIBLE);

    }

    /**
     * This method will make the loading indicator visible and hide the weather View and error
     * message.
     * <p>
     * Since it is okay to redundantly set the visibility of a View, we don't need to check whether
     * each view is currently visible or invisible.
     */
    private void showLoading() {
        // Then, hide the weather data
        cocktailRV.setVisibility(View.INVISIBLE);
        // Finally, show the loading indicator
        mLoadingIndicator.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(int position) {
        startActivity(DetailCocktailActivity.instance(this, cocktails.get(position).getIdDrink()));
    }
}
