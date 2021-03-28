package com.example.cbr_manager.ui.createreferral;

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
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.ui.ReferralViewModel;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateReferralStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {
    private StepperLayout createReferralStepperLayout;
    public Referral newReferralObj;
    private APIService apiService = APIService.getInstance();
    private int clientId = -1;
    private int userCreatorId = 1;
    private AuthViewModel authViewModel;
    private ReferralViewModel referralViewModel;
    public Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepper);
        setTitle("Create Referral");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        clientId = getIntent().getIntExtra("CLIENT_ID", -1);
        newReferralObj = new Referral();
        createReferralStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);

        setupStepperAdapterWithFragments();
    }

    private void setupStepperAdapterWithFragments() {
        GenericStepperAdapter CreateReferralStepperAdapter = new GenericStepperAdapter(getSupportFragmentManager(), this);
        CreateReferralStepperAdapter.addFragment(new CreateReferralFragment(), "Referral");
        createReferralStepperLayout.setAdapter(CreateReferralStepperAdapter);
        createReferralStepperLayout.setListener(this);
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