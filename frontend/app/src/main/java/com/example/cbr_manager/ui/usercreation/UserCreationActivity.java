package com.example.cbr_manager.ui.usercreation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.ui.login.LoginActivity;

public class UserCreationActivity extends AppCompatActivity {

    private EditText editTextUserName, editTextEmail, editTextPassword, editTextPasswordConfirm;
    private Button buttonSubmit;
    private TextView  textInputWarning;
    private String warningText = "";
    private static final APIService apiService = APIService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textInputWarning = findViewById(R.id.textInputWarning);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleValidationInput()){
                    sendUserCreateRequest();
                    //apiService
                    textInputWarning.setVisibility(View.GONE);
                    Toast.makeText(UserCreationActivity.this, "User succesfully registered!", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(UserCreationActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                }

        });
    }


    public void onRegisterButtonClick(View view) {
        ;
    }


    private void sendUserCreateRequest() {
        //TODO: method to send form data to register new user in Django server
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