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
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.VisitViewModel;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsActivity;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsFragment;
import com.example.cbr_manager.ui.visitdetails.VisitDetailsActivity;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableObserver;

@AndroidEntryPoint
public class VisitsFragment extends Fragment implements VisitsRecyclerItemAdapter.OnItemListener {
    private static final String TAG = "VisitsFragment";
    private static int NO_SPECIFIC_CLIENT = -1;
    ArrayList<VisitsRecyclerItem> visitsRecyclerItems = new ArrayList<>();
    private RecyclerView visitsRecyclerView;
    private VisitsRecyclerItemAdapter adapter;
    private RecyclerView.LayoutManager visitsLayoutManager;
    private int clientId = NO_SPECIFIC_CLIENT;

    private VisitViewModel visitViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        visitViewModel = new ViewModelProvider(this).get(VisitViewModel.class);

        int clientId = NO_SPECIFIC_CLIENT;

        FragmentActivity activity = getActivity();
        ClientDetailsActivity clientDetailsActivity;
        ClientDetailsFragment fragment;

        //If this fragment was called from ClientDetailsActivity, there will be an associated clientId
        if (activity instanceof ClientDetailsActivity) {
            clientDetailsActivity = (ClientDetailsActivity) activity;
            if (clientDetailsActivity != null) {
                fragment = (ClientDetailsFragment) clientDetailsActivity.getSupportFragmentManager().findFragmentById(R.id.fragment_client_details);
                clientId = fragment.getClientId();
            }
        }
        this.clientId = clientId;

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
        visitViewModel.getVisits().subscribe(new DisposableObserver<Visit>() {
            @Override
            public void onNext(@io.reactivex.annotations.NonNull Visit visit) {
                if (clientId == NO_SPECIFIC_CLIENT || visit.getClientId() == clientId) {
                    VisitsRecyclerItem visitsRecyclerItem = createVisitRecycleItem(visit);
                    visitUIList.add(visitsRecyclerItem);
                }
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private VisitsRecyclerItem createVisitRecycleItem(Visit visit) {
        Timestamp datetimeCreated = visit.getDatetimeCreated();
        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(datetimeCreated);
        String purpose = formatPurposeString(visit);
        return new VisitsRecyclerItem(formattedDate,
                visit.getClient().getFullName(), visit, purpose, visit.getLocationDropDown());
    }

    @NotNull
    private String formatPurposeString(@io.reactivex.annotations.NonNull Visit visit) {
        String purpose = "";
        if (visit.isCBRPurpose()) {
            purpose += "CBR ";
        }
        if (visit.isDisabilityReferralPurpose()) {
            purpose += "Disability Referral ";
        }
        if (visit.isDisabilityFollowUpPurpose()) {
            purpose += "Disability Follow up";
        }
        if (purpose.equals("")) {
            purpose = "No purpose indicated.";
        }
        return purpose;
    }


    @Override
    public void onItemClick(int position) {

        Intent visitInfoIntent = new Intent(getContext(), VisitDetailsActivity.class);
        VisitsRecyclerItem visitsRecyclerItem = adapter.getVisitItem(position);
        visitInfoIntent.putExtra(VisitDetailsActivity.KEY_VISIT_ID, visitsRecyclerItem.getId());

        startActivity(visitInfoIntent);
    }
}