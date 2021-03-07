package com.example.cbr_manager.ui.clientdetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.createreferral.CreateReferralActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;
import com.example.cbr_manager.ui.visits.VisitsFragment;
import com.example.cbr_manager.utils.Helper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIService apiService = APIService.getInstance();
    private int clientId = -1;
    private View parentLayout;

    public ClientDetailsFragment() {
        // Required empty public constructor
    }

    public static ClientDetailsFragment newInstance(String param1, String param2) {
        ClientDetailsFragment fragment = new ClientDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_client_details, container, false);

        parentLayout = root.findViewById(android.R.id.content);

        Intent intent = getActivity().getIntent();
        int clientId = intent.getIntExtra("clientId", -1);
        getClientInfo(clientId);

        ImageView locationImageView = root.findViewById(R.id.profileLocationImageView);
        locationImageView.setImageResource(R.drawable.ic_place);
        this.clientId = clientId;

        setupButtons(root);
        setupVectorImages(root);
        setupBottomNavigationView(root);

        return root;
    }

    private void setupBottomNavigationView(View root) {
        BottomNavigationView clientDetailsNavigationView = root.findViewById(R.id.clientDetailsBottomNavigationView);
        clientDetailsNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {
                    case R.id.visitsFragment:
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .add(android.R.id.content, new VisitsFragment()).commit();
                        break;
                    case R.id.newVisitFragment:
                        Intent createVisitIntent = new Intent(getActivity(), CreateVisitActivity.class);
                        createVisitIntent.putExtra("clientId", clientId);
                        startActivity(createVisitIntent);
                        break;
                    case R.id.createReferralActivityClient:
                        // TODO: Navigate to create referral fragment instead of activity
                        Intent createReferralIntent = new Intent(getActivity(), CreateReferralActivity.class);
                        createReferralIntent.putExtra("CLIENT_ID", clientId);
                        startActivity(createReferralIntent);
                        break;
                }
                return false;
            }
        });
    }


    private void setupVectorImages(View root) {
        ImageView age = root.findViewById(R.id.profileAgeImageView);
        age.setImageResource(R.drawable.ic_age);
        ImageView gender = root.findViewById(R.id.profileGenderImageView);
        gender.setImageResource(R.drawable.ic_person);
        ImageView disability = root.findViewById(R.id.profileDisabilityImageView);
        disability.setImageResource(R.drawable.ic_disable);
        ImageView education = root.findViewById(R.id.profileEducationGoalImageView);
        education.setImageResource(R.drawable.ic_education);
        ImageView social = root.findViewById(R.id.profileSocialImageView);
        social.setImageResource(R.drawable.ic_social);
        ImageView health = root.findViewById(R.id.profileHealthGoalImageView);
        health.setImageResource(R.drawable.ic_health);
        ImageView riskScore = root.findViewById(R.id.profileRiskImageView);
        riskScore.setImageResource(R.drawable.ic_risk);

        ImageView educationRiskScore = root.findViewById(R.id.profileEducationRiskImageView);
        educationRiskScore.setImageResource(R.drawable.ic_risk);

        ImageView socialRiskScore = root.findViewById(R.id.profileSocialRiskImageView);
        socialRiskScore.setImageResource(R.drawable.ic_risk);

        ImageView healthRiskScore = root.findViewById(R.id.profileHealthRiskImageView);
        healthRiskScore.setImageResource(R.drawable.ic_risk);
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

                    setupLocationTextView(client.getLocation());
                    setupAgeTextView(client.getAge().toString());
                    setupGenderTextView(client.getGender());
                    setupHealthTextView(client.getHealthGoal());
                    setupSocialTextView(client.getSocialGoal());
                    setupEducationTextView(client.getEducationGoal());
                    setupEducationRiskTextView(client.getEducationRisk().toString());
                    setupSocialRiskTextView(client.getSocialRisk().toString());
                    setupHealthRiskTextView(client.getHealthRisk().toString());
                    setupDisabilityTextView(client.getDisability());
                    setupRiskLevelTextView(client.getRiskScore().toString());
                } else {
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
        ImageView displayPicture = (ImageView)getView().findViewById(R.id.clientDetailsDisplayPictureImageView);
        Helper.setImageViewFromURL(imageURL, displayPicture, R.drawable.client_details_placeholder);
    }

    private void setupNameTextView(String fullName) {
        TextView nameTextView = (TextView)getView().findViewById(R.id.clientDetailsNameTextView);
        // TODO: Fill this TextView with information from the backend
        nameTextView.setText(fullName);
    }

    private void setupLocationTextView(String location) {
        TextView locationTextView = (TextView)getView().findViewById(R.id.clientDetailsLocationTextView);
        locationTextView.setText(location);
    }

    private void setupGenderTextView(String gender) {
        TextView genderTextView = (TextView)getView().findViewById(R.id.clientDetailsGenderTextView);
        genderTextView.setText(gender);
    }

    private void setupAgeTextView(String age) {
        TextView ageTextView = (TextView)getView().findViewById(R.id.clientDetailsAgeTextView);
        ageTextView.setText(age);
    }

    private void setupDisabilityTextView(String disability) {
        TextView disabilityTextView = (TextView)getView().findViewById(R.id.clientDetailsDisabilityTextView);
        disabilityTextView.setText(disability);
    }

    private void setupRiskLevelTextView(String riskLevel) {
        TextView riskLevelTextView = (TextView)getView().findViewById(R.id.clientDetailsRiskLevelTextView);
        riskLevelTextView.setText(riskLevel);
    }

    private void setupHealthTextView(String health) {
        TextView healthTextView = (TextView)getView().findViewById(R.id.clientDetailsHealthGoalTextView);
        healthTextView.setText(health);
    }

    private void setupEducationTextView(String education) {
        TextView educationTextView = (TextView)getView().findViewById(R.id.clientDetailsEducationGoalTextView);
        educationTextView.setText(education);
    }

    private void setupSocialTextView(String social) {
        TextView socialTextView = (TextView)getView().findViewById(R.id.clientDetailsSocialGoalTextView);
        socialTextView.setText(social);
    }

    private void setupHealthRiskTextView(String healthRisk) {
        TextView healthRiskTextView = (TextView)getView().findViewById(R.id.clientDetailsHealthRiskLevelTextView);
        healthRiskTextView.setText(healthRisk);
    }

    private void setupEducationRiskTextView(String educationRisk) {
        TextView educationRiskTextView = (TextView)getView().findViewById(R.id.clientDetailsEducationRiskLevelTextView);
        educationRiskTextView.setText(educationRisk);
    }
    private void setupSocialRiskTextView(String socialRisk) {
        TextView socialRiskTextView = (TextView)getView().findViewById(R.id.clientDetailsSocialRiskLevelTextView);
        socialRiskTextView.setText(socialRisk);
    }

    private void setupButtons(View root) {

        setupEditButton(root);
        setupBackButton(root);
    }

    private void setupEditButton(View root) {

        ImageView editButtonImageView = root.findViewById(R.id.clientDetailsEditImageView);

        editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_client_details, ClientDetailsEditFragment.class, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setupBackButton(View root) {
        ImageView backImageView = root.findViewById(R.id.clientDetailsBackImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public int getClientId() {
        return clientId;
    }

}