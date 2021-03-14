package com.example.cbr_manager.ui.alert.alert_list;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;

import java.util.ArrayList;
import java.util.List;

public class AlertListRecyclerItemAdapter extends RecyclerView.Adapter<AlertListRecyclerItemAdapter.AlertItemViewHolder> implements Filterable {

    private ArrayList<AlertListRecyclerItem> alertListRecyclerItems;
    private List<AlertListRecyclerItem> alertListRecyclerItemsFull;
    private OnItemListener onItemListener;
    private List<AlertListRecyclerItem> filteredAlerts;

    public AlertListRecyclerItem getAlert(int position) {
        return filteredAlerts.get(position);
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
                filteredAlerts = alertListRecyclerItems;
            } else {
                ArrayList<AlertListRecyclerItem> tempFilteredList = new ArrayList<>();

                for (AlertListRecyclerItem alertListRecyclerItem : alertListRecyclerItems) {
                    if (alertListRecyclerItem.getmyTitle().toLowerCase().trim().contains(searchString) | alertListRecyclerItem.getmyBody().toLowerCase().trim().contains(searchString)) {
                        tempFilteredList.add(alertListRecyclerItem);
                    }
                }
                filteredAlerts = tempFilteredList;
            }
            FilterResults results = new FilterResults();
            results.values = filteredAlerts;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredAlerts = (ArrayList<AlertListRecyclerItem>) results.values;

            notifyDataSetChanged();
        }
    };

    public static class AlertItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textListTitle;
        public TextView textListBody;
        public TextView textListDate;
        OnItemListener onItemListener;

        public AlertItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            textListTitle = itemView.findViewById(R.id.textListTitle);
            textListBody = itemView.findViewById(R.id.textListBody);
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

    public AlertListRecyclerItemAdapter(ArrayList<AlertListRecyclerItem> alertListRecyclerItems, OnItemListener onItemListener) {
        this.alertListRecyclerItems = alertListRecyclerItems;
        this.alertListRecyclerItemsFull = new ArrayList<>();
        this.alertListRecyclerItemsFull.addAll(alertListRecyclerItems);
        this.filteredAlerts = alertListRecyclerItems;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public AlertItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_item, parent, false);
        AlertItemViewHolder evh = new AlertItemViewHolder(v, onItemListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlertItemViewHolder holder, int position) {
        AlertListRecyclerItem currentItem = filteredAlerts.get(position);

        holder.textListTitle.setText(currentItem.getmyTitle());
        holder.textListBody.setText(currentItem.getmyBody());
        holder.textListDate.setText(currentItem.getmyDate());
    }

    @Override
    public int getItemCount() {
        return filteredAlerts.size();
    }



}
