package com.example.cbr_manager.create_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.MainActivity;
import com.example.cbr_manager.R;


public class Photo extends AppCompatActivity {
    Button cameraButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_client_photo);

        cameraButton = findViewById(R.id.takePhotoButton);
        //TODO: Add Camera functionality

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSurvey();
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
    private void submitSurvey() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void prevSurveyPage() {
        Intent intent = new Intent(this, CaregiverInfo.class);
        startActivity(intent);
    }
}
