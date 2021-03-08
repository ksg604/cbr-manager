package com.example.cbr_manager.ui.referral.referral_details;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cbr_manager.R;
import com.example.cbr_manager.ui.referral.referral_details.ReferralDetailsFragment;

public class ReferralDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral_details);

        if ( savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_referral_details, ReferralDetailsFragment.class, null)
                    .commit();

        }
    }



}