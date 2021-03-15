package com.example.cbr_manager.ui.client_history;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.ClientHistoryRecord;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientHistoryFragment extends Fragment implements ClientHistoryRecyclerItemAdapter.OnItemListener{

    private RecyclerView clientHistoryRecyclerView;
    private ClientHistoryRecyclerItemAdapter adapter;
    private RecyclerView.LayoutManager clientHistoryLayoutManager;
    private SearchView searchView;
    private int clientId;
    private String field;
    ArrayList<ClientHistoryRecyclerItem> clientHistoryRecyclerItems = new ArrayList<>();

    private APIService apiService = APIService.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_client_history, container, false);

        clientHistoryRecyclerView = root.findViewById(R.id.recyclerView);
        clientHistoryRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        clientHistoryLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ClientHistoryRecyclerItemAdapter(clientHistoryRecyclerItems, this);
        clientHistoryRecyclerView.setLayoutManager(clientHistoryLayoutManager);
        clientHistoryRecyclerView.setAdapter(adapter);

        this.clientId =getArguments().getInt("clientId", -1);
        this.field = getArguments().getString("field","");
        fetchClientHistorysToList(clientHistoryRecyclerItems);

        SearchView clientHistorySearchView = root.findViewById(R.id.clientHistorySearchView);
        clientHistorySearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return root;
    }

    public void fetchClientHistorysToList(List<ClientHistoryRecyclerItem> clientHistoryUIList) {
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClientHistoryRecords(clientId).enqueue(new Callback<List<ClientHistoryRecord>>() {
                @Override
                public void onResponse(Call<List<ClientHistoryRecord>> call, Response<List<ClientHistoryRecord>> response) {
                    if (response.isSuccessful()) {
                        List<ClientHistoryRecord> clientHistory = response.body();
                        for (ClientHistoryRecord record : clientHistory) {
                            if(record.getField().equals(field)){
                                clientHistoryUIList.add(0,new ClientHistoryRecyclerItem(record.getNewValue(),record.getFormattedDate()));
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<ClientHistoryRecord>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {

    }
}