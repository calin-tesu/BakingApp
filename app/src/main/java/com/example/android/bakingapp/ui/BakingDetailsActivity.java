package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.IngredientsStepsMasterFragment;
import com.example.android.bakingapp.models.Recipe;

public class BakingDetailsActivity extends AppCompatActivity {

    private Recipe recipe;
    public Bundle currentRecipeBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);

        if (getIntent() != null && getIntent().getExtras() != null) {
            recipe = getIntent().getExtras().getParcelable("currentRecipe");
        }
        getSupportActionBar().setTitle(recipe.getName());

        currentRecipeBundle = new Bundle();
        currentRecipeBundle.putParcelable("currentRecipe", recipe);

        IngredientsStepsMasterFragment ingredientsStepsMasterFragment = new IngredientsStepsMasterFragment();
        ingredientsStepsMasterFragment.setArguments(currentRecipeBundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.master_list_fragment, ingredientsStepsMasterFragment)
                .commit();
    }
}
