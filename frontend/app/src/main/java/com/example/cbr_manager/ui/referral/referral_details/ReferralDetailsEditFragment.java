package com.example.cbr_manager.ui.referral.referral_details;

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

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralDetailsEditFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private APIService apiService = APIService.getInstance();
    private View parentLayout;
    private Spinner genderSpinner;
    String gender="";
    Client client;
    private static final String[] paths = {"Male", "Female"};


    public ReferralDetailsEditFragment() {
        // Required empty public constructor
    }

    public static ReferralDetailsEditFragment newInstance(String param1, String param2) {
        ReferralDetailsEditFragment fragment = new ReferralDetailsEditFragment();
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

        View root = inflater.inflate(R.layout.fragment_client_details_edit, container, false);
        parentLayout = root.findViewById(android.R.id.content);

        Intent intent = getActivity().getIntent();
        int clientId = intent.getIntExtra("clientId", -1);

        setupGenderSpinner(root);
        setupEditTexts(clientId, root);
        setupButtons(root);


        return root;
    }

    private void modifyClientInfo(Client client) {


        apiService.clientService.modifyClient(client).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Client client = response.body();
                Log.d("log",client.getFirstName());
                getActivity().onBackPressed();
            }
            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });
    }

    private void getAndModifyClient(int clientId, View root) {

        EditText editClientName = (EditText) root.findViewById(R.id.clientDetailsEditName);
        EditText editClientAge = (EditText) root.findViewById(R.id.clientDetailsEditAge);
        EditText editClientLocation = (EditText) root.findViewById(R.id.clientDetailsEditLocation);
        EditText editClientEducation = (EditText) root.findViewById(R.id.clientDetailsEditEducation);
        EditText editClientDisability = (EditText) root.findViewById(R.id.clientDetailsEditDisability);
        EditText editClientSocial = (EditText) root.findViewById(R.id.clientDetailsEditSocial);
        EditText editClientHealth = (EditText) root.findViewById(R.id.clientDetailsEditHealth);

        EditText editClientEducationRisk = (EditText) root.findViewById(R.id.clientDetailsEditEducationRiskLevel);
        EditText editClientSocialRisk = (EditText) root.findViewById(R.id.clientDetailsEditSocialRiskLevel);
        EditText editClientHealthRisk = (EditText) root.findViewById(R.id.clientDetailsEditHealthRiskLevel);

        apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Client client = response.body();
                client.setGender(gender);

                String [] clientName = editClientName.getText().toString().split(" ");
                client.setFirstName(clientName[0]);
                client.setLastName(clientName[1]);
                client.setAge(Integer.parseInt(editClientAge.getText().toString()));
                client.setLocation(editClientLocation.getText().toString());
                client.setEducationGoal(editClientEducation.getText().toString());
                client.setDisability(editClientDisability.getText().toString());
                client.setSocialGoal(editClientSocial.getText().toString());
                client.setHealthGoal(editClientHealth.getText().toString());
                client.setEducationRisk(Integer.parseInt((editClientEducationRisk.getText().toString())));
                client.setSocialRisk(Integer.parseInt(editClientSocialRisk.getText().toString()));
                client.setHealthRisk(Integer.parseInt(editClientHealthRisk.getText().toString()));
                modifyClientInfo(client);
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });
    }

    private void setupGenderSpinner(View root) {
        String[] paths = {"Male", "Female"};
        Spinner spinner = (Spinner) root.findViewById(R.id.clientDetailsEditGenderSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                gender = paths[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                gender = paths[0];
            }
        });
    }

    private void setupEditTexts(int clientId, View root) {
        EditText editClientName = (EditText) root.findViewById(R.id.clientDetailsEditName);
        EditText editClientAge = (EditText) root.findViewById(R.id.clientDetailsEditAge);
        EditText editClientLocation = (EditText) root.findViewById(R.id.clientDetailsEditLocation);
        EditText editClientEducation = (EditText) root.findViewById(R.id.clientDetailsEditEducation);
        EditText editClientDisability = (EditText) root.findViewById(R.id.clientDetailsEditDisability);
        EditText editClientSocial = (EditText) root.findViewById(R.id.clientDetailsEditSocial);
        EditText editClientHealth = (EditText) root.findViewById(R.id.clientDetailsEditHealth);

        EditText editClientEducationRisk = (EditText) root.findViewById(R.id.clientDetailsEditEducationRiskLevel);
        EditText editClientSocialRisk = (EditText) root.findViewById(R.id.clientDetailsEditSocialRiskLevel);
        EditText editClientHealthRisk = (EditText) root.findViewById(R.id.clientDetailsEditHealthRiskLevel);

        apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Client client = response.body();
                String clientFirstName = client.getFirstName();
                String clientLastName = client.getLastName();
                editClientName.setText(clientFirstName + " " + clientLastName);
                editClientAge.setText(client.getAge().toString());
                editClientLocation.setText(client.getLocation());
                editClientEducation.setText(client.getEducationGoal());
                editClientDisability.setText(client.getDisability());
                editClientSocial.setText(client.getSocialGoal());
                editClientHealth.setText(client.getHealthGoal());
                editClientEducationRisk.setText(client.getEducationRisk().toString());
                editClientSocialRisk.setText(client.getSocialRisk().toString());
                editClientHealthRisk.setText(client.getHealthRisk().toString());

            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });
    }



    private void setupButtons(View root) {
        setupBackButton(root);
        setupSubmitButton(root);
    }
    private void setupSubmitButton(View root) {
        Intent intent = getActivity().getIntent();
        int clientId = intent.getIntExtra("clientId", -1);

        Button submitButton = root.findViewById(R.id.clientDetailsEditSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndModifyClient(clientId, root);
            }
        });
    }

    private void setupBackButton(View root) {
        ImageView backButtonImageView = root.findViewById(R.id.clientDetailsEditBackImageView);
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}