package com.example.cbr_manager.ui.baselinesurvey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbr_manager.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class BaselineLivelihoodFragment extends Fragment implements Step {

    public BaselineLivelihoodFragment() {
        // Required empty public constructor
    }

    public static BaselineLivelihoodFragment newInstance(String param1, String param2) {
        BaselineLivelihoodFragment fragment = new BaselineLivelihoodFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_baseline_livelihood, container, false);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}