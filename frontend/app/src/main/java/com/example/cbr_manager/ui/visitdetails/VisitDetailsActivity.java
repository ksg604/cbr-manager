package com.example.cbr_manager.ui.visitdetails;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsFragment;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VisitDetailsActivity extends AppCompatActivity {

    public  static String KEY_VISIT_ID = "KEY_VISIT_ID";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_details);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int visitId = intent.getIntExtra(KEY_VISIT_ID, -1);
            VisitDetailsFragment visitDetailsFragment = VisitDetailsFragment.newInstance(visitId);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_visit_details, visitDetailsFragment)
                    .commit();
        }
    }
}
