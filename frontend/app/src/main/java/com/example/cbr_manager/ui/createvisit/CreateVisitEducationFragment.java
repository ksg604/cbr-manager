package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.GoalViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

@AndroidEntryPoint
public class CreateVisitEducationFragment extends Fragment implements Step {

    TextInputLayout adviceInput;
    TextInputLayout advocacyInput;
    TextInputLayout referralInput;
    TextInputLayout encouragementInput;
    TextInputLayout conclusionInput;
    TextInputLayout newGoalInput;
    TextInputLayout newGoalDescriptionInput;
    Chip adviceChip;
    Chip advocacyChip;
    Chip referralChip;
    Chip encouragementChip;
    RadioGroup goalsMetRadioGroup;
    TextView currentGoalTextView;
    TextView currentGoalStatusTextView;
    TextView goalsMetTextView;
    private View view;
    private Visit visit;
    private final String GOAL_CONCLUDED_KEY = "concluded";
    private final String GOAL_CATEGORY_EDUCATION = "education";
    private APIService apiService = APIService.getInstance();
    private Integer clientId = -1;
    Goal educationGoal;
    Goal previousEducationGoal;
    private static final String EDUCATION_KEY = "Education";
    private static final String STATUS_ONGOING_KEY = "Ongoing";
    private boolean concludedOrNotFound = false;
    private GoalViewModel goalViewModel;

    public CreateVisitEducationFragment() {
        // Required empty public constructor
    }

