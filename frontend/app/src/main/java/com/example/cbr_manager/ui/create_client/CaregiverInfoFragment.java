package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;

public class CaregiverInfoFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String caregiverPresent="";
    private EditText editTextCaregiverContactNumber;
    private int caregiverContactNumber=0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.activity_create_client_caregiver_info, container, false);

        radioGroup = view.findViewById(R.id.radioGroup2);

        editTextCaregiverContactNumber = (EditText) view.findViewById(R.id.editTextCaregiverContactNumber);

        Button nextButton = view.findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(getView());
                updateInfo(getView());
                ((CreateClientActivity) getActivity()).setViewPager(CreateClientActivity.CreateClientFragments.PHOTO.ordinal());
            }
        });
        Button prevButton = view.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateClientActivity) getActivity()).setViewPager(CreateClientActivity.CreateClientFragments.DISABILITY.ordinal());
            }
        });

        return view;
    }

    private void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(radioId);
        caregiverPresent = radioButton.getText().toString();
    }

    private void updateInfo(View v) {
        String caregiverContactNumber = editTextCaregiverContactNumber.getText().toString();
        ((CreateClientActivity) getActivity()).setCaregiverInfo(caregiverPresent, caregiverContactNumber);
    }
}
