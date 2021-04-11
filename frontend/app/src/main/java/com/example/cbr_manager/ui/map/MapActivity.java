package com.example.cbr_manager.ui.map;

import com.example.cbr_manager.R;
import com.example.cbr_manager.service.client.Client;
import com.example.cbr_manager.ui.ClientViewModel;
import com.example.cbr_manager.ui.clientdetails.ClientDetailsActivity;
import com.example.cbr_manager.ui.create_client.CreateClientStepperActivity;
import com.example.cbr_manager.utils.Helper;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

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


import java.util.HashMap;

import dagger.hilt.android.AndroidEntryPoint;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

@AndroidEntryPoint
public class MapActivity extends AppCompatActivity implements
        OnMapReadyCallback {
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View customInfoWindowView;
        HashMap<String, String> clientInfoTable = new HashMap<String, String>();

        CustomInfoWindowAdapter() {
            customInfoWindowView = getLayoutInflater().inflate(R.layout.custom_map_marker_info_window, null);
        }

        @Override
        public View getInfoContents(Marker marker) {

            clientInfoTable = (HashMap<String, String>) marker.getTag();

            setupImageViews(clientInfoTable.get("photourl"));
            TextView nameTextView = ((TextView) customInfoWindowView.findViewById(R.id.customInfoWindowNameTextView));
            nameTextView.setText("Name: " + clientInfoTable.get("name"));

            TextView locationTextView = ((TextView) customInfoWindowView.findViewById(R.id.customInfoWindowLocationTextView));
            locationTextView.setText("Location: " + clientInfoTable.get("location"));

            return customInfoWindowView;
        }


        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

        private void setupImageViews(String imageURL) {
            ImageView displayPicture = (ImageView) customInfoWindowView.findViewById(R.id.customInfoWindowProfilePicture);
            Helper.setImageViewFromURL(imageURL, displayPicture, R.drawable.client_details_placeholder);
        }

    }


    private GoogleMap map;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private FusedLocationProviderClient fusedLocationClient;
    protected Location currentLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if ( ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }


        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        map.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        ClientViewModel clientViewModel = new ViewModelProvider(this).get(ClientViewModel.class);

        clientViewModel.getAllClients().observe(this, clients -> {
            for ( Client client : clients ) {
                HashMap<String, String> clientInfoTable = new HashMap<String, String>();
                Marker marker = map.addMarker(new MarkerOptions()
                        .position(new LatLng(client.getLatitude(), client.getLongitude()))
                        .title(client.getFullName())
                        );
                clientInfoTable.put("photourl", client.getPhotoURL());
                clientInfoTable.put("id", client.getId().toString());
                clientInfoTable.put("location", client.getLocation());
                clientInfoTable.put("name", client.getFullName());
                marker.setTag(clientInfoTable);
                map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        Intent intent = new Intent(getBaseContext(), ClientDetailsActivity.class);
                        intent.putExtra(ClientDetailsActivity.KEY_CLIENT_ID, Integer.parseInt(clientInfoTable.get("id")));
                        startActivity(intent);
                    }
                });
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getCurrentLocation(PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location
                            if ( location != null ) {
                                currentLocation = location;
                            }
                        }
                    });
        }

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    map.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(MapActivity.this, "You have to allow user permissions to enjoy all of the map's services!", Toast.LENGTH_LONG).show();
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        map.setMyLocationEnabled(true);
                    }
                }
                if (map != null && currentLocation != null) {

                    map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
                        @Override
                        public boolean onMyLocationButtonClick() {

                            CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                            CameraUpdate zoom=CameraUpdateFactory.zoomTo(12);

                            map.moveCamera(center);
                            map.animateCamera(zoom);
                            return false;
                        }
                    });

                }

            }
        }
    }





   }