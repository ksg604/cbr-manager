package com.example.cbr_manager.ui.alert.create_alert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.ui.AlertViewModel;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class AlertCreationFragment extends Fragment {

    private EditText editTextTitle, editTextBody;
    private Button buttonSubmit;
    private static final APIService apiService = APIService.getInstance();
    AlertViewModel alertViewModel;
    Alert alertToSend;
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_alert_creation, container, false);

        alertViewModel = new ViewModelProvider(this).get(AlertViewModel.class);
        editTextTitle = root.findViewById(R.id.editTextTitle);
        editTextBody = root.findViewById(R.id.editTextBody);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendAlertCreateRequest();

            }
        });
        return root;
    }


    public void sendAlertCreateRequest() {
        Alert alert = new Alert(editTextTitle.getText().toString(),editTextBody.getText().toString());
        alertViewModel.createAlert(alert).subscribe(new DisposableSingleObserver<Alert>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Alert alert) {
                NavHostFragment.findNavController(AlertCreationFragment.this)
                        .navigate(R.id.nav_alert_list);
            }
            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Snackbar.make(root, "Failed to create the alert. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}