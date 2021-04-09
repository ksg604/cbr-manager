package com.example.cbr_manager.ui.clientdetails;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.cbr_manager.ui.ClientViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableCompletableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ClientDetailsEditFragment extends Fragment {

    private APIService apiService = APIService.getInstance();
    private View parentLayout;
    private Spinner genderSpinner;
    String gender="";
    Client localClient;
    private int clientId;
    private static final String[] paths = {"Male", "Female"};
    private ClientViewModel clientViewModel;


    public ClientDetailsEditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_client_details_edit, container, false);
        parentLayout = root.findViewById(android.R.id.content);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        Bundle bundle = this.getArguments();
        this.clientId = bundle.getInt("clientId", -1);
        this.localClient = new Client();

        setupGenderSpinner(root);
        setupEditTexts(clientId, root);
        setupButtons(root);


        return root;
    }

    private void modifyClientInfo(Client client) {

        clientViewModel.modifyClient(client).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Snackbar.make(getView(), "Successfully updated client", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                getActivity().onBackPressed();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Snackbar.make(getView(), "Failed to update client", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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

        localClient.setGender(gender);
        String [] clientName = editClientName.getText().toString().split(" ");
        localClient.setFirstName(clientName[0]);
        localClient.setLastName(clientName[1]);
        localClient.setAge(Integer.parseInt(editClientAge.getText().toString()));
        localClient.setLocation(editClientLocation.getText().toString());
        localClient.setEducationGoal(editClientEducation.getText().toString());
        localClient.setDisability(editClientDisability.getText().toString());
        localClient.setSocialGoal(editClientSocial.getText().toString());
        localClient.setHealthGoal(editClientHealth.getText().toString());
        localClient.setEducationRisk(Integer.parseInt((editClientEducationRisk.getText().toString())));
        localClient.setSocialRisk(Integer.parseInt(editClientSocialRisk.getText().toString()));
        localClient.setHealthRisk(Integer.parseInt(editClientHealthRisk.getText().toString()));
        modifyClientInfo(localClient);
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

        clientViewModel.getClient(clientId).observe(getViewLifecycleOwner(), observeClient -> {
            this.localClient = observeClient;
            String clientFirstName = observeClient.getFirstName();
            String clientLastName = observeClient.getLastName();
            editClientName.setText(clientFirstName + " " + clientLastName);
            editClientAge.setText(observeClient.getAge().toString());
            editClientLocation.setText(observeClient.getLocation());
            editClientEducation.setText(observeClient.getEducationGoal());
            editClientDisability.setText(observeClient.getDisability());
            editClientSocial.setText(observeClient.getSocialGoal());
            editClientHealth.setText(observeClient.getHealthGoal());
            editClientEducationRisk.setText(observeClient.getEducationRisk().toString());
            editClientSocialRisk.setText(observeClient.getSocialRisk().toString());
            editClientHealthRisk.setText(observeClient.getHealthRisk().toString());
        });
    }


    private void setupButtons(View root) {
        setupBackButton(root);
        setupSubmitButton(root);
    }

    private void setupSubmitButton(View root) {
        Intent intent = getActivity().getIntent();
        int clientId = this.clientId;

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