package com.example.cbr_manager.ui.usercreation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbr_manager.NavigationActivity;
import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.user.User;

import com.example.cbr_manager.ui.login.LoginActivity;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserCreationFragment extends Fragment {

    private EditText editTextUserName, editTextEmail, editTextPassword, editTextPasswordConfirm;
    private Button buttonSubmit;
    private TextView  textInputWarning;
    private String warningText = "";
    private static final APIService apiService = APIService.getInstance();
    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_user_creation, container, false);

        editTextUserName = root.findViewById(R.id.editTextUserName);
        editTextPassword = root.findViewById(R.id.editTextPassword);
        editTextEmail = root.findViewById(R.id.editTextEmail);
        editTextPasswordConfirm = root.findViewById(R.id.editTextPasswordConfirm);
        buttonSubmit = root.findViewById(R.id.buttonSubmit);
        textInputWarning = root.findViewById(R.id.textInputWarning);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleValidationInput()){
                    //sendUserCreateRequest();
                    //apiService
                    textInputWarning.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "User succesfully registered!", Toast.LENGTH_SHORT).show();
                }
                }

        });
        return root;
    }

    private void sendUserCreateRequest(User user) {
        apiService.userService.createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                if(response.isSuccessful()){
                    User user = response.body();

                    // Todo: dynamically set the user info here
                    //setupNameTextView(user.getFullName());
                } else{
                    Snackbar.make(root, "Failed to create the user.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Snackbar.make(root, "Failed to get the user. Please try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private boolean simpleValidationInput() {
        boolean inputIsValid = true;
        String password = editTextPassword.getText().toString();
        String passwordConfirm = editTextPasswordConfirm.getText().toString();
        if (editTextUserName.getText().toString().equals("")){
            warningText += "Username missing\n";
            inputIsValid = false;
        }

        if (editTextEmail.getText().toString().equals("")){
            warningText += "Email missing\n";
            inputIsValid = false;
        }

        if (editTextPassword.getText().toString().equals("")){
            warningText += "Password missing\n";
            inputIsValid = false;
        }

        else if (editTextPasswordConfirm.getText().toString().equals("")||!passwordConfirm.equals(password)){
            warningText += "Please confirm password\n";
            inputIsValid = false;
        }

        if(!inputIsValid){
            textInputWarning.setVisibility(View.VISIBLE);
            textInputWarning.setText(warningText);
            warningText = "";
        }

        return inputIsValid;
    }
}