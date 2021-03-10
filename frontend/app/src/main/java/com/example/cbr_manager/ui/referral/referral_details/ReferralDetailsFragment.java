package com.example.cbr_manager.ui.referral.referral_details;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.ServiceDetails.PhysiotherapyServiceDetail;
import com.example.cbr_manager.ui.createreferral.CreateReferralActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;
import com.example.cbr_manager.ui.createvisit.NewVisitFragment;
import com.example.cbr_manager.ui.referral.referral_list.ReferralListFragment;
import com.example.cbr_manager.ui.visits.VisitsFragment;
import com.example.cbr_manager.utils.Helper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReferralDetailsFragment extends Fragment {

    private APIService apiService = APIService.getInstance();
    private int referralId = -1;
    private View parentLayout;

    public ReferralDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_referral_details, container, false);

        parentLayout = root.findViewById(android.R.id.content);

        Intent intent = getActivity().getIntent();
        int referralId = intent.getIntExtra("referralId", -1);
        getReferralInfo(referralId);

        ImageView locationImageView = root.findViewById(R.id.profileReferToImageView);
        locationImageView.setImageResource(R.drawable.ic_place);
        this.referralId = referralId;

        setupButtons(root);
        setupVectorImages(root);

        return root;
    }
    
    private void setupVectorImages(View root) {
        ImageView age = root.findViewById(R.id.profileAgeImageView);
        age.setImageResource(R.drawable.ic_age);
        ImageView gender = root.findViewById(R.id.profileGenderImageView);
        gender.setImageResource(R.drawable.ic_person);
        ImageView outcome = root.findViewById(R.id.profileOutcomeImageView);
        outcome.setImageResource(R.drawable.ic_disable);
        ImageView dateCreated = root.findViewById(R.id.profileDateCreatedImageView);
        dateCreated.setImageResource(R.drawable.ic_education);
        ImageView type = root.findViewById(R.id.profileTypeImageView);
        type.setImageResource(R.drawable.ic_health);
        ImageView riskScore = root.findViewById(R.id.profileRiskImageView);
        riskScore.setImageResource(R.drawable.ic_risk);
    }

    private void getReferralInfo(int referralId) {
        apiService.referralService.getReferral(referralId).enqueue(new Callback<Referral>() {
            @Override
            public void onResponse(Call<Referral> call, Response<Referral> response) {

                if (response.isSuccessful()) {
                    Referral referral = response.body();

                    setUpTextView(R.id.referralDetailsTypeTextView, referral.getServiceType());
                    setUpTextView(R.id.referralDetailsReferToTextView, referral.getRefer_to());
                    setUpTextView(R.id.referralDetailsStatusTextView, referral.getStatus());
                    setUpTextView(R.id.referralDetailsOutcomeTextView, referral.getOutcome());
                    setUpTextView(R.id.referralDetailsServiceDetailTextView, referral.getServiceDetail().getInfo());
                    setUpTextView(R.id.referralDetailsDateCreatedTextView, referral.getFormattedDate());
                    setupImageViews(referral.getPhotoURL());
                    setupClientTextView(referral.getClient());

                } else {
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

    private void setUpTextView(int textViewId, String text) {
        TextView textView = (TextView)getView().findViewById(textViewId);
        if(text.equals("")){
            text="None";
        }
        textView.setText(text);
    }

    private void setupClientTextView(int clientId) {
        TextView clientNameTextView = (TextView)getView().findViewById(R.id.referralDetailsClientTextView);

        if (apiService.isAuthenticated()) {
            apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    if (response.isSuccessful()) {
                        Client client = response.body();
                        clientNameTextView.setText(client.getFullName());
                    }
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {

                }
            });
        }
    }

    private void setupImageViews(String imageURL) {
        ImageView displayPicture = (ImageView)getView().findViewById(R.id.referralDetailsDisplayPictureImageView);
        Helper.setImageViewFromURL(imageURL, displayPicture, R.drawable.client_details_placeholder);
    }

    private void setupButtons(View root) {
        setupBackButton(root);
    }

    private void setupBackButton(View root) {
        ImageView backImageView = root.findViewById(R.id.referralDetailsBackImageView);
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    public int getReferralId() {
        return referralId;
    }



}