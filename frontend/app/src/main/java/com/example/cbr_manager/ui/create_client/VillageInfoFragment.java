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

public class VillageInfoFragment extends Fragment implements Step {
    private static final String[] paths = {"BidiBidi Zone 1", "BidiBidi Zone 2", "BidiBidi Zone 3", "BidiBidi Zone 4", "BidiBidi Zone 5",
            "Palorinya Basecamp", "Palorinya Zone 1", "Palorinya Zone 2", "Palorinya Zone 3"};
    private Spinner locationSpinner;
    private EditText editTextVillageNum;
    private TextView errorTextView;
    private Client client;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_create_client_village_info, container, false);

        client = ((CreateClientStepperActivity) getActivity()).formClientObj;

        setUpLocationSpinner(view);

        editTextVillageNum = (EditText) view.findViewById(R.id.editTextVillageNum);

        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        try {
            validateStepperTextViewNotNull(editTextVillageNum, "Required");
        } catch (InvalidCreateClientFormException e) {
            errorTextView = e.view;
            return new VerificationError(e.getMessage());
        }

        updateClient();

        errorTextView = null;
        return null;
    }

    private void updateClient() {
        client.setVillageNo(Integer.parseInt(editTextVillageNum.getText().toString()));
        client.setLocation(locationSpinner.getSelectedItem().toString());
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

    private void setUpLocationSpinner(View view) {
        locationSpinner = (Spinner) view.findViewById(R.id.location_dropdown);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, paths);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
    }
}
