package com.example.cbr_manager.ui.visits;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;

public class VisitsFragment extends Fragment {

    private VisitsViewModel visitsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        visitsViewModel =
                new ViewModelProvider(this).get(VisitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visits, container, false);

        setupButtons(root);

        return root;
    }

    private void setupButtons(View root) {
        setupCreateVisitButton(root);
    }

    private void setupCreateVisitButton(View root) {
        Button createVisitButton = root.findViewById(R.id.buttonCreateVisit);

        createVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createVisitIntent = new Intent(getActivity(), CreateVisitActivity.class);
                startActivity(createVisitIntent);
            }
        });
    }
}