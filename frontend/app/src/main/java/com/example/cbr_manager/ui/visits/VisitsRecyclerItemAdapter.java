package com.example.cbr_manager.ui.visits;

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
import java.util.Comparator;

public class VisitsRecyclerItemAdapter extends RecyclerView.Adapter<VisitsRecyclerItemAdapter.VisitItemViewHolder> implements Filterable {

    private ArrayList<VisitsRecyclerItem> visitsRecyclerItems;
    private ArrayList<VisitsRecyclerItem> visitsFilteredList;
    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchString = constraint.toString().toLowerCase().trim();

            if (searchString.isEmpty()) {
                visitsFilteredList = visitsRecyclerItems;
            } else {
                ArrayList<VisitsRecyclerItem> tempFilteredList = new ArrayList<>();

                for (VisitsRecyclerItem visitsRecyclerItem : visitsRecyclerItems) {
                    if (visitsRecyclerItem.getBodyText().toLowerCase().trim().contains(searchString)) {
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
    private onVisitClickListener onItemListener;

    public VisitsRecyclerItemAdapter(ArrayList<VisitsRecyclerItem> visitsRecyclerItems, onVisitClickListener onItemListener) {
        this.visitsRecyclerItems = visitsRecyclerItems;
        this.onItemListener = onItemListener;
        this.visitsFilteredList = visitsRecyclerItems;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public VisitsRecyclerItem getVisitItem(int position) {
        return visitsFilteredList.get(position);
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
        holder.dateTextView.setText(currentItem.getDateString());
        holder.textListBody.setText(currentItem.getBodyText());
        holder.purposeTextView.setText(currentItem.getPurposeText());
        holder.locationTextView.setText(currentItem.getLocationText());
    }

    @Override
    public int getItemCount() {
        return visitsFilteredList.size();
    }

    public interface onVisitClickListener {
        void onItemClick(int position);
    }

    private static class VisitRecyclerItemComparator implements Comparator<VisitsRecyclerItem>{

        @Override
        public int compare(VisitsRecyclerItem o1, VisitsRecyclerItem o2) {
            return 0;
        }
    }

    public static class VisitItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateTextView;
        public TextView textListBody;
        public TextView purposeTextView;
        public TextView locationTextView;
        onVisitClickListener onItemListener;

        public VisitItemViewHolder(@NonNull View itemView, onVisitClickListener onItemListener) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            textListBody = itemView.findViewById(R.id.textListBody);
            purposeTextView = itemView.findViewById(R.id.visitItemPurposeList);
            locationTextView = itemView.findViewById(R.id.textLocationVisitItem);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }
}
