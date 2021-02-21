package com.example.cbr_manager.ui.visits;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.cbr_manager.R;

public class VisitsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits);
        if ( savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.visits_fragment_container_view, VisitsFragment.class, null)
                    .commit();
        }
    }




}