package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cbr_manager.R;
import com.google.android.material.chip.Chip;

public class HealthProvisionFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HealthProvisionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_health_provision, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.healthFragmentNextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HealthProvisionFragment.this)
                        .navigate(R.id.action_healthProvisionFragment_to_educationProvisionFragment);
            }
        });

        view.findViewById(R.id.healthFragmentPreviousButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HealthProvisionFragment.this)
                        .navigate(R.id.action_healthProvisionFragment_to_locationFragment);
            }
        });

        setupVisibilityHealthProvisions(view);
        setupVisibilityEducationProvisions(view);

    }

    private void setupVisibilityHealthProvisions(View view) {
        EditText wheelchairMT = view.findViewById(R.id.wheelchairTextMultiLine);
        Chip wheelchairChip = view.findViewById(R.id.wheelchairChip);
        TextView wheelchairTextView = view.findViewById(R.id.healthProvisionsWheelChairTextView);
        setChipListener(wheelchairChip, wheelchairMT, wheelchairTextView);

        EditText prostheticMultiTextView = view.findViewById(R.id.protheticTextMultiLine);
        Chip c2 = view.findViewById(R.id.prostheticChip);
        TextView protheticTextView = view.findViewById(R.id.protheticTextView);
        setChipListener(c2, prostheticMultiTextView, protheticTextView);

        EditText t3 = view.findViewById(R.id.orthoticTextMultiLine);
        Chip c3 = view.findViewById(R.id.orthoticChip);
        TextView orthoticTextView = view.findViewById(R.id.orthoticTextView);
        setChipListener(c3, t3, orthoticTextView);

        EditText t4 = view.findViewById(R.id.wheelchairRepairsTextMultiLine);
        Chip c4 = view.findViewById(R.id.wheelchairRepairsChip);
        TextView wheelchairRepairsTextView = view.findViewById(R.id.wheelchairRepairsTextView);
        setChipListener(c4, t4, wheelchairRepairsTextView);

        EditText t5 = view.findViewById(R.id.referralTextMultiLine5);
        Chip c5 = view.findViewById(R.id.referralChip);
        TextView referralTextView = view.findViewById(R.id.referralTextView);
        setChipListener(c5, t5, referralTextView);

        EditText t6 = view.findViewById(R.id.adviceTextMultiLine);
        Chip c6 = view.findViewById(R.id.adviceChip);
        TextView adviceTextView = view.findViewById(R.id.adviceTextView);
        setChipListener(c6, t6, adviceTextView);

        EditText t7 = view.findViewById(R.id.advocacyTextMultiLine);
        Chip c7 = view.findViewById(R.id.advocacyChip);
        TextView advocacyTextView = view.findViewById(R.id.advocacyTextView);
        setChipListener(c7, t7, advocacyTextView);

        EditText t8 = view.findViewById(R.id.encouragementTextMultiLine);
        Chip c8 = view.findViewById(R.id.encouragementChip);
        TextView encouragementTextView = view.findViewById(R.id.encouragementTextView);
        setChipListener(c8, t8, encouragementTextView);
    }

    private void setupVisibilityEducationProvisions(View view) {
        Chip adviceChip = view.findViewById(R.id.educationProvisionsAdviceChip);
        TextView adviceTV = view.findViewById(R.id.educationProvisionAdviceTextView);
        EditText adviceET = view.findViewById(R.id.educationProvisionAdviceTextMultiLine);
        setChipListener(adviceChip,adviceET,adviceTV);

        Chip advocacyChip = view.findViewById(R.id.educationProvisionsAdvocacyChip);
        TextView advocacyTV = view.findViewById(R.id.educationProvisionAdvocacyTextView);
        EditText advocacyET = view.findViewById(R.id.educationProvisionAdvocacyTextMultiLine);
        setChipListener(advocacyChip,advocacyET,advocacyTV);

        Chip referralChip = view.findViewById(R.id.educationProvisionsReferralChip);
        TextView referralTV = view.findViewById(R.id.educationProvisionReferralTextView);
        EditText referralET = view.findViewById(R.id.educationProvisionReferralTextMultiLine);
        setChipListener(referralChip,referralET,referralTV);

        Chip encourageChip = view.findViewById(R.id.educationProvisionsEncouragementChip);
        TextView encourageTV = view.findViewById(R.id.educationProvisionEncouragementTextView);
        EditText encourageET = view.findViewById(R.id.educationProvisionEncouragementTextMultiLine);
        setChipListener(encourageChip,encourageET,encourageTV);

    }

    private void setChipListener(Chip chip, EditText editText, TextView textView) {
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chip.isChecked()) {
                    editText.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    editText.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                }
            }
        });
    }
}