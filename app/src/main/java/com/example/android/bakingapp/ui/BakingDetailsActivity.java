package com.example.android.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.fragments.IngredientsStepsMasterFragment;
import com.example.android.bakingapp.models.Recipe;

public class BakingDetailsActivity extends AppCompatActivity {

    private Recipe currentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baking_details);

        currentRecipe = getIntent().getExtras().getParcelable("currentRecipe");
        getSupportActionBar().setTitle(currentRecipe.getName());

        IngredientsStepsMasterFragment ingredientsStepsMasterFragment = new IngredientsStepsMasterFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.master_list_fragment, ingredientsStepsMasterFragment)
                .commit();
    }
}
