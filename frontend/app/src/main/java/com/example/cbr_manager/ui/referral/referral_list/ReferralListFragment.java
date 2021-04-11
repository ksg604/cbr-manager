package com.example.cbr_manager.ui.referral.referral_list;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.ui.ReferralViewModel;
import com.example.cbr_manager.ui.referral.referral_details.ReferralDetailsActivity;
import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetView;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ReferralListFragment extends Fragment implements ReferralListRecyclerItemAdapter.OnItemListener{
    private static final String TAG = "ReferralListFragment";
    private RecyclerView referralListRecyclerView;
    private ReferralListRecyclerItemAdapter adapter;
    private RecyclerView.LayoutManager referralListLayoutManager;
    private SearchView searchView;
    private CheckBox checkBox;
    private int clientId = -1;
    public static String KEY_CLIENT_ID = "KEY_CLIENT_ID";
    private final int NO_SPECIFIC_CLIENT = -1;
    ArrayList<ReferralListRecyclerItem> referralRecyclerItems = new ArrayList<>();;

    private ReferralViewModel referralViewModel;

    public ReferralListFragment() {
        // Required empty public constructor
    }

    public static ReferralListFragment newInstance(int clientId) {
        ReferralListFragment fragment = new ReferralListFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CLIENT_ID, clientId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchReferralsToList(referralRecyclerItems);
        if(clientId==NO_SPECIFIC_CLIENT){
            checkBox.setChecked(true);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        referralViewModel = new ViewModelProvider(this).get(ReferralViewModel.class);

        View root = inflater.inflate(R.layout.fragment_referral_list, container, false);

        if(getArguments()!=null){
            clientId = getArguments().getInt(KEY_CLIENT_ID, -1);}

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
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        if (!preferences.getBoolean("firstTimeOutstandingReferrals", false)) {
            TapTargetView.showFor(getActivity(),
                    TapTarget.forView(root.findViewById(R.id.checkBox), "Filter clients.", "Use the checkbox to filter between all referrals and only outstanding referrals.")
                            .outerCircleAlpha(0.96f)
                            .targetCircleColor(R.color.white)
                            .titleTextSize(20)
                            .drawShadow(true)
                            .tintTarget(true)
                            .dimColor(R.color.black)
                            .targetRadius(60));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstTimeOutstandingReferrals", true);
            editor.apply();
        }

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

    public void fetchReferralsToList(ArrayList<ReferralListRecyclerItem> referralUIList) {
            referralViewModel.getReferralsAsLiveData().observe(getViewLifecycleOwner(), new Observer<List<Referral>>() {
                @Override
                public void onChanged(List<Referral> referrals) {
                    ArrayList<ReferralListRecyclerItem> referralList = new ArrayList<>();
                    for (Referral referral :
                            referrals) {
                        if (clientId == NO_SPECIFIC_CLIENT || referral.getClientId() == clientId) {
                            referralList.add(new ReferralListRecyclerItem(referral.getStatus(), referral.getServiceType(), referral.getRefer_to(), referral, referral.getDateCreated(), referral.getClientId(), referral.getFullName()));
                        }
                    }
                    adapter.setReferrals(referralList);
                    adapter.notifyDataSetChanged();
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