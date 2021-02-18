package com.example.cbr_manager.ui.create_client;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClientCreatePagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> createClientFragmentList = new ArrayList<>();

    public ClientCreatePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment fragment) {
        createClientFragmentList.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return createClientFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return createClientFragmentList.size();
    }
}
