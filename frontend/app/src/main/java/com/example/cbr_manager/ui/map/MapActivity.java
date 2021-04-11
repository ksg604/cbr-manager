package com.example.cbr_manager.ui.map;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.ClientViewModel;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback
   {
       class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

           private final View customInfoWindowView;

           CustomInfoWindowAdapter(){
               customInfoWindowView = getLayoutInflater().inflate(R.layout.custom_map_marker_info_window, null);
           }

           @Override
           public View getInfoContents(Marker marker) {

               setupImageViews(marker.getTag().toString());
               TextView tvTitle = ((TextView)customInfoWindowView.findViewById(R.id.customInfoWindowNameTextView));
               tvTitle.setText("Name: " + marker.getTitle());


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
        Intent intent = getIntent();

        ClientViewModel clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        clientViewModel.getAllClients().observe(this, clients -> {
            for ( Client client : clients ) {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(client.getLatitude(), client.getLongitude()))
                        .title(client.getFullName())
                        );
                marker.setTag(client.getPhotoURL());
                googleMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            }
        });


    }



   }