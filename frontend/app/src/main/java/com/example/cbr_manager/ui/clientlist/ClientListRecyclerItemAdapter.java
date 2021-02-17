package com.example.cbr_manager.ui.clientlist;

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

public class ClientListRecyclerItemAdapter extends RecyclerView.Adapter<ClientListRecyclerItemAdapter.ClientItemViewHolder> implements Filterable {

    private ArrayList<ClientListRecyclerItem> clientListRecyclerItems;
    private ArrayList<ClientListRecyclerItem> clientListRecyclerItemsFull;
    private OnItemListener onItemListener;

    private Filter itemFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ClientListRecyclerItem> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(clientListRecyclerItemsFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (ClientListRecyclerItem item : clientListRecyclerItemsFull) {
                    if (item.getmText1().toLowerCase().contains(filterPattern)) { // or contains
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clientListRecyclerItems.clear();
            clientListRecyclerItems.addAll((ArrayList) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ClientItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView riskTextView;
        OnItemListener onItemListener;

        public ClientItemViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView6);
            textView2 = itemView.findViewById(R.id.textView7);
            riskTextView = itemView.findViewById(R.id.riskTextView);
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

    public ClientListRecyclerItemAdapter(ArrayList<ClientListRecyclerItem> clientListRecyclerItems, OnItemListener onItemListener) {
        this.clientListRecyclerItems = clientListRecyclerItems;
        clientListRecyclerItemsFull = new ArrayList<>(clientListRecyclerItems);
        this.onItemListener = onItemListener;
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
        ClientListRecyclerItem currentItem = clientListRecyclerItems.get(position);

        holder.imageView.setImageResource(currentItem.getmImageResource());
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
        holder.riskTextView.setText(currentItem.getRiskScore());
    }

    @Override
    public int getItemCount() {
        return clientListRecyclerItems.size();
    }

    @Override
    public Filter getFilter() {
        return itemFilter;
    }




}
