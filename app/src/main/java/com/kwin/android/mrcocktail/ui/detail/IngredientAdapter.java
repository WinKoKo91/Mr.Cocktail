package com.kwin.android.mrcocktail.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kwin.android.mrcocktail.R;
import com.kwin.android.mrcocktail.model.Cocktail;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    List<String> mIngredientMeasureList;

    public IngredientAdapter(List<String> ingredientMeasureList) {
        this.mIngredientMeasureList = ingredientMeasureList;
    }

    public void update(List<String> ingredientMeasureList) {
        if (ingredientMeasureList.isEmpty())
            return;

        if (mIngredientMeasureList.size() == 0) {
            this.mIngredientMeasureList = ingredientMeasureList;
            notifyItemRangeInserted(0, mIngredientMeasureList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mIngredientMeasureList.size();
                }

                @Override
                public int getNewListSize() {
                    return ingredientMeasureList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mIngredientMeasureList.get(oldItemPosition).equalsIgnoreCase(
                            ingredientMeasureList.get(newItemPosition));
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    String newProduct = ingredientMeasureList.get(newItemPosition);
                    String oldProduct = mIngredientMeasureList.get(oldItemPosition);
                    return newProduct.equalsIgnoreCase(oldProduct);
                }
            });
            mIngredientMeasureList = ingredientMeasureList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        holder.bind(mIngredientMeasureList.get(position));
    }

    @Override
    public int getItemCount() {
        return mIngredientMeasureList == null ? 0 : mIngredientMeasureList.size();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView nameTV;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.tv_name);
        }

        public void bind(String name) {

            nameTV.setText(name);
        }
    }
}
