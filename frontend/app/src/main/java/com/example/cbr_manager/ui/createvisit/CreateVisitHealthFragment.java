package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputLayout;

import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

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
    private List<Goal> goalList = new ArrayList<>();
    private final String GOAL_CREATED_KEY = "created";
    private final String GOAL_ONGOING_KEY = "ongoing";
    private final String GOAL_CONCLUDED_KEY = "concluded";
    private final String GOAL_CATEGORY_HEALTH = "health";
    private APIService apiService = APIService.getInstance();
    private Integer clientId = -1;
    Goal healthGoal;
    private static final String HEALTH_KEY = "Health";
    private static final String STATUS_ONGOING_KEY = "Ongoing";

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
        if (apiService.isAuthenticated()) {
            apiService.goalService.getGoals().enqueue(new Callback<List<Goal>>() {
                @Override
                public void onResponse(Call<List<Goal>> call, Response<List<Goal>> response) {
                    if (response.isSuccessful()) {
                        goalList = response.body();
                        Goal goal;
                        Collections.reverse(goalList);
                        goal = findNonConcludedGoal();
                        if (goal != null) {
                            currentGoalTextView.setText("Current goal: " + goal.getTitle());
                            currentGoalStatusTextView.setText("Current status: " + goal.getStatus());
                        }

                        if (goal == null) {
                            goal = findConcludedGoal();
                            if (goal != null) {
                                currentGoalTextView.setText("Current goal: " + goal.getTitle());
                                currentGoalStatusTextView.setText("Current status: " + goal.getStatus());
                            } else {
                                currentGoalTextView.setText("Current goal: No goal found. Please make one below.");
                                currentGoalStatusTextView.setText("Current status: No status.");
                            }
                        }
                    } else {
                        Toast.makeText(getContext(), "Response error.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Goal>> call, Throwable t) {

                }
            });
        }


    }

    private Goal findNonConcludedGoal() {
        Goal goal;
        for (int i = 0; i < goalList.size(); i++) {
            goal = goalList.get(i);
            Integer id = goal.getClientId();
            String type = goal.getCategory().trim().toLowerCase();
            String status = goal.getStatus().trim().toLowerCase();
            if (id.equals(clientId) && type.equals(GOAL_CATEGORY_HEALTH) && (status.equals(GOAL_CREATED_KEY) || status.equals(GOAL_ONGOING_KEY))) {
                return goal;
            }
        }
        return null;
    }

    private Goal findConcludedGoal() {
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
                } else if (checkedId == R.id.cancelledHPRadioButton) {
                    newGoalInput.setVisibility(View.VISIBLE);
                    conclusionInput.setVisibility(GONE);
                } else {
                    conclusionInput.setVisibility(GONE);
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

        if (goalOutcomeRadioGroup.getCheckedRadioButtonId() == R.id.concludedHPRadioButton || goalOutcomeRadioGroup.getCheckedRadioButtonId() == R.id.cancelledHPRadioButton) {
            if (!newGoalInput.getEditText().getText().toString().isEmpty()) {
                healthGoal.setCategory(HEALTH_KEY);
                healthGoal.setTitle(newGoalInput.getEditText().getText().toString());
                healthGoal.setStatus(STATUS_ONGOING_KEY);
                ((CreateVisitStepperActivity) getActivity()).healthGoalCreated = true;
            } else {
                ((CreateVisitStepperActivity) getActivity()).healthGoalCreated = false;
            }
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