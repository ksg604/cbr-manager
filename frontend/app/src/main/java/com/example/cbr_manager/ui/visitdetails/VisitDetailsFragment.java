package com.example.cbr_manager.ui.visitdetails;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.utils.Helper;
import com.google.android.material.snackbar.Snackbar;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private APIService apiService = APIService.getInstance();
    private View parentLayout;
    public static String KEY_VISIT_ID = "KEY_VISIT_ID";
    private int visitId = -1;

    public VisitDetailsFragment() {
        // Required empty public constructor
    }

    public static VisitDetailsFragment newInstance(String param1, String param2) {
        VisitDetailsFragment fragment = new VisitDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static VisitDetailsFragment newInstance(int visitId) {
        VisitDetailsFragment fragment = new VisitDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_VISIT_ID, visitId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_visit_details, container, false);
        parentLayout = root.findViewById(android.R.id.content);

        visitId = getArguments().getInt(KEY_VISIT_ID, -1);

        getVisitInfo(visitId);

        setupButtons(root);
        setupVectorImages(root);
        setupBackImageViewButton(root);

        return root;
    }

    private void getClientInfo(int clientId){
        apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                if(response.isSuccessful()){
                    Client client = response.body();

                    // Todo: dynamically set the client info here
                    setupNameTextView(client.getFullName());
                    setupImageViews(client.getPhotoURL());


                } else{
                    Snackbar.make(getView().findViewById(R.id.content), "Failed to get the client. Please try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });
    }

    private void getVisitInfo(int visitId) {
        apiService.visitService.getVisit(visitId).enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {

                if (response.isSuccessful()) {
                    Visit visit = response.body();

                    // Todo: dynamically set the client info here
                    Timestamp datetimeCreated = visit.getDatetimeCreated();
                    Format formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    String formattedDate = formatter.format(datetimeCreated);
                    setupDateTextView(formattedDate);

                    getClientInfo(visit.getClientId());
                    setupLocationTextView(visit.getLocationDropDown());
                    setupVillageNumTextView(visit.getVillageNoVisit().toString());
                    setupHealthTextViews(visit);
                    setupEducationTextViews(visit);
                    setupSocialTextViews(visit);

                } else {
                    Snackbar.make(parentLayout, "Failed to get the client. Please try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Visit> call, Throwable t) {

            }
        });
    }

    private void setupBackImageViewButton(View root) {
        ImageView backButtonImageView = root.findViewById(R.id.visitDetailsBackImageView);
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setUpTextView(int textViewID, String textValue){
        TextView textView = (TextView)getView().findViewById(textViewID);
        textView.setText(textValue);
    }

    private void setupVectorImages(View root) {
        ImageView location = root.findViewById(R.id.profileLocationImageView);
        location.setImageResource(R.drawable.ic_place);
        ImageView date = root.findViewById(R.id.profileDateImageView);
        date.setImageResource(R.drawable.ic_date);
    }

    private void setupImageViews(String imageURL) {
        ImageView displayPicture = (ImageView)getView().findViewById(R.id.visitDetailsDisplayPictureImageView);
        Helper.setImageViewFromURL(imageURL, displayPicture, R.drawable.client_details_placeholder);
    }

    private void setupNameTextView(String fullName) {
        setUpTextView(R.id.visitDetailsNameTextView, fullName);
    }

    private void setupLocationTextView(String location) {
        setUpTextView(R.id.visitDetailsLocationTextView, location);
    }

    private void setupDateTextView(String date) {
        setUpTextView(R.id.visitDetailsDateTextView, date);
    }

    private void setupVillageNumTextView(String villageNum) {
        setUpTextView(R.id.visitDetailsVillageNumTextView, villageNum);
    }

    private void setupHealthTextViews(Visit visit) {
        setUpTextView(R.id.visitDetailsWheelchairHealthTextView, visit.getWheelchairHealthProvisionText());
        setUpTextView(R.id.visitDetailsProstheticHealthTextView, visit.getProstheticHealthProvisionText());
        setUpTextView(R.id.visitDetailsOrthoticHealthTextView, visit.getOrthoticHealthProvisionText());
        setUpTextView(R.id.visitDetailsRepairsHealthTextView, visit.getRepairsHealthProvisionText());
        setUpTextView(R.id.visitDetailsReferralHealthTextView, visit.getReferralHealthProvisionText());
        setUpTextView(R.id.visitDetailsAdviceHealthTextView, visit.getAdviceHealthProvisionText());
        setUpTextView(R.id.visitDetailsAdvocacyHealthTextView, visit.getAdvocacyHealthProvisionText());
        setUpTextView(R.id.visitDetailsEncouragementHealthTextView, visit.getEncouragementHealthProvisionText());
        setUpTextView(R.id.visitDetailsConclusionHealthTextView, visit.getConclusionHealthProvision());
    }

    private void setupEducationTextViews(Visit visit) {
        setUpTextView(R.id.visitDetailsReferralEducationTextView, visit.getReferralHealthProvisionText());
        setUpTextView(R.id.visitDetailsAdviceEducationTextView, visit.getAdviceHealthProvisionText());
        setUpTextView(R.id.visitDetailsAdvocacyEducationTextView, visit.getAdvocacyHealthProvisionText());
        setUpTextView(R.id.visitDetailsEncouragementEducationTextView, visit.getEncouragementHealthProvisionText());
        setUpTextView(R.id.visitDetailsConclusionEducationTextView, visit.getConclusionHealthProvision());
    }

    private void setupSocialTextViews(Visit visit) {
        setUpTextView(R.id.visitDetailsReferralSocialTextView, visit.getReferralHealthProvisionText());
        setUpTextView(R.id.visitDetailsAdviceSocialTextView, visit.getAdviceHealthProvisionText());
        setUpTextView(R.id.visitDetailsAdvocacySocialTextView, visit.getAdvocacyHealthProvisionText());
        setUpTextView(R.id.visitDetailsEncouragementSocialTextView, visit.getEncouragementHealthProvisionText());
        setUpTextView(R.id.visitDetailsConclusionSocialTextView, visit.getConclusionHealthProvision());
    }

    private void setupButtons(View root) {
        setupBackButton(root);
        setupEditButton(root);
    }

    private void setupEditButton(View root) {
        ImageView editButton = root.findViewById(R.id.visitDetailsEditImageView);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putInt("visitId", visitId);
                VisitDetailsEditFragment visitDetailsEditFragment = new VisitDetailsEditFragment();
                visitDetailsEditFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_visit_details, visitDetailsEditFragment, null)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void setupBackButton(View root) {
        Button backButton = root.findViewById(R.id.visitDetailsBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}
