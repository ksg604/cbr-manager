package com.example.cbr_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AllClientsActivity extends AppCompatActivity implements ExampleItemAdapter.OnItemListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<ExampleItem> exampleItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clients);

        exampleItems = new ArrayList<>();
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "Burnaby Region"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "SFU"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "Greater Vancouver"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "SFU"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Leonhard Euler", "SFU"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Leonhard Euler", "Vancouver Region"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "Mission B.C"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "Surrey Region"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "Fraser Valley"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "SFU"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "SFU"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "SFU"));
        exampleItems.add(new ExampleItem(R.drawable.dog, "Peter Tran", "SFU"));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleItemAdapter(exampleItems, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onItemClick(int position) {
        ExampleItem exampleItem = exampleItems.get(position);
        Toast.makeText(this, "Item " + position + " selected.", Toast.LENGTH_SHORT).show();
    }
}