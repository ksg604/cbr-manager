package com.example.cbr_manager.ui.visits;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.VisitViewModel;
import com.example.cbr_manager.ui.visitdetails.VisitDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class VisitsFragment extends Fragment implements VisitRecyclerAdapter.onVisitClickListener {
    public static final String KEY_CLIENT_ID = "KEY_CLIENT_ID";
    private static final String TAG = "VisitsFragment";
    private static final int NO_SPECIFIC_CLIENT = -1;
    private VisitRecyclerAdapter adapter;
    private int clientId;
    private VisitViewModel visitViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        visitViewModel = new ViewModelProvider(this).get(VisitViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_visits, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clientId = getClientIDFromArgs();

        RecyclerView visitsRecyclerView = view.findViewById(R.id.recyclerView);
        visitsRecyclerView.setHasFixedSize(true); // if we know it won't change size.

        RecyclerView.LayoutManager visitsLayoutManager = new LinearLayoutManager(getContext());
        adapter = new VisitRecyclerAdapter(this);
        visitsRecyclerView.setLayoutManager(visitsLayoutManager);
        visitsRecyclerView.setAdapter(adapter);

        fetchVisitsToList();

        SearchView visitSearch = view.findViewById(R.id.visitSearchView);
        visitSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
    }

    private int getClientIDFromArgs() {
        if (getArguments() != null) {
            return this.getArguments().getInt(KEY_CLIENT_ID, NO_SPECIFIC_CLIENT);
        } else {
            return NO_SPECIFIC_CLIENT;
        }
    }

    public void fetchVisitsToList() {
        visitViewModel.getVisitsAsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Visit>>() {
            @Override
            public void onChanged(List<Visit> visits) {
                List<Visit> visitList = new ArrayList<>();
                for (Visit visit :
                        visits) {
                    if (clientId == NO_SPECIFIC_CLIENT || visit.getClientId() == clientId) {
                        visitList.add(visit);
                    }
                }
                adapter.setVisits(visitList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent visitInfoIntent = new Intent(getContext(), VisitDetailsActivity.class);
        Visit visit = adapter.getVisitItem(position);
        visitInfoIntent.putExtra(VisitDetailsActivity.KEY_VISIT_ID, visit.getId());
        startActivity(visitInfoIntent);
    }
}