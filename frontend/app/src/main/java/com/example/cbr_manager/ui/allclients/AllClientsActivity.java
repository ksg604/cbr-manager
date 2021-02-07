package com.example.cbr_manager.ui.allclients;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllClientsActivity extends AppCompatActivity implements ClientRecyclerItemAdapter.OnItemListener {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter adapter;
    private ArrayList<ClientRecyclerItem> clientRecyclerItems = new ArrayList<>();

    private APIService apiService = APIService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clients);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        mLayoutManager = new LinearLayoutManager(this);
        adapter = new ClientRecyclerItemAdapter(clientRecyclerItems, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        fetchClientsToList(clientRecyclerItems);
    }

    @Override
    public void onItemClick(int position) {
        Intent clientInfoIntent = new Intent(this, ClientDetailsActivity.class);

        ClientRecyclerItem recyclerItem = clientRecyclerItems.get(position);
        clientInfoIntent.putExtra("clientId", recyclerItem.getClient().getId());

        startActivity(clientInfoIntent);
    }

    public void fetchClientsToList(List<ClientRecyclerItem> clientUIList) {
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClients().enqueue(new Callback<List<Client>>() {
                @Override
                public void onResponse(Call<List<Client>> call, Response<List<Client>> response) {
                    if (response.isSuccessful()) {
                        List<Client> clientList = response.body();
                        for (Client client : clientList) {
                            clientUIList.add(new ClientRecyclerItem(R.drawable.dog, client.getFullName(), "Burnaby Region", client));
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
}