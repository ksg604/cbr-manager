package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbr_manager.R;

public class CreateVisitLocationFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_create_visit_location, container, false);
    }
}