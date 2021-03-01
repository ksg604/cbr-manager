package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;


public class ConsentFragment extends Fragment {

    TextInputEditText newDate;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText year, month, day;
    private String consent="";
    private String date="";
    private Client client;
    View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.activity_create_client_consent, container, false);
        client = ((CreateClientActivity) getActivity()).getClient();
        radioGroup = view.findViewById(R.id.radioGroup);
        year = view.findViewById(R.id.editTextYear);
        month = view.findViewById(R.id.editTextMonth);
        day = view.findViewById(R.id.editTextDay);

        newDate = view.findViewById(R.id.editTextDate);
        Calendar calendar = Calendar.getInstance();


        Button nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
                ((CreateClientActivity) getActivity()).setViewPager(CreateClientActivity.CreateClientFragments.VILLAGE_INFO.ordinal());
            }
        });

        return view;
    }

    private void updateInfo() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(radioId);
        consent = radioButton.getText().toString();
        date = year.getText().toString() + "/" + month.getText().toString() + "/" + day.getText().toString();
        ((CreateClientActivity) getActivity()).setConsentInfo(consent, date);
    }
}
