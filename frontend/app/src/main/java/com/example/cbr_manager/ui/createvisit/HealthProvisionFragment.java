package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView textView = view.findViewById(R.id.wheelchairTextMultiLine);
        textView.setVisibility(View.GONE);
        Chip wheelchairChip = view.findViewById(R.id.wheelchairChip);
        setChipListener(wheelchairChip, textView);

        TextView prostheticTextView = view.findViewById(R.id.protheticTextMultiLine);
        prostheticTextView.setVisibility(View.GONE);
        Chip c2 = view.findViewById(R.id.prostheticChip);
        setChipListener(c2, prostheticTextView);

        TextView t3 = view.findViewById(R.id.orthoticTextMultiLine);
        t3.setVisibility(View.GONE);
        Chip c3 = view.findViewById(R.id.orthoticChip);
        setChipListener(c3, t3);

        TextView t4 = view.findViewById(R.id.wheelchairRepairsTextMultiLine);
        t4.setVisibility(View.GONE);
        Chip c4 = view.findViewById(R.id.wheelchairRepairsChip);
        setChipListener(c4, t4);

        TextView t5 = view.findViewById(R.id.referralTextMultiLine5);
        t5.setVisibility(View.GONE);
        Chip c5 = view.findViewById(R.id.referralChip);
        setChipListener(c5, t5);

        TextView t6 = view.findViewById(R.id.adviceTextMultiLine);
        t6.setVisibility(View.GONE);
        Chip c6 = view.findViewById(R.id.adviceChip);
        setChipListener(c6, t6);

        TextView t7 = view.findViewById(R.id.advocacyTextMultiLine);
        t7.setVisibility(View.GONE);
        Chip c7 = view.findViewById(R.id.advocacyChip);
        setChipListener(c7, t7);

        TextView t8 = view.findViewById(R.id.encouragementTextMultiLine);
        t8.setVisibility(View.GONE);
        Chip c8 = view.findViewById(R.id.encouragementChip);
        setChipListener(c8, t8);
    }

    private void setChipListener(Chip chip, TextView textView) {
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                textView.setVisibility(View.VISIBLE);
                if (chip.isChecked()) {
//                    chip.setChecked(false);
                    textView.setVisibility(View.VISIBLE);
                } else {
                    textView.setVisibility(View.GONE);
//                    chip.setChecked(true);
                }
            }
        });
    }
}