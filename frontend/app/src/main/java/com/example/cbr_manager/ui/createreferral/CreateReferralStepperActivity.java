package com.example.cbr_manager.ui.createreferral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cbr_manager.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateReferralStepperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_referral_stepper);
    }
}