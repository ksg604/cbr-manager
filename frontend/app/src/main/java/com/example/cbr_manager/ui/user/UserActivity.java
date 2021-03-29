package com.example.cbr_manager.ui.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}