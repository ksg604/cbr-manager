package com.example.cbr_manager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.cbr_manager.service.APIService;
import com.example.cbr_manager.service.alert.Alert;
import com.example.cbr_manager.service.sync.Status;
import com.example.cbr_manager.service.user.User;
import com.example.cbr_manager.ui.AuthViewModel;
import com.example.cbr_manager.ui.StatusViewModel;
import com.example.cbr_manager.ui.create_client.CreateClientStepperActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class NavigationActivity extends AppCompatActivity {
    public static String KEY_SNACK_BAR_MESSAGE = "KEY_SNACK_BAR_MESSAGE";
    private final String TAG = "NavigationActivity";
    StatusViewModel statusViewModel;
    NavigationView navigationView;
    AuthViewModel authViewModel;
    private APIService apiService = APIService.getInstance();
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        // TODO: Sample usage of a ViewModel
        statusViewModel = new ViewModelProvider(this).get(StatusViewModel.class);
        statusViewModel.getStatus().subscribe(new SingleObserver<Status>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull Status status) {
                Log.d(TAG, "onSuccess: " + status.toString());
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }
        });

        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        handleIncomingSnackBarMessage(navigationView);

        View headerView = navigationView.getHeaderView(0);
        TextView navFirstName = headerView.findViewById(R.id.nav_first_name);
        TextView navEmail = headerView.findViewById(R.id.nav_email);

        authViewModel.getUser().subscribe(new SingleObserver<User>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
            }

            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull User user) {
                Log.d(TAG, "onSuccess: ");
                navFirstName.setText(user.getFirstName());
                navEmail.setText(user.getEmail());
            }

            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_dashboard, R.id.nav_client_list, R.id.nav_visits, R.id.nav_user_creation, R.id.nav_alert_creation, R.id.nav_referrals, R.id.nav_alert_list)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_new_client) {
                    Intent intent = new Intent(NavigationActivity.this, CreateClientStepperActivity.class);
                    startActivity(intent);
                }
                NavigationUI.onNavDestinationSelected(item, navController);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        setupAlertsBadge(navigationView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupAlertsBadge(navigationView);
    }

    private void setupAlertsBadge(NavigationView navigationView) {

        if (apiService.isAuthenticated()) {
            apiService.alertService.getAlerts().enqueue(new Callback<List<Alert>>() {
                @Override
                public void onResponse(Call<List<Alert>> call, Response<List<Alert>> response) {
                    if (response.isSuccessful()) {
                        List<Alert> alerts = response.body();
                        TextView alertsTV = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.nav_alert_list));
                        alertsTV.setGravity(Gravity.CENTER_VERTICAL);
                        alertsTV.setTypeface(null, Typeface.BOLD);
                        alertsTV.setTextColor(getResources().getColor(R.color.purple_700));
                        if (alerts.size() > 0) {
                            alertsTV.setText(Integer.toString(alerts.size()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<Alert>> call, Throwable t) {

                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    public void handleIncomingSnackBarMessage(View view) {
        String message = getIntent().getStringExtra(KEY_SNACK_BAR_MESSAGE);
        if (message != null && !message.isEmpty()) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

}

