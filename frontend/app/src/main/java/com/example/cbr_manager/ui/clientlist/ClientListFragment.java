package com.example.cbr_manager.ui.clientlist;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsActivity;
import com.example.cbr_manager.ui.create_client.CreateClientStepperActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientListFragment extends Fragment implements ClientListRecyclerItemAdapter.OnItemListener {

    List<Client> clientList = new ArrayList<>();
    private RecyclerView clientListRecyclerView;
    private ClientListRecyclerItemAdapter clientListAdapter;
    private RecyclerView.LayoutManager clientListLayoutManager;
    private APIService apiService = APIService.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setUpToolBar();

        View root = inflater.inflate(R.layout.fragment_client_list, container, false);

        clientListRecyclerView = root.findViewById(R.id.recyclerView);
        clientListRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        clientListLayoutManager = new LinearLayoutManager(getContext());
        clientListAdapter = new ClientListRecyclerItemAdapter(clientList, this);
        clientListRecyclerView.setLayoutManager(clientListLayoutManager);
        clientListRecyclerView.setAdapter(clientListAdapter);

        fetchClientsToList(clientList);

        SearchView clientSearch = root.findViewById(R.id.clientSearchView);
        clientSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    public void setUpToolBar() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.client_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_client) {
            launchCreateClientActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchCreateClientActivity() {
        Intent createClientIntent = new Intent(getActivity(), CreateClientStepperActivity.class);
        startActivity(createClientIntent);
    }

    public void fetchClientsToList(List<Client> clientList) {
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClients().enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                    if (response.isSuccessful()) {
                        List<Client> clients = response.body();
                        clientList.addAll(clients);
                    }
                    clientListAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Client>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {

        Intent clientInfoIntent = new Intent(getContext(), ClientDetailsActivity.class);

        Client client = clientListAdapter.getClient(position);
        clientInfoIntent.putExtra(ClientDetailsActivity.KEY_CLIENT_ID, client.getId());

        startActivity(clientInfoIntent);
    }

}