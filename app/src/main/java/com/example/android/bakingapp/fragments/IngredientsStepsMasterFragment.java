package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bakingapp.R;

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

        ButterKnife.bind(this, rootView);

        mIngredientsHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "ksdjhfskdhf", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
