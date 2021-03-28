package com.example.cbr_manager.ui.create_client;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbr_manager.R;

public class SocialRiskFragment extends Fragment {

    public SocialRiskFragment() {
        // Required empty public constructor
    }
    public static SocialRiskFragment newInstance(String param1, String param2) {
        SocialRiskFragment fragment = new SocialRiskFragment();
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
        return inflater.inflate(R.layout.fragment_social_risk, container, false);
    }
}