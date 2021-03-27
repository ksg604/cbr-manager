package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.example.cbr_manager.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.w3c.dom.Text;

import static android.view.View.GONE;

public class CreateVisitSocialFragment extends Fragment implements Step {

    Chip adviceChip;
    Chip advocacyChip;
    Chip referralChip;
    Chip encouragementChip;
    TextInputLayout adviceInputLayout;
    TextInputLayout advocacyInputLayout;
    TextInputLayout referralInputLayout;
    TextInputLayout encouragementInputLayout;
    TextInputLayout conclusionInputLayout;
    RadioGroup goalMetRadioGroup;
    private View view;

    public CreateVisitSocialFragment() {
        // Required empty public constructor
    }

    public static CreateVisitSocialFragment newInstance(String param1, String param2) {
        CreateVisitSocialFragment fragment = new CreateVisitSocialFragment();
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
        view = inflater.inflate(R.layout.fragment_create_visit_social, container, false);
        initializeInputLayouts(view);
        initializeChips(view);
        initializeRadioGroups(view);
        setupInputLayoutVisibility();
        return view;
    }

    private void initializeRadioGroups(View view) {
        goalMetRadioGroup = view.findViewById(R.id.socialProvisionRadioGroup);
        goalMetRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.socialProvisionConcludedRadioButton) {
                    conclusionInputLayout.setVisibility(View.VISIBLE);
                } else {
                    conclusionInputLayout.setVisibility(GONE);
                }
            }
        });
    }

    private void setupInputLayoutVisibility() {
        setChipListener(adviceChip, adviceInputLayout);
        setChipListener(advocacyChip, advocacyInputLayout);
        setChipListener(referralChip, referralInputLayout);
        setChipListener(encouragementChip, encouragementInputLayout);
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
        adviceChip = view.findViewById(R.id.socialProvisionAdviceChip);
        advocacyChip = view.findViewById(R.id.socialProvisionAdvocacyChip);
        referralChip = view.findViewById(R.id.socialProvisionReferralChip);
        encouragementChip = view.findViewById(R.id.socialProvisionEncouragementChip);
    }

    private void initializeInputLayouts(View view) {
        adviceInputLayout = view.findViewById(R.id.socialAdviceInputLayout);
        advocacyInputLayout = view.findViewById(R.id.socialAdvocacyInputLayout);
        referralInputLayout = view.findViewById(R.id.socialReferralInputLayout);
        encouragementInputLayout = view.findViewById(R.id.socialEncouragementInputLayout);
        conclusionInputLayout = view.findViewById(R.id.socialConclusionInputLayout);
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