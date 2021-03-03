package com.example.cbr_manager.ui.clientselector;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cbr_manager.R;

public class ClientSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_selector);
        setTitle("Select a Client");
    }
}