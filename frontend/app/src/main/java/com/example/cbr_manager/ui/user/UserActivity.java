package com.example.cbr_manager.ui.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.ui.login.LoginActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserActivity extends AppCompatActivity {

    public final static String TAG = "UserActivity";
    public final static String KEY_USER_ID = "USER_ID";
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Profile");

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        int userID = getIntent().getIntExtra(KEY_USER_ID, -1);

        setUpLoginButton();
    }

    private void setUpLoginButton() {
        Button logoutBtn = findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmedLogout();
            }
        });
    }

    private void confirmedLogout() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Logging out");
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onLogout();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void onLogout() {
        authViewModel.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        // clears activity stack to start anew
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}