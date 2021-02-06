package com.example.cbr_manager.create_client;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;


public class Consent extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText year, month, day;
    boolean consent;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_client_consent);
        TextView text = findViewById(R.id.textView3);

        radioGroup = findViewById(R.id.radioGroup);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkButton(v);
                checkDate(v);
                text.setText("Date:" + date);
            }
        });
    }

    public void checkButton(View v) {
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        String c = radioButton.getText().toString();
        if(c.equalsIgnoreCase("Yes")) {
            consent = true;
        } else{
            consent=false;
        }
    }

    public void checkDate(View v) {
        year = findViewById(R.id.editTextYear);
        month = findViewById(R.id.editTextMonth);
        day = findViewById(R.id.editTextDay);
        date = year.getText().toString() + "/" + month.getText().toString() + "/" + day.getText().toString();
    }
}
