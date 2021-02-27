package com.example.cbr_manager.ui.createreferral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.ServiceDetails.OrthoticServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.PhysiotherapyServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.ProstheticServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.WheelchairServiceDetail;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

public class CreateReferralActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_referral);
        setTitle("Create Referral");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        setupReferralServiceRadioGroup();
        setupPhysioLayout();
        setupWheelchairLayout();
        setupSubmission();
    }

    private void setupSubmission() {
        Button submitButton = findViewById(R.id.referralSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gatherData();
                
                onBackPressed();
            }
        });
    }

    private void gatherData() {
        RadioGroup selectedService = findViewById(R.id.createReferralServiceRadioGroup);
        int service = selectedService.getCheckedRadioButtonId();

        Referral referral = new Referral();

        if (service == R.id.referralPhysioRadioButton) {
            PhysiotherapyServiceDetail physiotherapyServiceDetail = new PhysiotherapyServiceDetail();
            String clientCondition;

            TextInputEditText physioOtherCondition = findViewById(R.id.referralOtherPhysio);

            String otherConditionDescription = "";
            Spinner referralPhysioDDL = findViewById(R.id.referralPhysioDDL);
            clientCondition = referralPhysioDDL.getSelectedItem().toString();

            if (clientCondition.equals("Other")) {
                otherConditionDescription = physioOtherCondition.getText().toString();
            }

            physiotherapyServiceDetail.setSpecifiedCondition(clientCondition);
            // TODO: Waiting on addition of other description.

        } else if (service == R.id.referralProstheticRadioButton) {
            ProstheticServiceDetail prostheticServiceDetail = new ProstheticServiceDetail();
            RadioGroup aboveOrBelowKnee = findViewById(R.id.referralAboveOrBelowKnee);
            int getSelectedId = aboveOrBelowKnee.getCheckedRadioButtonId();
            RadioButton aboveOrBelow = (RadioButton) findViewById(getSelectedId);
            String getRadioText = aboveOrBelow.getText().toString();

            prostheticServiceDetail.setKneeInjuryLocation(getRadioText);

        } else if (service == R.id.referralOrthoticRadioButton) {
            OrthoticServiceDetail orthoticServiceDetail = new OrthoticServiceDetail();
            RadioGroup aboveOrBelowElbow = findViewById(R.id.referralAboveOrBelowElbow);
            int getSelectedId = aboveOrBelowElbow.getCheckedRadioButtonId();
            RadioButton aboveOrBelow = (RadioButton) findViewById(getSelectedId);
            String getRadioText = aboveOrBelow.getText().toString();

            orthoticServiceDetail.setElbowInjuryLocation(getRadioText);

        } else if (service == R.id.referralWheelChairRadioButton) {
            WheelchairServiceDetail wheelchairServiceDetail = new WheelchairServiceDetail();

            boolean isExisting = false;

        } else if (service == R.id.referralOtherRadioButton) {
            // TODO
        }
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