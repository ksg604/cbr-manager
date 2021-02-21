package com.example.cbr_manager.ui.clientdetails;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class ClientDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

        if ( savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_client_details, ClientDetailsFragment.class, null)
                    .commit();

        }
    }



}