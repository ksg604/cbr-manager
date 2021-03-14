package com.example.cbr_manager.ui.referral.referral_list;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReferralListRecyclerItemAdapter extends RecyclerView.Adapter<ReferralListRecyclerItemAdapter.ReferralItemViewHolder> implements Filterable {

    private ArrayList<ReferralListRecyclerItem> referralListRecyclerItems;
    private List<ReferralListRecyclerItem> referralListRecyclerItemsFull;
    private OnItemListener onItemListener;
    private List<ReferralListRecyclerItem> filteredReferrals;
    private APIService apiService = APIService.getInstance();
    private boolean outstandingChecked = false;

    public ReferralListRecyclerItem getReferral(int position) {
        return filteredReferrals.get(position);
    }

    public Filter getFilterWithCheckBox(boolean outstandingChecked){
        this.outstandingChecked = outstandingChecked;
        return newfilter;
    }

    public Filter newfilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchString = constraint.toString().toLowerCase().trim();

            ArrayList<ReferralListRecyclerItem> tempFilteredList = new ArrayList<>();
            for (ReferralListRecyclerItem referralListRecyclerItem : referralListRecyclerItems) {
                if ((searchString.isEmpty()|referralListRecyclerItem.getReferTo().toLowerCase().trim().contains(searchString))&passCheckBoxTest(referralListRecyclerItem)) {
                    tempFilteredList.add(referralListRecyclerItem);
                }
            }
            filteredReferrals = tempFilteredList;
            FilterResults results = new FilterResults();
            results.values = filteredReferrals;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredReferrals = (ArrayList<ReferralListRecyclerItem>) results.values;
            notifyDataSetChanged();
        }
    };

    private boolean passCheckBoxTest(ReferralListRecyclerItem referralListRecyclerItem) {
        Boolean result = true;
        if(outstandingChecked){
            result = (!referralListRecyclerItem.getStatus().equals("RESOLVED"));
        }
        return result;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchString = constraint.toString().toLowerCase().trim();

            if (searchString.isEmpty()) {
                filteredReferrals = referralListRecyclerItems;
            } else {
                ArrayList<ReferralListRecyclerItem> tempFilteredList = new ArrayList<>();

                for (ReferralListRecyclerItem referralListRecyclerItem : referralListRecyclerItems) {
                    if (referralListRecyclerItem.getReferTo().toLowerCase().trim().contains(searchString)) {
                        tempFilteredList.add(referralListRecyclerItem);
                    }
                }
                filteredReferrals = tempFilteredList;
            }
            FilterResults results = new FilterResults();
            results.values = filteredReferrals;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredReferrals = (ArrayList<ReferralListRecyclerItem>) results.values;

            notifyDataSetChanged();
        }
    };

    public static class ReferralItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textListStatus;
        public TextView textListReferTo;
        public TextView textListType;
        public TextView textListDate;
        public TextView textListName;
        OnItemListener onItemListener;

        public ReferralItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            textListReferTo = itemView.findViewById(R.id.textListReferTo);
            textListStatus = itemView.findViewById(R.id.textListStatus);
            textListType = itemView.findViewById(R.id.textListType);
            textListDate = itemView.findViewById(R.id.textListDate);
            textListName = itemView.findViewById(R.id.textListClientName);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }

    public ReferralListRecyclerItemAdapter(ArrayList<ReferralListRecyclerItem> referralListRecyclerItems, OnItemListener onItemListener) {
        this.referralListRecyclerItems = referralListRecyclerItems;
        this.referralListRecyclerItemsFull = new ArrayList<>();
        this.referralListRecyclerItemsFull.addAll(referralListRecyclerItems);
        this.filteredReferrals = referralListRecyclerItems;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ReferralItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.referral_item, parent, false);
        ReferralItemViewHolder evh = new ReferralItemViewHolder(v, onItemListener);
        return evh;
    }



    @Override
    public void onBindViewHolder(@NonNull ReferralItemViewHolder holder, int position) {
        ReferralListRecyclerItem currentItem = filteredReferrals.get(position);
        int clientId = currentItem.getClientId();
        apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                if (response.isSuccessful()) {
                    Client client = response.body();
                    holder.textListName.setText(client.getFullName());
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });
        holder.textListStatus.setText(currentItem.getStatus());
        holder.textListReferTo.setText(currentItem.getReferTo());
        holder.textListType.setText(currentItem.getType());
        holder.textListDate.setText(currentItem.getDate());

    }

    @Override
    public int getItemCount() {
        return filteredReferrals.size();
    }



}
