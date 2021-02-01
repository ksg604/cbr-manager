package com.example.cbr_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExampleItemAdapter extends RecyclerView.Adapter<ExampleItemAdapter.ExampleViewHolder> {

    private ArrayList<ExampleItem> exampleItems;
    private OnItemListener onItemListener;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        OnItemListener onItemListener;

        public ExampleViewHolder(@NonNull View itemView, OnItemListener onItemListener) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            textView1 = itemView.findViewById(R.id.textView6);
            textView2 = itemView.findViewById(R.id.textView7);
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

    public ExampleItemAdapter(ArrayList<ExampleItem> exampleItems, OnItemListener onItemListener) {
        this.exampleItems = exampleItems;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, onItemListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = exampleItems.get(position);

        holder.imageView.setImageResource(currentItem.getmImageResource());
        holder.textView1.setText(currentItem.getmText1());
        holder.textView2.setText(currentItem.getmText2());
    }

    @Override
    public int getItemCount() {
        return exampleItems.size();
    }




}
