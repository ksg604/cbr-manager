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
import com.example.cbr_manager.ui.clientlist.ClientListRecyclerItemAdapter;
import com.example.cbr_manager.ui.createreferral.CreateReferralActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientSelectorFragment extends Fragment implements ClientListRecyclerItemAdapter.OnItemListener {

    List<Client> clientList = new ArrayList<>();
    private RecyclerView clientListRecyclerView;
    private ClientListRecyclerItemAdapter clientListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private APIService apiService = APIService.getInstance();

    private final int NEW_VISIT_CODE = 100;
    private final int NEW_REFERRAL_CODE = 101;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ClientSelectorFragment() {
        // Required empty public constructor
    }

    public static ClientSelectorFragment newInstance(String param1, String param2) {
        ClientSelectorFragment fragment = new ClientSelectorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_client_selector, container, false);

        clientListRecyclerView = root.findViewById(R.id.clientSelectorRecyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        clientListAdapter = new ClientListRecyclerItemAdapter(clientList, this);
        clientListRecyclerView.setLayoutManager(mLayoutManager);
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
                    clientListRecyclerView.setAdapter(clientListAdapter);
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
        Client client = clientList.get(position);
        int clientId = client.getId();

        if (code == NEW_REFERRAL_CODE) {
            Intent referralIntent = new Intent(getContext(), CreateReferralActivity.class);
            referralIntent.putExtra("CLIENT_ID", clientId);
            startActivity(referralIntent);
        } else {
            Intent visitsIntent = new Intent(getContext(), CreateVisitActivity.class);
            visitsIntent.putExtra("clientId", clientId);
            startActivity(visitsIntent);
        }
    }
}