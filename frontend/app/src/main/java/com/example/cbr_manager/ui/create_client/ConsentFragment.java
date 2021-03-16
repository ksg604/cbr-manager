package com.example.cbr_manager.ui.create_client;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.Calendar;

import static com.example.cbr_manager.ui.create_client.ValidatorHelper.validateStepperTextViewNotNull;


public class ConsentFragment extends Fragment implements Step {

    private View view;
    private EditText dateEditTextView;
    private RadioGroup radioGroup;
    private CheckBox clientConsent;
    private TextView clientNoConsent;
    private Client client;

    private TextView errorTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        view = inflater.inflate(R.layout.activity_create_client_consent, container, false);

        client = ((CreateClientStepperActivity) getActivity()).formClientObj;

//        radioGroup = view.findViewById(R.id.radioGroup);
        clientConsent = view.findViewById(R.id.clientConsentCheckBox);
        clientNoConsent = view.findViewById(R.id.clientNoConsentTextView);
        clientConsent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clientNoConsent.setVisibility(View.GONE);
                } else {
                    clientNoConsent.setVisibility(View.VISIBLE);
                }
            }
        });

        setupDatePicker();
        return view;
    }

    private void setupDatePicker() {
        dateEditTextView = view.findViewById(R.id.editTextDate);
        Calendar calendar = Calendar.getInstance();

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        dateEditTextView.setText(year + "/" + (month + 1) + "/" + day);
        dateEditTextView.setFocusable(false);
        dateEditTextView.setClickable(false);
        dateEditTextView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateEditTextView.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        int radioId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = view.findViewById(radioId);

        if (!clientConsent.isChecked()) {
            clientNoConsent.setVisibility(View.VISIBLE);
            return new VerificationError("Required");
        }

        try {
//            validateStepperTextViewNotNull(radioButton, "Required");
            validateStepperTextViewNotNull(dateEditTextView, "Required");
        } catch (InvalidCreateClientFormException e) {
            errorTextView = e.view;
            return new VerificationError(e.getMessage());
        }

        updateClient();

        return null;
    }


    @Override
    public void onSelected() {
        //update UI when selected
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        if (errorTextView != null) {
            errorTextView.setError(error.getErrorMessage());
        }
    }

    private void updateClient() {
//        int radioId = radioGroup.getCheckedRadioButtonId();
//        RadioButton radioButton = getView().findViewById(radioId);
        client.setDate(dateEditTextView.getText().toString().trim());
        if (clientConsent.isChecked()) {
            client.setConsent("yes");
        } else {
            client.setConsent("no");
        }

//        client.setConsent(radioButton.getText().toString().trim());
    }

}
