package com.example.cbr_manager.ui.client_history;

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

public class ClientHistoryRecyclerItemAdapter extends RecyclerView.Adapter<ClientHistoryRecyclerItemAdapter.ClientHistoryItemViewHolder> implements Filterable {

    private ArrayList<ClientHistoryRecyclerItem> clientHistoryRecyclerItems;
    private List<ClientHistoryRecyclerItem> clientHistoryRecyclerItemsFull;
    private OnItemListener onItemListener;
    private List<ClientHistoryRecyclerItem> filteredClientHistory;

    public ClientHistoryRecyclerItem getClientHistory(int position) {
        return filteredClientHistory.get(position);
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
                filteredClientHistory = clientHistoryRecyclerItems;
            } else {
                ArrayList<ClientHistoryRecyclerItem> tempFilteredList = new ArrayList<>();

                for (ClientHistoryRecyclerItem clientHistoryRecyclerItem : clientHistoryRecyclerItems) {
                    if (clientHistoryRecyclerItem.getValue().toLowerCase().trim().contains(searchString)) {
                        tempFilteredList.add(clientHistoryRecyclerItem);
                    }
                }
                filteredClientHistory = tempFilteredList;
            }
            FilterResults results = new FilterResults();
            results.values = filteredClientHistory;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredClientHistory = (ArrayList<ClientHistoryRecyclerItem>) results.values;

            notifyDataSetChanged();
        }
    };

    public static class ClientHistoryItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textListValue;
        public TextView textListDate;
        OnItemListener onItemListener;

        public ClientHistoryItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            textListValue = itemView.findViewById(R.id.textListValue);
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

    public ClientHistoryRecyclerItemAdapter(ArrayList<ClientHistoryRecyclerItem> clientHistoryRecyclerItems, OnItemListener onItemListener) {
        this.clientHistoryRecyclerItems = clientHistoryRecyclerItems;
        this.clientHistoryRecyclerItemsFull = new ArrayList<>();
        this.clientHistoryRecyclerItemsFull.addAll(clientHistoryRecyclerItems);
        this.filteredClientHistory = clientHistoryRecyclerItems;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ClientHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        ClientHistoryItemViewHolder evh = new ClientHistoryItemViewHolder(v, onItemListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientHistoryItemViewHolder holder, int position) {
        ClientHistoryRecyclerItem currentItem = filteredClientHistory.get(position);
        holder.textListValue.setText(currentItem.getValue());
        holder.textListDate.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return filteredClientHistory.size();
    }



}
