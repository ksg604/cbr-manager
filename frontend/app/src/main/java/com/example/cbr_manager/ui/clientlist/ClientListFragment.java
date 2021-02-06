package com.example.cbr_manager.ui.clientlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.ui.allclients.ClientRecyclerItem;
import com.example.cbr_manager.ui.allclients.ClientRecyclerItemAdapter;

import java.util.ArrayList;

public class ClientListFragment extends Fragment implements ClientListRecyclerItemAdapter.OnItemListener{

    private ClientListViewModel clientListViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ClientListRecyclerItem> clientRecyclerItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        clientListViewModel =
                new ViewModelProvider(this).get(ClientListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_client_list, container, false);

        // Test data for now before backend implementation.
        clientRecyclerItems = new ArrayList<>();
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "Burnaby Region"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "Greater Vancouver"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "Mission B.C"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "Surrey Region"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "Fraser Valley"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientListRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ClientListRecyclerItemAdapter(clientRecyclerItems, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        return root;
    }

    @Override
    public void onItemClick(int position) {
        ClientListRecyclerItem clientListRecyclerItem = clientRecyclerItems.get(position);
        Toast.makeText(getContext(), "Item " + position + " selected.", Toast.LENGTH_SHORT).show();
    }
}