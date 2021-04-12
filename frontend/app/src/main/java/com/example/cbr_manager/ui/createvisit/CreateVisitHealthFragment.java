package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.GoalViewModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

@AndroidEntryPoint
public class CreateVisitHealthFragment extends Fragment implements Step {
    
    private View view;
    private Visit visit;
    TextInputLayout wheelchairInput;
    TextInputLayout prostheticInput;
    TextInputLayout orthoticInput;
    TextInputLayout wheelchairRepairsInput;
    TextInputLayout referralInput;
    TextInputLayout adviceInput;
    TextInputLayout advocacyInput;
    TextInputLayout encouragementInput;
    TextInputLayout conclusionInput;
    TextInputLayout newGoalInput;
    TextInputLayout newGoalDescriptionInput;
    TextView goalMetTextView;
    Chip wheelchairChip;
    Chip prostheticChip;
    Chip orthoticChip;
    Chip wheelchairRepairsChip;
    Chip referralChip;
    Chip adviceChip;
    Chip advocacyChip;
    Chip encouragementChip;
    RadioGroup goalOutcomeRadioGroup;
    TextView currentGoalTextView;
    TextView currentGoalStatusTextView;
    private final String GOAL_CONCLUDED_KEY = "concluded";
    private final String GOAL_CATEGORY_HEALTH = "health";
    private APIService apiService = APIService.getInstance();
    private Integer clientId = -1;
    Goal healthGoal;
    Goal previousHealthGoal;
    private static final String HEALTH_KEY = "Health";
    private static final String STATUS_ONGOING_KEY = "Ongoing";
    private boolean concludedOrNotFound = false;
    private GoalViewModel goalViewModel;

    public CreateVisitHealthFragment() {
        // Required empty public constructor
    }

    public static CreateVisitHealthFragment newInstance(String param1, String param2) {
        CreateVisitHealthFragment fragment = new CreateVisitHealthFragment();
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
        view = inflater.inflate(R.layout.fragment_create_visit_health, container, false);
        visit = ((CreateVisitStepperActivity) getActivity()).formVisitObj;
        healthGoal = ((CreateVisitStepperActivity) getActivity()).healthGoalObj;
        clientId = ((CreateVisitStepperActivity) getActivity()).clientId;
        initializeInputLayouts(view);
        initializeChips(view);
        initializeRadioGroups(view);
        setupInputLayoutVisibility(view);
        getHealthGoal(view);
        return view;
    }

