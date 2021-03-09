package com.example.cbr_manager.ui.referral.referral_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.ui.referral.referral_details.ReferralDetailsActivity;
import com.example.cbr_manager.ui.referral.referral_details.ReferralDetailsFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralListFragment extends Fragment implements ReferralListRecyclerItemAdapter.OnItemListener{

    private ReferralListViewModel referralListViewModel;
    private RecyclerView mRecyclerView;
    private ReferralListRecyclerItemAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchView searchView;
    private int clientId;
    ArrayList<ReferralListRecyclerItem> referralRecyclerItems = new ArrayList<>();

    private APIService apiService = APIService.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        referralListViewModel =
                new ViewModelProvider(this).get(ReferralListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_referral_list, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            clientId = bundle.getInt("CLIENT_ID", -1);
        }
        mRecyclerView = root.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        mLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReferralListRecyclerItemAdapter(referralRecyclerItems, this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter);
        fetchReferralsToList(referralRecyclerItems);

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
            apiService.referralService.getReferrals().enqueue(new Callback<List<Referral>>() {
                @Override
                public void onResponse(Call<List<Referral>> call, Response<List<Referral>> response) {
                    if (response.isSuccessful()) {
                        List<Referral> referralList = response.body();
                        for (Referral referral : referralList) {
                            if(referral.getClient()==clientId){
                            referralUIList.add(new ReferralListRecyclerItem(referral.getStatus(), referral.getServiceType(), referral.getRefer_to(), referral, referral.getDateCreated()));
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