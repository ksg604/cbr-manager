package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.ui.AuthViewModel;
import com.google.android.material.chip.Chip;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;


import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableSingleObserver;

import static com.example.cbr_manager.ui.create_client.ValidatorHelper.validateStepperTextViewNotNull;

@AndroidEntryPoint
public class GoalFragment extends Fragment implements Step {

    private Goal healthGoal, educationGoal, socialGoal;
    private Chip healthChip, educationChip, socialChip;
    private CardView healthCard, educationCard, socialCard;
    private boolean healthVisible = false, educationVisible = false, socialVisible = false;
    private EditText healthGoalTitle, educationGoalTitle, socialGoalTitle;
    private EditText healthGoalDescription, educationGoalDescription, socialGoalDescription;
    private TextView errorTextView;
    private AuthViewModel authViewModel;
    private int userId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        authViewModel.getUser().subscribe(new DisposableSingleObserver<User>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull User user) {
                userId = user.getId();
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Toast.makeText(getContext(), "User response error. " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.activity_create_client_goals, container, false);

        healthGoal = ((CreateClientStepperActivity) getActivity()).healthGoal;
        educationGoal = ((CreateClientStepperActivity) getActivity()).educationGoal;
        socialGoal = ((CreateClientStepperActivity) getActivity()).socialGoal;

        initializeChips(view);
        initializeCards(view);
        initializeEditTexts(view);
        setupCardVisibility();
        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        try {
            if(healthVisible) {
                validateStepperTextViewNotNull(healthGoalTitle, "Required");
                validateStepperTextViewNotNull(healthGoalDescription, "Required");
            }
            if(educationVisible) {
                validateStepperTextViewNotNull(educationGoalTitle, "Required");
                validateStepperTextViewNotNull(educationGoalDescription, "Required");
            }
            if(socialVisible) {
                validateStepperTextViewNotNull(socialGoalTitle, "Required");
                validateStepperTextViewNotNull(socialGoalDescription, "Required");
            }
        } catch (InvalidCreateClientFormException e) {
            errorTextView = e.view;
            return new VerificationError(e.getMessage());
        }

        updateGoal();

        return null;
    }

    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        if (errorTextView != null) {
            errorTextView.setError(error.getErrorMessage());
        }
    }

    private void initializeChips(View view) {
        healthChip = view.findViewById(R.id.healthGoalChip);
        educationChip = view.findViewById(R.id.educationGoalChip);
        socialChip = view.findViewById(R.id.socialGoalChip);
    }

    private void initializeCards(View view) {
        healthCard = view.findViewById(R.id.healthGoalCard);
        healthCard.setVisibility(View.GONE);
        educationCard = view.findViewById(R.id.educationGoalCard);
        educationCard.setVisibility(View.GONE);
        socialCard = view.findViewById(R.id.socialGoalCard);
        socialCard.setVisibility(View.GONE);
    }

    private void initializeEditTexts(View view) {
        healthGoalTitle = view.findViewById(R.id.healthGoalTitleEditTextView);
        healthGoalDescription = view.findViewById(R.id.healthGoalDescriptionEditTextView);
        educationGoalTitle = view.findViewById(R.id.educationGoalTitleEditTextView);
        educationGoalDescription = view.findViewById(R.id.educationGoalDescriptionEditTextView);
        socialGoalTitle = view.findViewById(R.id.socialGoalTitleEditTextView);
        socialGoalDescription = view.findViewById(R.id.socialGoalDescriptionEditTextView);
    }

    private void setupCardVisibility() {
        healthChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!healthVisible) {
                    healthCard.setVisibility(View.VISIBLE);
                } else {
                    healthCard.setVisibility(View.GONE);
                }
                healthVisible = !healthVisible;
            }
        });
        educationChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!educationVisible) {
                    educationCard.setVisibility(View.VISIBLE);
                } else {
                    educationCard.setVisibility(View.GONE);
                }
                educationVisible = !educationVisible;
            }
        });
        socialChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!socialVisible) {
                    socialCard.setVisibility(View.VISIBLE);
                } else {
                    socialCard.setVisibility(View.GONE);
                }
                socialVisible = !socialVisible;
            }
        });
    }
    
    private void updateGoal() {

        if(healthVisible) {
            healthGoal.setUserId(userId);
            healthGoal.setStatus("Ongoing");
            healthGoal.setTitle(healthGoalTitle.getText().toString().trim());
            healthGoal.setDescription(healthGoalDescription.getText().toString().trim());
            healthGoal.setCategory("Health");
            healthGoal.setIsInitialGoal(true);
        }
        if(educationVisible) {
            educationGoal.setUserId(userId);
            educationGoal.setStatus("Ongoing");
            educationGoal.setTitle(educationGoalTitle.getText().toString().trim());
            educationGoal.setDescription(educationGoalDescription.getText().toString().trim());
            educationGoal.setCategory("Education");
            educationGoal.setIsInitialGoal(true);
        }
        if(socialVisible) {
            socialGoal.setUserId(userId);
            socialGoal.setStatus("Ongoing");
            socialGoal.setTitle(socialGoalTitle.getText().toString().trim());
            socialGoal.setDescription(socialGoalDescription.getText().toString().trim());
            socialGoal.setCategory("Social");
            socialGoal.setIsInitialGoal(true);
        }
    }
}
