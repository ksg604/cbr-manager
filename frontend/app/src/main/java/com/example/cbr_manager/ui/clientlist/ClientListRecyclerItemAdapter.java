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
import com.example.cbr_manager.utils.Helper;
import com.example.cbr_manager.service.client.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientListRecyclerItemAdapter extends RecyclerView.Adapter<ClientListRecyclerItemAdapter.ClientItemViewHolder> implements Filterable {

    private List<Client> clients;
    private List<Client> clientsListFull = new ArrayList<>();
    private OnItemListener onItemListener;

    public ClientListRecyclerItemAdapter(List<Client> clientList, OnItemListener onItemListener) {
        this.clients = clientList;
        this.onItemListener = onItemListener;
        clientsListFull = new ArrayList<>(clientList);
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
        Client currentClient = clients.get(position);
        Helper.setImageViewFromURL(currentClient.getPhotoURL(), holder.imageView, R.drawable.client_details_placeholder);

        holder.textViewFullName.setText(currentClient.getFullName());
        holder.textViewLocation.setText(currentClient.getLocation());
        holder.riskTextView.setText(Integer.toString(currentClient.getRiskScore()));

        String riskColourCode = Helper.riskToColourCode(currentClient.getRiskScore());
        holder.riskTextView.setTextColor(Color.parseColor(riskColourCode));
    }

    @Override
    public int getItemCount() {
        return clients.size();
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
            List<Client> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList = new ArrayList<>(clientsListFull);
                filteredList.addAll(clientsListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (Client client : clientsListFull) {
                    if (client.getFullName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(client);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clients.clear();
            clients.addAll((List) results.values);
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
