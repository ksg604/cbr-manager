package com.example.cbr_manager.ui.visits;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;

public class VisitsFragment extends Fragment {

    private VisitsViewModel visitsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        visitsViewModel =
                new ViewModelProvider(this).get(VisitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visits, container, false);
        return root;
    }
}