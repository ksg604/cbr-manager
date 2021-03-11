package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import static com.example.cbr_manager.ui.create_client.ValidatorHelper.validateStepperTextViewNotNull;

public class PersonalInfoFragment extends Fragment implements Step {
    private static final String[] paths = {"Male", "Female"};

    private Spinner genderSpinner;

    private EditText editTextFirstName, editTextLastName, editTextAge, editTextContactNumber;

    private Client client;

    private TextView errorTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_create_client_personal_info, container, false);

        client = ((CreateClientStepperActivity) getActivity()).formClientObj;

        editTextFirstName = setUpEditView(view, R.id.editTextFirstName);

        editTextLastName = setUpEditView(view, R.id.editTextLastName);

        editTextAge = setUpEditView(view, R.id.editTextAge);

        editTextContactNumber = setUpEditView(view, R.id.editTextContactNumber);

        genderSpinner = setUpSpinner(view, R.id.gender_dropdown, paths);

        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        try {
            validateStepperTextViewNotNull(editTextFirstName, "Required");
            validateStepperTextViewNotNull(editTextLastName, "Required");
            validateStepperTextViewNotNull(editTextAge, "Required");
            validateStepperTextViewNotNull(editTextContactNumber, "Required");
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

    private Spinner setUpSpinner(View view, int spinnerId, String[] options) {
        Spinner spinner = (Spinner) view.findViewById(spinnerId);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, options);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        return spinner;
    }

    private EditText setUpEditView(View view, int editTextViewId) {
        return (EditText) view.findViewById(editTextViewId);
    }

    public void updateClient() {
        client.setFirstName(editTextFirstName.getText().toString().trim());
        client.setLastName(editTextLastName.getText().toString().trim());
        client.setAge(Integer.parseInt(editTextAge.getText().toString().trim()));
        client.setContactClient(editTextContactNumber.getText().toString().trim());

        client.setGender(genderSpinner.getSelectedItem().toString().trim());
    }

}
