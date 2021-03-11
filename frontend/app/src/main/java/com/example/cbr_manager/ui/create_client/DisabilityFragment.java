package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

public class DisabilityFragment extends Fragment implements Step {

    CheckBox[] checkBoxes = new CheckBox[10];
    String[] disability_list = {"Amputee", "Polio", "Spinal Cord Injury", "Cerebral Palsy", "Spina Bifada",
                                "Hydrocephalus", "Visual Impairment", "Hearing Impairment", "Don't Know", "Other"};
    private Client client;

    private TextView errorTextView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        client = ((CreateClientStepperActivity) getActivity()).formClientObj;

        View view = inflater.inflate(R.layout.activity_create_client_disability, container, false);
        String txt;

        for(int i=0 ; i<10 ; i++) {
            txt = "checkBox" + i;
            int resourceId = this.getResources().getIdentifier(txt, "id", getActivity().getPackageName());
            checkBoxes[i] = view.findViewById(resourceId);
        }

        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
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

    public void updateClient() {
        String disabilities = "";
        for(int i=0 ; i<10 ; i++) {
            if(checkBoxes[i].isChecked()) {
                disabilities = disabilities.concat(disability_list[i] + "/");
            }
        }
        client.setDisability(disabilities);
    }
}
