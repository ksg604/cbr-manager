package com.example.cbr_manager.ui.createreferral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cbr_manager.R;

import java.util.Objects;

public class CreateReferralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_referral);
        setTitle("Create Referral");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button submitButton = findViewById(R.id.referralSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        setupReferralServiceRadioGroup();
        setupPhysioLayout();
        setupWheelchairLayout();
    }

    private void setupWheelchairLayout() {
        RadioGroup existingChair = findViewById(R.id.referralExistingWheelchairRadioGroup);
        RadioGroup canRepair = findViewById(R.id.referralCanRepairRadioGroup);
        canRepair.setVisibility(View.GONE);

        TextView bringWheelChair = findViewById(R.id.referralBringWheelchairTextView);
        bringWheelChair.setVisibility(View.GONE);

        existingChair.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.referralExistingWheelchairYes) {
                    canRepair.setVisibility(View.VISIBLE);
                } else {
                    canRepair.setVisibility(View.GONE);
                    canRepair.clearCheck();
                    bringWheelChair.setVisibility(View.GONE);
                }
            }
        });

        canRepair.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.referralCanRepairYes) {
                    bringWheelChair.setVisibility(View.VISIBLE);
                } else {
                    bringWheelChair.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupPhysioLayout() {
    }

    private void setupReferralServiceRadioGroup() {
        RadioGroup serviceRequired = findViewById(R.id.createReferralServiceRadioGroup);
        serviceRequired.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                LinearLayout physioLayout = findViewById(R.id.referralPhysioLayout);
                LinearLayout prostheticLayout = findViewById(R.id.referralProstheticLayout);
                LinearLayout orthoticLayout = findViewById(R.id.referralOrthoticLayout);
                LinearLayout wheelchairLayout = findViewById(R.id.referralWheelchairLayout);

                if (checkedId == R.id.referralPhysioRadioButton) {
                    setAllGone();
                    physioLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.referralProstheticRadioButton) {
                    setAllGone();
                    prostheticLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.referralOrthoticRadioButton) {
                    setAllGone();
                    orthoticLayout.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.referralWheelChairRadioButton) {
                    setAllGone();
                    wheelchairLayout.setVisibility(View.VISIBLE);
                } else {
                    setAllGone();
                }
            }
        });
    }

    private void setAllGone() {
        LinearLayout physioLayout = findViewById(R.id.referralPhysioLayout);
        LinearLayout prostheticLayout = findViewById(R.id.referralProstheticLayout);
        LinearLayout orthoticLayout = findViewById(R.id.referralOrthoticLayout);
        LinearLayout wheelchairLayout = findViewById(R.id.referralWheelchairLayout);
        LinearLayout otherLayout = findViewById(R.id.referralOrthoticLayout);

        physioLayout.setVisibility(View.GONE);
        prostheticLayout.setVisibility(View.GONE);
        orthoticLayout.setVisibility(View.GONE);
        wheelchairLayout.setVisibility(View.GONE);
        otherLayout.setVisibility(View.GONE);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}