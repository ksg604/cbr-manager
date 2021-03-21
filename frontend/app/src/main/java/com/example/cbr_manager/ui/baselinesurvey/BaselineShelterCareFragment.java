package com.example.cbr_manager.ui.baselinesurvey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.baseline_survey.BaselineSurvey;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class BaselineShelterCareFragment extends Fragment implements Step {

    private View view;
    private BaselineSurvey baselineSurvey;
    private final String NOT_AVAILABLE = "N/A";
    RadioGroup adequateRadioGroup;
    RadioGroup essentialRadioGroup;

    public BaselineShelterCareFragment() {
        // Required empty public constructor
    }

    public static BaselineShelterCareFragment newInstance(String param1, String param2) {
        BaselineShelterCareFragment fragment = new BaselineShelterCareFragment();
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
        view = inflater.inflate(R.layout.fragment_shelter_care, container, false);
        adequateRadioGroup = view.findViewById(R.id.shelterAdequateRadioGroup);
        essentialRadioGroup = view.findViewById(R.id.shelterEssentialRadioGroup);
        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateBaselineSurvey();
        return null;
    }

    private void updateBaselineSurvey() {
        baselineSurvey.setAdequateShelter(getRadioButtonString(adequateRadioGroup));
        baselineSurvey.setAccessEssentials(getRadioButtonString(essentialRadioGroup));
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private String getRadioButtonString(RadioGroup radioGroup) {
        int id = radioGroup.getCheckedRadioButtonId();
        if (id == -1) {
            return "";
        }
        RadioButton radioButton = getView().findViewById(id);
        if (radioButton.getText().toString().trim().equals(NOT_AVAILABLE)) {
            return "";
        }

        return radioButton.getText().toString().trim();
    }
}