package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import static com.example.cbr_manager.ui.create_client.ValidatorHelper.validateStepperTextViewNotNull;

public class CaregiverInfoFragment extends Fragment implements Step {

    private RadioGroup careGiverPresentRadioGroup;

    private EditText editTextCaregiverContactNumber;

    private Client client;

    private TextView errorTextView;

    private View view;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        client = ((CreateClientStepperActivity) getActivity()).formClientObj;

        View view = inflater.inflate(R.layout.activity_create_client_caregiver_info, container, false);

        careGiverPresentRadioGroup = view.findViewById(R.id.radioGroup2);

        editTextCaregiverContactNumber = (EditText) view.findViewById(R.id.editTextCaregiverContactNumber);

        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        try {
            validateStepperTextViewNotNull(editTextCaregiverContactNumber, "Required");
        } catch (InvalidCreateClientFormException e) {
            errorTextView = e.view;
            return new VerificationError(e.getMessage());
        }

        updateClient();

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

    private void updateClient() {
        client.setContactCare(editTextCaregiverContactNumber.getText().toString());
        client.setCarePresent(extractRadioSelection(getView()));
    }

    private String extractRadioSelection(View view) {
        int radioGroupId = careGiverPresentRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) view.findViewById(radioGroupId);
        return radioButton.getText().toString();
    }
}
