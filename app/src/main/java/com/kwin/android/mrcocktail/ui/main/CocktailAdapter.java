package com.kwin.android.mrcocktail.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.kwin.android.mrcocktail.R;
import com.kwin.android.mrcocktail.model.Cocktail;
import com.kwin.android.mrcocktail.utilities.OnItemClickCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CocktailAdapter extends RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder> {

    private Context context;
    private List<? extends Cocktail> mCocktails;
    private OnItemClickCallback listener;


    public CocktailAdapter(Context context, List<Cocktail> cocktails) {
        this.context = context;
        this.mCocktails = cocktails;
    }

    @NonNull
    @Override
    public CocktailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cocktail, viewGroup, false);
        return new CocktailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CocktailViewHolder cocktailViewHolder, final int position) {
        cocktailViewHolder.bind(mCocktails.get(position));
        cocktailViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCocktails == null ? 0 : mCocktails.size();
    }

    public void setListener(OnItemClickCallback listener) {
        this.listener = listener;
    }

    public void uploadData(final List<? extends Cocktail> cocktails) {
        if (mCocktails == null) {
            this.mCocktails = cocktails;
            notifyItemRangeInserted(0, cocktails.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mCocktails.size();
                }

                @Override
                public int getNewListSize() {
                    return cocktails.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mCocktails.get(oldItemPosition).getIdDrink().equalsIgnoreCase(
                            cocktails.get(newItemPosition).getIdDrink());
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Cocktail newProduct = cocktails.get(newItemPosition);
                    Cocktail oldProduct = mCocktails.get(oldItemPosition);
                    return newProduct.getIdDrink().equalsIgnoreCase(oldProduct.getIdDrink())
                            && newProduct.getName().equalsIgnoreCase(oldProduct.getName())
                            && newProduct.getThumbnail().equalsIgnoreCase(oldProduct.getThumbnail());
                }
            });
            mCocktails = cocktails;
            result.dispatchUpdatesTo(this);
        }
    }

    public class CocktailViewHolder extends RecyclerView.ViewHolder {


        TextView nameTV;
        ImageView cockTialIV;


        public CocktailViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = (TextView) itemView.findViewById(R.id.tv_name);
            cockTialIV = (ImageView) itemView.findViewById(R.id.iv_image);
        }

        public void bind(Cocktail cocktail) {
            nameTV.setText(cocktail.getName());
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.placeholder);
            Glide.with(context).load(cocktail.getThumbnail())
                    .apply(requestOptions).into(cockTialIV);
        }
    }
}
