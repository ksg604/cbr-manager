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
 * Use the {@link ReferralDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferralDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIService apiService = APIService.getInstance();
    private int referralId = -1;
    private View parentLayout;

    public ReferralDetailsFragment() {
        // Required empty public constructor
    }

    public static ReferralDetailsFragment newInstance(String param1, String param2) {
        ReferralDetailsFragment fragment = new ReferralDetailsFragment();
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
        ImageView social = root.findViewById(R.id.profileSocialImageView);
        social.setImageResource(R.drawable.ic_social);
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

                    // Todo: dynamically set the referral info here
                    setupTypeTextView(referral.getServiceType());
                    setupImageViews(referral.getPhotoURL());
                    setupReferToTextView(referral.getRefer_to());
                    setupStatusTextView(referral.getStatus());
                    setupDateCreatedTextView(referral.getFormattedDate());
                    setupOutcomeTextView(referral.getOutcome());
                    setupClientTextView(referral.getClient());
                    setupServiceDetailTextView(referral);

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

    private void setupImageViews(String imageURL) {
        ImageView displayPicture = (ImageView)getView().findViewById(R.id.referralDetailsDisplayPictureImageView);
        Helper.setImageViewFromURL(imageURL, displayPicture, R.drawable.client_details_placeholder);
    }

    private void setupTypeTextView(String type) {
        TextView typeTextView = (TextView)getView().findViewById(R.id.referralDetailsTypeTextView);
        // TODO: Fill this TextView with information from the backend
        typeTextView.setText(type);
    }

    private void setupReferToTextView(String location) {
        TextView locationTextView = (TextView)getView().findViewById(R.id.referralDetailsReferToTextView);
        locationTextView.setText(location);
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

    private void setupStatusTextView(String status) {
        TextView statusTextView = (TextView)getView().findViewById(R.id.referralDetailsStatusTextView);
        statusTextView.setText(status);
    }

    private void setupOutcomeTextView(String outcome) {
        TextView outcomeTextView = (TextView)getView().findViewById(R.id.referralDetailsOutcomeTextView);
        outcomeTextView.setText(outcome);
    }

    private void setupServiceDetailTextView(Referral referral) {
        TextView serviceDetailTextView = (TextView)getView().findViewById(R.id.referralDetailsServiceDetailTextView);
        serviceDetailTextView.setText(referral.getServiceDetail().getInfo());
    }

    private void setupDateCreatedTextView(String dateCreated) {
        TextView dateCreatedTextView = (TextView)getView().findViewById(R.id.referralDetailsDateCreatedTextView);
        dateCreatedTextView.setText(dateCreated);
    }

    private void setupSocialTextView(String social) {
        TextView socialTextView = (TextView)getView().findViewById(R.id.referralDetailsSocialTextView);
        socialTextView.setText(social);
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