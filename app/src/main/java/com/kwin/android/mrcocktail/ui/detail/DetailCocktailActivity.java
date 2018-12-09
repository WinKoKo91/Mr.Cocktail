package com.kwin.android.mrcocktail.ui.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kwin.android.mrcocktail.R;
import com.kwin.android.mrcocktail.data.db.entity.CocktailEntry;
import com.kwin.android.mrcocktail.utilities.InjectorUtils;
import com.kwin.android.mrcocktail.viewmodel.DetailActivityViewModel;
import com.kwin.android.mrcocktail.viewmodel.DetailViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DetailCocktailActivity extends AppCompatActivity {

    private final static String DRINK_ID = "id";
    private String drinkId;
    private DetailActivityViewModel mViewModel;

    private ImageView cocktailIV;
    private TextView instructionsTV;
    private RecyclerView ingredientRV;
    private IngredientAdapter adapter;
    private List<String> ingredientMeasureList = new ArrayList<>();

    public static Intent instance(Activity activity, String idDrink) {
        Intent intent = new Intent(activity, DetailCocktailActivity.class);
        intent.putExtra(DRINK_ID, idDrink);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cocktail);

        if (getIntent().hasExtra(DRINK_ID)) {
            drinkId = getIntent().getStringExtra(DRINK_ID);
        }

        DetailViewModelFactory factory = InjectorUtils.provideDetailCocktailViewHolder(this.getApplicationContext(), drinkId);
        mViewModel = ViewModelProviders.of(this, factory).get(DetailActivityViewModel.class);

        mViewModel.getCocktail().observe(this, cocktailEntry -> {
            if (cocktailEntry != null) bindCocktailToUI(cocktailEntry);
        });

        // THIS IS JUST TO RUN THE CODE; REPOSITORY SHOULD NEVER BE CREATED IN
        // DETAILACTIVITY
        /*       InjectorUtils.provideRepository(this).loadCocktailById(drinkId);
         */
        cocktailIV = findViewById(R.id.iv_image);
        ingredientRV = findViewById(R.id.rv_ingredient);
        ingredientRV.setLayoutManager(new LinearLayoutManager(this));
        ingredientRV.setHasFixedSize(true);

        adapter = new IngredientAdapter(ingredientMeasureList);
        ingredientRV.setAdapter(adapter);

        instructionsTV = findViewById(R.id.tv_instructions);

    }

    private void bindCocktailToUI(CocktailEntry cocktailEntry) {
        getSupportActionBar().setTitle(cocktailEntry.getName());
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.placeholder);
        Glide.with(this).load(cocktailEntry.getThumbnail())
                .apply(requestOptions).into(cocktailIV);
        instructionsTV.setText(cocktailEntry.getInstructions());
        if(!cocktailEntry.getIngredientMeasureList().isEmpty())
        {
            ingredientMeasureList.clear();
            ingredientMeasureList.addAll(cocktailEntry.getIngredientMeasureList());
            adapter.notifyDataSetChanged();
        }
    }
}
