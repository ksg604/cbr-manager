package com.example.cbr_manager.ui.goalhistory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.ui.referral.referral_list.ReferralListRecyclerItemAdapter;

import java.util.ArrayList;

public class GoalHistoryItemAdapter extends RecyclerView.Adapter<GoalHistoryItemAdapter.GoalHistoryItemViewHolder> {

    private ArrayList<Goal> goals;

    public GoalHistoryItemAdapter(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    @NonNull
    @Override
    public GoalHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_history_item, parent, false);
        GoalHistoryItemAdapter.GoalHistoryItemViewHolder evh = new GoalHistoryItemAdapter.GoalHistoryItemViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull GoalHistoryItemViewHolder holder, int position) {
        Goal goal = goals.get(position);
        holder.statusTextView.setText(goal.getStatus());
        if (goal.getStatus().toLowerCase().equals("completed")) {
            holder.dateTextView.setText(goal.getDatetimeCompleted().toString());
        } else {
            holder.dateTextView.setText(goal.getDatetimeCreated().toString());
        }
        holder.goalTitleTextView.setText(goal.getTitle());
        holder.goalDescriptionTextView.setText(goal.getDescription());
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
