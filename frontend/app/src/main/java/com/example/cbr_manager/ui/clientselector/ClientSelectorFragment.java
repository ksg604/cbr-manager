package com.example.cbr_manager.ui.clientselector;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.baselinesurvey.BaselineSurveyStepperActivity;
import com.example.cbr_manager.ui.clientlist.ClientListRecyclerItemAdapter;
import com.example.cbr_manager.ui.createreferral.CreateReferralActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitStepperActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientSelectorFragment extends Fragment implements ClientListRecyclerItemAdapter.OnItemListener {

    List<Client> clientList = new ArrayList<>();
    private RecyclerView clientListRecyclerView;
    private ClientListRecyclerItemAdapter clientListAdapter;
    private RecyclerView.LayoutManager clientSelectorLayoutManager;
    private APIService apiService = APIService.getInstance();

    private final int NEW_VISIT_CODE = 100;
    private final int NEW_REFERRAL_CODE = 101;
    private final int NEW_BASELINE_CODE = 102;


    public ClientSelectorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_client_selector, container, false);

        clientListRecyclerView = root.findViewById(R.id.clientSelectorRecyclerView);
        clientSelectorLayoutManager = new LinearLayoutManager(getContext());
        clientListAdapter = new ClientListRecyclerItemAdapter(clientList, this);
        clientListRecyclerView.setLayoutManager(clientSelectorLayoutManager);
        clientListRecyclerView.setAdapter(clientListAdapter);

        fetchClientsToList(clientList);

        SearchView search = root.findViewById(R.id.clientSelectorSearchView);
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

        return root;
    }

    private void fetchClientsToList(List<Client> clientList) {
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClients().enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                    if (response.isSuccessful()) {
                        List<Client> clients = response.body();
                        clientList.addAll(clients);
                    }
                    clientListAdapter.notifyDataSetChanged();
//                    clientListRecyclerView.setAdapter(clientListAdapter);
                }

                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {
        int code = ((ClientSelectorActivity) getActivity()).getCode();
        Client client = clientListAdapter.getClient(position);
        int clientId = client.getId();

        if (code == NEW_REFERRAL_CODE) {
            Intent referralIntent = new Intent(getContext(), CreateReferralActivity.class);
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