package com.example.cbr_manager.ui.referral.referral_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.ui.referral.referral_details.ReferralDetailsActivity;
import com.example.cbr_manager.ui.referral.referral_details.ReferralDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralListFragment extends Fragment implements ReferralListRecyclerItemAdapter.OnItemListener{

    private RecyclerView referralListecyclerView;
    private ReferralListRecyclerItemAdapter adapter;
    private RecyclerView.LayoutManager referralListLayoutManager;
    private SearchView searchView;
    private int clientId=-1;
    ArrayList<ReferralListRecyclerItem> referralRecyclerItems = new ArrayList<>();;

    private APIService apiService = APIService.getInstance();

    @Override
    public void onResume() {
        super.onResume();
        fetchReferralsToList(referralRecyclerItems);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_referral_list, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            clientId = bundle.getInt("CLIENT_ID", -1);
        }
        referralListecyclerView = root.findViewById(R.id.recyclerView);
        referralListecyclerView.setHasFixedSize(true); // if we know it won't change size.
        referralListLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReferralListRecyclerItemAdapter(referralRecyclerItems, this);
        referralListecyclerView.setLayoutManager(referralListLayoutManager);
        referralListecyclerView.setAdapter(adapter);

        SearchView referralSearchView = root.findViewById(R.id.referralSearchView);
        referralSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    public void fetchReferralsToList(List<ReferralListRecyclerItem> referralUIList) {
        if (apiService.isAuthenticated()) {
            referralUIList.clear();
            adapter.notifyDataSetChanged();
            apiService.referralService.getReferrals().enqueue(new Callback<List<Referral>>() {
                @Override
                public void onResponse(Call<List<Referral>> call, Response<List<Referral>> response) {
                    if (response.isSuccessful()) {
                        List<Referral> referralList = response.body();
                        for (Referral referral : referralList) {
                            if(referral.getClient()==clientId| clientId==-1){
                            referralUIList.add(new ReferralListRecyclerItem(referral.getStatus(), referral.getServiceType(), referral.getRefer_to(), referral, referral.getDateCreated(),referral.getClient()));
                        }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<List<Referral>> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onItemClick(int position) {

        Intent referralInfoIntent = new Intent(getContext(), ReferralDetailsActivity.class);

        ReferralListRecyclerItem referralListRecyclerItem = adapter.getReferral(position);
        referralInfoIntent.putExtra("referralId", referralListRecyclerItem.getReferral().getId());

        startActivity(referralInfoIntent);
    }
}