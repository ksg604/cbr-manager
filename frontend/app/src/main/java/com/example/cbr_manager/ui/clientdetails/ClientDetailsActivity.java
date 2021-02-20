package com.example.cbr_manager.ui.clientdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;
import com.example.cbr_manager.utils.Helper;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;
import com.example.cbr_manager.ui.visits.VisitsPerClientFragment;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailsActivity extends AppCompatActivity {


    private APIService apiService = APIService.getInstance();
    private int clientId = -1;
    private View parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);
        parentLayout = findViewById(android.R.id.content);

        Intent intent = getIntent();
        int clientId = intent.getIntExtra("clientId", -1);
        getClientInfo(clientId);

        ImageView locationImageView = findViewById(R.id.profileLocationImageView);
        locationImageView.setImageResource(R.drawable.ic_place);
        this.clientId = clientId;

        setupButtons();
        setupTextViews();
        setupImageViews();
        setupVectorImages();

    }

    private void setupVectorImages() {
        ImageView age = findViewById(R.id.profileAgeImageView);
        age.setImageResource(R.drawable.ic_age);
        ImageView gender = findViewById(R.id.profileGenderImageView);
        gender.setImageResource(R.drawable.ic_person);
        ImageView disability = findViewById(R.id.profileDisabilityImageView);
        disability.setImageResource(R.drawable.ic_disable);
        ImageView education = findViewById(R.id.profileEducationImageView);
        education.setImageResource(R.drawable.ic_education);
        ImageView social = findViewById(R.id.profileSocialImageView);
        social.setImageResource(R.drawable.ic_social);
        ImageView health = findViewById(R.id.profileHealthImageView);
        health.setImageResource(R.drawable.ic_health);
        ImageView riskScore = findViewById(R.id.profileRiskImageView);
        riskScore.setImageResource(R.drawable.ic_risk);
    }

    private void getClientInfo(int clientId) {
        apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                if (response.isSuccessful()) {
                    Client client = response.body();

                    // Todo: dynamically set the client info here
                    setupNameTextView(client.getFullName());
                    setupImageViews(client.getPhotoURL());
                } else {
                    setupLocationTextView(client.getLocation());
                    setupAgeTextView(client.getAge().toString());
                    setupGenderTextView(client.getGender());
                    setupHealthTextView(client.getHealthGoal());
                    setupSocialTextView(client.getSocialGoal());
                    setupEducationTextView(client.getEducationGoal());
                    setupDisabilityTextView(client.getDisability());
                    setupRiskLevelTextView(client.getRiskScore().toString());
                } else{
                    Snackbar.make(parentLayout, "Failed to get the client. Please try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Snackbar.make(parentLayout, "Failed to get the client. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupImageViews(String imageURL) {
        ImageView displayPicture = findViewById(R.id.clientDetailsDisplayPictureImageView);
        Helper.setImageViewFromURL(imageURL, displayPicture);
    }

    private void setupTextViews() {
//        setupGenderTextView();
//        setupLocationTextView();
//        setupAgeTextView();
//        setupDisabilityTextView();
//        setupRiskLevelTextView();
//        setupHealthTextView();
//        setupEducationTextView();
//        setupSocialTextView();
    }

    private void setupNameTextView(String fullName) {
        TextView nameTextView = findViewById(R.id.clientDetailsNameTextView);
        // TODO: Fill this TextView with information from the backend
        nameTextView.setText(fullName);
    }

    private void setupLocationTextView(String location) {
        TextView locationTextView = findViewById(R.id.clientDetailsLocationTextView);
        locationTextView.setText(location);
    }

    private void setupGenderTextView(String gender) {
        TextView genderTextView = findViewById(R.id.clientDetailsGenderTextView);
        genderTextView.setText(gender);
    }

    private void setupAgeTextView(String age) {
        TextView ageTextView = findViewById(R.id.clientDetailsAgeTextView);
        ageTextView.setText(age);
    }

    private void setupDisabilityTextView(String disability) {
        TextView disabilityTextView = findViewById(R.id.clientDetailsDisabilityTextView);
        disabilityTextView.setText(disability);
    }

    private void setupRiskLevelTextView(String riskLevel) {
        TextView riskLevelTextView = findViewById(R.id.clientDetailsRiskLevelTextView);
        riskLevelTextView.setText(riskLevel);
    }

    private void setupHealthTextView(String health) {
        TextView healthTextView = findViewById(R.id.clientDetailsHealthTextView);
        healthTextView.setText(health);
    }

    private void setupEducationTextView(String education) {
        TextView educationTextView = findViewById(R.id.clientDetailsEducationTextView);
        educationTextView.setText(education);
    }

    private void setupSocialTextView(String social) {
        TextView socialTextView = findViewById(R.id.clientDetailsSocialTextView);
        socialTextView.setText(social);
    }

    private void setupButtons() {
        setupBackButton();
        setupNewVisitButton();
        setupSeeVisitsButton();

        ImageView backImageView = findViewById(R.id.clientDetailsBackImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void setupNewVisitButton() {
        Button newVisitButton = findViewById(R.id.clientDetailsNewVisitButton);
        newVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nameTextView = (TextView) findViewById(R.id.clientDetailsNameTextView);
                String name = nameTextView.getText().toString();
                Intent intent = new Intent(ClientDetailsActivity.this, CreateVisitActivity.class);
                intent.putExtra("clientId", clientId);
                startActivity(intent);
            }
        });
    }

    private void setupSeeVisitsButton() {
        Button newVisitButton = findViewById(R.id.clientDetailsSeeVisitsButton);
        newVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, new VisitsPerClientFragment()).commit();
            }
        });
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.clientDetailsBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public int getClientId() {
        return clientId;
    }


}