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
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.visit.Visit;
import com.example.cbr_manager.ui.alert.alert_list.AlertListRecyclerItem;
import com.example.cbr_manager.utils.Helper;

import org.threeten.bp.Instant;
import org.threeten.bp.format.FormatStyle;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class VisitRecyclerAdapter extends RecyclerView.Adapter<VisitRecyclerAdapter.VisitItemViewHolder> implements Filterable {

    private final onVisitClickListener onItemListener;
    private List<Visit> visitList;
    private List<Visit> visitFilteredList;
    private String purposeTag = "";
    private String provisionTag = "";

    public Filter getFilterWithTags(String purpose, String provision) {
        provisionTag = provision;
        purposeTag = purpose;
        return filter;
    }

    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchString = constraint.toString().toLowerCase().trim();

            List<Visit> tempFilteredList = new ArrayList<>();

            if (searchString.isEmpty()&provisionTag.isEmpty()&purposeTag.equals("All")) {
                tempFilteredList.addAll(visitList);
            } else {
                for (Visit visit : visitList) {
                    Client client = visit.getClient();
                    if (client.getFullName().toLowerCase().trim().contains(searchString)&visit.getProvisionText().toLowerCase().trim().contains(provisionTag)) {
                        boolean pass=false;
                        switch(purposeTag){
                            case "All":
                                pass = true;
                                break;
                            case "CBR":
                                if(visit.isCBRPurpose()){
                                    pass = true;
                                }
                                break;
                            case "Disability Referral":
                                if(visit.isDisabilityReferralPurpose()){
                                    pass = true;
                                }
                                break;
                            case "Disability Follow Up":
                                if(visit.isDisabilityFollowUpPurpose()){
                                    pass = true;
                                }
                                break;
                        }
                        if(pass){
                            tempFilteredList.add(visit);
                        }
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = tempFilteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            visitFilteredList = (List<Visit>) results.values;
            visitFilteredList.sort(new VisitByDateComparator().reversed());
            notifyDataSetChanged();
        }
    };

    public VisitRecyclerAdapter(onVisitClickListener onItemListener) {
        this.visitFilteredList = new ArrayList<>();
        this.onItemListener = onItemListener;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public Visit getVisitItem(int position) {
        return visitFilteredList.get(position);
    }

    @NonNull
    @Override
    public VisitItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.visit_item, parent, false);
        return new VisitItemViewHolder(v, onItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitItemViewHolder holder, int position) {
        Visit visit = visitFilteredList.get(position);
        holder.dateTextView.setText(Helper.formatDateTimeToLocalString(visit.getCreatedAt(), FormatStyle.SHORT));
        holder.fullnameTextView.setText(visit.getClient().getFullName());
        holder.purposeTextView.setText(formatPurposeString(visit));
        holder.provisionTextView.setText(visit.getLocationDropDown());
    }

    private String formatPurposeString(Visit visit) {
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
    public int getItemCount() {
        return visitFilteredList.size();
    }

    public void setVisits(List<Visit> visitList) {
        visitList.sort(new VisitByDateComparator().reversed());
        this.visitList = visitList;
        this.visitFilteredList = new ArrayList<>(visitList);
    }

    public interface onVisitClickListener {
        void onItemClick(int position);
    }

    private static class VisitByDateComparator implements Comparator<Visit> {
        @Override
        public int compare(Visit o1, Visit o2) {
            Instant o1Date = Instant.parse(o1.getCreatedAt());
            Instant o2Date = Instant.parse(o2.getCreatedAt());
            return o1Date.compareTo(o2Date);
        }
    }

    public static class VisitItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView dateTextView;
        public TextView fullnameTextView;
        public TextView purposeTextView;
        public TextView provisionTextView;
        onVisitClickListener onItemListener;

        public VisitItemViewHolder(@NonNull View itemView, onVisitClickListener onItemListener) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.textViewDate);
            fullnameTextView = itemView.findViewById(R.id.textViewFullName);
            purposeTextView = itemView.findViewById(R.id.visitItemPurposeList);
            provisionTextView = itemView.findViewById(R.id.textLocationVisitItem);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }
}
