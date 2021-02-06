package com.example.cbr_manager.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.cbr_manager.R;
import com.example.cbr_manager.ui.dashboard.ViewPagerAdapter;
import com.example.cbr_manager.ui.dashboard.ViewPagerModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    List<ViewPagerModel> models;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        models = new ArrayList<>();
        models.add(new ViewPagerModel(R.drawable.dog, "Peter Tran", "Simon Fraser University") );
        models.add(new ViewPagerModel(R.drawable.dog, "Paul Erdos", "Budapest, Germany") );
        models.add(new ViewPagerModel(R.drawable.dog, "Leonhard Euler", "Switzerland") );
        models.add(new ViewPagerModel(R.drawable.dog, "James Stewart", "Simon Fraser University") );
        models.add(new ViewPagerModel(R.drawable.dog, "Demo Boi", "Simon Fraser University") );

        setupViewPager(root);
        return root;
    }

    private void setupViewPager(View root) {
        adapter = new ViewPagerAdapter(models, getContext());
        viewPager = root.findViewById(R.id.viewPager2);
        viewPager.setAdapter(adapter);
        viewPager.setClipToPadding(false);
        viewPager.setPadding(220,0,220,0);
    }


}