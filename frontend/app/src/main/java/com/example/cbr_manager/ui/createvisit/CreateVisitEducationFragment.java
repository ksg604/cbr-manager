package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.visit.Visit;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import static android.view.View.GONE;

public class CreateVisitEducationFragment extends Fragment implements Step {

    TextInputLayout adviceInput;
    TextInputLayout advocacyInput;
    TextInputLayout referralInput;
    TextInputLayout encouragementInput;
    TextInputLayout conclusionInput;
    Chip adviceChip;
    Chip advocacyChip;
    Chip referralChip;
    Chip encouragementChip;
    RadioGroup goalsMetRadioGroup;
    private View view;
    private Visit visit;

    public CreateVisitEducationFragment() {
        // Required empty public constructor
    }

    public static CreateVisitEducationFragment newInstance(String param1, String param2) {
        CreateVisitEducationFragment fragment = new CreateVisitEducationFragment();
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
        view = inflater.inflate(R.layout.fragment_create_visit_education, container, false);
        visit = ((CreateVisitStepperActivity) getActivity()).formVisitObj;
        initializeInputLayouts(view);
        initializeChips(view);
        initializeRadioGroups(view);
        setupInputLayoutVisibility();
        return view;
    }

    private void setupInputLayoutVisibility() {
        setChipListener(adviceChip, adviceInput);
        setChipListener(advocacyChip, advocacyInput);
        setChipListener(referralChip, referralInput);
        setChipListener(encouragementChip, encouragementInput);
    }

    private void initializeRadioGroups(View view) {
        goalsMetRadioGroup = view.findViewById(R.id.educationProvisionRadioGroup);
        goalsMetRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.educationProvisionConcludedRadioButton) {
                    conclusionInput.setVisibility(View.VISIBLE);
                } else {
                    conclusionInput.setVisibility(GONE);
                }
            }
        });
    }

    private void setChipListener(Chip chip, TextInputLayout textInputLayout) {
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chip.isChecked()) {
                    textInputLayout.setVisibility(View.VISIBLE);
                } else {
                    textInputLayout.setVisibility(GONE);
                }
            }
        });
    }

    private void initializeChips(View view) {
        adviceChip = view.findViewById(R.id.educationProvisionsAdviceChip);
        advocacyChip = view.findViewById(R.id.educationProvisionsAdvocacyChip);
        referralChip = view.findViewById(R.id.educationProvisionsReferralChip);
        encouragementChip = view.findViewById(R.id.educationProvisionsEncouragementChip);
    }

    private void initializeInputLayouts(View view) {
        adviceInput = view.findViewById(R.id.educationAdviceInputLayout);
        advocacyInput = view.findViewById(R.id.educationAdvocacyInputLayout);
        referralInput = view.findViewById(R.id.educationReferralInputLayout);
        encouragementInput = view.findViewById(R.id.educationEncouragementInputLayout);
        conclusionInput = view.findViewById(R.id.educationConclusionInputLayout);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateCreateClient();
        return null;
    }

    private void updateCreateClient() {
        visit.setAdviceEducationProvision(adviceChip.isChecked());
        visit.setAdvocacyEducationProvision(advocacyChip.isChecked());
        visit.setReferralEducationProvision(referralChip.isChecked());
        visit.setEncouragementEducationProvision(encouragementChip.isChecked());

        visit.setAdviceEducationProvisionText(getInputLayoutString(adviceInput));
        visit.setAdvocacyEducationProvisionText(getInputLayoutString(advocacyInput));
        visit.setReferralEducationProvisionText(getInputLayoutString(referralInput));
        visit.setEncouragementEducationProvisionText(getInputLayoutString(encouragementInput));

        visit.setConclusionEducationProvision(getInputLayoutString(conclusionInput));

        // TODO: RadioGroup goals
    }

    private String getInputLayoutString(TextInputLayout textInputLayout) {
        EditText editText  = textInputLayout.getEditText();
        return editText.getText().toString();
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}