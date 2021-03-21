package com.example.cbr_manager.ui.baselinesurvey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.cbr_manager.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class BaselineHealthFragment extends Fragment implements Step {

    Spinner generalHealthSpinner;
    RadioGroup rehabAccessRadioGroup;
    RadioGroup assistiveDeviceHave;
    RadioGroup assistiveDeviceWorking;
    Spinner assistiveDeivceNeed;
    Spinner healthSatisfactionSpinner;

    private View view;

    public BaselineHealthFragment() {
        // Required empty public constructor
    }

    public static BaselineHealthFragment newInstance(String param1, String param2) {
        BaselineHealthFragment fragment = new BaselineHealthFragment();
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
        view =  inflater.inflate(R.layout.fragment_baseline_health, container, false);

        generalHealthSpinner = view.findViewById(R.id.baselineHealthRateSpinner);
        rehabAccessRadioGroup = view.findViewById(R.id.rehabAccessRadioGroup);
        assistiveDeviceHave = view.findViewById(R.id.assistiveDeviceHaveRadioGroup);
        assistiveDeviceWorking = view.findViewById(R.id.assistiveDeviceWorkingRadioGroup);
        assistiveDeivceNeed = view.findViewById(R.id.baselineAssistiveDeviceNeedSpinner);
        healthSatisfactionSpinner = view.findViewById(R.id.baselineHealthSatisfactionSpinner);

        return view;
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