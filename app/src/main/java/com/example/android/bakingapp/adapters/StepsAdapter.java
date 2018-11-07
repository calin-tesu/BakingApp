package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;

import java.util.ArrayList;

/**
 * Created by Calin Tesu on 11/2/2018.
 */
public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private Context context;
    public ArrayList<Step> stepsList;

    private StepAdapterOnClickListener onClickListener;

    public StepsAdapter(StepAdapterOnClickListener clickListener, ArrayList<Step> steps) {
        onClickListener = clickListener;
        stepsList = steps;
    }


    public interface StepAdapterOnClickListener {
        void onClick(Step clickedStep);
    }

    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.steps_main_rv, parent, false);
        return new StepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder stepsViewHolder, int position) {
        Step currentStep = stepsList.get(position);
        stepsViewHolder.mStepID.setText(currentStep.getShortDescription());
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }

    public class StepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final TextView mStepID;

        public StepsViewHolder(@NonNull View itemView) {
            super(itemView);
            mStepID = itemView.findViewById(R.id.steps_id);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step currentStep = stepsList.get(adapterPosition);
            onClickListener.onClick(currentStep);
        }
    }
}
