package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
            recipe = getArguments().getParcelable(Constants.KEY_CURRENT_RECIPE);
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

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.KEY_CURRENT_RECIPE, recipe);
    }
}
