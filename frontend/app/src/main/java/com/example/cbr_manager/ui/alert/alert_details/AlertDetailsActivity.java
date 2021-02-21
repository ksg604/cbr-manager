package com.example.cbr_manager.ui.alert.alert_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertDetailsActivity extends AppCompatActivity {


    private APIService apiService = APIService.getInstance();
    private int alertId = -1;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);
        parentLayout = findViewById(android.R.id.content);

        Intent intent = getIntent();
        int alertId = intent.getIntExtra("alertId", -1);
        getAlertInfo(alertId);

        this.alertId = alertId;

        setupButtons();

    }

    private void getAlertInfo(int alertId){
        apiService.alertService.getAlert(alertId).enqueue(new Callback<Alert>() {
            @Override
            public void onResponse(Call<Alert> call, Response<Alert> response) {

                if(response.isSuccessful()){
                    Alert alert = response.body();

                    // Todo: dynamically set the alert info here
                    setupTitleTextView(alert.getTitle());
                    setupBodyTextView(alert.getBody());
                    alert.formatDate();
                    setupDateTextView("Date posted:  " + alert.getDate());
                } else{
                    Snackbar.make(parentLayout, "Failed to get the alert. Please try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Alert> call, Throwable t) {
                Snackbar.make(parentLayout, "Failed to get the alert. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupTitleTextView(String title) {
        TextView nameTextView = findViewById(R.id.textTitle);
        nameTextView.setText(title);
    }

    private void setupBodyTextView(String body) {
        TextView locationTextView = findViewById(R.id.textBody);
        locationTextView.setText(body);
    }

    private void setupDateTextView(String date) {
        TextView genderTextView = findViewById(R.id.textDate);
        genderTextView.setText(date);
    }



    private void setupButtons() {
        setupBackButton();
        setupMarkAsReadButton();
    }

    private void setupMarkAsReadButton() {
        Button newVisitButton = findViewById(R.id.buttonMarkAsRead);
        newVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: add function to mark email as read (requires local database)
            }
        });
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.buttonBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public int getAlertId() {
        return alertId;
    }


}