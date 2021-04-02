package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
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

public class CreateVisitSocialFragment extends Fragment implements Step {

    Chip adviceChip;
    Chip advocacyChip;
    Chip referralChip;
    Chip encouragementChip;
    TextInputLayout adviceInputLayout;
    TextInputLayout advocacyInputLayout;
    TextInputLayout referralInputLayout;
    TextInputLayout encouragementInputLayout;
    TextInputLayout conclusionInputLayout;
    RadioGroup goalMetRadioGroup;
    TextView currentGoalTextView;
    TextView currentGoalStatusTextView;
    private View view;
    private Visit visit;
    private List<Goal> goalList = new ArrayList<>();
    private final String GOAL_CREATED_KEY = "created";
    private final String GOAL_ONGOING_KEY = "ongoing";
    private final String GOAL_CONCLUDED_KEY = "concluded";
    private final String GOAL_CATEGORY_SOCIAL = "social";
    private APIService apiService = APIService.getInstance();
    private Integer clientId = -1;

    public CreateVisitSocialFragment() {
        // Required empty public constructor
    }

    public static CreateVisitSocialFragment newInstance(String param1, String param2) {
        CreateVisitSocialFragment fragment = new CreateVisitSocialFragment();
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
        view = inflater.inflate(R.layout.fragment_create_visit_social, container, false);
        visit = ((CreateVisitStepperActivity) getActivity()).formVisitObj;
        clientId = ((CreateVisitStepperActivity) getActivity()).clientId;
        initializeInputLayouts(view);
        initializeChips(view);
        initializeRadioGroups(view);
        setupInputLayoutVisibility();
        return view;
    }

    private void getSocialGoal(View view) {
        currentGoalTextView = view.findViewById(R.id.socialProvisionCurrentGoalTextView);
        currentGoalStatusTextView = view.findViewById(R.id.socialProvisionCurrentGoalStatusTextView);
        if (apiService.isAuthenticated()) {
            apiService.goalService.getGoals().enqueue(new Callback<List<Goal>>() {
                @Override
                public void onResponse(Call<List<Goal>> call, Response<List<Goal>> response) {
                    if (response.isSuccessful()) {
                        goalList = response.body();
                    }
                }

                @Override
                public void onFailure(Call<List<Goal>> call, Throwable t) {

                }
            });
        }

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
    }

    private Goal findNonConcludedGoal() {
        Goal goal;
        for (int i = 0; i < goalList.size(); i++) {
            goal = goalList.get(i);
            Integer id = goal.getClient().getId();
            String type = goal.getCategory().trim().toLowerCase();
            String status = goal.getStatus().trim().toLowerCase();
            if (id.equals(clientId) && type.equals(GOAL_CATEGORY_SOCIAL) && (status.equals(GOAL_CREATED_KEY) || status.equals(GOAL_ONGOING_KEY))) {
                return goal;
            }
        }
        return null;
    }

    private Goal findConcludedGoal() {
        Goal goal;
        for (int i = 0; i < goalList.size(); i++) {
            goal = goalList.get(i);
            Integer id = goal.getClient().getId();
            String type = goal.getCategory().trim().toLowerCase();
            String status = goal.getStatus().trim().toLowerCase();
            if (id.equals(clientId) && type.equals(GOAL_CATEGORY_SOCIAL) && status.equals(GOAL_CONCLUDED_KEY)) {
                return goal;
            }
        }
        return null;
    }

    private void initializeRadioGroups(View view) {
        goalMetRadioGroup = view.findViewById(R.id.socialProvisionRadioGroup);
        goalMetRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.socialProvisionConcludedRadioButton) {
                    conclusionInputLayout.setVisibility(View.VISIBLE);
                } else {
                    conclusionInputLayout.setVisibility(GONE);
                }
            }
        });
    }

    private void setupInputLayoutVisibility() {
        setChipListener(adviceChip, adviceInputLayout);
        setChipListener(advocacyChip, advocacyInputLayout);
        setChipListener(referralChip, referralInputLayout);
        setChipListener(encouragementChip, encouragementInputLayout);
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
        adviceChip = view.findViewById(R.id.socialProvisionAdviceChip);
        advocacyChip = view.findViewById(R.id.socialProvisionAdvocacyChip);
        referralChip = view.findViewById(R.id.socialProvisionReferralChip);
        encouragementChip = view.findViewById(R.id.socialProvisionEncouragementChip);
    }

    private void initializeInputLayouts(View view) {
        adviceInputLayout = view.findViewById(R.id.socialAdviceInputLayout);
        advocacyInputLayout = view.findViewById(R.id.socialAdvocacyInputLayout);
        referralInputLayout = view.findViewById(R.id.socialReferralInputLayout);
        encouragementInputLayout = view.findViewById(R.id.socialEncouragementInputLayout);
        conclusionInputLayout = view.findViewById(R.id.socialConclusionInputLayout);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateCreateVisit();
        return null;
    }

    private void updateCreateVisit() {
        visit.setAdviceSocialProvision(adviceChip.isChecked());
        visit.setAdvocacySocialProvision(advocacyChip.isChecked());
        visit.setReferralSocialProvision(referralChip.isChecked());
        visit.setEncouragementSocialProvision(encouragementChip.isChecked());

        visit.setAdviceSocialProvisionText(getInputLayoutString(adviceInputLayout));
        visit.setAdvocacySocialProvisionText(getInputLayoutString(advocacyInputLayout));
        visit.setReferralSocialProvisionText(getInputLayoutString(referralInputLayout));
        visit.setEncouragementSocialProvisionText(getInputLayoutString(encouragementInputLayout));

        visit.setConclusionSocialProvision(getInputLayoutString(conclusionInputLayout));

        // TODO: RadioGroup
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