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
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralDetailsEditFragment extends Fragment {

    private APIService apiService = APIService.getInstance();
    private View parentLayout;
    private Spinner statusSpinner;
    String status="";


    public ReferralDetailsEditFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View root = inflater.inflate(R.layout.fragment_referral_details_edit, container, false);
        parentLayout = root.findViewById(android.R.id.content);

        Intent intent = getActivity().getIntent();
        int referralId = intent.getIntExtra("referralId", -1);

        setupStatusSpinner(root);
        setupTexts(referralId, root);
        setupButtons(root);

        return root;
    }

    private void modifyReferralInfo(Referral referral) {

        apiService.referralService.updateReferral(referral).enqueue(new Callback<Referral>() {
            @Override
            public void onResponse(Call<Referral> call, Response<Referral> response) {
                getActivity().onBackPressed();
            }
            @Override
            public void onFailure(Call<Referral> call, Throwable t) {
            }
        });
    }

    private void getAndModifyReferral(int referralId, View root) {
        EditText editReferTo = (EditText) root.findViewById(R.id.referralDetailsReferToEditTextView);
        EditText editOutcome = (EditText) root.findViewById(R.id.referralDetailsOutcomeEditTextView);

        apiService.referralService.getReferral(referralId).enqueue(new Callback<Referral>() {
            @Override
            public void onResponse(Call<Referral> call, Response<Referral> response) {
                Referral referral = response.body();
                referral.setStatus(status);
                referral.setRefer_to(editReferTo.getText().toString());
                referral.setOutcome(editOutcome.getText().toString());
                modifyReferralInfo(referral);
            }

            @Override
            public void onFailure(Call<Referral> call, Throwable t) {

            }
        });
    }

    private void setupStatusSpinner(View root) {
        String[] paths = {"CREATED", "RESOLVED"};
        Spinner spinner = (Spinner) root.findViewById(R.id.referralDetailsEditStatusSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = paths[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                status = paths[0];
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

    private void setUpEditTextView(int editTextViewId, String text) {
        EditText editTextView = (EditText)getView().findViewById(editTextViewId);
        if(text.equals("")){
            text="None";
        }
        editTextView.setText(text);
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


    private void setupTexts(int referralId, View root) {
        apiService.referralService.getReferral(referralId).enqueue(new Callback<Referral>() {
            @Override
            public void onResponse(Call<Referral> call, Response<Referral> response) {
                Referral referral = response.body();
                setupClientTextView(referral.getClient());
                setUpTextView(R.id.referralDetailsTypeTextView, referral.getServiceType());
                setUpEditTextView(R.id.referralDetailsReferToEditTextView, referral.getRefer_to());
                setUpEditTextView(R.id.referralDetailsOutcomeEditTextView, referral.getOutcome());
                setUpTextView(R.id.referralDetailsServiceDetailTextView, referral.getServiceDetail().getInfo());
                setUpTextView(R.id.referralDetailsDateCreatedTextView, referral.getFormattedDate());
            }
            @Override
            public void onFailure(Call<Referral> call, Throwable t) {
            }
        });
    }



    private void setupButtons(View root) {
        setupBackButton(root);
        setupSubmitButton(root);
    }
    private void setupSubmitButton(View root) {
        Intent intent = getActivity().getIntent();
        int referralId = intent.getIntExtra("referralId", -1);

        Button submitButton = root.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndModifyReferral(referralId, root);
            }
        });
    }

    private void setupBackButton(View root) {
        ImageView backButtonImageView = root.findViewById(R.id.referralDetailsBackImageView);
        backButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }
}