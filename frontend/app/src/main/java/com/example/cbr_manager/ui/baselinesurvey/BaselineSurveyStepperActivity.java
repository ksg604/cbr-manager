package com.example.cbr_manager.ui.baselinesurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.baseline_survey.BaselineSurvey;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.StepAdapter;

public class BaselineSurveyStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout baseLineSurveyStepperLayout;
    public BaselineSurvey formBaselineSurveyObj;
    private APIService apiService = APIService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepper);
        setTitle("Baseline Survey");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        baseLineSurveyStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        baseLineSurveyStepperLayout.setAdapter(setupStepperAdapterWithFragments());
        baseLineSurveyStepperLayout.setListener(this);
    }

    private StepAdapter setupStepperAdapterWithFragments() {
        GenericStepperAdapter baselineStepperAdapter = new GenericStepperAdapter(getSupportFragmentManager(), this);
        baselineStepperAdapter.addFragment(new BaselineHealthFragment(), "Health");
        baselineStepperAdapter.addFragment(new BaselineEducationFragment(), "Education");
        baselineStepperAdapter.addFragment(new BaselineSocialFragment(), "Social");
        baselineStepperAdapter.addFragment(new BaselineLivelihoodFragment(), "Livelihood");
        baselineStepperAdapter.addFragment(new BaselineFoodNutritionFragment(), "Food & Nutrition");
        baselineStepperAdapter.addFragment(new BaselineEmpowermentFragment(), "Empowerment");
        baselineStepperAdapter.addFragment(new BaselineShelterCareFragment(), "Shelter & Care");

        return baselineStepperAdapter;
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Leave page");
        alertDialogBuilder.setMessage("Are you sure you want to leave? Changes will not be saved.");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
}