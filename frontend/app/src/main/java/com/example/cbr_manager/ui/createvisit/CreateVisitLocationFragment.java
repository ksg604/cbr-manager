package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.cbr_manager.R;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class CreateVisitLocationFragment extends Fragment implements Step {

    private View view;
    Spinner locationSpinner;

    public CreateVisitLocationFragment() {
        // Required empty public constructor
    }

    public static CreateVisitLocationFragment newInstance(String param1, String param2) {
        CreateVisitLocationFragment fragment = new CreateVisitLocationFragment();
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
        view = inflater.inflate(R.layout.fragment_create_visit_location, container, false);
        locationSpinner = view.findViewById(R.id.locationFragmentSpinner);
        return view;
    }

    private void setupLocationSpinner(View view) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.locationsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(adapter);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}