    private void getHealthGoal(View view) {
        currentGoalTextView = view.findViewById(R.id.healthProvisionCurrentGoalTextView);
        currentGoalStatusTextView = view.findViewById(R.id.healthProvisionCurrentGoalStatusTextView);

        goalViewModel.getAllGoals().observe(getViewLifecycleOwner(), goals -> {
            Collections.reverse(goals);
            previousHealthGoal = findNonConcludedGoal(goals);
            if (previousHealthGoal != null) {
                currentGoalTextView.setText("Current goal: " + previousHealthGoal.getTitle());
                currentGoalStatusTextView.setText("Current status: " + previousHealthGoal.getStatus());
            }

            if (previousHealthGoal == null) {
                previousHealthGoal = findConcludedGoal(goals);
                if (previousHealthGoal != null) {
                    currentGoalTextView.setText("Current goal: " + previousHealthGoal.getTitle());
                    currentGoalStatusTextView.setText("Current status: " + previousHealthGoal.getStatus());
                } else {
                    currentGoalTextView.setText("Current goal: No goal found. Please make one below.");
                    currentGoalStatusTextView.setText("Current status: No status.");
                }
                goalOutcomeRadioGroup.setVisibility(GONE);
                goalMetTextView.setVisibility(GONE);
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
            if (id.equals(clientId) && type.equals(GOAL_CATEGORY_HEALTH) && (status.equals(GOAL_CREATED_KEY) || status.equals(GOAL_ONGOING_KEY))) {
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
            if (id.equals(clientId) && type.equals(GOAL_CATEGORY_HEALTH) && status.equals(GOAL_CONCLUDED_KEY)) {
                return goal;
            }
        }
        return null;
    }

    private void initializeRadioGroups(View view) {
        goalOutcomeRadioGroup = view.findViewById(R.id.healthProvisionsRadioGroup);
        goalOutcomeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.concludedHPRadioButton) {
                    conclusionInput.setVisibility(View.VISIBLE);
                    newGoalInput.setVisibility(View.VISIBLE);
                    newGoalDescriptionInput.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.cancelledHPRadioButton) {
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

    private void setupInputLayoutVisibility(View view) {
        setChipListener(wheelchairChip, wheelchairInput);
        setChipListener(prostheticChip, prostheticInput);
        setChipListener(orthoticChip, orthoticInput);
        setChipListener(orthoticChip, orthoticInput);
        setChipListener(wheelchairRepairsChip, wheelchairRepairsInput);
        setChipListener(referralChip, referralInput);
        setChipListener(adviceChip, adviceInput);
        setChipListener(advocacyChip, advocacyInput);
        setChipListener(encouragementChip, encouragementInput);
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

    private void initializeInputLayouts(View view) {
        goalMetTextView = view.findViewById(R.id.healthGoalMetTextView);
        wheelchairInput = view.findViewById(R.id.healthWheelChairInputLayout);
        prostheticInput = view.findViewById(R.id.healthProstheticInputLayout);
        orthoticInput = view.findViewById(R.id.healthOrthoticInputLayout);
        wheelchairRepairsInput = view.findViewById(R.id.healthWheelchairRepairsInputLayout);
        referralInput = view.findViewById(R.id.healthReferralInputLayout);
        adviceInput = view.findViewById(R.id.healthAdviceInputLayout);
        advocacyInput = view.findViewById(R.id.healthAdvocacyInputLayout);
        encouragementInput = view.findViewById(R.id.healthEncouragementInputLayout);
        conclusionInput = view.findViewById(R.id.healthGoalConclusionInputLayout);
        newGoalInput = view.findViewById(R.id.healthNewGoalInputLayout);
        newGoalDescriptionInput = view.findViewById(R.id.healthNewGoalDescriptionInputLayout);
    }

    private void initializeChips(View view) {
        wheelchairChip = view.findViewById(R.id.wheelchairChip);
        prostheticChip = view.findViewById(R.id.prostheticChip);
        orthoticChip = view.findViewById(R.id.orthoticChip);
        wheelchairRepairsChip = view.findViewById(R.id.wheelchairRepairsChip);
        referralChip = view.findViewById(R.id.referralChip);
        adviceChip = view.findViewById(R.id.adviceChip);
        advocacyChip = view.findViewById(R.id.advocacyChip);
        encouragementChip = view.findViewById(R.id.encouragementChip);
    }

    private String getInputLayoutString(TextInputLayout textInputLayout) {
        EditText editText  = textInputLayout.getEditText();
        return editText.getText().toString();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateCreateVisit();
        return null;
    }

    private void updateCreateVisit() {
        visit.setWheelchairHealthProvision(wheelchairChip.isChecked());
        visit.setProstheticHealthProvision(prostheticChip.isChecked());
        visit.setOrthoticHealthProvision(orthoticChip.isChecked());
        visit.setRepairsHealthProvision(wheelchairRepairsChip.isChecked());
        visit.setReferralHealthProvision(referralChip.isChecked());
        visit.setAdviceHealthProvision(adviceChip.isChecked());
        visit.setAdvocacyHealthProvision(advocacyChip.isChecked());
        visit.setEncouragementHealthProvision(encouragementChip.isChecked());

        visit.setWheelchairHealthProvisionText(getInputLayoutString(wheelchairInput));
        visit.setProstheticHealthProvisionText(getInputLayoutString(prostheticInput));
        visit.setOrthoticHealthProvisionText(getInputLayoutString(orthoticInput));
        visit.setRepairsHealthProvisionText(getInputLayoutString(wheelchairRepairsInput));
        visit.setReferralHealthProvisionText(getInputLayoutString(referralInput));
        visit.setAdviceHealthProvisionText(getInputLayoutString(adviceInput));
        visit.setAdvocacyHealthProvisionText(getInputLayoutString(advocacyInput));
        visit.setEncouragementHealthProvisionText(getInputLayoutString(encouragementInput));

        if (goalOutcomeRadioGroup.getCheckedRadioButtonId() == R.id.concludedHPRadioButton || goalOutcomeRadioGroup.getCheckedRadioButtonId() == R.id.cancelledHPRadioButton || concludedOrNotFound) {
            if (!newGoalInput.getEditText().getText().toString().isEmpty()) {
                healthGoal.setCategory(HEALTH_KEY);
                healthGoal.setTitle(newGoalInput.getEditText().getText().toString());
                healthGoal.setStatus(STATUS_ONGOING_KEY);
                String description = newGoalDescriptionInput.getEditText().getText().toString();

                if (description.isEmpty()) {
                    healthGoal.setDescription("No description listed.");
                } else {
                    healthGoal.setDescription(description);
                }

                ((CreateVisitStepperActivity) getActivity()).healthGoalCreated = true;
            } else {
                ((CreateVisitStepperActivity) getActivity()).healthGoalCreated = false;
            }
        }

        if (!concludedOrNotFound && previousHealthGoal != null) {
            switch (goalOutcomeRadioGroup.getCheckedRadioButtonId()) {
                case R.id.concludedHPRadioButton:
                    previousHealthGoal.setStatus(GOAL_CONCLUDED_KEY);
                    break;
                case R.id.ongoingHPRadioButton:
                    previousHealthGoal.setStatus(STATUS_ONGOING_KEY);
                    break;
                case R.id.cancelledHPRadioButton:
                    previousHealthGoal.setStatus("Cancelled");
                    break;
            }
            ((CreateVisitStepperActivity) getActivity()).prevHealthGoalObj = previousHealthGoal;
        }

        visit.setConclusionHealthProvision(getInputLayoutString(conclusionInput));
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}