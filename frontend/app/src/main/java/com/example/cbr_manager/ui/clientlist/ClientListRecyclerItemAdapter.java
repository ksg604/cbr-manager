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
import com.example.cbr_manager.ui.referral.referral_list.ReferralListRecyclerItem;
import com.example.cbr_manager.utils.Helper;
import com.example.cbr_manager.service.client.Client;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientListRecyclerItemAdapter extends RecyclerView.Adapter<ClientListRecyclerItemAdapter.ClientItemViewHolder> implements Filterable {

    private List<Client> clients;
    private List<Client> filteredClientList;
    private OnItemListener onItemListener;

    private String genderTag = "";
    private String locationTag = "";
    private String disabilityTag = "";

    public ClientListRecyclerItemAdapter(List<Client> clientList, OnItemListener onItemListener) {
        this.clients = clientList;
        this.onItemListener = onItemListener;
        this.filteredClientList = clientList;
    }

    public Client getClient(int position) {
        return filteredClientList.get(position);
    }


    @NonNull
    @Override
    public ClientItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        ClientItemViewHolder evh = new ClientItemViewHolder(v, onItemListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientItemViewHolder holder, int position) {
        Client currentClient = filteredClientList.get(position);
        Helper.setImageViewFromURL(currentClient.getPhotoURL(), holder.imageView, R.drawable.client_details_placeholder);

        holder.textViewFullName.setText(currentClient.getFullName());
        holder.textViewLocation.setText(currentClient.getLocation());
        holder.riskTextView.setText(Integer.toString(currentClient.calculateRiskScore()));

        String riskColourCode = Helper.riskToColourCode(currentClient.calculateRiskScore());
        holder.riskTextView.setTextColor(Color.parseColor(riskColourCode));
    }

    @Override
    public int getItemCount() {
        return filteredClientList.size();
    }

    public Filter getFilterWithTags(String genderTag, String disabilityTag, String locationTag){
        this.genderTag = genderTag;
        this.locationTag = locationTag;
        this.disabilityTag = disabilityTag;
        return filter;
    }

    private boolean passTagFilterTest(Client client) {
        boolean genderResult, locationResult, disabilityResult;
        if(genderTag.equals("any")){
            genderResult = true;
        } else{
            genderResult = client.getGender().toLowerCase().trim().contains(genderTag);
        }
        if(locationTag.equals("any")){
            locationResult = true;
        } else {
            locationResult = client.getLocation().toLowerCase().trim().contains(locationTag);
        }
        if(disabilityTag.equals("any")){
            disabilityResult = true;
        } else{
            disabilityResult = client.getDisability().toLowerCase().trim().contains(disabilityTag);
        }
        return genderResult&locationResult&disabilityResult;
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    public interface OnItemListener {
        void onItemClick(int position);
    }

    public Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchString = constraint.toString().toLowerCase().trim();

            ArrayList<Client> tempFilteredList = new ArrayList<>();
            if (searchString.isEmpty()) {
                for (Client client : clients) {
                    if (passTagFilterTest(client)) {
                        tempFilteredList.add(client);
                    }
                }
                filteredClientList = tempFilteredList;
            } else {
                for (Client client : clients) {
                    if ((client.getFullName().toLowerCase().contains(searchString)|client.getCbrClientId().toLowerCase().contains(searchString)|client.getId().toString().contains(searchString))&passTagFilterTest(client)) {
//                        Log.d("tag", client.getFullName());
                        tempFilteredList.add(client);
                    }
                }
                filteredClientList = tempFilteredList;
            }
            FilterResults results = new FilterResults();
            results.values = filteredClientList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredClientList = (ArrayList<Client>) results.values;

//            for (Client client : filteredClientList) {
//                Log.d("tag", client.getFullName());
//            }
            notifyDataSetChanged();
        }

    };

    public static class ClientItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textViewFullName;
        public TextView textViewLocation;
        public TextView riskTextView;
        OnItemListener onItemListener;

        public ClientItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
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
