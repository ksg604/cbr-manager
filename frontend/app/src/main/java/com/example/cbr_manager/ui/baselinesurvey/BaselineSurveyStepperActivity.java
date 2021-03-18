package com.example.cbr_manager.ui.baselinesurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.cbr_manager.R;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

public class BaselineSurveyStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout baseLineSurveyStepperLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepper);
        setTitle("Baseline Survey");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onCompleted(View completeButton) {

    }

    @Override
    public void onError(VerificationError verificationError) {

    }

    @Override
    public void onStepSelected(int newStepPosition) {

    }

    @Override
    public void onReturn() {

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}