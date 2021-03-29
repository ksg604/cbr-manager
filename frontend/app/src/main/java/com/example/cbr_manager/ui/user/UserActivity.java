package com.example.cbr_manager.ui.user;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class UserActivity extends AppCompatActivity {

    public final static String TAG = "UserActivity";
    public final static String KEY_USER_ID = "USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Profile");


        int userID = getIntent().getIntExtra(KEY_USER_ID, -1);
        Log.d(TAG, "onCreate: " + userID);
    }
}