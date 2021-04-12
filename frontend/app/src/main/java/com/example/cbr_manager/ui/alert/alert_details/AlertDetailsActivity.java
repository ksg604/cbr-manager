package com.example.cbr_manager.ui.alert.alert_details;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.ui.AlertViewModel;
import com.example.cbr_manager.utils.Helper;
import com.google.android.material.snackbar.Snackbar;

import org.threeten.bp.format.FormatStyle;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.observers.DisposableCompletableObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class AlertDetailsActivity extends AppCompatActivity {

//    AppCompatActivity myActivity = this.etBaseContext();
    private APIService apiService = APIService.getInstance();
    private int alertId;
    private View parentLayout;
    private Alert localAlert;
    private AlertViewModel alertViewModel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_details);
        setupToolbar();
        alertViewModel = new ViewModelProvider(this).get(AlertViewModel.class);
        parentLayout = findViewById(android.R.id.content);

        Intent intent = getIntent();
        alertId = intent.getIntExtra("alertId", -1);
        getAlertInfo(alertId);

        setupButtons();

    }

    private void setupToolbar(){
        getSupportActionBar().setTitle("Alerts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getAlertInfo(int alertId){
        alertViewModel.getAlert(alertId).observe(this, requestedAlert -> {
            localAlert = requestedAlert;

            setUpTextView(R.id.textTitle, localAlert.getTitle());
            setUpTextView(R.id.textBody, localAlert.getBody());
            setUpTextView(R.id.textViewDate, Helper.formatDateTimeToLocalString(localAlert.getCreatedAt(), FormatStyle.SHORT));

        });

    }

    private void setUpTextView(int textViewId, String text) {
        TextView textView = findViewById(textViewId);
        textView.setText(text);
    }

    private void setupButtons() {
        setupMarkAsReadButton();
    }

    private void setupMarkAsReadButton() {
        Button newVisitButton = findViewById(R.id.buttonMarkAsRead);
        newVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localAlert.setMarkedRead(true);
                alertViewModel.modifyAlertOffline(localAlert).subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Snackbar.make(parentLayout, "Marked as read", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Snackbar.make(parentLayout, "Failed to mark as read", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}