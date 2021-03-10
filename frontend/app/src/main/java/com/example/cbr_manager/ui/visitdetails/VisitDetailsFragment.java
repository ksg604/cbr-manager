package com.example.cbr_manager.ui.visitdetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsEditFragment;
import com.example.cbr_manager.ui.createreferral.CreateReferralActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;
import com.example.cbr_manager.ui.referral.referral_list.ReferralListFragment;
import com.example.cbr_manager.ui.visits.VisitsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitDetailsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIService apiService = APIService.getInstance();
    private View parentLayout;
    private String additionalInfo;
    private String location;
    private int villageNum;
    private String formattedDate;
    private int clientId = -1;

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

        View root = inflater.inflate(R.layout.fragment_visit_details, container, false);
        parentLayout = root.findViewById(android.R.id.content);

        Intent intent = getActivity().getIntent();
        this.additionalInfo = intent.getStringExtra("additionalInfo");
        this.formattedDate = intent.getStringExtra("formattedDate");
        this.location = intent.getStringExtra("location");
        this.clientId = intent.getIntExtra("clientId", -1);

        getClientInfo(clientId);

        setupButtons(root);
        setupTextViews(root);
        setupVectorImages(root);
        setupImageViews(root);
        setupBackImageViewButton(root);

        return root;
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

    private void getClientInfo(int clientId){
        apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                if(response.isSuccessful()){
                    Client client = response.body();

                    // Todo: dynamically set the client info here
                    setupNameTextView(client.getFullName());
                } else{
                    Snackbar.make(parentLayout, "Failed to get the client. Please try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
//                    try {
//                        JSONObject jObjError = new JSONObject(response.errorBody().string());
//                        Toast.makeText(this, jObjError.getJSONObject("error").getString("message"), Toast.LENGTH_LONG).show();
//                    } catch (Exception e) {
//                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Snackbar.make(parentLayout, "Failed to get the client. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupVectorImages(View root) {
        ImageView location = root.findViewById(R.id.profileLocationImageView);
        location.setImageResource(R.drawable.ic_place);
        ImageView date = root.findViewById(R.id.profileDateImageView);
        date.setImageResource(R.drawable.ic_date);
        ImageView additionalInfo = root.findViewById(R.id.profileAdditionalInfoImageView);
        additionalInfo.setImageResource(R.drawable.ic_info);
    }

    private void setupImageViews(View root) {
        ImageView displayPicture = root.findViewById(R.id.visitDetailsDisplayPictureImageView);
        displayPicture.setImageResource(R.drawable.client_details_placeholder);
    }

    private void setupTextViews(View root) {
        setupLocationTextView(root);
        setupAdditionalInfoTextView(additionalInfo, root);
        setupDateTextView(root);
        setupLocationTextView(root);
    }

    private void setupNameTextView(String fullName) {
        TextView nameTextView = (TextView)getView().findViewById(R.id.visitDetailsNameTextView);
        nameTextView.setText(fullName);
    }

    private void setupLocationTextView(View root) {
        TextView locationTextView = root.findViewById(R.id.visitDetailsLocationTextView);
        locationTextView.setText(this.location);
    }


    private void setupDateTextView(View root) {
        TextView dateTextView = root.findViewById(R.id.visitDetailsDateTextView);
        dateTextView.setText(this.formattedDate);
    }

    private void setupAdditionalInfoTextView(String additionalInfo, View root) {
        TextView additionalInfoTextView = root.findViewById(R.id.visitDetailsAdditionalInfoTextView);
        additionalInfoTextView.setText(additionalInfo);
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
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_visit_details, VisitDetailsEditFragment.class, null)
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
