package com.example.cbr_manager.ui.create_client;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.example.cbr_manager.ui.create_client.ValidatorHelper.validateStepperTextViewNotNull;

public class CaregiverInfoFragment extends Fragment implements Step {

    private RadioGroup careGiverPresentRadioGroup;

    private EditText editTextCaregiverContactNumber;

    private Client client;

    private TextView errorTextView;

    private View view;

    static final int REQUEST_IMAGE_CAPTURE = 102;
    static final int REQUEST_CAMERA_USE = 101;
    private static final APIService apiService = APIService.getInstance();
    private static final int REQUEST_GALLERY = 103;
    private static final int PICK_FROM_GALLERY = 104;
    Button cameraButton;
    private String imageFilePath = "";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        client = ((CreateClientStepperActivity) getActivity()).formClientObj;

        view = inflater.inflate(R.layout.activity_create_client_caregiver_info, container, false);

        careGiverPresentRadioGroup = view.findViewById(R.id.radioGroup2);

        editTextCaregiverContactNumber = (EditText) view.findViewById(R.id.editTextCaregiverContactNumber);

//        cameraButton = view.findViewById(R.id.takePhotoButton);
        setupCameraButtonListener();

        return view;
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        try {
            validateStepperTextViewNotNull(editTextCaregiverContactNumber, "Required");
        } catch (InvalidCreateClientFormException e) {
            errorTextView = e.view;
            return new VerificationError(e.getMessage());
        }

        updateClient();

        return null;
    }


    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {
        if (errorTextView != null) {
            errorTextView.setError(error.getErrorMessage());
        }
    }

    private void updateClient() {
        client.setContactCare(editTextCaregiverContactNumber.getText().toString());
        client.setCarePresent(extractRadioSelection(getView()));
        ((CreateClientStepperActivity) getActivity()).setPhotoFile(new File(imageFilePath));
    }

    private String extractRadioSelection(View view) {
        int radioGroupId = careGiverPresentRadioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) view.findViewById(radioGroupId);
        return radioButton.getText().toString();
    }

    private void setupCameraButtonListener() {
        cameraButton = view.findViewById(R.id.takePhotoButtonCaregiver);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTakePictureDialog();
            }
        });
    }

    private void showTakePictureDialog() {
        AlertDialog.Builder takePhotoDialog = new AlertDialog.Builder(getContext());
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
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY);
        } else {
            dispatchGalleryIntent();
        }
    }

    private void dispatchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_FROM_GALLERY);

    }
    private void askCameraPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_USE);
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
        } else if (requestCode == REQUEST_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchGalleryIntent();
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();

        } catch (ActivityNotFoundException | IOException e) {
        }

        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(getContext(),
                    "com.example.android.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        imageFilePath = image.getAbsolutePath();

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView referralImageView = view.findViewById(R.id.createClientProfilePicture);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            File imgFile = new File(imageFilePath);
            if (imgFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                referralImageView.setImageBitmap(bitmap);
            }
        } else if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImage);
                referralImageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));

                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imageFilePath = cursor.getString(columnIndex);
                cursor.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
