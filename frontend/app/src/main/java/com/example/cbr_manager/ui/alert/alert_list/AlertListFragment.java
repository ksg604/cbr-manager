package com.example.cbr_manager.ui.alert.alert_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.ui.alert.alert_details.AlertDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlertListFragment extends Fragment implements AlertListRecyclerItemAdapter.OnItemListener{

    private AlertListViewModel alertListViewModel;
    private RecyclerView mRecyclerView;
    private AlertListRecyclerItemAdapter adapter; // TODO
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchView searchView;
    ArrayList<AlertListRecyclerItem> alertRecyclerItems = new ArrayList<>();

    private APIService apiService = APIService.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alertListViewModel =
                new ViewModelProvider(this).get(AlertListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_alert_list, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        mLayoutManager = new LinearLayoutManager(getContext());
        adapter = new AlertListRecyclerItemAdapter(alertRecyclerItems, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        fetchAlertsToList(alertRecyclerItems);

        SearchView alertSearchView = root.findViewById(R.id.alertSearchView);
        alertSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    public void fetchAlertsToList(List<AlertListRecyclerItem> alertUIList) {
        if (apiService.isAuthenticated()) {
            apiService.alertService.getAlerts().enqueue(new Callback<List<Alert>>() {
                @Override
                public void onResponse(Call<List<Alert>> call, Response<List<Alert>> response) {
                    if (response.isSuccessful()) {
                        List<Alert> alertList = response.body();
                        for (Alert alert : alertList) {
                            alertUIList.add(new AlertListRecyclerItem(alert.getTitle(), alert.getBody(), alert, alert.getDate()));
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Alert>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {

        Intent alertInfoIntent = new Intent(getContext(), AlertDetailsActivity.class);

        AlertListRecyclerItem alertListRecyclerItem = adapter.getAlert(position);
        alertInfoIntent.putExtra("alertId", alertListRecyclerItem.getAlert().getId());

        startActivity(alertInfoIntent);
    }
}