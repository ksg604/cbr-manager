package com.example.cbr_manager.ui.create_client;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.cbr_manager.NavigationActivity;
import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.user.User;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PhotoFragment extends Fragment {
    Button cameraButton;
    private static final APIService apiService = APIService.getInstance();
    View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.activity_create_client_photo, container, false);

        cameraButton = view.findViewById(R.id.takePhotoButton);
        //TODO: Add Camera functionality

        Button submitButton = view.findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitSurvey();
            }
        });
        Button prevButton = view.findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CreateClientActivity) getActivity()).setViewPager(4);
            }
        });

        return view;
    }

    private void submitSurvey() {
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        startActivity(intent);
        Client client = ((CreateClientActivity) getActivity()).getClient();

        Call<Client> call = apiService.clientService.createClientManual(client);
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if(response.isSuccessful()){
                    Snackbar.make(view, "Successfully created the client.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else{
                    Snackbar.make(view, "Failed to create the client.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Snackbar.make(view, "Failed to create the client. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); }

}
