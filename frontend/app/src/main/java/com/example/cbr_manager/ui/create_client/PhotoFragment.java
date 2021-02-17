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


public class PhotoFragment extends Fragment {
    Button cameraButton;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.activity_create_client_photo, container, false);

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
    }

}
