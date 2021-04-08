package com.example.cbr_manager.ui.clientlist;

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
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.utils.Helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ClientListItemAdapter extends RecyclerView.Adapter<ClientListItemAdapter.ClientItemViewHolder> implements Filterable {

    private List<Client> clients;
    private List<Client> filteredClientList;
    private OnItemClickListener onItemClickListener;
    private String genderTag = "";
    private String locationTag = "";
    private String disabilityTag = "";
    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchString = constraint.toString().toLowerCase().trim();

            List<Client> tempFilteredList = new ArrayList<>();

            if (searchString.isEmpty()) {
                for (Client client : clients) {
                    if (passTagFilterTest(client)) {
                        tempFilteredList.add(client);
                    }
                }
            } else {
                for (Client client : clients) {
                    if ((client.getFullName().toLowerCase().contains(searchString) | client.getCbrClientId().toLowerCase().contains(searchString) | client.getId().toString().contains(searchString)) & passTagFilterTest(client)) {
                        tempFilteredList.add(client);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = tempFilteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredClientList.clear();
            filteredClientList.addAll((List) results.values);
            Collections.sort(filteredClientList, new ClientAlphabeticalComparator());
            notifyDataSetChanged();
        }

    };

    public ClientListItemAdapter(List<Client> clientList, OnItemClickListener onItemListener) {
        this.clients = clientList;
        this.onItemClickListener = onItemListener;
        this.filteredClientList = new ArrayList<>(clientList);
    }

    public ClientListItemAdapter(OnItemClickListener onItemClickListener) {
        clients = new ArrayList<>();
        filteredClientList = new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
        filteredClientList = new ArrayList<>(clients);
        Collections.sort(filteredClientList, new ClientAlphabeticalComparator());
        notifyDataSetChanged();
    }

    public Client getClient(int position) {
        return filteredClientList.get(position);
    }

    @NonNull
    @Override
    public ClientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        return new ClientItemViewHolder(v, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientItemViewHolder clientViewHolder, int position) {

        Client currentClient = filteredClientList.get(position);

        Helper.setImageViewFromURL(currentClient.getPhotoURL(), clientViewHolder.imageView, R.drawable.client_details_placeholder);
        clientViewHolder.textViewFullName.setText(currentClient.getFullName());
        clientViewHolder.textViewLocation.setText(currentClient.getLocation());

        String riskScoreAsString = Integer.toString(currentClient.calculateRiskScore());
        clientViewHolder.riskTextView.setText(riskScoreAsString);

        String riskColourCode = Helper.riskToColourCode(currentClient.calculateRiskScore());
        clientViewHolder.riskTextView.setTextColor(Color.parseColor(riskColourCode));
    }

    @Override
    public int getItemCount() {
        return filteredClientList.size();
    }

    public Filter getFilterWithTags(String genderTag, String disabilityTag, String locationTag) {
        this.genderTag = genderTag;
        this.locationTag = locationTag;
        this.disabilityTag = disabilityTag;
        return filter;
    }

    private boolean passTagFilterTest(Client client) {
        boolean genderResult, locationResult, disabilityResult;
        if (genderTag.equals("any")) {
            genderResult = true;
        } else {
            genderResult = client.getGender().toLowerCase().trim().contains(genderTag);
        }
        if (locationTag.equals("any")) {
            locationResult = true;
        } else {
            locationResult = client.getLocation().toLowerCase().trim().contains(locationTag);
        }
        if (disabilityTag.equals("any")) {
            disabilityResult = true;
        } else {
            disabilityResult = client.getDisability().toLowerCase().trim().contains(disabilityTag);
        }
        return genderResult & locationResult & disabilityResult;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class ClientAlphabeticalComparator implements Comparator<Client> {

        @Override
        public int compare(Client o1, Client o2) {
            return o1.getFullName().compareTo(o2.getFullName());
        }
    }

    public static class ClientItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textViewFullName;
        public TextView textViewLocation;
        public TextView riskTextView;
        OnItemClickListener onItemListener;

        public ClientItemViewHolder(@NonNull View itemView, OnItemClickListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewPhoto);
            textViewFullName = itemView.findViewById(R.id.textViewFullName);
            textViewLocation = itemView.findViewById(R.id.textViewLocation);
            riskTextView = itemView.findViewById(R.id.textViewRiskScore);
            this.onItemListener = onItemListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }


}
