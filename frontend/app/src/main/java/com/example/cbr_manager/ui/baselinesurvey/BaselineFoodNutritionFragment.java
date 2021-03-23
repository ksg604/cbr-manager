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

public class BaselineFoodNutritionFragment extends Fragment implements Step {

    private View view;
    private BaselineSurvey baselineSurvey;
    private final String NOT_AVAILABLE = "N/A";
    Spinner foodSecuritySpinner;
    RadioGroup enoughFoodRadioGroup;
    Spinner nutritionSpinner;

    public BaselineFoodNutritionFragment() {
        // Required empty public constructor
    }

    public static BaselineFoodNutritionFragment newInstance(String param1, String param2) {
        BaselineFoodNutritionFragment fragment = new BaselineFoodNutritionFragment();
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
        view = inflater.inflate(R.layout.fragment_baseline_food_nutrition, container, false);
        baselineSurvey = ((BaselineSurveyStepperActivity) getActivity()).formBaselineSurveyObj;
        foodSecuritySpinner = view.findViewById(R.id.foodSecuritySpinner);
        enoughFoodRadioGroup = view.findViewById(R.id.foodEnoughFoodRadioGroup);
        nutritionSpinner = view.findViewById(R.id.baselineChildNutritionSpinner);
        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateBaselineSurvey();
        return null;
    }

    private void updateBaselineSurvey() {
        baselineSurvey.setFoodSecurity(getSpinnerString(foodSecuritySpinner));
        baselineSurvey.setEnoughFood(getRadioButtonString(enoughFoodRadioGroup));
        baselineSurvey.setChildNourishment(getSpinnerString(nutritionSpinner));
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