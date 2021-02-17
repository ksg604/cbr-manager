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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ClientListRecyclerItemAdapter extends RecyclerView.Adapter<ClientListRecyclerItemAdapter.ClientItemViewHolder> {

    private ArrayList<ClientListRecyclerItem> clientListRecyclerItems;
    private List<ClientListRecyclerItem> clientListRecyclerItemsFull;
    private OnItemListener onItemListener;

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
        this.clientListRecyclerItemsFull = new ArrayList<>();
        this.clientListRecyclerItemsFull.addAll(clientListRecyclerItems);
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
        int intRiskScore = Integer.parseInt(currentItem.getRiskScore());
        if (intRiskScore >= 10) {
            holder.riskTextView.setTextColor(Color.parseColor("#b02323"));
        } else if (intRiskScore < 10 && intRiskScore >= 5) {
            holder.riskTextView.setTextColor(Color.parseColor("#c45404"));
        } else {
            holder.riskTextView.setTextColor(Color.parseColor("#c49704"));
        }
    }

    @Override
    public int getItemCount() {
        return clientListRecyclerItems.size();
    }





}
