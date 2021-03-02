package com.example.cbr_manager.ui.create_client;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;


public class ConsentFragment extends Fragment {

    EditText newDate;
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

        setupDatePicker();
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

    private void setupDatePicker() {
        newDate = view.findViewById(R.id.editTextDate);
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        newDate.setText(year + "/" + (month + 1) + "/" + day);
//        newDate.setInputType(InputType.TYPE_NULL);
        newDate.setFocusable(false);
        newDate.setClickable(false);
        newDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                newDate.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    private void updateInfo() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = view.findViewById(radioId);
        consent = radioButton.getText().toString();
//        date = year.getText().toString() + "/" + month.getText().toString() + "/" + day.getText().toString();
        date = newDate.getText().toString().trim();
        ((CreateClientActivity) getActivity()).setConsentInfo(consent, date);
    }
}
