package com.example.cbr_manager.ui.createreferral;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import android.widget.Toast;

import com.example.cbr_manager.NavigationActivity;
import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.service.referral.Referral;
import com.example.cbr_manager.service.referral.ServiceDetails.OrthoticServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.OtherServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.PhysiotherapyServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.ProstheticServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.ServiceDetail;
import com.example.cbr_manager.service.referral.ServiceDetails.WheelchairServiceDetail;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsFragment;
import com.example.cbr_manager.ui.homepage.HomepageFragment;
import com.example.cbr_manager.ui.referral.referral_list.ReferralListFragment;
import com.example.cbr_manager.ui.referral.referral_list.ReferralListRecyclerItemAdapter;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// REFERENCES: https://medium.com/android-news/androids-new-image-capture-from-a-camera-using-file-provider-dd178519a954
//             https://developer.android.com/training/camera/photobasics
// dispatchCameraIntent() and gatherData() methods both draw from the above references.

public class CreateReferralActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 102;
    static final int REQUEST_CAMERA_USE = 101;
    private static final int PICK_FROM_GALLERY = 1;
    int clientId = -1;
    private Integer userId = -1;
    private APIService apiService = APIService.getInstance();
    private String imageFilePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_referral);
        clientId = getIntent().getIntExtra("CLIENT_ID", -1);
        setTitle("Create Referral");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        TextInputEditText clientName = findViewById(R.id.referralClientName);
        if (apiService.isAuthenticated()) {
            apiService.clientService.getClient(clientId).enqueue(new Callback<Client>() {
                @Override
                public void onResponse(Call<Client> call, Response<Client> response) {
                    if (response.isSuccessful()) {
                        Client client = response.body();
                        clientName.setText(client.getFullName());
                        clientName.setFocusable(false);
                    }
                }

                @Override
                public void onFailure(Call<Client> call, Throwable t) {

                }
            });

        }

        getUserId();
        setupReferralServiceRadioGroup();
        setupPhysioLayout();
        setupWheelchairLayout();
        setupSubmission();
        setupCameraButtonListener();
    }

    private boolean validateEditText(int textInputLayoutId, Editable stringInput) {
        TextInputLayout textInputLayout = findViewById(textInputLayoutId);
        if (TextUtils.isEmpty(stringInput)) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError("Required field");
            return false;
        } else {
            textInputLayout.setErrorEnabled(false);
        }
        return true;
    }

    private boolean validateRadiogroupSelection(int radiogroupId, int errorTextViewId) {
        RadioGroup radiogroup = findViewById(radiogroupId);
        TextView textView = findViewById(errorTextViewId);
        if (radiogroup.getCheckedRadioButtonId() == -1) {
            textView.setVisibility(View.VISIBLE);
            return false;
        } else {
            textView.setVisibility(View.GONE);
        }
        return true;
    }

    private void radioGroupErrorListener(int radiogroupId, int errorTextViewId) {
        RadioGroup radioGroup = findViewById(radiogroupId);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                validateRadiogroupSelection(radiogroupId, errorTextViewId);
            }
        });
    }

    private void validationErrorListener(int textInputEditTextId, int textInputLayoutId) {
        TextInputEditText textInputEditText = findViewById(textInputEditTextId);
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence stringInput, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence stringInput, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable stringInput) {
                validateEditText(textInputLayoutId, stringInput);
            }
        });
    }

    private void showTakePhotoDialog() {
        AlertDialog.Builder takePhotoDialog = new AlertDialog.Builder(this);
        takePhotoDialog.setTitle("Upload a photo");
        takePhotoDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        String[] dialogItems = {"Select from gallery", "Take photo"};
        takePhotoDialog.setItems(dialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch(which) {
                    case 0:
                        askGalleryPermission();
                        break;
                    case 1:
                        askCameraPermission();
                        break;
                }
            }
        });
        takePhotoDialog.show();
    }

    private void askGalleryPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        } else {
            dispatchGalleryIntent();
        }
    }

    private void dispatchGalleryIntent() {
    }


    private void getUserId() {
        if (apiService.isAuthenticated()) {
            apiService.userService.getCurrentUser().enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        User user = response.body();
                        userId = user.getId();
                    }
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    private void setupCameraButtonListener() {
        Button cameraButton = findViewById(R.id.referralTakePhotoButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });
    }

    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_CAMERA_USE);
        } else {
            dispatchTakePictureIntent();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_USE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            File photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (ActivityNotFoundException | IOException e) {
                Toast.makeText(this, "Error making file.", Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView referralImageView = findViewById(R.id.referralImageView);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            File imgFile = new File(imageFilePath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                referralImageView.setImageBitmap(bitmap);
            }
        }
    }

    private void setupSubmission() {
        Button submitButton = findViewById(R.id.referralSubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    private void onSubmit() {
        boolean requiredFieldsFilled = true;

        RadioGroup selectedService = findViewById(R.id.createReferralServiceRadioGroup);
        if(!validateRadiogroupSelection(R.id.createReferralServiceRadioGroup, R.id.referralNoServiceSelectedTextView)) {
            requiredFieldsFilled = false;
        }

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
                if (!validateEditText(R.id.referralPhysioOtherTextInputLayout, physioOtherCondition.getText())) {
                    requiredFieldsFilled = false;
                }
                validationErrorListener(R.id.referralOtherPhysio, R.id.referralPhysioOtherTextInputLayout);
                physiotherapyServiceDetail.setOther_description(otherConditionDescription);
            }
            physiotherapyServiceDetail.setCondition(clientCondition);

            referral.setServiceDetail(physiotherapyServiceDetail);
            referral.setServiceType("Physiotherapy");

        } else if (service == R.id.referralProstheticRadioButton) {
            ProstheticServiceDetail prostheticServiceDetail = new ProstheticServiceDetail();
            RadioGroup aboveOrBelowKnee = findViewById(R.id.referralAboveOrBelowKnee);
            if (!validateRadiogroupSelection(R.id.referralAboveOrBelowKnee, R.id.referralNoProstheticSelectedTextView)) {
                requiredFieldsFilled = false;
            }
            radioGroupErrorListener(R.id.referralAboveOrBelowKnee, R.id.referralNoProstheticSelectedTextView);
            int getSelectedId = aboveOrBelowKnee.getCheckedRadioButtonId();
            RadioButton aboveOrBelow = (RadioButton) findViewById(getSelectedId);
            String getRadioText = "";
            if (aboveOrBelow != null) {
                getRadioText = aboveOrBelow.getText().toString();
            }

            prostheticServiceDetail.setKneeInjuryLocation(getRadioText);

            referral.setServiceDetail(prostheticServiceDetail);
            referral.setServiceType("Prosthetic");

        } else if (service == R.id.referralOrthoticRadioButton) {
            OrthoticServiceDetail orthoticServiceDetail = new OrthoticServiceDetail();
            RadioGroup aboveOrBelowElbow = findViewById(R.id.referralAboveOrBelowElbow);
            if (!validateRadiogroupSelection(R.id.referralAboveOrBelowElbow, R.id.referralNoOrthoticSelectedTextView)) {
                requiredFieldsFilled = false;
            }
            radioGroupErrorListener(R.id.referralAboveOrBelowElbow, R.id.referralNoOrthoticSelectedTextView);
            int getSelectedId = aboveOrBelowElbow.getCheckedRadioButtonId();
            RadioButton aboveOrBelow = (RadioButton) findViewById(getSelectedId);
            String getRadioText = "";

            if (aboveOrBelow != null) {
                getRadioText = aboveOrBelow.getText().toString();
            }

            orthoticServiceDetail.setElbowInjuryLocation(getRadioText);

            referral.setServiceDetail(orthoticServiceDetail);
            referral.setServiceType("Orthotic");

        } else if (service == R.id.referralWheelChairRadioButton) {
            WheelchairServiceDetail wheelchairServiceDetail = new WheelchairServiceDetail();
            String hipWidth;
            boolean isExisting = false;
            boolean isRepairable = false;

            TextInputEditText hipWidthEditText = findViewById(R.id.referralHipWidth);
            if (!validateEditText(R.id.referralHipWidthTextInputLayout, hipWidthEditText.getText())) {
                requiredFieldsFilled = false;
            }
            validationErrorListener(R.id.referralHipWidth, R.id.referralHipWidthTextInputLayout);
            hipWidth = hipWidthEditText.getText().toString();
            if (!hipWidth.isEmpty()) {
                wheelchairServiceDetail.setClientHipWidth(Float.parseFloat(hipWidth));
            }

            RadioGroup usageExperience = findViewById(R.id.referralWheelChairUsageRadioGroup);
            if (!validateRadiogroupSelection(R.id.referralWheelChairUsageRadioGroup, R.id.referralNoWheelchairUsageSelectedTextView)) {
                requiredFieldsFilled = false;
            }
            if (usageExperience.getCheckedRadioButtonId() == R.id.referralWheelchairIntermediate) {
                wheelchairServiceDetail.setUsageExperience("Intermediate");
            } else {
                wheelchairServiceDetail.setUsageExperience("Basic");
            }

            RadioGroup isExistingWheelchair = findViewById(R.id.referralExistingWheelchairRadioGroup);
            if (!validateRadiogroupSelection(R.id.referralExistingWheelchairRadioGroup, R.id.referralNoExisitingWheelchairTextView)) {
                requiredFieldsFilled = false;
            }
            if (isExistingWheelchair.getCheckedRadioButtonId() == R.id.referralExistingWheelchairYes) {
                isExisting = true;
            }

            RadioGroup isRepairableWheelchair = findViewById(R.id.referralCanRepairRadioGroup);
            if (isExisting) {
                if (!validateRadiogroupSelection(R.id.referralCanRepairRadioGroup, R.id.referralNoWheelchairRepairSelectedTextView)) {
                    requiredFieldsFilled = false;
                }
            }
            if (isRepairableWheelchair.getCheckedRadioButtonId() == R.id.referralCanRepairYes) {
                isRepairable = true;
            }

            if (!hipWidth.isEmpty()) {
                wheelchairServiceDetail.setClientHipWidth(Float.parseFloat(hipWidth));
            }
            wheelchairServiceDetail.setClientHasExistingWheelchair(isExisting);
            wheelchairServiceDetail.setIsWheelChairRepairable(isRepairable);
            wheelchairServiceDetail.setUsageExperience("Basic");
            referral.setServiceDetail(wheelchairServiceDetail);
            referral.setServiceType("Wheelchair");

        } else if (service == R.id.referralOtherRadioButton) {
            OtherServiceDetail otherServiceDetail = new OtherServiceDetail();

            TextInputEditText otherServiceEditText = findViewById(R.id.referralOtherServiceDescription);
            if(!validateEditText(R.id.referralDescribeOtherTextInputLayout, otherServiceEditText.getText())) {
                requiredFieldsFilled = false;
            }
            validationErrorListener(R.id.referralOtherServiceDescription, R.id.referralDescribeOtherTextInputLayout);
            String otherDescription = "";
            otherDescription = otherServiceEditText.getText().toString();

            otherServiceDetail.setDescription(otherDescription);
            referral.setServiceDetail(otherServiceDetail);
            referral.setServiceType("Other");
        }

        TextInputEditText referTo = findViewById(R.id.referralReferToEditText);
        if (!validateEditText(R.id.createReferralReferToInputLayout, referTo.getText())) {
            requiredFieldsFilled = false;
        }
        validationErrorListener(R.id.referralReferToEditText, R.id.createReferralReferToInputLayout);
        String referToString = referTo.getText().toString();
        referral.setRefer_to(referToString);

        referral.setClient(new Integer(clientId));
        referral.setUserCreator(userId);
        if (requiredFieldsFilled) {
            makeServerCall(referral);
        } else {
            Toast.makeText(this, "Missing required fields. Please check.", Toast.LENGTH_LONG).show();
        }
    }

    private void makeServerCall(Referral referral) {
        if (apiService.isAuthenticated()) {
            Call<Referral> call = apiService.getReferralService().createReferral(referral);
            call.enqueue(new Callback<Referral>() {
                @Override
                public void onResponse(Call<Referral> call, Response<Referral> response) {
                    if (response.isSuccessful()) {
                        Referral submittedReferral = response.body();
                        File photoFile = new File(imageFilePath);
                        if (photoFile.exists()) {
                            Call<ResponseBody> photoCall = apiService.getReferralService().uploadPhoto(photoFile, submittedReferral.getId().intValue());
                            photoCall.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(CreateReferralActivity.this, "Referral Photo successfully uploaded!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(CreateReferralActivity.this, "Referral photo upload failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }

                        Toast.makeText(CreateReferralActivity.this, "Referral successfully created!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CreateReferralActivity.this, NavigationActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(CreateReferralActivity.this, "Error creating referral.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Referral> call, Throwable t) {
                    Toast.makeText(CreateReferralActivity.this, "Failure connecting to server.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        imageFilePath = image.getAbsolutePath();

        return image;
    }

    private void setupWheelchairLayout() {
        RadioGroup existingChair = findViewById(R.id.referralExistingWheelchairRadioGroup);
        RadioGroup canRepair = findViewById(R.id.referralCanRepairRadioGroup);
        RadioGroup usageExperience = findViewById(R.id.referralWheelChairUsageRadioGroup);
        canRepair.setVisibility(View.GONE);

        TextView bringWheelChair = findViewById(R.id.referralBringWheelchairTextView);
        TextView canRepairedTextView = findViewById(R.id.referralCanRepairTextView);
        bringWheelChair.setVisibility(View.GONE);

        usageExperience.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                validateRadiogroupSelection(R.id.referralWheelChairUsageRadioGroup, R.id.referralNoWheelchairUsageSelectedTextView);
            }
        });

        existingChair.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                validateRadiogroupSelection(R.id.referralExistingWheelchairRadioGroup, R.id.referralNoExisitingWheelchairTextView);
                if (checkedId == R.id.referralExistingWheelchairYes) {
                    canRepair.setVisibility(View.VISIBLE);
                    canRepairedTextView.setVisibility(View.VISIBLE);
                } else {
                    canRepair.setVisibility(View.GONE);
                    canRepairedTextView.setVisibility(View.GONE);
                    canRepair.clearCheck();
                    bringWheelChair.setVisibility(View.GONE);
                    TextView textView = findViewById(R.id.referralNoWheelchairRepairSelectedTextView);
                    textView.setVisibility(View.GONE);
                }
            }
        });

        canRepair.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                validateRadiogroupSelection(R.id.referralCanRepairRadioGroup, R.id.referralNoWheelchairRepairSelectedTextView);
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

                validateRadiogroupSelection(R.id.createReferralServiceRadioGroup, R.id.referralNoServiceSelectedTextView);

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


    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Leave page");
        alertDialogBuilder.setMessage("Are you sure you want to leave? Changes will not be saved.");
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}