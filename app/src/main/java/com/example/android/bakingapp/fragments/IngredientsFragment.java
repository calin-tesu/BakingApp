package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientsFragment extends Fragment {

    @BindView(R.id.ingredient_name)
    TextView ingredientsTv;
    Recipe recipe;
    String ingredientsString = "";

    public IngredientsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate((R.layout.fragment_ingredients), container, false);

        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            recipe = getArguments().getParcelable("currentRecipe");
        }

        List<Ingredient> ingredientList = recipe.getIngredients();
        Ingredient currentIngredient;

        //No need to use a RecyclerView for the ingredients
        for (int i = 0; i < ingredientList.size(); i++) {
            currentIngredient = ingredientList.get(i);
            ingredientsString = ingredientsString +
                    //Capitalize only the first letter of the ingredient
                    (currentIngredient.getIngredient().substring(0, 1).toUpperCase() +
                            currentIngredient.getIngredient().substring(1).toLowerCase() +
                            ": " + String.valueOf(currentIngredient.getQuantity()) +
                            " " + currentIngredient.getMeasure() +
                            "\n\n");
        }

        ingredientsTv.setText(ingredientsString);

        return rootView;
    }
}
