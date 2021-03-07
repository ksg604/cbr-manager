package com.example.cbr_manager.ui.alert.create_alert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.alert.Alert;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertCreationFragment extends Fragment {

    private EditText editTextTitle, editTextBody;
    private Button buttonSubmit;
    private static final APIService apiService = APIService.getInstance();
    Alert alertToSend;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_alert_creation, container, false);

        editTextTitle = root.findViewById(R.id.editTextTitle);
        editTextBody = root.findViewById(R.id.editTextBody);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAlertCreateRequest();
                NavHostFragment.findNavController(AlertCreationFragment.this)
                        .navigate(R.id.nav_alert_list_to_action_nav_dashboard);
            }
        });
        return root;
    }


    public void sendAlertCreateRequest() {
        Alert alert = new Alert(editTextTitle.getText().toString(),editTextBody.getText().toString());
        Call<Alert> call = apiService.alertService.createAlert(alert);
        call.enqueue(new Callback<Alert>() {
            @Override
            public void onResponse(Call<Alert> call, Response<Alert> response) {
                if(response.isSuccessful()){

                } else{

                }
            }

        @Override
        public void onFailure(Call<Alert> call, Throwable t) {
            Snackbar.make(root, "Failed to create the alert. Please try again", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }); }
}