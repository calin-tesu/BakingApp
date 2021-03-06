package com.example.android.bakingapp.ui;

import android.os.Bundle;

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.IngredientsStepsMasterFragment;
import com.example.android.bakingapp.fragments.StepFragment;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class BakingDetailsActivity extends AppCompatActivity implements IngredientsStepsMasterFragment.OnStepClickListener {

    private Recipe recipe;
    public Bundle currentRecipeBundle;
    public boolean mDualPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);

        if (getIntent() != null && getIntent().getExtras() != null) {
            recipe = getIntent().getExtras().getParcelable(Constants.KEY_CURRENT_RECIPE);
        }
        getSupportActionBar().setTitle(recipe.getName());

        currentRecipeBundle = new Bundle();
        currentRecipeBundle.putParcelable(Constants.KEY_CURRENT_RECIPE, recipe);

        IngredientsStepsMasterFragment ingredientsStepsMasterFragment = new IngredientsStepsMasterFragment();
        ingredientsStepsMasterFragment.setArguments(currentRecipeBundle);

        //Create new fragments only if there is no previously savedInstanceState
        if (savedInstanceState == null) {

            if (findViewById(R.id.steps_frag_container) != null) {

                mDualPane = true;

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.steps_frag_container, ingredientsStepsMasterFragment)
                        .commit();
            } else {

                mDualPane = false;

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.master_list_fragment, ingredientsStepsMasterFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onStepSelected(Step clickedStep) {
        Bundle stepBundle = new Bundle();
        stepBundle.putParcelable("currentStep", clickedStep);

        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(stepBundle);

        if (mDualPane) {
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
