package com.example.cbr_manager.ui.visits;

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

public class VisitsRecyclerItemAdapter extends RecyclerView.Adapter<VisitsRecyclerItemAdapter.VisitItemViewHolder> implements Filterable {

    private ArrayList<VisitsRecyclerItem> visitsRecyclerItems;
    private ArrayList<VisitsRecyclerItem> visitsFilteredList;
    private OnItemListener onItemListener;

    @Override
    public Filter getFilter() {
        return filter;
    }

    public VisitsRecyclerItem getVisitItem(int position) {
        return visitsFilteredList.get(position);
    }

    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchString = constraint.toString().toLowerCase().trim();

            if (searchString.isEmpty()) {
                visitsFilteredList = visitsRecyclerItems;
            } else {
                ArrayList<VisitsRecyclerItem> tempFilteredList = new ArrayList<>();

                for (VisitsRecyclerItem visitsRecyclerItem : visitsRecyclerItems) {
                    if (visitsRecyclerItem.getmText2().toLowerCase().trim().contains(searchString)) {
                        tempFilteredList.add(visitsRecyclerItem);
                    }
                }
                visitsFilteredList = tempFilteredList;
            }
            FilterResults results = new FilterResults();
            results.values = visitsFilteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            visitsFilteredList = (ArrayList<VisitsRecyclerItem>) results.values;
            notifyDataSetChanged();
        }
    };

    public static class VisitItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        OnItemListener onItemListener;

        public VisitItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textListTitle);
            textView2 = itemView.findViewById(R.id.textListBody);
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

    public VisitsRecyclerItemAdapter(ArrayList<VisitsRecyclerItem> visitsRecyclerItems, OnItemListener onItemListener) {
        this.visitsRecyclerItems = visitsRecyclerItems;
        this.onItemListener = onItemListener;
        this.visitsFilteredList = visitsRecyclerItems;
    }

    @NonNull
    @Override
    public VisitItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.visit_item, parent, false);
        VisitItemViewHolder evh = new VisitItemViewHolder(v, onItemListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull VisitItemViewHolder holder, int position) {
        VisitsRecyclerItem currentItem = visitsFilteredList.get(position);

        holder.imageView.setImageResource(currentItem.getmImageResource());
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return visitsFilteredList.size();
    }
}
