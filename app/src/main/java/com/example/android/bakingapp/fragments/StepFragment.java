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
import com.example.android.bakingapp.models.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Calin Tesu on 11/7/2018.
 */
public class StepFragment extends Fragment {

    @BindView(R.id.step_title)
    TextView stepTitle;
    @BindView(R.id.step_description)
    TextView stepDescription;
    Step currentStep;

    // Mandatory empty constructor
    public StepFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_step, container, false);

        ButterKnife.bind(this, rootView);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentStep = getArguments().getParcelable("currentStep");
        }

        stepTitle.setText(currentStep.getShortDescription());
        stepDescription.setText(currentStep.getDescription());

        return rootView;
    }
}
