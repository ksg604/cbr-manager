package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;

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

        view.findViewById(R.id.preambleSubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavHostFragment.findNavController(PreambleFragment.this)
//                        .navigate(R.id.action_preambleFragment_to_locationFragment);
                Toast.makeText(getContext(), "Submitted.", Toast.LENGTH_SHORT).show();
            }
        });

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = format.format(today);

       EditText textView = (EditText) view.findViewById(R.id.fragmentPreambleEditTextDate);
       textView.setText(todayString);

       setupLocationSpinner(view);

        setupProvisionChipListeners(view);
        setupProvisionLayoutVisibility(view);

        setupVisibilityHealthProvisions(view);
        setupVisibilityEducationProvisions(view);
        setupVisibilitySocialProvisions(view);

        setupRadioGroupProvisionListeners(view);
    }

    private void setupLocationSpinner(View view) {
        Spinner spinner = (Spinner) view.findViewById(R.id.locationFragmentSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.locationsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void setupProvisionLayoutVisibility(View view) {

        ChipGroup cbrChipGroup = view.findViewById(R.id.cbrTypeChipGroup);
        TextView cbrTypeTextView = view.findViewById(R.id.cbrTypeTextView);
        cbrChipGroup.setVisibility(GONE);
        cbrTypeTextView.setVisibility(GONE);
        LinearLayout healthProvisionLayout = view.findViewById(R.id.healthProvisionsLayout);
        LinearLayout educationProvisionLayout = view.findViewById(R.id.educationProvisionsLayout);
        LinearLayout socialProvisionLayout = view.findViewById(R.id.socialProvisionsLayout);

        Chip healthProvisionChip = view.findViewById(R.id.healthProvisionChip);
        Chip educationProvisionChip = view.findViewById(R.id.educationProvisionChip);
        Chip socialProvisionChip = view.findViewById(R.id.socialProvisionChip);

        Chip cbrChip = view.findViewById(R.id.cbrChip);
        cbrChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cbrChip.isChecked()) {
                    healthProvisionLayout.setVisibility(GONE);
                    educationProvisionLayout.setVisibility(GONE);
                    socialProvisionLayout.setVisibility(GONE);
                    cbrChipGroup.setVisibility(View.VISIBLE);
                    cbrTypeTextView.setVisibility(View.VISIBLE);

                    setupProvisionLayoutChipListeners(healthProvisionChip, healthProvisionLayout);
                    setupProvisionLayoutChipListeners(educationProvisionChip, educationProvisionLayout);
                    setupProvisionLayoutChipListeners(socialProvisionChip, socialProvisionLayout);
                } else {
                    healthProvisionLayout.setVisibility(View.VISIBLE);
                    educationProvisionLayout.setVisibility(View.VISIBLE);
                    socialProvisionLayout.setVisibility(View.VISIBLE);
                    cbrChipGroup.setVisibility(GONE);
                    cbrTypeTextView.setVisibility(GONE);

                    healthProvisionChip.setChecked(false);
                    educationProvisionChip.setChecked(false);
                    socialProvisionChip.setChecked(false);
                }
            }

            private void setupProvisionLayoutChipListeners(Chip chip, LinearLayout linearLayout) {
                chip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (chip.isChecked()) {
                            linearLayout.setVisibility(View.VISIBLE);
                        } else {
                            linearLayout.setVisibility(GONE);
                        }
                    }
                });
            }
        });
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

    private void setupVisibilitySocialProvisions(View view) {
        Chip adviceChip = view.findViewById(R.id.socialProvisionAdviceChip);
        TextView adviceTV = view.findViewById(R.id.socialProvisionAdviceTextView);
        EditText adviceET = view.findViewById(R.id.socialProvisionAdviceTextMultiLine);
        setChipListener(adviceChip,adviceET,adviceTV);

        Chip advocacyChip = view.findViewById(R.id.socialProvisionAdvocacyChip);
        TextView advocacyTV = view.findViewById(R.id.socialProvisionAdvocacyTextView);
        EditText advocacyET = view.findViewById(R.id.socialProvisionAdvocacyMultiLineText);
        setChipListener(advocacyChip,advocacyET,advocacyTV);

        Chip referralChip = view.findViewById(R.id.socialProvisionReferralChip);
        TextView referralTV = view.findViewById(R.id.socialProvisionReferralTextView);
        EditText referralET = view.findViewById(R.id.socialProvisionReferralTextMultiLine);
        setChipListener(referralChip,referralET,referralTV);

        Chip encourageChip = view.findViewById(R.id.socialProvisionEncouragementChip);
        TextView encourageTV = view.findViewById(R.id.socialProvisionEncouragementTextView);
        EditText encourageET = view.findViewById(R.id.socialProvisionEncouragementTextMultiLine);
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
                    editText.setVisibility(GONE);
                    textView.setVisibility(GONE);
                }
            }
        });
    }

    private void setupRadioGroupProvisionListeners(View view) {
        RadioGroup healthRadioGroup = view.findViewById(R.id.healthProvisionsRadioGroup);
        RadioGroup educationRadioGroup = view.findViewById(R.id.educationProvisionRadioGroup);
        RadioGroup socialRadioGroup = view.findViewById(R.id.socialProvisionRadioGroup);

        healthRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.concludedHPRadioButton) {
                    view.findViewById(R.id.healthProvisionConclusionTextView).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.healthProvisionConclusionTextMultiLine).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.healthProvisionConclusionTextView).setVisibility(GONE);
                    view.findViewById(R.id.healthProvisionConclusionTextMultiLine).setVisibility(GONE);
                }
            }
        });

        educationRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.educationProvisionConcludedRadioButton) {
                    view.findViewById(R.id.educationProvisionConclusionTextView).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.educationProvisionConclusionTextMultiLine).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.educationProvisionConclusionTextView).setVisibility(GONE);
                    view.findViewById(R.id.educationProvisionConclusionTextMultiLine).setVisibility(GONE);
                }
            }
        });

        socialRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.socialProvisionConcludedRadioButton) {
                    view.findViewById(R.id.socialProvisionConclusionTextView).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.socialProvisionConclusionTextMultiLine).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.socialProvisionConclusionTextView).setVisibility(GONE);
                    view.findViewById(R.id.socialProvisionConclusionTextMultiLine).setVisibility(GONE);
                }
            }
        });
    }
}