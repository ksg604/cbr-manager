package com.example.cbr_manager.ui.createvisit;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.ui.stepper.GenericStepperAdapter;
import com.example.cbr_manager.ui.ClientViewModel;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableSingleObserver;

@AndroidEntryPoint
public class CreateVisitPurposeFragment extends Fragment implements Step {

    EditText clientNameEditText;
    EditText cbrWorkerName;
    EditText dateEditText;
    Chip cbrChip;
    Chip referralChip;
    Chip followUpChip;
    Chip healthChip;
    Chip educationChip;
    Chip socialChip;
    private View view;
    private AuthViewModel authViewModel;
    private ClientViewModel clientViewModel;
    private int clientId = -1;
    private int userId = -1;
    private Visit visit;
    ChipGroup provisionChipGroup;
    GenericStepperAdapter genericStepperAdapter;

    public CreateVisitPurposeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_visit_purpose, container, false);
        visit = ((CreateVisitStepperActivity) getActivity()).formVisitObj;
        clientId = ((CreateVisitStepperActivity) getActivity()).clientId;
        genericStepperAdapter = ((CreateVisitStepperActivity) getActivity()).createVisitStepperAdapter;
        initializeChips(view);
        setupAutoFilledTextViews(view);
        setupProvisionVisibility();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (!preferences.getBoolean("firstTimeProvision", false)) {
            TapTargetView.showFor(getActivity(),
                    TapTarget.forView(view.findViewById(R.id.cbrTypeTextView), "Select a provision.", "Provisions will be updated by selecting the CBR chips.")
                            .outerCircleAlpha(0.96f)
                            .targetCircleColor(R.color.white)
                            .titleTextSize(20)
                            .drawShadow(true)
                            .transparentTarget(true)
                        .tintTarget(true)
                            .dimColor(R.color.black));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstTimeProvision", true);
            editor.apply();
        }
        return view;
    }

    private void setupProvisionVisibility() {
        healthChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (healthChip.isChecked()) {
                    ((CreateVisitStepperActivity) getActivity()).makePrivisionVisible("Health");
                }
            }
        });

        educationChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (educationChip.isChecked()) {
                    ((CreateVisitStepperActivity) getActivity()).makePrivisionVisible("Education");
                }
            }
        });

        socialChip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (socialChip.isChecked()) {
                    ((CreateVisitStepperActivity) getActivity()).makePrivisionVisible("Social");
                }
            }
        });
    }

    private void initializeChips(View view) {
        provisionChipGroup = view.findViewById(R.id.cbrTypeChipGroup);
        cbrChip = view.findViewById(R.id.cbrChip);
        referralChip = view.findViewById(R.id.purposeReferralChip);
        followUpChip = view.findViewById(R.id.purposeFollowUpChip);
        healthChip = view.findViewById(R.id.healthProvisionChip);
        educationChip = view.findViewById(R.id.educationProvisionChip);
        socialChip = view.findViewById(R.id.socialProvisionChip);
    }

    private void setupAutoFilledTextViews(View view) {
        clientNameEditText = view.findViewById(R.id.fragmentPreambleClientEditText);
        cbrWorkerName = view.findViewById(R.id.fragmentPreambleCBRNameEditText);
        dateEditText = view.findViewById(R.id.fragmentPreambleEditTextDate);

        clientViewModel.getClient(clientId).observe(getViewLifecycleOwner(), client -> {
            clientNameEditText.setText(client.getFullName());
            clientNameEditText.setEnabled(false);
        });

        authViewModel.getUser().subscribe(new DisposableSingleObserver<User>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull User user) {
                userId = user.getId();
                visit.setUserId(userId);
                visit.setCbrWorkerName(user.getUsername());
                cbrWorkerName.setText(user.getUsername());
                cbrWorkerName.setEnabled(false);
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Toast.makeText(getContext(), "User response error. " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayString = format.format(today);
        dateEditText.setText(todayString);
        dateEditText.setEnabled(false);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        updateCreateVisit();
        return null;
    }

    private void updateCreateVisit() {
        visit.setCBRPurpose(cbrChip.isChecked());
        visit.setDisabilityReferralPurpose(referralChip.isChecked());
        visit.setDisabilityFollowUpPurpose(followUpChip.isChecked());
        visit.setHealthProvision(healthChip.isChecked());
        visit.setEducationProvision(educationChip.isChecked());
        visit.setSocialProvision(socialChip.isChecked());
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}