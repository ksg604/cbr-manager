package com.example.cbr_manager.ui.clientselector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.ClientViewModel;
import com.example.cbr_manager.ui.baselinesurvey.BaselineSurveyStepperActivity;
import com.example.cbr_manager.ui.clientlist.ClientListRecyclerItemAdapter;
import com.example.cbr_manager.ui.createreferral.CreateReferralStepperActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitStepperActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ClientSelectorFragment extends Fragment implements ClientListRecyclerItemAdapter.OnItemClickListener {

    private static final String TAG = ClientSelectorFragment.class.getName();
    private static final int NEW_VISIT_CODE = 100;
    private static final int NEW_REFERRAL_CODE = 101;
    private static final int NEW_BASELINE_CODE = 102;
    private ClientListRecyclerItemAdapter clientListAdapter;
    private ClientViewModel clientViewModel;

    public ClientSelectorFragment() {
        super(R.layout.fragment_client_selector);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clientListAdapter = new ClientListRecyclerItemAdapter(this);

        setupClientSearchView(view, clientListAdapter);

        populateClientListAdapter(clientListAdapter);

        SearchView search = view.findViewById(R.id.clientSelectorSearchView);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                clientListAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void setupClientSearchView(View view, ClientListRecyclerItemAdapter clientListAdapter) {
        RecyclerView clientListRecyclerView = view.findViewById(R.id.clientSelectorRecyclerView);
        RecyclerView.LayoutManager clientSelectorLayoutManager = new LinearLayoutManager(getContext());
        clientListRecyclerView.setLayoutManager(clientSelectorLayoutManager);
        clientListRecyclerView.setAdapter(clientListAdapter);
    }

    private void populateClientListAdapter(ClientListRecyclerItemAdapter clientListAdapter) {
        clientViewModel.getAllClients().observe(getViewLifecycleOwner(), clientList1 -> {
            List<Client> clientList = new ArrayList<>();
            for (int i = 0; i < clientList1.size(); i++) {
                int code = ((ClientSelectorActivity) getActivity()).getCode();
                if (code == NEW_BASELINE_CODE && clientList1.get(i).isBaselineSurveyTaken()) {
                } else {
                    clientList.add(clientList1.get(i));
                }
            }
            clientListAdapter.setClients(clientList);
        });
    }

    @Override
    public void onItemClick(int position) {
        int code = ((ClientSelectorActivity) getActivity()).getCode();
        Client client = clientListAdapter.getClient(position);
        int clientId = client.getId();

        if (code == NEW_REFERRAL_CODE) {
            Intent referralIntent = new Intent(getContext(), CreateReferralStepperActivity.class);
            referralIntent.putExtra("CLIENT_ID", clientId);
            startActivity(referralIntent);
            getActivity().finish();
        } else if (code == NEW_VISIT_CODE) {
            Intent visitsIntent = new Intent(getContext(), CreateVisitStepperActivity.class);
            visitsIntent.putExtra("clientId", clientId);
            startActivity(visitsIntent);
            getActivity().finish();
        } else if (code == NEW_BASELINE_CODE) {
            Intent baselineIntent = new Intent(getContext(), BaselineSurveyStepperActivity.class);
            baselineIntent.putExtra("CLIENT_ID", clientId);
            startActivity(baselineIntent);
            getActivity().finish();

        }
    }
}