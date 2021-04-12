package com.example.cbr_manager.ui.goalhistory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.ui.referral.referral_list.ReferralListRecyclerItemAdapter;
import com.github.vipulasri.timelineview.TimelineView;

import java.sql.Time;
import java.text.DateFormat;
import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

public class GoalHistoryItemAdapter extends RecyclerView.Adapter<GoalHistoryItemAdapter.GoalHistoryItemViewHolder> {

    private ArrayList<Goal> goals;
    private Context context;

    public GoalHistoryItemAdapter(Context context, ArrayList<Goal> goals) {
        this.context = context;
        this.goals = goals;
    }

    @NonNull
    @Override
    public GoalHistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goal_history_item, parent, false);
        GoalHistoryItemAdapter.GoalHistoryItemViewHolder evh = new GoalHistoryItemAdapter.GoalHistoryItemViewHolder(v, viewType);
        return evh;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    @Override
    public void onBindViewHolder(@NonNull GoalHistoryItemViewHolder holder, int position) {
        Goal goal = goals.get(position);

        if (goal == null){
            return;
        }

        holder.statusTextView.setText(goal.getStatus());

        if (goal.getStatus().toLowerCase().equals("concluded")) {
            holder.timelineView.setMarker(context.getDrawable(R.drawable.ic_check_circle_green));
        } else if (goal.getStatus().toLowerCase().equals("cancelled")) {
            holder.timelineView.setMarker(context.getDrawable(R.drawable.ic_baseline_remove_circle_outline_24));
        }
        Log.d("BindView", "onBindViewHolder: past ifs");
        String date = goal.getDatetimeCreated();
        String dateAndTime[] = new String[2];

        if (date != null) {
            dateAndTime = date.split(" ");
        }

        holder.dateTextView.setText(dateAndTime[0]);
        holder.goalTitleTextView.setText(goal.getTitle());
        holder.goalDescriptionTextView.setText(goal.getDescription());
        holder.timelineView.setMarkerColor(R.color.purple_700);
        Log.d("BindView", "onBindViewHolder: made it to end");
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
        public TimelineView timelineView;

        public GoalHistoryItemViewHolder(@NonNull View itemView, int viewType) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.goalHistoryDate);
            goalTitleTextView = itemView.findViewById(R.id.goalHistoryTitle);
            goalDescriptionTextView = itemView.findViewById(R.id.goalHistoryDescription);
            statusTextView = itemView.findViewById(R.id.goalHistoryStatus);
            timelineView = itemView.findViewById(R.id.timelineView);
            timelineView.initLine(viewType);
        }
    }
}
