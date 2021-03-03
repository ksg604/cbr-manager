package com.example.cbr_manager.ui.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.cbr_manager.NavigationActivity;
import com.example.cbr_manager.R;
import com.example.cbr_manager.ui.clientlist.ClientListFragment;
import com.example.cbr_manager.ui.create_client.CreateClientActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;

public class HomepageFragment extends Fragment {
    private ImageButton newClientButton, newVisitButton, dashboardButton;
    private ImageButton newReferralButton, clientListButton, syncButton;

    View view;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, container, false);

        newClientButton = view.findViewById(R.id.newClientButton);
        newClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateClientActivity.class);
                startActivity(intent);
            }
        });

        newVisitButton = view.findViewById(R.id.newVisitButton);
        newVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), CreateVisitActivity.class);
                startActivity(intent);
            }
        });

        dashboardButton = view.findViewById(R.id.dashboardButton);
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NavigationActivity.class);
                startActivity(intent);
            }
        });

        newReferralButton = view.findViewById(R.id.newReferralButton);

        clientListButton = view.findViewById(R.id.clientListButton);
        clientListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.add(android.R.id.content, new ClientListFragment()).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        syncButton = view.findViewById(R.id.syncButton);

        return view;
    }



}
