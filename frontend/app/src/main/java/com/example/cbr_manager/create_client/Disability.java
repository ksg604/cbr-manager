package com.example.cbr_manager.create_client;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class Disability extends AppCompatActivity {

    RadioButton[] buttons = new RadioButton[10];
    boolean[] disabilities_check = new boolean[10];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_client_disability);
        String txt;

        for(int i=0 ; i<10 ; i++) {
            txt = "radioButton" + i;
            int resourceId = this.getResources().getIdentifier(txt, "id", this.getPackageName());
            buttons[i] = (RadioButton) findViewById(resourceId);
        }

        Button button = findViewById(R.id.nextButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo(v);
            }
        });
    }

    public void updateInfo(View v) {
        for(int i=0 ; i<10 ; i++) {
            if(buttons[i].isChecked()) {
                disabilities_check[i] = true;
            } else {
                disabilities_check[i] = false;
            }
        }
    }
}
