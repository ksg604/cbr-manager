package com.example.cbr_manager.ui.createvisit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.ui.ClientViewModel;
import com.example.cbr_manager.ui.VisitViewModel;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.example.cbr_manager.ui.visitdetails.VisitDetailsActivity;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableSingleObserver;

@AndroidEntryPoint
public class CreateVisitStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    public StepperLayout createVisitStepperLayout;
    public int clientId = -1;
    public int userCreatorId = -1;
    public int visitId;
    public Visit formVisitObj;
    public Goal healthGoalObj;
    public Goal educationGoalObj;
    public Goal socialGoalObj;
    public Goal prevHealthGoalObj;
    public Goal prevEducationGoalObj;
    public Goal prevSocialGoalObj;
    private APIService apiService = APIService.getInstance();
    private Client client;
    public GenericStepperAdapter createVisitStepperAdapter;
    boolean healthVisible = false;
    boolean educationVisible = false;
    boolean socialVisible = false;
    public boolean healthGoalCreated = false;
    public boolean educationGoalCreated = false;
    public boolean socialGoalCreated = false;

    private AuthViewModel authViewModel;

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
        healthGoalObj = new Goal();
        educationGoalObj = new Goal();
        socialGoalObj = new Goal();
        createVisitStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
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

    private void modifyPreviousGoal(Goal goal) {
        if (apiService.isAuthenticated()) {
            apiService.goalService.modifyGoal(goal).enqueue(new Callback<Goal>() {
                @Override
                public void onResponse(Call<Goal> call, Response<Goal> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(CreateVisitStepperActivity.this, "Previous goal modified.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Goal> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onCompleted(View completeButton) {
        if (healthGoalCreated) {
            createNewGoals(healthGoalObj);
        }

        if (prevHealthGoalObj != null) {
            modifyPreviousGoal(prevHealthGoalObj);
        }

        if (educationGoalCreated) {
            createNewGoals(educationGoalObj);
        }

        if (prevEducationGoalObj != null) {
            modifyPreviousGoal(prevEducationGoalObj);
        }

        if (socialGoalCreated) {
            createNewGoals(socialGoalObj);
        }

        if (prevSocialGoalObj != null) {
            modifyPreviousGoal(prevSocialGoalObj);
        }
        clientViewModel.getClient(clientId).subscribe(new DisposableSingleObserver<Client>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Client client) {
                formVisitObj.setClientId(clientId);
                formVisitObj.setClient(client);
                visitViewModel.createVisit(formVisitObj).subscribe(new DisposableSingleObserver<Visit>() {
                    @SuppressLint("LongLogTag")
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
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Toast.makeText(CreateVisitStepperActivity.this, "Response error finding client.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void createNewGoals(Goal goal) {
        goal.setClientId(clientId);
        goal.setUserId(userCreatorId);
        apiService.goalService.createGoal(goal).enqueue(new Callback<Goal>() {
            @Override
            public void onResponse(Call<Goal> call, Response<Goal> response) {
            }
            @Override
            public void onFailure(Call<Goal> call, Throwable t) {
                Toast.makeText(CreateVisitStepperActivity.this, "Failed goal creation.", Toast.LENGTH_SHORT).show();
            }
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