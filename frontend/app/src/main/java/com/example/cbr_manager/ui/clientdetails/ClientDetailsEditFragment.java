package com.example.cbr_manager.ui.clientdetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClientDetailsEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClientDetailsEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private APIService apiService = APIService.getInstance();
    private View parentLayout;
    private Spinner genderSpinner;
    String gender="";
    private static final String[] paths = {"Male", "Female"};


    public ClientDetailsEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClientDetailsEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClientDetailsEditFragment newInstance(String param1, String param2) {
        ClientDetailsEditFragment fragment = new ClientDetailsEditFragment();
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
        setupButtons(root);
        setupVectorImages(root);

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

        return root;
    }

    private void modifyClientInfo(Client client) {
        apiService.clientService.modifyClient(client).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                if ( response.code() == 200 ) {
                    Toast.makeText(parentLayout.getContext(), "Client updated successfully", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });
    }

    private void getAndModifyClientInfo(int clientId, View root) {

        EditText editClientName = (EditText) root.findViewById(R.id.clientDetailsEditName);
        EditText editClientAge = (EditText) root.findViewById(R.id.clientDetailsEditAge);
        EditText editClientLocation = (EditText) root.findViewById(R.id.clientDetailsEditLocation);
        EditText editClientEducation = (EditText) root.findViewById(R.id.clientDetailsEditEducation);
        EditText editClientDisability = (EditText) root.findViewById(R.id.clientDetailsEditRiskLevel);
        EditText editClientRiskLevel = (EditText) root.findViewById(R.id.clientDetailsEditRiskLevel);
        EditText editClientSocial = (EditText) root.findViewById(R.id.clientDetailsEditSocial);
        EditText editClientHealth = (EditText) root.findViewById(R.id.clientDetailsEditHealth);

        apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                if (response.isSuccessful()) {
                    Client client = response.body();



                    String[] name = editClientName.getText().toString().split(" ");
                    client.setFirstName(name[0]);
                    client.setLastName(name[1]);
                    client.setAge(Integer.parseInt(editClientAge.getText().toString()));
                    client.setLocation(editClientLocation.getText().toString());
                    client.setEducationGoal(editClientEducation.getText().toString());
                    client.setDisability(editClientDisability.getText().toString());
                    client.setRiskScore(Integer.parseInt(editClientRiskLevel.getText().toString()));
                    client.setSocialGoal(editClientSocial.getText().toString());
                    client.setHealthGoal(editClientHealth.getText().toString());


                    modifyClientInfo(client);
                    Toast.makeText(parentLayout.getContext(), "Client updated successfully", Toast.LENGTH_SHORT).show();
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

    private void setupButtons(View root) {
        setupSubmitButton(root);
    }
    private void setupSubmitButton(View root) {
        Intent intent = getActivity().getIntent();
        int clientId = intent.getIntExtra("clientId", -1);

        Button submitButton = root.findViewById(R.id.clientDetailsEditSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndModifyClientInfo(clientId, root);
            }
        });
    }

    private void setupVectorImages(View root) {
        ImageView locationImageView = root.findViewById(R.id.profileLocationImageViewEdit);
        locationImageView.setImageResource(R.drawable.ic_place);
        ImageView age = root.findViewById(R.id.profileAgeImageViewEdit);
        age.setImageResource(R.drawable.ic_age);
        ImageView gender = root.findViewById(R.id.profileGenderImageViewEdit);
        gender.setImageResource(R.drawable.ic_person);
        ImageView disability = root.findViewById(R.id.profileDisabilityImageViewEdit);
        disability.setImageResource(R.drawable.ic_disable);
        ImageView education = root.findViewById(R.id.profileEducationImageViewEdit);
        education.setImageResource(R.drawable.ic_education);
        ImageView social = root.findViewById(R.id.profileSocialImageViewEdit);
        social.setImageResource(R.drawable.ic_social);
        ImageView health = root.findViewById(R.id.profileHealthImageViewEdit);
        health.setImageResource(R.drawable.ic_health);
        ImageView riskScore = root.findViewById(R.id.profileRiskImageViewEdit);
        riskScore.setImageResource(R.drawable.ic_risk);
    }




    // TODO: Implement form which edits client on the backend
}