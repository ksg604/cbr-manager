package com.example.cbr_manager.ui.homepage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toolbar;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.ui.clientselector.ClientSelectorActivity;
import com.example.cbr_manager.ui.create_client.CreateClientStepperActivity;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.example.cbr_manager.ui.map.MapActivity;


public class HomepageFragment extends Fragment {
    private APIService apiService = APIService.getInstance();
    private CardView newClientCard, dashboardCard, clientListCard, surveyCard, newVisitCard, newReferralCard, syncCard, mapCard;
    private ImageButton newSurvey;
    private final int NEW_VISIT_CODE = 100;
    private final int NEW_REFERRAL_CODE = 101;
    private final int NEW_BASELINE_CODE = 102;

    View view;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_homepage, container, false);

        newClientCard = view.findViewById(R.id.newClientCardView);
        newClientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), CreateClientStepperActivity.class);
                startActivity(intent);
            }
        });

        newVisitCard = view.findViewById(R.id.newVisitCardView);
        newVisitCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ClientSelectorActivity.class);
                intent.putExtra("CODE", NEW_VISIT_CODE);
                startActivity(intent);
            }
        });

        dashboardCard = view.findViewById(R.id.dashboardCardView);
        dashboardCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomepageFragment.this)
                        .navigate(R.id.action_nav_home_to_nav_dashboard);
            }
        });

        newReferralCard = view.findViewById(R.id.newReferralCardView);
        newReferralCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), ClientSelectorActivity.class);
                intent.putExtra("CODE", NEW_REFERRAL_CODE);
                startActivity(intent);
            }
        });

        clientListCard = view.findViewById(R.id.clientListCardView);
        clientListCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomepageFragment.this)
                        .navigate(R.id.action_nav_home_to_nav_client_list);
            }
        });

        syncCard = view.findViewById(R.id.syncCardView);
        syncCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        surveyCard = view.findViewById(R.id.baselineSurveyCardView);
        surveyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClientSelectorActivity.class);
                intent.putExtra("CODE", NEW_BASELINE_CODE);
                startActivity(intent);
            }
        });

        mapCard = view.findViewById(R.id.mapCardView);
        mapCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
