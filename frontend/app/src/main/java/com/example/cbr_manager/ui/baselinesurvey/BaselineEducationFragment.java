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


public class BaselineEducationFragment extends Fragment implements Step {

    private View view;
    private BaselineSurvey baselineSurvey;
    RadioGroup goToSchool;
    TextInputEditText gradeEditText;
    Spinner noSchoolReasonSpinner;
    RadioGroup beenToSchoolRadioGroup;
    RadioGroup wantToSchoolRadioGroup;
    private final String NOT_AVAILABLE = "N/A";

    public BaselineEducationFragment() {
        // Required empty public constructor
    }

    public static BaselineEducationFragment newInstance(String param1, String param2) {
        BaselineEducationFragment fragment = new BaselineEducationFragment();
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
        view = inflater.inflate(R.layout.fragment_baseline_education, container, false);
        baselineSurvey = ((BaselineSurveyStepperActivity) getActivity()).formBaselineSurveyObj;
        goToSchool = view.findViewById(R.id.goToSchoolRadioGroup);
        gradeEditText = view.findViewById(R.id.baselineGradeEditText);
        noSchoolReasonSpinner = view.findViewById(R.id.baselineEducationNoSchoolSpinner);
        beenToSchoolRadioGroup = view.findViewById(R.id.beenToSchoolRadioGroup);
        wantToSchoolRadioGroup = view.findViewById(R.id.wantGoSchoolRadioGroup);

        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateBaselineSurvey();
        return null;
    }

    private void updateBaselineSurvey() {
        baselineSurvey.setAttendSchool(getRadioButtonString(goToSchool));
        if (!gradeEditText.getText().toString().isEmpty()) {
            baselineSurvey.setGrade(Integer.parseInt(gradeEditText.getText().toString()));
        }
        baselineSurvey.setReasonNoSchool(getSpinnerString(noSchoolReasonSpinner));
        baselineSurvey.setBeenToSchool(getRadioButtonString(beenToSchoolRadioGroup));
        baselineSurvey.setWantToGoSchool(getRadioButtonString(wantToSchoolRadioGroup));
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