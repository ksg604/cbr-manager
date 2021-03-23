package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbr_manager.R;

public class CreateVisitPurposeFragment extends Fragment {

    public CreateVisitPurposeFragment() {
        // Required empty public constructor
    }

    public static CreateVisitPurposeFragment newInstance(String param1, String param2) {
        CreateVisitPurposeFragment fragment = new CreateVisitPurposeFragment();
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
        return inflater.inflate(R.layout.fragment_create_visit_purpose, container, false);
    }
}