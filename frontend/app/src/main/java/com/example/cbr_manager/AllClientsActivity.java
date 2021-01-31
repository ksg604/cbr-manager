package com.example.cbr_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class AllClientsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_clients);

        ArrayList<ExampleItem> exampleItems = new ArrayList<>();
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
        mAdapter = new ExampleItemAdapter(exampleItems);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}