package com.example.cbr_manager.ui.referral.referral_list;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.ui.ReferralViewModel;
import com.example.cbr_manager.ui.referral.referral_details.ReferralDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

@AndroidEntryPoint
public class ReferralListFragment extends Fragment implements ReferralListRecyclerItemAdapter.OnItemListener{
    private static final String TAG = "ReferralListFragment";
    private RecyclerView referralListRecyclerView;
    private ReferralListRecyclerItemAdapter adapter;
    private RecyclerView.LayoutManager referralListLayoutManager;
    private SearchView searchView;
    private CheckBox checkBox;
    private int clientId = -1;
    private final int FROM_DASHBOARD = -1;
    ArrayList<ReferralListRecyclerItem> referralRecyclerItems = new ArrayList<>();;

    private APIService apiService = APIService.getInstance();
    private ReferralViewModel referralViewModel;

    @Override
    public void onResume() {
        super.onResume();
        fetchReferralsToList(referralRecyclerItems);

        if(clientId==FROM_DASHBOARD){
            checkBox.setChecked(true);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        referralViewModel = new ViewModelProvider(this).get(ReferralViewModel.class);

        View root = inflater.inflate(R.layout.fragment_referral_list, container, false);

        if(getArguments()!=null){
        clientId = getArguments().getInt("CLIENT_ID", -1);}

        referralListRecyclerView = root.findViewById(R.id.recyclerView);
        referralListRecyclerView.setHasFixedSize(true); // if we know it won't change size.
        referralListLayoutManager = new LinearLayoutManager(getContext());
        adapter = new ReferralListRecyclerItemAdapter(referralRecyclerItems, this);
        referralListRecyclerView.setLayoutManager(referralListLayoutManager);
        referralListRecyclerView.setAdapter(adapter);


        checkBox = root.findViewById(R.id.checkBox);
        searchView = root.findViewById(R.id.referralSearchView);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CharSequence newText = searchView.getQuery();
                adapter.getFilterWithCheckBox(checkBox.isChecked()).filter(newText);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilterWithCheckBox(checkBox.isChecked()).filter(newText);
                return true;
            }
        });

        return root;
    }

    public void fetchReferralsToList(List<ReferralListRecyclerItem> referralUIList) {
            referralViewModel.getReferrals().subscribe(new Observer<Referral>() {
                @Override
                public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                    referralUIList.clear();
                }

                @Override
                public void onNext(@io.reactivex.annotations.NonNull Referral referral) {
                    if (referral.getClient() == clientId | clientId < 0) {
                        Log.d(TAG, "onNext: " + referral.getId());
                        referralUIList.add(new ReferralListRecyclerItem(referral.getStatus(), referral.getServiceType(), referral.getRefer_to(), referral, referral.getDateCreated(), referral.getClient(), referral.getFullName()));
                    }
                }

                @Override
                public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                    Log.d(TAG, "onError: " + e.getMessage());
                }

                @Override
                public void onComplete() {
                    Log.d(TAG, "onComplete: ");
                    adapter.getFilterWithCheckBox(checkBox.isChecked()).filter(searchView.getQuery());
                }
            });
    }

    @Override
    public void onItemClick(int position) {
        Intent referralInfoIntent = new Intent(getContext(), ReferralDetailsActivity.class);

        ReferralListRecyclerItem referralListRecyclerItem = adapter.getReferral(position);
        referralInfoIntent.putExtra("referralId", referralListRecyclerItem.getReferral().getId());

        startActivity(referralInfoIntent);
    }
}