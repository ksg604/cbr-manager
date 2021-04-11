package com.example.cbr_manager.ui.createreferral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.baseline_survey.BaselineSurvey;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.ui.ClientViewModel;
import com.example.cbr_manager.ui.ReferralViewModel;
import com.example.cbr_manager.ui.VisitViewModel;
import com.example.cbr_manager.ui.referral.referral_details.ReferralDetailsActivity;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class CreateReferralStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {
    private StepperLayout createReferralStepperLayout;
    public Referral formReferralObj;
    private APIService apiService = APIService.getInstance();
    public int userCreatorId = -1;
    public int clientId;
    private int referralId;
    public String imageFilePath = "";
    private AuthViewModel authViewModel;
    private static final String KEY_CLIENT_ID = "CLIENT_ID";
    public Client client;

    private ReferralViewModel referralViewModel;
    private ClientViewModel clientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepper);

        referralViewModel = new ViewModelProvider(this).get(ReferralViewModel.class);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        setTitle("Create Referral");
        clientId = getIntent().getIntExtra(KEY_CLIENT_ID, -1);
        formReferralObj = new Referral();
        createReferralStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.getUser().subscribe(new DisposableSingleObserver<User>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull User user) {
                userCreatorId = user.getId();
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
            }
        });

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
        clientViewModel.getClientAsSingle(clientId)
                .flatMap(clientResponse -> {
                    formReferralObj.setClientId(clientId);
                    formReferralObj.setClient(clientResponse);
                    return referralViewModel.createReferral(formReferralObj);
                })
                .subscribe(new DisposableSingleObserver<Referral>() {
                    @Override
                    public void onSuccess(@io.reactivex.annotations.NonNull Referral referral) {
                        referralId = referral.getId();
//                        File photoFile = new File(imageFilePath);
//                        if(photoFile.exists()) {
//                            referralViewModel.uploadphoto(photoFile, submittedReferral).subscribe(new DisposableCompletableObserver() {
//                                @Override
//                                public void onComplete() {
//                                }
//                                @Override
//                                public void onError(@io.reactivex.annotations.NonNull Throwable e) {
//                                }
//                            });
//                        }
                        Toast.makeText(CreateReferralStepperActivity.this, "Referral successfully created!", Toast.LENGTH_SHORT).show();
                        onSubmitSuccess();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Toast.makeText(CreateReferralStepperActivity.this, "Error creating referral.", Toast.LENGTH_SHORT).show();
                    }
        });
    }

    private void onSubmitSuccess() {
        Intent intent = new Intent(this, ReferralDetailsActivity.class);
        intent.putExtra("referralId", referralId);
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