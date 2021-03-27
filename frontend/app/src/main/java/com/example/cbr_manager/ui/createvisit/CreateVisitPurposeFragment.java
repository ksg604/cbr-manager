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
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.AuthViewModel;
import com.google.android.material.chip.Chip;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class CreateVisitPurposeFragment extends Fragment implements Step {

    private View view;
    private AuthViewModel authViewModel;
    private APIService apiService = APIService.getInstance();
    private int clientId = -1;
    private int userId = -1;
    
    private Visit visit;

    EditText clientNameEditText;
    EditText cbrWorkerName;
    EditText dateEditText;
    Chip cbrChip;
    Chip referralChip;
    Chip followUpChip;
    Chip healthChip;
    Chip educationChip;
    Chip socialChip;

    public CreateVisitPurposeFragment() {
        // Required empty public constructor
    }

//    public static CreateVisitPurposeFragment newInstance(String param1, String param2) {
//        CreateVisitPurposeFragment fragment = new CreateVisitPurposeFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_visit_purpose, container, false);
        visit = ((CreateVisitStepperActivity) getActivity()).formVisitObj;
        clientId = ((CreateVisitStepperActivity) getActivity()).clientId;
        initializeChips(view);
        setupAutoFilledTextViews(view);
        return view;
    }

    private void initializeChips(View view) {
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
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    if (response.isSuccessful()) {
                        Client client = response.body();
                        clientNameEditText.setText(client.getFullName());
                        clientNameEditText.setEnabled(false);
                    }
                }
                @Override
                public void onFailure(Call<Client> call, Throwable t) {
                }
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
        }

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