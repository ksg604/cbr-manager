package com.example.cbr_manager.ui.map;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.ClientViewModel;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsActivity;
import com.example.cbr_manager.utils.Helper;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback
   {
       class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

           private final View customInfoWindowView;
           HashMap<String, String> clientInfoTable = new HashMap<String, String>();

           CustomInfoWindowAdapter(){
               customInfoWindowView = getLayoutInflater().inflate(R.layout.custom_map_marker_info_window, null);
           }

           @Override
           public View getInfoContents(Marker marker) {

               clientInfoTable = (HashMap<String, String>)marker.getTag();

               setupImageViews(clientInfoTable.get("photourl"));
               TextView nameTextView = ((TextView)customInfoWindowView.findViewById(R.id.customInfoWindowNameTextView));
               nameTextView.setText("Name: " + clientInfoTable.get("name"));

               TextView locationTextView = ((TextView)customInfoWindowView.findViewById(R.id.customInfoWindowLocationTextView));
               locationTextView.setText("Location: " + clientInfoTable.get("location"));

               Button customInfoWindowButton = ((Button)customInfoWindowView.findViewById(R.id.customInfoWindowButton));
               customInfoWindowButton.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent = new Intent(getApplicationContext(), ClientDetailsActivity.class);
                       intent.putExtra(ClientDetailsActivity.KEY_CLIENT_ID, Integer.parseInt(clientInfoTable.get("id")));
                       startActivity(intent);
                   }
               });
               
               return customInfoWindowView;
           }


           @Override
           public View getInfoWindow(Marker marker) {
               // TODO Auto-generated method stub
               return null;
           }

           private void setupImageViews(String imageURL) {
               ImageView displayPicture = (ImageView)customInfoWindowView.findViewById(R.id.customInfoWindowProfilePicture);
               Helper.setImageViewFromURL(imageURL, displayPicture, R.drawable.client_details_placeholder);
           }

       }




    private GoogleMap map;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        HashMap<String, String> clientInfoTable = new HashMap<String, String>();
        Intent intent = getIntent();

        ClientViewModel clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        clientViewModel.getAllClients().observe(this, clients -> {
            for ( Client client : clients ) {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(client.getLatitude(), client.getLongitude()))
                        .title(client.getFullName())
                        );
                clientInfoTable.put("photourl", client.getPhotoURL());
                clientInfoTable.put("id", client.getId().toString());
                clientInfoTable.put("location", client.getLocation());
                clientInfoTable.put("name", client.getFullName());
                marker.setTag(clientInfoTable);
                googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            }
        });


    }



   }