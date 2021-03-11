package com.example.cbr_manager.ui.clientdetails;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

import java.util.Objects;

public class ClientDetailsActivity extends AppCompatActivity {
    public  static String KEY_CLIENT_ID = "KEY_CLIENT_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_details);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle("Client Details");

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int clientId = intent.getIntExtra(KEY_CLIENT_ID, -1);
            ClientDetailsFragment clientDetailsFragment = ClientDetailsFragment.newInstance(clientId);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_client_details, clientDetailsFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

}