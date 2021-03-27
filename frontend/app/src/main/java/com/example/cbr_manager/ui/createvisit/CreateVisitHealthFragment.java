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
import android.widget.TextView;

import com.example.cbr_manager.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.w3c.dom.Text;

import static android.view.View.GONE;

public class CreateVisitHealthFragment extends Fragment implements Step {
    
    private View view;
    TextInputLayout wheelchairInput;
    TextInputLayout prostheticInput;
    TextInputLayout orthoticInput;
    TextInputLayout wheelchairRepairsInput;
    TextInputLayout referralInput;
    TextInputLayout adviceInput;
    TextInputLayout advocacyInput;
    TextInputLayout encouragementInput;
    TextInputLayout conclusionInput;
    Chip wheelchairChip;
    Chip prostheticChip;
    Chip orthoticChip;
    Chip wheelchairRepairsChip;
    Chip referralChip;
    Chip adviceChip;
    Chip advocacyChip;
    Chip encouragementChip;
    RadioGroup goalOutcomeRadioGroup;

    public CreateVisitHealthFragment() {
        // Required empty public constructor
    }

    public static CreateVisitHealthFragment newInstance(String param1, String param2) {
        CreateVisitHealthFragment fragment = new CreateVisitHealthFragment();
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
        view = inflater.inflate(R.layout.fragment_create_visit_health, container, false);
        
        initializeInputLayouts(view);
        initializeChips(view);
        initializeRadioGroups(view);
        setupInputLayoutVisibility(view);
        return view;
    }

    private void initializeRadioGroups(View view) {
        goalOutcomeRadioGroup = view.findViewById(R.id.healthProvisionsRadioGroup);
        goalOutcomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.concludedHPRadioButton) {
                    conclusionInput.setVisibility(View.VISIBLE);
                } else {
                    conclusionInput.setVisibility(GONE);
                }
            }
        });
    }

    private void setupInputLayoutVisibility(View view) {
        setChipListener(wheelchairChip, wheelchairInput);
        setChipListener(prostheticChip, prostheticInput);
        setChipListener(orthoticChip, orthoticInput);
        setChipListener(orthoticChip, orthoticInput);
        setChipListener(wheelchairRepairsChip, wheelchairRepairsInput);
        setChipListener(referralChip, referralInput);
        setChipListener(adviceChip, adviceInput);
        setChipListener(advocacyChip, advocacyInput);
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

    private void initializeInputLayouts(View view) {
        wheelchairInput = view.findViewById(R.id.healthWheelChairInputLayout);
        prostheticInput = view.findViewById(R.id.healthProstheticInputLayout);
        orthoticInput = view.findViewById(R.id.healthOrthoticInputLayout);
        wheelchairRepairsInput = view.findViewById(R.id.healthWheelchairRepairsInputLayout);
        referralInput = view.findViewById(R.id.healthReferralInputLayout);
        adviceInput = view.findViewById(R.id.healthAdviceInputLayout);
        advocacyInput = view.findViewById(R.id.healthAdvocacyInputLayout);
        encouragementInput = view.findViewById(R.id.healthEncouragementInputLayout);
        conclusionInput = view.findViewById(R.id.healthGoalConclusionInputLayout);
    }

    private void initializeChips(View view) {
        wheelchairChip = view.findViewById(R.id.wheelchairChip);
        prostheticChip = view.findViewById(R.id.prostheticChip);
        orthoticChip = view.findViewById(R.id.orthoticChip);
        wheelchairRepairsChip = view.findViewById(R.id.wheelchairRepairsChip);
        referralChip = view.findViewById(R.id.referralChip);
        adviceChip = view.findViewById(R.id.adviceChip);
        advocacyChip = view.findViewById(R.id.advocacyChip);
        encouragementChip = view.findViewById(R.id.encouragementChip);
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