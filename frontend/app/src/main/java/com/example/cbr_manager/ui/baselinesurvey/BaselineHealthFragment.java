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
import android.widget.Spinner;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.baseline_survey.BaselineSurvey;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class BaselineHealthFragment extends Fragment implements Step {

    Spinner generalHealthSpinner;
    RadioGroup rehabAccessRadioGroup;
    RadioGroup assistiveDeviceHave;
    RadioGroup assistiveDeviceWorking;
    Spinner assistiveDeivceNeed;
    Spinner healthSatisfactionSpinner;

    private BaselineSurvey baselineSurvey;
    private final String NOT_AVAILABLE = "N/A";

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

        baselineSurvey = ((BaselineSurveyStepperActivity) getActivity()).formBaselineSurveyObj;

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
        updateBaselineSurvey();
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private void updateBaselineSurvey() {
        baselineSurvey.setGeneralHealth(getSpinnerString(generalHealthSpinner));
        baselineSurvey.setHaveAccessRehab(getRadioButtonString(rehabAccessRadioGroup));
        baselineSurvey.setAssistiveDeviceHave(getRadioButtonString(assistiveDeviceHave));
        baselineSurvey.setAssistiveDeviceWorking(getRadioButtonString(assistiveDeviceWorking));
        baselineSurvey.setAssistiveDeviceNeed(getSpinnerString(assistiveDeivceNeed));
        baselineSurvey.setHealthSatisfaction(getSpinnerString(healthSatisfactionSpinner));
    }

    private String getSpinnerString(Spinner spinner) {
        if (spinner.getSelectedItem().toString().trim().equals(NOT_AVAILABLE)) {
            return "";
        }
        return spinner.getSelectedItem().toString().trim();
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