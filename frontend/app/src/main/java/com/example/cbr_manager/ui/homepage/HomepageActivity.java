package com.example.cbr_manager.ui.homepage;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;

public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_homepage);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.home_container, new HomepageFragment())
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}