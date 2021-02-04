package com.example.cbr_manager.ui.allclients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.cbr_manager.R;

import java.util.ArrayList;

public class AllClientsActivity extends AppCompatActivity implements ClientRecyclerItemAdapter.OnItemListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ClientRecyclerItem> clientRecyclerItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clients);

        // Test data for now before backend implementation.
        clientRecyclerItems = new ArrayList<>();
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "Burnaby Region"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "Greater Vancouver"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "Mission B.C"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "Surrey Region"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "Fraser Valley"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));
        clientRecyclerItems.add(new ClientRecyclerItem(R.drawable.dog, "Peter Tran", "SFU"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ClientRecyclerItemAdapter(clientRecyclerItems, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        ClientRecyclerItem clientRecyclerItem = clientRecyclerItems.get(position);
        Toast.makeText(this, "Item " + position + " selected.", Toast.LENGTH_SHORT).show();
    }
}