package com.example.cbr_manager.ui.referral.referral_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;

import java.util.ArrayList;
import java.util.List;

public class ReferralListRecyclerItemAdapter extends RecyclerView.Adapter<ReferralListRecyclerItemAdapter.ReferralItemViewHolder> implements Filterable {

    private ArrayList<ReferralListRecyclerItem> referralListRecyclerItems;
    private List<ReferralListRecyclerItem> referralListRecyclerItemsFull;
    private OnItemListener onItemListener;
    private List<ReferralListRecyclerItem> filteredReferrals;

    public ReferralListRecyclerItem getReferral(int position) {
        return filteredReferrals.get(position);
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
                    if (referralListRecyclerItem.getmType().toLowerCase().trim().contains(searchString)) {
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
        public TextView textListType;
        public TextView textListDate;
        OnItemListener onItemListener;

        public ReferralItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            textListStatus = itemView.findViewById(R.id.textListStatus);
            textListType = itemView.findViewById(R.id.textListType);
            textListDate = itemView.findViewById(R.id.textListDate);
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

        holder.textListStatus.setText(currentItem.getmStatus());
        holder.textListType.setText(currentItem.getmType());
        holder.textListDate.setText(currentItem.getmDate());
    }

    @Override
    public int getItemCount() {
        return filteredReferrals.size();
    }



}
