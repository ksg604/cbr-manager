package com.example.cbr_manager.ui.goalhistory;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.goal.Goal;

import java.util.ArrayList;

public class GoalHistoryItemAdapter extends RecyclerView.Adapter<GoalHistoryItemAdapter.GoalHistoryItemViewHolder> {

    private ArrayList<Goal> goals;

    @NonNull
    @Override
    public GoalHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalHistoryItemViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return goals.size();
    }

    public static class GoalHistoryItemViewHolder extends RecyclerView.ViewHolder {
        public TextView dateTextView;
        public TextView goalTitleTextView;
        public TextView goalDescriptionTextView;
        public TextView statusTextView;

        public GoalHistoryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.goalHistoryDate);
            goalTitleTextView = itemView.findViewById(R.id.goalHistoryTitle);
            goalDescriptionTextView = itemView.findViewById(R.id.goalHistoryDescription);
            statusTextView = itemView.findViewById(R.id.goalHistoryStatus);
        }
    }
}
