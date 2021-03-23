package com.example.cbr_manager.ui.baselinesurvey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.baseline_survey.BaselineSurvey;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;
import com.stepstone.stepper.adapter.StepAdapter;

import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaselineSurveyStepperActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    private StepperLayout baseLineSurveyStepperLayout;
    public BaselineSurvey formBaselineSurveyObj;
    private APIService apiService = APIService.getInstance();
    private int clientId = -1;
    private int userCreatorId = 1;
    private AuthViewModel authViewModel;
    public Client client;
    int age;

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stepper);
        setTitle("Baseline Survey");
        client = new Client();

        clientId = getIntent().getIntExtra("CLIENT_ID", -1);
        formBaselineSurveyObj = new BaselineSurvey();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        baseLineSurveyStepperLayout = (StepperLayout) findViewById(R.id.stepperLayout);
        getClientAge(baseLineSurveyStepperLayout);
//        baseLineSurveyStepperLayout.setAdapter(setupStepperAdapterWithFragments(age));
//        baseLineSurveyStepperLayout.setListener(this);
    }

    private void getClientAge(StepperLayout baseLineSurveyStepperLayout) {
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    if (response.isSuccessful()) {
                        client = response.body();
                        age = client.getAge();
                        Toast.makeText(BaselineSurveyStepperActivity.this, Integer.toString(age), Toast.LENGTH_SHORT).show();
                        setupStepperAdapterWithFragments(age);
                    } else {
                        Toast.makeText(BaselineSurveyStepperActivity.this, "Failed.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                    Toast.makeText(BaselineSurveyStepperActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getUserCreator() {
        authViewModel.getUser().subscribe(new DisposableSingleObserver<User>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull User user) {
                userCreatorId = user.getId();
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
            }
        });
    }

    private StepAdapter setupStepperAdapterWithFragments(int clientAge) {
        GenericStepperAdapter baselineStepperAdapter = new GenericStepperAdapter(getSupportFragmentManager(), this);
        baselineStepperAdapter.addFragment(new BaselineHealthFragment(), "Health");
        Toast.makeText(this,  Integer.toString(client.getAge()), Toast.LENGTH_SHORT).show();
        if (clientAge <= 18) {
            baselineStepperAdapter.addFragment(new BaselineEducationFragment(), "Education");
        }
        baselineStepperAdapter.addFragment(new BaselineSocialFragment(), "Social");
        if (clientAge >= 16) {
            baselineStepperAdapter.addFragment(new BaselineLivelihoodFragment(), "Livelihood");
        }
        baselineStepperAdapter.addFragment(new BaselineFoodNutritionFragment(), "Food & Nutrition");
        baselineStepperAdapter.addFragment(new BaselineEmpowermentFragment(), "Empowerment");
        baselineStepperAdapter.addFragment(new BaselineShelterCareFragment(), "Shelter & Care");

        baseLineSurveyStepperLayout.setAdapter(baselineStepperAdapter);
        baseLineSurveyStepperLayout.setListener(this);
        return baselineStepperAdapter;
    }

    @Override
    public void onCompleted(View completeButton) {
        submitSurvey();
    }

    private void submitSurvey() {
        if (apiService.isAuthenticated()) {
            formBaselineSurveyObj.setUserCreator(userCreatorId);
            formBaselineSurveyObj.setClient(clientId);
            apiService.baselineSurveyService.createBaselineSurvey(formBaselineSurveyObj).enqueue(new Callback<BaselineSurvey>() {
                @Override
                public void onResponse(Call<BaselineSurvey> call, Response<BaselineSurvey> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(BaselineSurveyStepperActivity.this, "Survey successfully submitted!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(BaselineSurveyStepperActivity.this, "Response error.", Toast.LENGTH_SHORT).show();
                        Log.d("Response", response.errorBody().toString());
                    }
                }

                @Override
                public void onFailure(Call<BaselineSurvey> call, Throwable t) {
                    Toast.makeText(BaselineSurveyStepperActivity.this, "Call error.", Toast.LENGTH_SHORT).show();
                }
            });
        }
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