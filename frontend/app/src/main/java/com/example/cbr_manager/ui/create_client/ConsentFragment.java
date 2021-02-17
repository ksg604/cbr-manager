package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;


public class ConsentFragment extends Fragment {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private EditText year, month, day;
    private boolean consent;
    private String date="";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.activity_create_client_consent, container, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        Button nextButton = view.findViewById(R.id.nextButton);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(getView());
                checkDate(getView());
                ((CreateClientActivity) getActivity()).setViewPager(CreateClientActivity.CreateClientFragments.VILLAGE_INFO.ordinal());
            }
        });

        return view;
    }

    private void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = v.findViewById(radioId);
        String c = radioButton.getText().toString();
        if(c.equalsIgnoreCase("Yes")) {
            consent = true;
        } else{
            consent=false;
        }
    }

    private void checkDate(View v) {
        year = v.findViewById(R.id.editTextYear);
        month = v.findViewById(R.id.editTextMonth);
        day = v.findViewById(R.id.editTextDay);
        date = year.getText().toString() + "/" + month.getText().toString() + "/" + day.getText().toString();
    }

}
