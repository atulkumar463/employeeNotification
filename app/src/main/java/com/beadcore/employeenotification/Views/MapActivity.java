package com.beadcore.employeenotification.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.beadcore.employeenotification.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    Double latitude,longitude;
    GoogleMap googleMap;
    MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));
        Log.i("SDSD","Lat : "+latitude+"  Long : "+longitude);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap gMap) {
//        gMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("Marker"));


        googleMap = gMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Edit the following as per you needs

        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);


        LatLng placeLocation = new LatLng(latitude,longitude); //Make them global
        googleMap.addMarker(new MarkerOptions().position(placeLocation)
                .title("Your Attendance Was Marked Here!!")).showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);
    }
}
