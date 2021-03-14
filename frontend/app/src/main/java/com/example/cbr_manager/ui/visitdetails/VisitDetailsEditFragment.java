package com.example.cbr_manager.ui.visitdetails;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
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


        getVisitInfo(visitId, root);
        setupSubmitButton(root);

        return root;
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

        // Setup health provision details edit texts
        setupEditText(root.findViewById(R.id.visitDetailsEditVillageNumber), visit.getVillageNoVisit().toString());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthWheelchairProvision), visit.getWheelchairHealthProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthProstheticProvision), visit.getProstheticHealthProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthOrthoticProvision), visit.getOrthoticHealthProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthRepairsProvision), visit.getRepairsHealthProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthReferralProvision), visit.getReferralHealthProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthAdviceProvision), visit.getAdviceHealthProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthAdvocacyProvision), visit.getAdvocacyHealthProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthEncouragementProvision), visit.getEncouragementHealthProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditHealthConclusion), visit.getConclusionHealthProvision());

        // Setup education details provision edit texts
        setupEditText(root.findViewById(R.id.visitDetailsEditEducationReferralProvision), visit.getReferralEducationProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditEducationAdviceProvision), visit.getAdviceEducationProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditEducationAdvocacyProvision), visit.getAdvocacyEducationProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditEducationEncouragementProvision), visit.getEncouragementEducationProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditEducationConclusion), visit.getConclusionEducationProvision());

        // Setup social details provision edit texts
        setupEditText(root.findViewById(R.id.visitDetailsEditSocialReferralProvision), visit.getReferralSocialProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditSocialAdviceProvision), visit.getAdviceSocialProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditSocialAdvocacyProvision), visit.getAdvocacySocialProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditSocialEncouragementProvision), visit.getEncouragementSocialProvisionText());
        setupEditText(root.findViewById(R.id.visitDetailsEditSocialConclusion), visit.getConclusionSocialProvision());

        setupEditText(root.findViewById(R.id.visitDetailsEditAdditionalInfo), visit.getAdditionalInfo());

    }

    private void setupEditText(EditText editText, String editTextValue) {
        editText.setText(editTextValue);
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

    private void setupSubmitButton(View root) {
        Button submitButton = root.findViewById(R.id.visitDetailsEditSubmitButton);
        submitButton.setOnClickListener(v -> {getAndUpdateVisit(visitId,root);});
    }

    private void getAndUpdateVisit(int visitId, View root) {

        EditText editVillageNumber = root.findViewById(R.id.visitDetailsEditVillageNumber);

        EditText editHealthWheelChairProvision = root.findViewById(R.id.visitDetailsEditHealthWheelchairProvision);
        EditText editHealthProstheticProvision = root.findViewById(R.id.visitDetailsEditHealthProstheticProvision);
        EditText editHealthOrthoticProvision = root.findViewById(R.id.visitDetailsEditHealthOrthoticProvision);
        EditText editHealthRepairsProvision = root.findViewById(R.id.visitDetailsEditHealthRepairsProvision);
        EditText editHealthReferralProvision = root.findViewById(R.id.visitDetailsEditHealthReferralProvision);
        EditText editHealthAdviceProvision = root.findViewById(R.id.visitDetailsEditHealthAdviceProvision);
        EditText editHealthAdvocacyProvision = root.findViewById(R.id.visitDetailsEditHealthAdvocacyProvision);
        EditText editHealthEncouragementProvision = root.findViewById(R.id.visitDetailsEditHealthEncouragementProvision);
        EditText editHealthConclusionProvision = root.findViewById(R.id.visitDetailsEditHealthConclusion);

        EditText editEducationReferralProvision = root.findViewById(R.id.visitDetailsEditEducationReferralProvision);
        EditText editEducationAdviceProvision = root.findViewById(R.id.visitDetailsEditEducationAdviceProvision);
        EditText editEducationAdvocacyProvision = root.findViewById(R.id.visitDetailsEditEducationAdvocacyProvision);
        EditText editEducationEncouragementProvision = root.findViewById(R.id.visitDetailsEditEducationEncouragementProvision);
        EditText editEducationConclusionProvision = root.findViewById(R.id.visitDetailsEditEducationConclusion);


        EditText editSocialReferralProvision = root.findViewById(R.id.visitDetailsEditSocialReferralProvision);
        EditText editSocialAdviceProvision = root.findViewById(R.id.visitDetailsEditSocialAdviceProvision);
        EditText editSocialAdvocacyProvision = root.findViewById(R.id.visitDetailsEditSocialAdvocacyProvision);
        EditText editSocialEncouragementProvision = root.findViewById(R.id.visitDetailsEditSocialEncouragementProvision);
        EditText editSocialConclusionProvision = root.findViewById(R.id.visitDetailsEditSocialConclusion);

        EditText editAdditionalInfo = root.findViewById(R.id.visitDetailsEditAdditionalInfo);

        apiService.visitService.getVisit(visitId).enqueue(new Callback<Visit>() {
            @Override
            public void onResponse(Call<Visit> call, Response<Visit> response) {
                Visit visit = response.body();
                visit.setClient(currentClient);
                visit.setLocationDropDown(location);

                visit.setVillageNoVisit(Integer.parseInt(editVillageNumber.getText().toString()));
                visit.setWheelchairHealthProvisionText(editHealthWheelChairProvision.getText().toString());
                visit.setProstheticHealthProvisionText(editHealthProstheticProvision.getText().toString());



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


}
