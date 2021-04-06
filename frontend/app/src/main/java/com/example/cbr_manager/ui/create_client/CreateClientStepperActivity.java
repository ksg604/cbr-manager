package com.example.cbr_manager.ui.create_client;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.ui.ClientViewModel;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsActivity;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class CreateClientStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    public Client formClientObj;
    public File photoFile;
    public Goal healthGoal, educationGoal, socialGoal;
    private StepperLayout CreateClientStepperLayout;
    private APIService apiService = APIService.getInstance();
    private static final String TAG = "CreateClientStepperActivity";

    private ClientViewModel clientViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepper);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        formClientObj = new Client();
        healthGoal = new Goal();
        educationGoal = new Goal();
        socialGoal = new Goal();
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        setTitle("Create a Client");

        CreateClientStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        CreateClientStepperLayout.setAdapter(setUpStepperAdapterWithFragments());
        CreateClientStepperLayout.setListener(this);
    }

    public GenericStepperAdapter setUpStepperAdapterWithFragments() {
        // order of these fragments added matters
        GenericStepperAdapter createClientStepperAdapter = new GenericStepperAdapter(getSupportFragmentManager(), this);

        createClientStepperAdapter.addFragment(new ConsentFragment(), "Consent");
        createClientStepperAdapter.addFragment(new PersonalInfoFragment(), "Personal Info");
        createClientStepperAdapter.addFragment(new DisabilityFragment(), "Disability Info");
        createClientStepperAdapter.addFragment(new CaregiverInfoFragment(), "Caregiver Info");
        createClientStepperAdapter.addFragment(new HealthRiskFragment(), "Health Risk");
        createClientStepperAdapter.addFragment(new EducationRiskFragment(), "Education Risk");
        createClientStepperAdapter.addFragment(new SocialRiskFragment(), "Social Risk");
        createClientStepperAdapter.addFragment(new GoalFragment(), "Goals");

        return createClientStepperAdapter;
    }

    private void onSubmitSuccess(Client client) {
        Intent intent = new Intent(this, ClientDetailsActivity.class);
        intent.putExtra(ClientDetailsActivity.KEY_CLIENT_ID, client.getId());
        startActivity(intent);
        this.finish();
    }

    private void submitGoalSurvey(Goal goal) {
        Call<Goal> call = apiService.goalService.createGoal(goal);
        call.enqueue(new Callback<Goal>() {
            @Override
            public void onResponse(Call<Goal> call, Response<Goal> response) {
                if (response.isSuccessful()) {
                    Goal goal = response.body();
                } else {
                    Snackbar.make( CreateClientStepperLayout, "Failed to create the client.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<Goal> call, Throwable t) {
                Snackbar.make( CreateClientStepperLayout, "Failed to create the goal. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void submitSurvey() {
        clientViewModel.createClient(formClientObj).subscribe(new DisposableSingleObserver<Client>() {
            @SuppressLint("LongLogTag")
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Client client) {
                Log.d(TAG, "onSuccess Client created: " + client.getId());
                if(photoFile.exists()) {
                    clientViewModel.uploadphoto(photoFile, client).subscribe(new DisposableCompletableObserver() {
                        @Override
                        public void onComplete() {
                            Log.d(TAG, "onSuccess Client photo uploaded: " + client.getId());
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                        }
                    });
                }
                if(healthGoal.getStatus().equalsIgnoreCase("Ongoing")) {
                    healthGoal.setClientId(client.getId());
                    submitGoalSurvey(healthGoal);
                }
                if(educationGoal.getStatus().equalsIgnoreCase("Ongoing")) {
                    educationGoal.setClientId(client.getId());
                    submitGoalSurvey(educationGoal);
                }
                if(socialGoal.getStatus().equalsIgnoreCase("Ongoing")) {
                    socialGoal.setClientId(client.getId());
                    submitGoalSurvey(socialGoal);
                }

                onSubmitSuccess(client);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Snackbar.make( CreateClientStepperLayout, "Failed to create the client.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onCompleted(View completeButton) {
        submitSurvey();
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

    public void setPhotoFile(File file) {
        photoFile = file;
    }
}