package com.example.cbr_manager.ui.createvisit;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.ClientViewModel;
import com.example.cbr_manager.ui.VisitViewModel;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.example.cbr_manager.ui.visitdetails.VisitDetailsActivity;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableSingleObserver;

@AndroidEntryPoint
public class CreateVisitStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    public StepperLayout createVisitStepperLayout;
    public int clientId = -1;
    public int userCreatorId = -1;
    public int visitId;
    public Visit formVisitObj;
    private APIService apiService = APIService.getInstance();
    private Client client;
    public GenericStepperAdapter createVisitStepperAdapter;
    boolean healthVisible = false;
    boolean educationVisible = false;
    boolean socialVisible = false;

    private static final String TAG = "CreateVisitStepperActivity";

    private VisitViewModel visitViewModel;
    private ClientViewModel clientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepper);

        visitViewModel = new ViewModelProvider(this).get(VisitViewModel.class);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        setTitle("Create Visit");
        Intent intent = getIntent();
        clientId = intent.getIntExtra("clientId", -1);
        formVisitObj = new Visit();
        createVisitStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setupStepperAdapterWithFragments();
    }

    private void setupStepperAdapterWithFragments() {
        createVisitStepperAdapter = new GenericStepperAdapter(getSupportFragmentManager(), this);
        createVisitStepperAdapter.addFragment(new CreateVisitPurposeFragment(), "Purpose");
        createVisitStepperAdapter.addFragment(new CreateVisitLocationFragment(), "Location");
        createVisitStepperLayout.setAdapter(createVisitStepperAdapter);
        createVisitStepperLayout.setListener(this);
    }

    public void makePrivisionVisible(String title) {
        if (title.equals("Health") && !healthVisible) {
            createVisitStepperAdapter.addFragment(new CreateVisitHealthFragment(), "Health");
            healthVisible = true;
            createVisitStepperLayout.setAdapter(createVisitStepperAdapter);
            createVisitStepperAdapter.notifyDataSetChanged();
            return;
        } else if (title.equals("Education") && !educationVisible) {
            createVisitStepperAdapter.addFragment(new CreateVisitEducationFragment(), "Education");
            educationVisible = true;
            createVisitStepperLayout.setAdapter(createVisitStepperAdapter);
            createVisitStepperAdapter.notifyDataSetChanged();
            return;
        } else if (title.equals("Social") && !socialVisible) {
            createVisitStepperAdapter.addFragment(new CreateVisitSocialFragment(), "Social");
            socialVisible = true;
            createVisitStepperLayout.setAdapter(createVisitStepperAdapter);
            createVisitStepperAdapter.notifyDataSetChanged();
            return;
        }
    }

    public void makeProvisionInvisible(String title) {
        if (title.equals("Health")) {
            createVisitStepperAdapter.removeFragment("Health");
            healthVisible = false;
            createVisitStepperLayout.setAdapter(createVisitStepperAdapter);
            createVisitStepperAdapter.notifyDataSetChanged();
            return;
        } else if (title.equals("Education")) {
            createVisitStepperAdapter.removeFragment("Education");
            educationVisible = false;
            createVisitStepperLayout.setAdapter(createVisitStepperAdapter);
            createVisitStepperAdapter.notifyDataSetChanged();
            return;
        } else if (title.equals("Social")) {
            createVisitStepperAdapter.removeFragment("Social");
            socialVisible = false;
            createVisitStepperLayout.setAdapter(createVisitStepperAdapter);
            createVisitStepperAdapter.notifyDataSetChanged();
            return;
        }
    }

    @Override
    public void onCompleted(View completeButton) {

        clientViewModel.getClient(clientId).observe( this, client1 -> {
            formVisitObj.setClientId(clientId);
            formVisitObj.setClient(client);
            visitViewModel.createVisit(formVisitObj).subscribe(new DisposableSingleObserver<Visit>() {
                @Override
                public void onSuccess(@io.reactivex.annotations.NonNull Visit visit) {
                    visitId = visit.getId();
                    Log.d(TAG, "onSuccess Visit created: " + visitId);
                    Toast.makeText(CreateVisitStepperActivity.this, "Successfully created visit!", Toast.LENGTH_SHORT).show();
                    onSubmitSuccess();
                }

                @Override
                public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                    Toast.makeText(CreateVisitStepperActivity.this, "Response error creating visit. " + e.getMessage() , Toast.LENGTH_LONG).show();
                }
            });

        });
    }

    private void onSubmitSuccess() {
        Intent intent = new Intent(this, VisitDetailsActivity.class);
        intent.putExtra(VisitDetailsActivity.KEY_VISIT_ID, visitId);
        startActivity(intent);
        finish();
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