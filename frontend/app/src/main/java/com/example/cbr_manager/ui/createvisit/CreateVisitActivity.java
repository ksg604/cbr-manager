package com.example.cbr_manager.ui.createvisit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.cbr_manager.R;

public class CreateVisitActivity extends AppCompatActivity {

    private boolean checkedHealthProvision = false;
    private boolean checkedEducationProvision = false;
    private boolean checkedSocialProvision = false;
    int clientId = -1;

    public boolean isCheckedHealthProvision() {
        return checkedHealthProvision;
    }

    public void setCheckedHealthProvision(boolean checkedHealthProvision) {
        this.checkedHealthProvision = checkedHealthProvision;
    }

    public boolean isCheckedEducationProvision() {
        return checkedEducationProvision;
    }

    public void setCheckedEducationProvision(boolean checkedEducationProvision) {
        this.checkedEducationProvision = checkedEducationProvision;
    }

    public boolean isCheckedSocialProvision() {
        return checkedSocialProvision;
    }

    public void setCheckedSocialProvision(boolean checkedSocialProvision) {
        this.checkedSocialProvision = checkedSocialProvision;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_visit);

        Intent intent = getIntent();
        clientId = intent.getIntExtra("clientId", -1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}