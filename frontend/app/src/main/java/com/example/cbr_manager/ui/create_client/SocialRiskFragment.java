package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class SocialRiskFragment extends Fragment implements Step {

    private View view;
    private Client client;
    RadioGroup riskRadioGroup;
    EditText socialRequireEditText;
    final Integer CRITICAL_RISK = 5;
    final Integer HIGH_RISK = 3;
    final Integer MEDIUM_RISK = 2;
    final Integer LOW_RISK = 1;

    public SocialRiskFragment() {
        // Required empty public constructor
    }
    public static SocialRiskFragment newInstance(String param1, String param2) {
        SocialRiskFragment fragment = new SocialRiskFragment();
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
        view = inflater.inflate(R.layout.fragment_social_risk, container, false);
        client = ((CreateClientStepperActivity) getActivity()).formClientObj;
        riskRadioGroup = view.findViewById(R.id.socialRiskRatingRadioGroup);
        socialRequireEditText = view.findViewById(R.id.socialNeedsEditTextTextMultiLine);
        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        client.setSocialRisk(convertRiskRating(riskRadioGroup));
        client.setSocialRequire(socialRequireEditText.getText().toString());
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private Integer convertRiskRating(RadioGroup riskRadioGroup) {
        int radioButtonId = riskRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = getView().findViewById(radioButtonId);
        String risk = radioButton.getText().toString().trim().toLowerCase();
        switch (risk) {
            case "critical":
                return CRITICAL_RISK;
            case "high":
                return HIGH_RISK;
            case "medium":
                return MEDIUM_RISK;
            default:
                return LOW_RISK;
        }
    }
}