package com.example.cbr_manager.ui.createreferral;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.ui.ReferralViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.observers.DisposableSingleObserver;

import static android.app.Activity.RESULT_OK;


public class CreateReferralFragment extends Fragment implements Step {

    static final int REQUEST_IMAGE_CAPTURE = 102;
    static final int REQUEST_CAMERA_USE = 101;
    private static final int PICK_FROM_GALLERY = 1;
    private static final int REQUEST_GALLERY = 103;
    private AuthViewModel authViewModel;
    private ReferralViewModel referralViewModel;
    int clientId = -1;
    private Integer userId = -1;
    private String imageFilePath = "";

    public CreateReferralFragment() {
        // Required empty public constructor
    }

    public static CreateReferralFragment newInstance(String param1, String param2) {
        CreateReferralFragment fragment = new CreateReferralFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_referral, container, false);
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView referralImageView = getView().findViewById(R.id.referralImageView);
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

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

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

    private boolean validateEditText(int textInputLayoutId, Editable stringInput) {
        TextInputLayout textInputLayout = getView().findViewById(textInputLayoutId);
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
        RadioGroup radiogroup = getView().findViewById(radiogroupId);
        TextView textView = getView().findViewById(errorTextViewId);
        if (radiogroup.getCheckedRadioButtonId() == -1) {
            textView.setVisibility(View.VISIBLE);
            return false;
        } else {
            textView.setVisibility(View.GONE);
        }
        return true;
    }

    private void radioGroupErrorListener(int radiogroupId, int errorTextViewId) {
        RadioGroup radioGroup = getView().findViewById(radiogroupId);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                validateRadiogroupSelection(radiogroupId, errorTextViewId);
            }
        });
    }

    private void validationErrorListener(int textInputEditTextId, int textInputLayoutId) {
        TextInputEditText textInputEditText = getView().findViewById(textInputEditTextId);
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
                switch (which) {
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
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_GALLERY);
        } else {
            dispatchGalleryIntent();
        }
    }

    private void dispatchGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_FROM_GALLERY);
    }


    private void getUserId() {
        authViewModel.getUser().subscribe(new DisposableSingleObserver<User>() {
            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull User user) {
                userId = user.getId();
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

            }
        });
    }

    private void setupCameraButtonListener() {
        Button cameraButton = getView().findViewById(R.id.referralTakePhotoButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTakePhotoDialog();
            }
        });
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
}