package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbr_manager.R;

public class HealthRiskFragment extends Fragment {

    public HealthRiskFragment() {
        // Required empty public constructor
    }

    public static HealthRiskFragment newInstance(String param1, String param2) {
        HealthRiskFragment fragment = new HealthRiskFragment();
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
        return inflater.inflate(R.layout.fragment_health_risk, container, false);
    }
}