    public static CreateVisitEducationFragment newInstance(String param1, String param2) {
        CreateVisitEducationFragment fragment = new CreateVisitEducationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        goalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);
        view = inflater.inflate(R.layout.fragment_create_visit_education, container, false);
        visit = ((CreateVisitStepperActivity) getActivity()).formVisitObj;
        educationGoal = ((CreateVisitStepperActivity) getActivity()).educationGoalObj;
        clientId = ((CreateVisitStepperActivity) getActivity()).clientId;
        initializeInputLayouts(view);
        initializeChips(view);
        initializeRadioGroups(view);
        getEducationGoal(view);
        setupInputLayoutVisibility();
        return view;
    }

    private void setupInputLayoutVisibility() {
        setChipListener(adviceChip, adviceInput);
        setChipListener(advocacyChip, advocacyInput);
        setChipListener(referralChip, referralInput);
        setChipListener(encouragementChip, encouragementInput);
    }

    private void getEducationGoal(View view) {
        currentGoalTextView = view.findViewById(R.id.educationProvisionCurrentGoalTextView);
        currentGoalStatusTextView = view.findViewById(R.id.educationProvisionCurrentGoalStatusTextView);

        goalViewModel.getAllGoals().observe(getViewLifecycleOwner(), goals -> {
            Collections.reverse(goals);
            previousEducationGoal = findNonConcludedGoal(goals);
            if (previousEducationGoal != null) {
                currentGoalTextView.setText("Current goal: " + previousEducationGoal.getTitle());
                currentGoalStatusTextView.setText("Current status: " + previousEducationGoal.getStatus());
            }

            if (previousEducationGoal == null) {
                previousEducationGoal = findConcludedGoal(goals);
                if (previousEducationGoal != null) {
                    currentGoalTextView.setText("Current goal: " + previousEducationGoal.getTitle());
                    currentGoalStatusTextView.setText("Current status: " + previousEducationGoal.getStatus());
                } else {
                    currentGoalTextView.setText("Current goal: No goal found. Please make one below.");
                    currentGoalStatusTextView.setText("Current status: No status.");
                }
                newGoalInput.setVisibility(View.VISIBLE);
                newGoalDescriptionInput.setVisibility(View.VISIBLE);
                concludedOrNotFound = true;

            }
        });
    }

    private Goal findNonConcludedGoal(List<Goal> goalList) {
        Goal goal;
        for (int i = 0; i < goalList.size(); i++) {
            goal = goalList.get(i);
            Integer id = goal.getClientId();
            String type = goal.getCategory().trim().toLowerCase();
            String status = goal.getStatus().trim().toLowerCase();
            String GOAL_CREATED_KEY = "created";
            String GOAL_ONGOING_KEY = "ongoing";
            if (id.equals(clientId) && type.equals(GOAL_CATEGORY_EDUCATION) && (status.equals(GOAL_CREATED_KEY) || status.equals(GOAL_ONGOING_KEY))) {
                return goal;
            }
        }
        return null;
    }

    private Goal findConcludedGoal(List<Goal> goalList) {
        Goal goal;
        for (int i = 0; i < goalList.size(); i++) {
            goal = goalList.get(i);
            Integer id = goal.getClientId();
            String type = goal.getCategory().trim().toLowerCase();
            String status = goal.getStatus().trim().toLowerCase();
            if (id.equals(clientId) && type.equals(GOAL_CATEGORY_EDUCATION) && status.equals(GOAL_CONCLUDED_KEY)) {
                return goal;
            }
        }
        return null;
    }

    private void initializeRadioGroups(View view) {
        goalsMetRadioGroup = view.findViewById(R.id.educationProvisionRadioGroup);
        goalsMetRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.educationProvisionConcludedRadioButton) {
                    conclusionInput.setVisibility(View.VISIBLE);
                    newGoalInput.setVisibility(View.VISIBLE);
                    newGoalDescriptionInput.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.educationProvisionCancelledRadioButton) {
                    newGoalInput.setVisibility(View.VISIBLE);
                    newGoalDescriptionInput.setVisibility(View.VISIBLE);
                    conclusionInput.setVisibility(GONE);
                } else {
                    conclusionInput.setVisibility(GONE);
                    newGoalDescriptionInput.setVisibility(GONE);
                    newGoalInput.setVisibility(GONE);
                }
            }
        });
    }

    private void setChipListener(Chip chip, TextInputLayout textInputLayout) {
        chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chip.isChecked()) {
                    textInputLayout.setVisibility(View.VISIBLE);
                } else {
                    textInputLayout.setVisibility(GONE);
                }
            }
        });
    }

    private void initializeChips(View view) {
        adviceChip = view.findViewById(R.id.educationProvisionsAdviceChip);
        advocacyChip = view.findViewById(R.id.educationProvisionsAdvocacyChip);
        referralChip = view.findViewById(R.id.educationProvisionsReferralChip);
        encouragementChip = view.findViewById(R.id.educationProvisionsEncouragementChip);
    }

    private void initializeInputLayouts(View view) {
        goalsMetTextView = view.findViewById(R.id.educationProvisionRadioGroupTextView);
        adviceInput = view.findViewById(R.id.educationAdviceInputLayout);
        advocacyInput = view.findViewById(R.id.educationAdvocacyInputLayout);
        referralInput = view.findViewById(R.id.educationReferralInputLayout);
        encouragementInput = view.findViewById(R.id.educationEncouragementInputLayout);
        conclusionInput = view.findViewById(R.id.educationConclusionInputLayout);
        newGoalInput = view.findViewById(R.id.educationNewGoalInputLayout);
        newGoalDescriptionInput = view.findViewById(R.id.educationNewGoalDescriptionInputLayout);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateCreateClient();
        return null;
    }

    private void updateCreateClient() {
        visit.setAdviceEducationProvision(adviceChip.isChecked());
        visit.setAdvocacyEducationProvision(advocacyChip.isChecked());
        visit.setReferralEducationProvision(referralChip.isChecked());
        visit.setEncouragementEducationProvision(encouragementChip.isChecked());

        visit.setAdviceEducationProvisionText(getInputLayoutString(adviceInput));
        visit.setAdvocacyEducationProvisionText(getInputLayoutString(advocacyInput));
        visit.setReferralEducationProvisionText(getInputLayoutString(referralInput));
        visit.setEncouragementEducationProvisionText(getInputLayoutString(encouragementInput));

        visit.setConclusionEducationProvision(getInputLayoutString(conclusionInput));

        if (goalsMetRadioGroup.getCheckedRadioButtonId() == R.id.educationProvisionConcludedRadioButton || goalsMetRadioGroup.getCheckedRadioButtonId() == R.id.educationProvisionCancelledRadioButton || concludedOrNotFound) {
            if (!newGoalInput.getEditText().getText().toString().isEmpty()) {
                educationGoal.setCategory(EDUCATION_KEY);
                educationGoal.setTitle(newGoalInput.getEditText().getText().toString());
                educationGoal.setStatus(STATUS_ONGOING_KEY);
                String description = newGoalDescriptionInput.getEditText().getText().toString();

                if (description.isEmpty()) {
                    educationGoal.setDescription("No description listed.");
                } else {
                    educationGoal.setDescription(description);
                }
                ((CreateVisitStepperActivity) getActivity()).educationGoalCreated = true;
            } else {
                ((CreateVisitStepperActivity) getActivity()).educationGoalCreated = false;
            }
        }

        if (!concludedOrNotFound && previousEducationGoal != null) {
            switch (goalsMetRadioGroup.getCheckedRadioButtonId()) {
                case R.id.educationProvisionConcludedRadioButton:
                    previousEducationGoal.setStatus(GOAL_CONCLUDED_KEY);
                    break;
                case R.id.educationProvisionOngoingRadioButton:
                    previousEducationGoal.setStatus(STATUS_ONGOING_KEY);
                    break;
                case R.id.educationProvisionCancelledRadioButton:
                    previousEducationGoal.setStatus("Cancelled");
                    break;
            }
            ((CreateVisitStepperActivity) getActivity()).prevEducationGoalObj = previousEducationGoal;
        }
    }

    private String getInputLayoutString(TextInputLayout textInputLayout) {
        EditText editText  = textInputLayout.getEditText();
        return editText.getText().toString();
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}