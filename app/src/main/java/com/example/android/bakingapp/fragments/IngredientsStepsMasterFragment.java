package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.models.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Calin Tesu on 11/1/2018.
 */
public class IngredientsStepsMasterFragment extends Fragment {

    @BindView(R.id.steps_rv)
    RecyclerView mStepsRecyclerView;
    @BindView(R.id.ingredients_header)
    TextView mIngredientsHeader;
    private RecyclerView.LayoutManager layoutManager;
    private StepsAdapter stepsAdapter;
    private Recipe recipe;
    private Bundle currentRecipeBundle;
//    private ArrayList<Step> steps;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public IngredientsStepsMasterFragment() {
    }

    /**
     * Inflates the fragment layout file and sets the correct resource for the image to display
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Inflate the fragment layout
        View rootView = inflater.inflate(R.layout.fragment_ingredients_steps_master,container, false);

        currentRecipeBundle = this.getArguments();
        if (currentRecipeBundle != null) {
            recipe = getArguments().getParcelable("currentRecipe");
        }

        ButterKnife.bind(this, rootView);

        mStepsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        mStepsRecyclerView.setLayoutManager(layoutManager);

        stepsAdapter = new StepsAdapter(recipe.getSteps());
        mStepsRecyclerView.setAdapter(stepsAdapter);

        mIngredientsHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(currentRecipeBundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.master_list_fragment, ingredientsFragment)
                        .commit();
            }
        });

        return rootView;
    }
}
