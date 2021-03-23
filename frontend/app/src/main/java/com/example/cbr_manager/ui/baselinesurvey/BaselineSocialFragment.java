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

public class BaselineSocialFragment extends Fragment implements Step {

    private View view;
    private final String NOT_AVAILABLE = "N/A";
    private BaselineSurvey baselineSurvey;
    RadioGroup socialFeelValuedRadioGroup;
    RadioGroup socialIndependenceRadioGroup;
    RadioGroup socialParticipateRadioGroup;
    RadioGroup socialDisabilityAffectsRadioGroup;
    RadioGroup socialDiscriminatedRadioGroup;

    public BaselineSocialFragment() {
    }

    public static BaselineSocialFragment newInstance(String param1, String param2) {
        BaselineSocialFragment fragment = new BaselineSocialFragment();
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
        view = inflater.inflate(R.layout.fragment_baseline_social, container, false);
        baselineSurvey = ((BaselineSurveyStepperActivity) getActivity()).formBaselineSurveyObj;
        socialFeelValuedRadioGroup = view.findViewById(R.id.socialFeelValuedRadioGroup);
        socialIndependenceRadioGroup = view.findViewById(R.id.socialIndependentRadioGroup);
        socialParticipateRadioGroup = view.findViewById(R.id.socialParticipateRadioGroup);
        socialDisabilityAffectsRadioGroup = view.findViewById(R.id.socialDisabilityAffectsRadioGroup);
        socialDiscriminatedRadioGroup = view.findViewById(R.id.socialDiscriminationRadioGroup);
        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateBaselineSurvey();
        return null;
    }

    private void updateBaselineSurvey() {
        baselineSurvey.setFeelValued(getRadioButtonString(socialFeelValuedRadioGroup));
        baselineSurvey.setFeelIndependent(getRadioButtonString(socialIndependenceRadioGroup));
        baselineSurvey.setAbleToParticipate(getRadioButtonString(socialParticipateRadioGroup));
        baselineSurvey.setDisabilityAffectsSocial(getRadioButtonString(socialDisabilityAffectsRadioGroup));
        baselineSurvey.setDiscriminated(getRadioButtonString(socialDiscriminatedRadioGroup));
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