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
import com.google.android.material.chip.ChipGroup;

public class PreambleFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public PreambleFragment() {
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
        return inflater.inflate(R.layout.fragment_preamble, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.preambleNextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(PreambleFragment.this)
                        .navigate(R.id.action_preambleFragment_to_locationFragment);
            }
        });

        ChipGroup cbrChipGroup = view.findViewById(R.id.cbrTypeChipGroup);
        TextView cbrTypeTextView = view.findViewById(R.id.cbrTypeTextView);
        cbrChipGroup.setVisibility(View.GONE);
        cbrTypeTextView.setVisibility(View.GONE);

        Chip cbrChip = view.findViewById(R.id.cbrChip);
        cbrChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbrChip.isChecked()) {
                    cbrChipGroup.setVisibility(View.VISIBLE);
                    cbrTypeTextView.setVisibility(View.VISIBLE);
                } else {
                    cbrChipGroup.setVisibility(View.GONE);
                    cbrTypeTextView.setVisibility(View.GONE);
                }
            }
        });
    }
}