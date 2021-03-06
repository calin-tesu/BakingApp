package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.ui.BakingDetailsActivity;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Calin Tesu on 10/31/2018.
 */
public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(List<Recipe> recipes) {
        recipeList = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recipe_card, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe currentRecipe = recipeList.get(position);
        holder.mRecipeNameTv.setText(currentRecipe.getName());
        Picasso.with(context)
                .load(Constants.BASE_URL + currentRecipe.getImage())
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mImageView;
        public final TextView mRecipeNameTv;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.thumbnail);
            mRecipeNameTv = itemView.findViewById(R.id.recipe_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe currentRecipe = recipeList.get(adapterPosition);
            Intent intent = new Intent(context, BakingDetailsActivity.class);
            intent.putExtra(Constants.KEY_CURRENT_RECIPE, currentRecipe);
            context.startActivity(intent);

            /* Save the clicked recipe name and ingredients in SharedPreferences
            We will use this for updating the WidgetProvider with the last viewed recipe */
            SharedPreferences.Editor editor = context.getSharedPreferences(Constants.KEY_CURRENT_RECIPE, Context.MODE_PRIVATE).edit();

            //use gson to store class objects into SharedPreferences
            Gson gson = new Gson();
            String jsonRecipe = gson.toJson(currentRecipe);
            editor.putString(Constants.KEY_CURRENT_RECIPE, jsonRecipe);

            editor.apply();
        }
    }
}
