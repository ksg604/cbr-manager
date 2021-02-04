package com.example.cbr_manager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.auth.AuthToken;
import com.example.cbr_manager.service.auth.LoginUserPass;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button requestBtn = findViewById(R.id.request);
        Button responseBtn = findViewById(R.id.response);

        LoginUserPass cred = new LoginUserPass("user1", "password2021");
        APIService client = new APIService(cred);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.authenticate();
                Snackbar.make(v, "request", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        responseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthToken authToken = client.getAuthToken();

                if (authToken != null) {
                    Snackbar.make(v, client.getAuthToken().toString(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(v, "no request for the token has been done yet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });


    }
}