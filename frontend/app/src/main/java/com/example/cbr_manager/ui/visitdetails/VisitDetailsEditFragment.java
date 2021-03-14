package com.example.cbr_manager.ui.visitdetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.utils.Helper;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitDetailsEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIService apiService = APIService.getInstance();
    private View parentLayout;
    private String location="";
    private int visitId = -1;
    private Client currentClient;

    public VisitDetailsEditFragment() {
        // Required empty public constructor
    }

    public static VisitDetailsEditFragment newInstance(String param1, String param2) {
        VisitDetailsEditFragment fragment = new VisitDetailsEditFragment();
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

        View root = inflater.inflate(R.layout.fragment_visit_details_edit, container, false);
        parentLayout = root.findViewById(android.R.id.content);
        
        Bundle bundle = this.getArguments();
        visitId = bundle.getInt("visitId", -1);

        setupVectorImages(root);
        getVisitInfo(visitId, root);
        setupButtons(root);

        return root;
    }

    private void setupVectorImages(View root) {
        ImageView location = root.findViewById(R.id.profileLocationImageView);
        location.setImageResource(R.drawable.ic_place);
        ImageView date = root.findViewById(R.id.profileDateImageView);
        date.setImageResource(R.drawable.ic_date);
        ImageView additionalInfo = root.findViewById(R.id.profileAdditionalInfoImageView);
        additionalInfo.setImageResource(R.drawable.ic_info);
    }

    private void getVisitInfo(int visitId, View root) {
        apiService.visitService.getVisit(visitId).enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {
                Visit visit = response.body();
                setupLocationSpinner(root, visit.getLocationDropDown());
                setupEditTexts(visit, root);
                getClientInfo(visit.getClientId());
            }

            @Override
            public void onFailure(Call<Visit> call, Throwable t) {
            }
        });
    }

    private void setupLocationSpinner(View root, String locationDropDown) {
        String[] paths = {"BidiBidi Zone 1", "BidiBidi Zone 2", "BidiBidi Zone 3", "BidiBidi Zone 4", "BidiBidi Zone 5",
                "Palorinya Basecamp", "Palorinya Zone 1", "Palorinya Zone 2", "Palorinya Zone 3"};
        Spinner spinner = (Spinner) root.findViewById(R.id.visitDetailsEditLocationSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        int initialPosition=0;
        for(int i=0 ; i<paths.length ; i++) {
            if (paths[i].equalsIgnoreCase(locationDropDown)) {
                initialPosition = i;
                break;
            }
        }
        spinner.setSelection(initialPosition);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location = paths[position];
                Log.d("location", location);
                spinner.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                location = paths[0];
            }
        });
    }

    private void setupEditTexts(Visit visit, View root) {
        setupDate(visit.getDatetimeCreated().toString(), root);
        setupAdditionalInfo(visit.getAdditionalInfo(), root);
    }

    private void setupDate(String date, View root) {
        EditText dateEditText = root.findViewById(R.id.visitDetailsEditDate);
        dateEditText.setText(date);
    }

    private void setupAdditionalInfo(String additionalInfo, View root) {
        EditText additionalInfoEditText = root.findViewById(R.id.visitDetailsEditAdditionalInfo);
        additionalInfoEditText.setText(additionalInfo);
    }

    private void getClientInfo(int clientId){
        apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                if(response.isSuccessful()){
                    Client client = response.body();
                    setupCurrentClient(client);
                    setupNameTextView(client.getFullName());
                    setupImageViews(client.getPhotoURL());
                } else{
                    Snackbar.make(getView().findViewById(R.id.content), "Failed to get the client. Please try again", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Snackbar.make(getView().findViewById(R.id.content), "Failed to get the client. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void setupCurrentClient(Client client) {
        this.currentClient = client;
    }

    private void setupImageViews(String imageURL) {
        ImageView displayPicture = (ImageView)getView().findViewById(R.id.visitDetailsEditDisplayPictureImageView);
        Helper.setImageViewFromURL(imageURL, displayPicture, R.drawable.client_details_placeholder);
    }

    private void setupNameTextView(String fullName) {
        TextView visitDetailsEdit = (TextView)getView().findViewById(R.id.visitDetailsEditNameTextView);
        visitDetailsEdit.setText(fullName);
    }

    private void setupButtons(View root) {
        setupBackButton(root);
        setupSubmitButton(root);
    }

    private void setupSubmitButton(View root) {
        Button submitButton = root.findViewById(R.id.visitDetailsEditSubmitButton);
        submitButton.setOnClickListener(v -> {getAndUpdateVisit(visitId,root);});
    }

    private void getAndUpdateVisit(int visitId, View root) {
        EditText editDate = root.findViewById(R.id.visitDetailsEditDate);
        EditText editAdditionalInfo = root.findViewById(R.id.visitDetailsEditAdditionalInfo);

        apiService.visitService.getVisit(visitId).enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {
                Visit visit = response.body();
                visit.setClient(currentClient);
                visit.setLocationDropDown(location);
                visit.setDatetimeCreated(Timestamp.valueOf(editDate.getText().toString()));
                visit.setAdditionalInfo(editAdditionalInfo.getText().toString());
                modifyVisitInfo(visit);
            }
            @Override
            public void onFailure(Call<Visit> call, Throwable t) {

            }
        });
    }

    private void modifyVisitInfo(Visit visit) {
        apiService.visitService.modifyVisit(visit).enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {
                if(response.isSuccessful()) {
                    Snackbar.make(getView(), "Successfully updated user", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    Visit visit = response.body();
                } else{
                    Snackbar.make(getView(), "Failed to update user", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                getActivity().onBackPressed();
            }

            @Override
            public void onFailure(Call<Visit> call, Throwable t) {

            }
        });
    }

    private void setupBackButton(View root) {
        ImageView backButtonImageView = root.findViewById(R.id.visitDetailsBackImageView);
        backButtonImageView.setOnClickListener(v -> {getActivity().onBackPressed();});
    }
}
