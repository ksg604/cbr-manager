package com.example.cbr_manager.create_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class PersonalInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    EditText editTextFirstName, editTextLastName, editTextAge, editTextContactNumber;
    Spinner spinner;
    String firstName, lastName, contactNumber;
    int age=0;
    char gender;
    private static final String[] paths = {"Male", "Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_client_personal_info);

        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);

        spinner = (Spinner)findViewById(R.id.gender_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PersonalInfo.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextContactNumber = (EditText) findViewById(R.id.editTextContactNumber);

        Button nextButton = findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo(v);
                nextSurveyPage();
            }
        });
        Button prevButton = findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevSurveyPage();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        gender = paths[position].charAt(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        gender = paths[0].charAt(0);
    }
    public void updateInfo(View v) {
        firstName = editTextFirstName.getText().toString();
        lastName = editTextLastName.getText().toString();
        String age_string = editTextAge.getText().toString();
        if(age_string.length()==0) {
            age=0;
        } else {
            age = Integer.parseInt(age_string);
        }
        contactNumber = editTextContactNumber.getText().toString();
    }
    private void nextSurveyPage() {
        Intent intent = new Intent(this, Disability.class);
        startActivity(intent);
    }
    private void prevSurveyPage() {
        Intent intent = new Intent(this, VillageInfo.class);
        startActivity(intent);
    }
}
