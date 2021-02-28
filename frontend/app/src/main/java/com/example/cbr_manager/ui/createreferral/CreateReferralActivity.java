package com.example.cbr_manager.ui.createreferral;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.ServiceDetails.OrthoticServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.OtherServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.PhysiotherapyServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.ProstheticServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.ServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.WheelchairServiceDetail;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class CreateReferralActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = -1;

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
        setupCameraButtonListener();
    }

    private void setupCameraButtonListener() {
        Button cameraButton = findViewById(R.id.referralTakePhotoButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ImageView referralImageView = findViewById(R.id.referralImageView);
        if (requestCode == REQUEST_IMAGE_CAPTURE && requestCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            referralImageView.setImageBitmap(imageBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
                physiotherapyServiceDetail.setOther_description(otherConditionDescription);
            }
            physiotherapyServiceDetail.setCondition(clientCondition);

            referral.setServiceDetail(physiotherapyServiceDetail);

        } else if (service == R.id.referralProstheticRadioButton) {
            ProstheticServiceDetail prostheticServiceDetail = new ProstheticServiceDetail();
            RadioGroup aboveOrBelowKnee = findViewById(R.id.referralAboveOrBelowKnee);
            int getSelectedId = aboveOrBelowKnee.getCheckedRadioButtonId();
            RadioButton aboveOrBelow = (RadioButton) findViewById(getSelectedId);
            String getRadioText = "";
            if (aboveOrBelow != null) {
                getRadioText = aboveOrBelow.getText().toString();
            }

            prostheticServiceDetail.setKneeInjuryLocation(getRadioText);

            referral.setServiceDetail(prostheticServiceDetail);

        } else if (service == R.id.referralOrthoticRadioButton) {
            OrthoticServiceDetail orthoticServiceDetail = new OrthoticServiceDetail();
            RadioGroup aboveOrBelowElbow = findViewById(R.id.referralAboveOrBelowElbow);
            int getSelectedId = aboveOrBelowElbow.getCheckedRadioButtonId();
            RadioButton aboveOrBelow = (RadioButton) findViewById(getSelectedId);
            String getRadioText = "";

            if (aboveOrBelow != null) {
                getRadioText = aboveOrBelow.getText().toString();
            }

            orthoticServiceDetail.setElbowInjuryLocation(getRadioText);

            referral.setServiceDetail(orthoticServiceDetail);

        } else if (service == R.id.referralWheelChairRadioButton) {
            WheelchairServiceDetail wheelchairServiceDetail = new WheelchairServiceDetail();
            String hipWidth;
            boolean isExisting = false;
            boolean isRepairable = false;

            TextInputEditText hipWidthEditText = findViewById(R.id.referralHipWidth);
            hipWidth = hipWidthEditText.getText().toString();

            RadioGroup isExistingWheelchair = findViewById(R.id.referralExistingWheelchairRadioGroup);
            if (isExistingWheelchair.getCheckedRadioButtonId() == R.id.referralExistingWheelchairYes) {
                isExisting = true;
            }

            RadioGroup isRepairableWheelchair = findViewById(R.id.referralCanRepairRadioGroup);
            if (isRepairableWheelchair.getCheckedRadioButtonId() == R.id.referralCanRepairYes) {
                isRepairable = true;
            }

            if (!hipWidth.isEmpty()) {
                wheelchairServiceDetail.setClientHipWidth(Float.parseFloat(hipWidth));
            }
            wheelchairServiceDetail.setClientHasExistingWheelchair(isExisting);
            wheelchairServiceDetail.setIsWheelChairRepairable(isRepairable);

            referral.setServiceDetail(wheelchairServiceDetail);

        } else if (service == R.id.referralOtherRadioButton) {
            OtherServiceDetail otherServiceDetail = new OtherServiceDetail();
            TextInputEditText otherServiceEditText = findViewById(R.id.referralOtherServiceDescription);
            String otherDescription = "";
            otherDescription = otherServiceEditText.getText().toString();
            otherServiceDetail.setDescription(otherDescription);

            referral.setServiceDetail(otherServiceDetail);
        }

        TextInputEditText referTo = findViewById(R.id.referralReferToEditText);
        String referToString = referTo.getText().toString();

        referral.setRefer_to(referToString);
        referral.setStatus("made");

        // TODO: You are here! Referral is ready to be sent to the server. What's missing is the clientID (pass from clientDetails over, I think?) and current User ID.
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
        TextInputLayout referralPhysioOther = findViewById(R.id.referralPhysioOtherTextInputLayout);
        referralPhysioOther.setVisibility(View.GONE);
        Spinner spinner = findViewById(R.id.referralPhysioDDL);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (spinner.getSelectedItem().toString().equals("Other")) {
                    referralPhysioOther.setVisibility(View.VISIBLE);
                } else {
                    referralPhysioOther.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupReferralServiceRadioGroup() {
        TextInputLayout referralServiceOther = findViewById(R.id.referralDescribeOtherTextInputLayout);
        referralServiceOther.setVisibility(View.GONE);
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
                    referralServiceOther.setVisibility(View.VISIBLE);
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

        TextInputLayout referralServiceOther = findViewById(R.id.referralDescribeOtherTextInputLayout);
        referralServiceOther.setVisibility(View.GONE);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}