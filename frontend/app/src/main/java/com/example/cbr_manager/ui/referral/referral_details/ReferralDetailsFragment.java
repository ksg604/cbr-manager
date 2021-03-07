package com.example.cbr_manager.ui.referral.referral_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.referral.Referral;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralDetailsFragment extends AppCompatActivity {


    private APIService apiService = APIService.getInstance();
    private int referralId = -1;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_details);
        parentLayout = findViewById(android.R.id.content);

        Intent intent = getIntent();
        int referralId = intent.getIntExtra("referralId", -1);
        getReferralInfo(referralId);

        this.referralId = referralId;

        setupButtons();

    }

    private void getReferralInfo(int referralId){
        apiService.referralService.getReferral(referralId).enqueue(new Callback<Referral>() {
            @Override
            public void onResponse(Call<Referral> call, Response<Referral> response) {

                if(response.isSuccessful()){
                    Referral referral = response.body();

                    // Todo: dynamically set the referral info here
                } else{
                    Snackbar.make(parentLayout, "Failed to get the referral. Please try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Referral> call, Throwable t) {
                Snackbar.make(parentLayout, "Failed to get the referral. Please try again", Snackbar.LENGTH_LONG)
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

    public int getReferralId() {
        return referralId;
    }


}