package com.example.cbr_manager.ui.visits;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitsPerClientFragment extends Fragment implements VisitsRecyclerItemAdapter.OnItemListener{

    private VisitsViewModel visitsViewModel;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int clientId = -1;
    ArrayList<VisitsRecyclerItem> visitsRecyclerItems = new ArrayList<>();

    private APIService apiService = APIService.getInstance();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        int clientId = -1;

        ClientDetailsActivity activity = (ClientDetailsActivity) getActivity();
        ClientDetailsFragment fragment = (ClientDetailsFragment)activity.getSupportFragmentManager().findFragmentById(R.id.fragment_client_details);

        if (activity != null)
            clientId = fragment.getClientId();

        this.clientId = clientId;
        visitsViewModel =
                new ViewModelProvider(this).get(VisitsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_visits, container, false);

        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        mLayoutManager = new LinearLayoutManager(getContext());
        adapter = new VisitsRecyclerItemAdapter(visitsRecyclerItems, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);

        fetchVisitsToList(visitsRecyclerItems);

        return root;
    }

    public void fetchVisitsToList(List<VisitsRecyclerItem> visitUIList) {
        if (apiService.isAuthenticated()) {
            apiService.visitService.getVisits().enqueue(new Callback<List<Visit>>() {
                @Override
                public void onResponse(Call<List<Visit>> call, Response<List<Visit>> response) {
                    if (response.isSuccessful()) {
                        List<Visit> visitList = response.body();
                        for (Visit visit : visitList) {
                            if (visit.getClientID() == clientId) {
                                Call<Client> call1 = apiService.clientService.getClient(clientId);
                                call1.enqueue(new Callback<Client>() {
                                    @Override
                                    public void onResponse(Call<Client> call, Response<Client> response) {
                                        if (response.isSuccessful()) {
                                            Client client = response.body();
                                            visit.setClient(client);
                                            visitUIList.add(new VisitsRecyclerItem(R.drawable.dog, visit.getClient().getFullName(), String.valueOf(visit.getClientID()), visit));
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

        VisitsRecyclerItem visitsRecyclerItem = visitsRecyclerItems.get(position);
        visitInfoIntent.putExtra("additionalInfo", visitsRecyclerItem.getVisit().getAdditionalInfo());
        visitInfoIntent.putExtra("clientId", visitsRecyclerItem.getVisit().getClientID());

        startActivity(visitInfoIntent);
    }
}