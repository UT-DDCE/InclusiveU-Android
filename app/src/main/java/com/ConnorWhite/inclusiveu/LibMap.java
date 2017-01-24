package com.ConnorWhite.inclusiveu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class LibMap extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    private String LibMapFile = "UT Libraries Map.kml", LibMapURL = "https://www.google.com/maps/d/u/0/kml?mid=1bqRbBRb_yi_nPG_uxn6hCQ8sBTI&forcekml=1&cid=mp&cv=LLS4f3GpivQ.en.";
    private GoogleMap mMap;
    LatLng center = new LatLng(30.28565, -97.73921);//Set default location to the UT Tower
    LatLngBounds campus = new LatLngBounds(new LatLng(30.278620,-97.742686), new LatLng(30.291998,-97.725392));
    private WebHandler webHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lib_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        webHandler = new WebHandler(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Set up location services
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Create map
        MapMaker maker = new MapMaker(this, googleMap, locationManager);
        maker.setURL(LibMapURL);
        maker.setFileName(LibMapFile);
        maker.setBounds(campus);
        maker.setSnippetName("URL");
        maker.setLinkName("URL");
        mMap = maker.getMap();

        //Request location updates
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, maker);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        webHandler.goToURL(marker.getSnippet());
    }

    public void launchHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
