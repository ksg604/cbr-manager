package com.example.cbr_manager.ui.goalhistory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.goal.Goal;
import com.example.cbr_manager.ui.GoalViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class GoalHistoryFragment extends Fragment {

    private static final int HEALTH_GOAL_KEY = 100;
    private static final int EDUCATION_GOAL_KEY = 101;
    private static final int SOCIAL_GOAL_KEY = 102;
    private static int goalType = -1;
    private static int clientId = -1;
    private APIService apiService = APIService.getInstance();
    private View view;
    private RecyclerView goalRecyclerView;
    private GoalHistoryItemAdapter goalHistoryItemAdapter;
    private GoalViewModel goalViewModel;


    public GoalHistoryFragment() {
        super(R.layout.fragment_goal_history);
    }

    public static GoalHistoryFragment newInstance(int goalKey, int clientKey) {
        GoalHistoryFragment fragment = new GoalHistoryFragment();
        Bundle args = new Bundle();
        args.putInt("GOAL_KEY", goalKey);
        args.putInt("CLIENT_ID", clientKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            goalType = bundle.getInt("GOAL_KEY", -1);
            clientId = bundle.getInt("CLIENT_ID", -1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        goalViewModel = new ViewModelProvider(this).get(GoalViewModel.class);
        return inflater.inflate(R.layout.fragment_goal_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goalRecyclerView = view.findViewById(R.id.goalHistoryRecyclerView);
        getGoals();
    }

    private void getGoals() {
        goalViewModel.getAllGoals().observe(getViewLifecycleOwner(), goals -> {

            ArrayList<Goal> goalsToAdd = new ArrayList<>();
            Collections.reverse(goals);
            String category = "";
            if (goalType == HEALTH_GOAL_KEY) {
                category = "health";
            } else if (goalType == EDUCATION_GOAL_KEY) {
                category = "education";
            } else if (goalType == SOCIAL_GOAL_KEY) {
                category = "social";
            }

            for (Goal goal : goals) {
                if (goal.getClientId().equals(clientId) && goal.getCategory().toLowerCase().equals(category)) {
                    goalsToAdd.add(goal);
                }
            }
            goalHistoryItemAdapter = new GoalHistoryItemAdapter(getContext(),goalsToAdd);
            RecyclerView.LayoutManager goalHistoryLayoutManager = new LinearLayoutManager(getContext());
            goalRecyclerView.setLayoutManager(goalHistoryLayoutManager);
            goalRecyclerView.setAdapter(goalHistoryItemAdapter);
        });

    }
}