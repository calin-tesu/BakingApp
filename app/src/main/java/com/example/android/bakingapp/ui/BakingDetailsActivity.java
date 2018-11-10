package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.IngredientsStepsMasterFragment;
import com.example.android.bakingapp.fragments.StepFragment;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

public class BakingDetailsActivity extends AppCompatActivity implements IngredientsStepsMasterFragment.OnStepClickListener {

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

        if (getResources().getBoolean(R.bool.isTablet)) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .add(R.id.steps_frag_container, ingredientsStepsMasterFragment)
                    .commit();
        } else {

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.master_list_fragment, ingredientsStepsMasterFragment)
                    .commit();
        }
    }

    @Override
    public void onStepSelected(Step clickedStep) {
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelable("currentStep", clickedStep);

        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(stepBundle);

        if (getResources().getBoolean(R.bool.isTablet)) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.master_list_fragment, stepFragment)
                    .commit();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.master_list_fragment, stepFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
