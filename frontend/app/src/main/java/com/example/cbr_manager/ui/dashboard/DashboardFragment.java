package com.example.cbr_manager.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager.widget.ViewPager;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.client.ClientRiskScoreComparator;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.VisitViewModel;
import com.example.cbr_manager.ui.alert.alert_details.AlertDetailsActivity;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsEditFragment;
import com.example.cbr_manager.ui.clientselector.ClientSelectorActivity;
import com.example.cbr_manager.ui.create_client.CreateClientStepperActivity;
import com.example.cbr_manager.ui.referral.referral_list.ReferralListFragment;
import com.example.cbr_manager.utils.Helper;

import org.threeten.bp.format.FormatStyle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class DashboardFragment extends Fragment {

    private static final int NEW_VISIT_CODE = 100;
    private final APIService apiService = APIService.getInstance();
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    List<Client> clientViewPagerList;
    Alert newestAlert;
    TextView seeMoreTextView;
    TextView dateAlertTextView;
    TextView titleTextView;
    int homeAlertId;
    View root;

    private VisitViewModel visitViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visitViewModel = new ViewModelProvider(this).get(VisitViewModel.class);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_home, container, false);
        fetchNewestAlert();
        setupViewPager(root);
        setupButtons(root);
        setAlertButtons();

        fetchTopFiveRiskiestClients(clientViewPagerList);

        setupVisitStats(root);
        setupOutstandingReferralStats(root);

        return root;
    }

    private void setupOutstandingReferralStats(View root) {
        if (apiService.isAuthenticated()) {
            apiService.referralService.getReferrals().enqueue(new Callback<List<Referral>>() {
                @Override
                public void onResponse(Call<List<Referral>> call, Response<List<Referral>> response) {
                    if (response.isSuccessful()) {
                        List<Referral> referrals = response.body();
                        int createdReferrals = 0;
                        int completedReferrals = 0;
                        String status;
                        for (Referral referral : referrals) {
                            status = referral.getStatus();

                            if (status.toLowerCase().trim().equals("created")) {
                                createdReferrals++;
                            } else if (status.toLowerCase().trim().equals("resolved")) {
                                completedReferrals++;
                            }
                        }

//                        fillTopThreeOutstandingReferrals(referrals);

                        TextView createdReferralsTextView = root.findViewById(R.id.dashboardOutstandingReferralsNumTextView);
                        createdReferralsTextView.setText(Integer.toString(createdReferrals));
                        TextView completedReferralsTextView = root.findViewById(R.id.dashboardCompletedReferralsNumTextView);
                        completedReferralsTextView.setText(Integer.toString(completedReferrals));
                    }
                }

                @Override
                public void onFailure(Call<List<Referral>> call, Throwable t) {

                }
            });
        }
    }

    private void fillTopThreeOutstandingReferrals(List<Referral> referrals) {
        if (referrals.size() == 0) {
            // Fill nothing.
            return;
        } else if (referrals.size() == 1) {
            LinearLayout outStandingReferral1 = getView().findViewById(R.id.outstandingReferralPerson1);
            outStandingReferral1.setVisibility(View.VISIBLE);
        } else if (referrals.size() == 2) {
            LinearLayout outstandingReferral1 = getView().findViewById(R.id.outstandingReferralPerson1);
            LinearLayout outstandingReferral2 = getView().findViewById(R.id.outstandingReferralPerson2);
            outstandingReferral1.setVisibility(View.VISIBLE);
            outstandingReferral2.setVisibility(View.VISIBLE);
        } else {
            LinearLayout outstandingReferral1 = getView().findViewById(R.id.outstandingReferralPerson1);
            LinearLayout outstandingReferral2 = getView().findViewById(R.id.outstandingReferralPerson2);
            LinearLayout outstandingReferral3 = getView().findViewById(R.id.outstandingReferralPerson3);
            outstandingReferral1.setVisibility(View.VISIBLE);
            outstandingReferral2.setVisibility(View.VISIBLE);
            outstandingReferral3.setVisibility(View.VISIBLE);
        }
    }

    private void setupOutstandingReferralCard(int imageId, int nameTextViewId, int serviceTextViewId, int dateTextViewId, String name, String service, String date) {
        TextView nameTextView = getView().findViewById(nameTextViewId);
        nameTextView.setText(name);
        TextView serviceTextView = getView().findViewById(serviceTextViewId);
        serviceTextView.setText(service);
    }

    private void setupVisitStats(View root) {
        visitViewModel.getVisitsAsLiveData().observe(getViewLifecycleOwner(), visits -> {
            int totalVisits = visits.size();

            TextView totalNumberVisits = root.findViewById(R.id.dashboardTotalVisitsNum);
            totalNumberVisits.setText(Integer.toString(totalVisits));
            List<String> differentLocations = new ArrayList<>();
            List<Integer> differentClients = new ArrayList<>();
            for (Visit eachVisit : visits) {
                if (!differentClients.contains(eachVisit.getClientId())) {
                    differentClients.add(eachVisit.getClientId());
                }
                if (!differentLocations.contains(eachVisit.getLocationDropDown())) {
                    differentLocations.add(eachVisit.getLocationDropDown());
                }
            }

            TextView totalClientsVisited = root.findViewById(R.id.dashboardClientsVisitedNum);
            totalClientsVisited.setText(Integer.toString(differentClients.size()));

            TextView totalLocationsVisited = root.findViewById(R.id.dashboardLocationsNum);
            totalLocationsVisited.setText(Integer.toString(differentLocations.size()));
        });
    }

    public void fetchNewestAlert() {
        dateAlertTextView = root.findViewById(R.id.dateAlertTextView);
        titleTextView = root.findViewById(R.id.announcementTextView);
        if (apiService.isAuthenticated()) {
            apiService.alertService.getAlerts().enqueue(new Callback<List<Alert>>() {
                @Override
                public void onResponse(Call<List<Alert>> call, Response<List<Alert>> response) {
                    if (response.isSuccessful()) {
                        List<Alert> alerts = response.body();

                        if (alerts != null & !alerts.isEmpty()) {
                            newestAlert = alerts.get(0);
                            dateAlertTextView.setText("Date posted:  " + Helper.formatDateTimeToLocalString(newestAlert.getDate(), FormatStyle.SHORT));
                            titleTextView.setText(newestAlert.getTitle());
                            homeAlertId = newestAlert.getId();
                        }

                    }
                }

                @Override
                public void onFailure(Call<List<Alert>> call, Throwable t) {

                }
            });
        }
    }

    public void setAlertButtons() {
        seeMoreTextView = root.findViewById(R.id.seeAllTextView);
        TextView moreTextView = root.findViewById(R.id.dashboardAlertsMoreTextView);
        moreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AlertDetailsActivity.class);
                intent.putExtra("alertId", homeAlertId);
                startActivity(intent);
            }
        });
        seeMoreTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DashboardFragment.this)
                        .navigate(R.id.action_nav_dashboard_to_nav_alert_list);
            }
        });
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
        TextView allClientsTextView = root.findViewById(R.id.dashboardAllClientsTextView);
        allClientsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DashboardFragment.this)
                        .navigate(R.id.action_nav_dashboard_to_nav_client_list);
            }
        });

        TextView addClientTextView = root.findViewById(R.id.dashaboardAddClientTextView);
        addClientTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateClientStepperActivity.class);
                startActivity(intent);
            }
        });

        TextView newVisitTextView = root.findViewById(R.id.dashboardNewVisitTextView);
        newVisitTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ClientSelectorActivity.class);
                intent.putExtra("CODE", NEW_VISIT_CODE);
                startActivity(intent);
            }
        });

        TextView allOutstandingReferralsTextView = root.findViewById(R.id.allOutstandingReferralsTextView);
        allOutstandingReferralsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavHostFragment.findNavController(DashboardFragment.this)
                        .navigate(R.id.action_nav_dashboard_to_nav_referral_list);
            }
        });
    }

    private void setupViewPager(View root) {
        clientViewPagerList = new ArrayList<>();
        adapter = new ViewPagerAdapter(getContext(), this.getActivity(), clientViewPagerList);
        viewPager = root.findViewById(R.id.clientPriorityList);
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(220, 0, 220, 0);
    }


}