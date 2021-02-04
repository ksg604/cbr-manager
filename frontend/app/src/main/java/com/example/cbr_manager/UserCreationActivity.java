package com.example.cbr_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserCreationActivity extends AppCompatActivity {

    private EditText editTextUserName, editTextEmail, editTextPassword, editTextPasswordConfirm;
    private Button buttonSubmit;
    private TextView textUserName, textPassword, textEmail, textPasswordConfirm, textInputWarning;
    private String warningText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_creation);
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        textUserName = findViewById(R.id.textUserName);
        textEmail = findViewById(R.id.textEmail);
        textPassword = findViewById(R.id.textPassword);
        textPasswordConfirm = findViewById(R.id.textPasswordConfirm);
        textInputWarning = findViewById(R.id.textInputWarning);
    }

    public void onRegisterButtonClick(View view) {
        initRegister();
    }

    private void initRegister() {
        if (validateInput()){
            sendUserCreateRequest();
            textInputWarning.setVisibility(View.GONE);
            Toast.makeText(this, "User succesfully registered!", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendUserCreateRequest() {
        //TODO: method to send form data to register new user in Django server
    }

    private boolean validateInput() {
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