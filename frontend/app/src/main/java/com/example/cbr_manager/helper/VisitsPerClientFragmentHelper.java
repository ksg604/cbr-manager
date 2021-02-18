package com.example.cbr_manager.helper;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.cbr_manager.ui.visits.VisitsPerClientFragment;

// this method inspired by https://stackoverflow.com/questions/36100187/how-to-start-fragment-from-an-activity
public class VisitsPerClientFragmentHelper extends FragmentActivity {
    public int clientId = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            Intent intent = getIntent();
            int clientId = intent.getIntExtra("clientId", -1);
            this.clientId = clientId;
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new VisitsPerClientFragment()).commit();}
    }
}
