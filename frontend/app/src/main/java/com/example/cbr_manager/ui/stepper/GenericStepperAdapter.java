package com.example.cbr_manager.ui.stepper;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;
import com.stepstone.stepper.viewmodel.StepViewModel;

import java.util.ArrayList;
import java.util.List;

public class GenericStepperAdapter extends AbstractFragmentStepAdapter {

    private static final String CURRENT_STEP_POSITION_KEY = "CURRENT_STEP_POSITION_KEY";

    private final List<StepperAdapterItem> fragmentSteps;

    public GenericStepperAdapter(FragmentManager fm, Context context) {
        super(fm, context);
        fragmentSteps = new ArrayList<>();
    }

    @Override
    public Step createStep(int position) {
        Bundle b = new Bundle();
        if (position < fragmentSteps.size()) {
            StepperAdapterItem adapterItem = fragmentSteps.get(position);
            Fragment fragmentStep = adapterItem.fragment;
            b.putInt(CURRENT_STEP_POSITION_KEY, position);
            fragmentStep.setArguments(b);
            return (Step) fragmentStep;
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragmentSteps.size();
    }

    @NonNull
    @Override
    public StepViewModel getViewModel(@IntRange(from = 0) int position) {
        if (position < fragmentSteps.size()) {
            StepperAdapterItem adapterItem = fragmentSteps.get(position);
            new StepViewModel.Builder(context)
                    .setTitle(adapterItem.fragmentTitle) //can be a CharSequence instead
                    .create();
        }

        return new StepViewModel.Builder(context)
                .setTitle("Default Title") //can be a CharSequence instead
                .create();
    }

    public void addFragment(Fragment fragment, String fragmentTitle) {
        fragmentSteps.add(new StepperAdapterItem(fragment, fragmentTitle));
    }

    public void removeFragment(Fragment fragment, String fragmentTitle) {
        StepperAdapterItem toBeRemoved = null;
        for (StepperAdapterItem stepperAdapterItem : fragmentSteps) {
            if (stepperAdapterItem.getFragmentTitle().equals(fragmentTitle)) {
                toBeRemoved = stepperAdapterItem;
            }
        }
        fragmentSteps.remove(toBeRemoved);
//        fragmentSteps.remove(new StepperAdapterItem(fragment, fragmentTitle));
    }
}