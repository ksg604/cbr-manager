package com.example.cbr_manager.ui.visits;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsActivity;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsFragment;
import com.example.cbr_manager.ui.visitdetails.VisitDetailsActivity;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitsFragment extends Fragment implements VisitsRecyclerItemAdapter.OnItemListener{

    private VisitsViewModel visitsViewModel;
    private RecyclerView visitsRecyclerView;
    private VisitsRecyclerItemAdapter adapter;
    private RecyclerView.LayoutManager visitsLayoutManager;
    private static int NO_SPECIFIC_CLIENT = -1;
    private int clientId = NO_SPECIFIC_CLIENT;
    ArrayList<VisitsRecyclerItem> visitsRecyclerItems = new ArrayList<>();

    private APIService apiService = APIService.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        int clientId = NO_SPECIFIC_CLIENT;

        FragmentActivity activity = getActivity();
        ClientDetailsActivity clientDetailsActivity;
        ClientDetailsFragment fragment;

        //If this fragment was called from ClientDetailsActivity, there will be an associated clientId
        if (activity instanceof ClientDetailsActivity) {
            clientDetailsActivity = (ClientDetailsActivity) activity;
            if (clientDetailsActivity != null) {
                fragment = (ClientDetailsFragment)clientDetailsActivity.getSupportFragmentManager().findFragmentById(R.id.fragment_client_details);
                clientId = fragment.getClientId();
            }
        }
        this.clientId = clientId;
        visitsViewModel =
                new ViewModelProvider(this).get(VisitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visits, container, false);

        visitsRecyclerView = root.findViewById(R.id.recyclerView);
        visitsRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        visitsLayoutManager = new LinearLayoutManager(getContext());
        adapter = new VisitsRecyclerItemAdapter(visitsRecyclerItems, this);
        visitsRecyclerView.setLayoutManager(visitsLayoutManager);
        visitsRecyclerView.setAdapter(adapter);

        fetchVisitsToList(visitsRecyclerItems);

        SearchView visitSearch = root.findViewById(R.id.visitSearchView);
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

        return root;
    }
    public void fetchVisitsToList(List<VisitsRecyclerItem> visitUIList) {
        if (apiService.isAuthenticated()) {
            apiService.visitService.getVisits().enqueue(new Callback<List<Visit>>() {
                @Override
                public void onResponse(Call<List<Visit>> visitCall, Response<List<Visit>> response) {
                    if (response.isSuccessful()) {
                        List<Visit> visitList = response.body();
                        for (Visit visit : visitList) {
                            int currClientID = visit.getClientId();

                            if (clientId == NO_SPECIFIC_CLIENT || visit.getClientId() == clientId) {
                                Call<Client> clientIdCall = apiService.clientService.getClient(currClientID);
                                clientIdCall.enqueue(new Callback<Client>() {
                                    @Override
                                    public void onResponse(Call<Client> clientCall, Response<Client> response) {
                                        if (response.isSuccessful()) {
                                            Client client = response.body();
                                            visit.setClient(client);
                                            Timestamp datetimeCreated = visit.getDatetimeCreated();
                                            Format formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                                            String formattedDate = formatter.format(datetimeCreated);
                                            visitUIList.add(new VisitsRecyclerItem(R.drawable.visit_default_pic, formattedDate, visit.getClient().getFullName(), visit));
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                    @Override
                                    public void onFailure(Call<Client> call, Throwable t) {
                                    }
                                });
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Visit>> call, Throwable t) {

                }
            });
        }
    }


    @Override
    public void onItemClick(int position) {

        Intent visitInfoIntent = new Intent(getContext(), VisitDetailsActivity.class);
        VisitsRecyclerItem visitsRecyclerItem = adapter.getVisitItem(position);
        visitInfoIntent.putExtra(VisitDetailsActivity.KEY_VISIT_ID, visitsRecyclerItem.getId());

        startActivity(visitInfoIntent);
    }
}