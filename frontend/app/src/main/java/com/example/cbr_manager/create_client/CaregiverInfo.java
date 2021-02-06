package com.example.cbr_manager.create_client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class CaregiverInfo extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private boolean caregiverPresent;
    private EditText editTextCaregiverContactNumber;
    private String caregiverContactNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_client_caregiver_info);

        radioGroup = findViewById(R.id.radioGroup2);

        editTextCaregiverContactNumber = (EditText)findViewById(R.id.editTextCaregiverContactNumber);

        Button button = findViewById(R.id.nextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);
                updateInfo(v);
            }
        });
    }
    private void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String c = radioButton.getText().toString();
        if(c.equalsIgnoreCase("Yes")) {
            caregiverPresent = true;
        } else{
            caregiverPresent = false;
        }
    }
    private void updateInfo(View v) {
        caregiverContactNumber = editTextCaregiverContactNumber.getText().toString();
    }
}
