package com.example.cbr_manager.ui.homepage;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class HomepageActivity extends AppCompatActivity {
    private ImageButton newClientButton, newVisitButton, dashboardButton;
    private ImageButton newReferralButton, clientListButton, syncButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Home Page");
        setContentView(R.layout.homepage);

        newClientButton = findViewById(R.id.newClientButton);
        newVisitButton = findViewById(R.id.newVisitButton);
        dashboardButton = findViewById(R.id.dashboardButton);
        newReferralButton = findViewById(R.id.newReferralButton);
        clientListButton = findViewById(R.id.clientListButton);
        syncButton = findViewById(R.id.syncButton);
    }
}
