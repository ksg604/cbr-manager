package com.example.cbr_manager.ui.createvisit;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbr_manager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateVisitSocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateVisitSocialFragment extends Fragment {

    public CreateVisitSocialFragment() {
        // Required empty public constructor
    }

    public static CreateVisitSocialFragment newInstance(String param1, String param2) {
        CreateVisitSocialFragment fragment = new CreateVisitSocialFragment();
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
        return inflater.inflate(R.layout.fragment_create_visit_social, container, false);
    }
}