package com.example.cbr_manager.ui.createvisit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class CreateVisitStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout createVisitStepperLayout;
    public int clientId = -1;
    public int userCreatorId = 1;
    public int visitId;
    public Visit formVisitObj;
    private APIService apiService = APIService.getInstance();
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepper);
        setTitle("Create Visit");
        Intent intent = getIntent();
        clientId = intent.getIntExtra("clientId", -1);
        formVisitObj = new Visit();
        createVisitStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupStepperAdapterWithFragments();
    }

    private void setupStepperAdapterWithFragments() {
        GenericStepperAdapter createVisitStepperAdapter = new GenericStepperAdapter(getSupportFragmentManager(), this);
        createVisitStepperAdapter.addFragment(new CreateVisitPurposeFragment(), "Purpose");
        createVisitStepperAdapter.addFragment(new CreateVisitLocationFragment(), "Location");
        createVisitStepperAdapter.addFragment(new CreateVisitHealthFragment(), "Health");
        createVisitStepperAdapter.addFragment(new CreateVisitEducationFragment(), "Education");
        createVisitStepperAdapter.addFragment(new CreateVisitSocialFragment(), "Social");

        createVisitStepperLayout.setAdapter(createVisitStepperAdapter);
        createVisitStepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    if (response.isSuccessful()) {
                        client = response.body();
//                        Visit visit = new Visit("", clientId, userCreatorId, client);
                        formVisitObj.setClientId(clientId);
                        formVisitObj.setUserId(userCreatorId);
                        formVisitObj.setClient(client);
                        formVisitObj.setCbrWorkerName("petertran");

                        Call<Visit> call1 = apiService.visitService.createVisit(formVisitObj);
                        call1.enqueue(new Callback<Visit>() {
                            @Override
                            public void onResponse(Call<Visit> call, Response<Visit> response) {
                                if (response.isSuccessful()) {
                                    visitId = response.body().getId();
                                    Toast.makeText(CreateVisitStepperActivity.this, "Successfully created visit!", Toast.LENGTH_SHORT).show();
                                    onSubmitSuccess();
                                } else {
                                    Toast.makeText(CreateVisitStepperActivity.this, "Response error creating visit.", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Visit> call, Throwable t) {
                                Toast.makeText(CreateVisitStepperActivity.this, "Error in creating new visit.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(CreateVisitStepperActivity.this, "Response error finding client.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Toast.makeText(CreateVisitStepperActivity.this, "Error finding client.", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void onSubmitSuccess() {
        finish(); // TODO: For now.
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