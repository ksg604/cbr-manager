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
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = format.format(today);

       EditText textView = (EditText) view.findViewById(R.id.fragmentPreambleEditTextDate);
       textView.setText(todayString);

        ChipGroup cbrChipGroup = view.findViewById(R.id.cbrTypeChipGroup);
        TextView cbrTypeTextView = view.findViewById(R.id.cbrTypeTextView);
        cbrChipGroup.setVisibility(View.GONE);
        cbrTypeTextView.setVisibility(View.GONE);

        Chip cbrChip = view.findViewById(R.id.cbrChip);
//        if (!cbrChip.isChecked()) {
//            ((CreateVisitActivity) getActivity()).setCheckedHealthProvision(true);
//            ((CreateVisitActivity) getActivity()).setCheckedEducationProvision(true);
//            ((CreateVisitActivity) getActivity()).setCheckedSocialProvision(true);
//        }
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

        setupProvisionChipListeners(view);
    }

    private void setupProvisionChipListeners(View view) {
        Chip healthProvisionChip = view.findViewById(R.id.healthProvisionChip);
        Chip educationProvisionChip = view.findViewById(R.id.educationProvisionChip);
        Chip socialProvisionChip = view.findViewById(R.id.socialProvisionChip);

        if (!healthProvisionChip.isChecked()) {
            ((CreateVisitActivity) getActivity()).setCheckedHealthProvision(false);
        }
        if (!educationProvisionChip.isChecked()) {
            ((CreateVisitActivity) getActivity()).setCheckedEducationProvision(false);
        }
        if (!socialProvisionChip.isChecked()) {
            ((CreateVisitActivity) getActivity()).setCheckedSocialProvision(false);
        }

        healthProvisionChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (healthProvisionChip.isChecked()) {
                    ((CreateVisitActivity) getActivity()).setCheckedHealthProvision(true);
                }
            }
        });

        educationProvisionChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (educationProvisionChip.isChecked()) {
                    ((CreateVisitActivity) getActivity()).setCheckedEducationProvision(true);
                }
            }
        });

        socialProvisionChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socialProvisionChip.isChecked()) {
                    ((CreateVisitActivity) getActivity()).setCheckedSocialProvision(true);
                }
            }
        });
    }
}