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
import com.google.android.material.textfield.TextInputEditText;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class BaselineLivelihoodFragment extends Fragment implements Step {

    RadioGroup workingRadioGroup;
    TextInputEditText whatJobEditText;
    Spinner enrollmentTypeSpinner;
    RadioGroup meetFinanceRadioGroup;
    RadioGroup wantWorkRadioGroup;
    private View view;
    private final String NOT_AVAILABLE = "N/A";
    private BaselineSurvey baselineSurvey;

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
        view = inflater.inflate(R.layout.fragment_baseline_livelihood, container, false);
        baselineSurvey = ((BaselineSurveyStepperActivity) getActivity()).formBaselineSurveyObj;
        workingRadioGroup = view.findViewById(R.id.livelihoodWorkingRadioGroup);
        whatJobEditText = view.findViewById(R.id.livelihoodWhatWorkEditText);
        enrollmentTypeSpinner = view.findViewById(R.id.baselineEmploymentTypeSpinner);
        meetFinanceRadioGroup = view.findViewById(R.id.livelihoodFinancialRadioGroup);
        wantWorkRadioGroup = view.findViewById(R.id.livelihoodWantWorkRadioGroup);
        
        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateBaselineSurvey();
        return null;
    }

    private void updateBaselineSurvey() {
        baselineSurvey.setWorking(getRadioButtonString(workingRadioGroup));
        baselineSurvey.setJob(whatJobEditText.getText().toString());
        baselineSurvey.setEmployment(getSpinnerString(enrollmentTypeSpinner));
        baselineSurvey.setMeetsFinancial(getRadioButtonString(meetFinanceRadioGroup));
        baselineSurvey.setWantWork(getRadioButtonString(wantWorkRadioGroup));
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

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