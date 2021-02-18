package com.example.cbr_manager.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientRiskScoreComparator;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.create_client.CreateClientActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private final APIService apiService = APIService.getInstance();
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    List<Client> clientViewPagerList = new ArrayList<>();
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        setupViewPager(root, clientViewPagerList);
        setupButtons(root);

        fetchTopFiveRiskiestClients(clientViewPagerList);

        setupVisitStats(root);

        return root;
    }

    private void setupVisitStats(View root) {
        if (apiService.isAuthenticated()) {
            apiService.visitService.getVisits().enqueue(new Callback<List<Visit>>() {
                @Override
                public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                    if (response.isSuccessful()) {
                        List<Visit> visits = response.body();
                        int totalVisits = visits.size();

                        TextView totalNumberVisits = root.findViewById(R.id.totalVisitsNumberTextView);
                        totalNumberVisits.setText(Integer.toString(totalVisits));
                        List<String> differentLocations = new ArrayList<>();
                        List<Integer> differentClients = new ArrayList<>();
                        for (Visit eachVisit : visits) {
                            if (!differentClients.contains(eachVisit.getClientID())) {
                                differentClients.add(eachVisit.getClientID());
                            }
                            if (!differentLocations.contains(eachVisit.getClient().getLocationDropDown())) {
                                differentLocations.add(eachVisit.getClient().getLocationDropDown());
                            }
                        }

                        TextView totalClientsVisited = root.findViewById(R.id.clientsVisitedNumberTextView);
                        totalClientsVisited.setText(Integer.toString(differentClients.size()));

                        TextView totalLocationsVisited = root.findViewById(R.id.regionsVisitedNumberTextView);
                        totalLocationsVisited.setText(Integer.toString(differentLocations.size()));
                    }
                }

                @Override
                public void onFailure(Call<List<Visit>> call, Throwable t) {

                }
            });
        }
    }

    public void fetchTopFiveRiskiestClients(List<Client> clientList) {
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClients().enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                    if (response.isSuccessful()) {
                        List<Client> clients = response.body();

                        if (clients != null & !clients.isEmpty()) {
                            Collections.sort(clients, new ClientRiskScoreComparator(ClientRiskScoreComparator.SortOrder.DESCENDING));

                            List<Client> topFiveClients = null;
                            if (clients.size() > 5) {
                                topFiveClients = clients.subList(0, 5);
                            } else {
                                topFiveClients = clients;
                            }

                            clientList.addAll(topFiveClients);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {

                }
            });
        }
    }

    private void setupButtons(View root) {
        Button allClientsButton = (Button) root.findViewById(R.id.allClientsButton);
        allClientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(HomeFragment.this)
                        .navigate(R.id.action_nav_dashboard_to_nav_client_list);
            }
        });

        Button button = (Button) root.findViewById(R.id.addClientButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateClientActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupViewPager(View root, List<Client> clientList) {
        adapter = new ViewPagerAdapter(getContext(), this.getActivity(), clientList);
        viewPager = root.findViewById(R.id.clientPriorityList);
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(220, 0, 220, 0);
    }


}