package com.example.android.bakingapp.fragments;

import android.content.Context;
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

import com.example.android.bakingapp.Constants;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepsAdapter;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Calin Tesu on 11/1/2018.
 */
public class IngredientsStepsMasterFragment extends Fragment implements StepsAdapter.StepAdapterOnClickListener {

    @BindView(R.id.steps_rv)
    RecyclerView mStepsRecyclerView;
    @BindView(R.id.ingredients_header)
    TextView mIngredientsHeader;
    private RecyclerView.LayoutManager layoutManager;
    private StepsAdapter stepsAdapter;
    private Recipe recipe;
    private Bundle currentRecipeBundle;

    // Define a new interface OnStepClickListener that triggers a callback in the host activity
    OnStepClickListener mCallback;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the fragment
     */
    public IngredientsStepsMasterFragment() {
    }

    @Override
    public void onClick(Step clickedStep) {
        mCallback.onStepSelected(clickedStep);

    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
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
            recipe = getArguments().getParcelable(Constants.KEY_CURRENT_RECIPE);
        }

        ButterKnife.bind(this, rootView);

        mStepsRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        mStepsRecyclerView.setLayoutManager(layoutManager);

        stepsAdapter = new StepsAdapter(this, recipe.getSteps());
        mStepsRecyclerView.setAdapter(stepsAdapter);

        mIngredientsHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientsFragment ingredientsFragment = new IngredientsFragment();
                ingredientsFragment.setArguments(currentRecipeBundle);

                if (getActivity().findViewById(R.id.steps_frag_container) != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.master_list_fragment, ingredientsFragment)
                            .commit();
                } else {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.master_list_fragment, ingredientsFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        return rootView;
    }

    // OnStepClickListener interface, calls a method in the host activity named onStepSelected
    public interface OnStepClickListener {
        void onStepSelected(Step clickedStep);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.KEY_CURRENT_RECIPE, recipe);
    }
}
