package com.example.cbr_manager.ui.homepage;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import com.example.cbr_manager.NavigationActivity;
import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.clientlist.ClientListFragment;
import com.example.cbr_manager.ui.clientlist.ClientListRecyclerItemAdapter;
import com.example.cbr_manager.ui.clientselector.ClientSelectorActivity;
import com.example.cbr_manager.ui.create_client.CreateClientActivity;
import com.example.cbr_manager.ui.createvisit.CreateVisitActivity;
import com.example.cbr_manager.ui.login.LoginActivity;

public class HomepageActivity extends AppCompatActivity {
    private ImageButton newClientButton, newVisitButton, dashboardButton;
    private ImageButton newReferralButton, clientListButton, syncButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Home Page");
        setContentView(R.layout.homepage);

        newClientButton = findViewById(R.id.newClientButton);
        newClientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, CreateClientActivity.class);
                startActivity(intent);
            }
        });

        newVisitButton = findViewById(R.id.newVisitButton);
        newVisitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(HomepageActivity.this, CreateVisitActivity.class);
                Intent intent = new Intent(HomepageActivity.this, ClientSelectorActivity.class);
                startActivity(intent);
            }
        });

        dashboardButton = findViewById(R.id.dashboardButton);
        dashboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomepageActivity.this, NavigationActivity.class);
                startActivity(intent);
            }
        });

        newReferralButton = findViewById(R.id.newReferralButton);

        clientListButton = findViewById(R.id.clientListButton);
        clientListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(android.R.id.content, new ClientListFragment()).commit();
                fragmentTransaction.addToBackStack(null);
            }
        });

        syncButton = findViewById(R.id.syncButton);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }
}
