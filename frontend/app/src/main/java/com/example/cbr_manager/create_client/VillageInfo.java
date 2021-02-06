package com.example.cbr_manager.create_client;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class VillageInfo extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;
    private static final String[] paths = {"BidiBidi Zone 1", "BidiBidi Zone 2", "BidiBidi Zone 3", "BidiBidi Zone 4", "BidiBidi Zone 5",
            "Palorinya Basecamp", "Palorinya Zone 1", "Palorinya Zone 2", "Palorinya Zone 3"};
    private EditText editTextId, editTextVillageNum;

    String id, location, villageNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_client_village_info);

        editTextId = (EditText)findViewById(R.id.editTextFirstName);

        spinner = (Spinner)findViewById(R.id.location_dropdown);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(VillageInfo.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        editTextVillageNum = (EditText)findViewById(R.id.editTextVillageNum);

        Button button = findViewById(R.id.nextButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo(v);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        location = paths[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        location = "";
    }

    private void updateInfo(View v) {
        id = editTextId.getText().toString();
        villageNumber = editTextVillageNum.getText().toString();
    }
}
