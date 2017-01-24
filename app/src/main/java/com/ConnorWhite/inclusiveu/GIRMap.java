package com.ConnorWhite.inclusiveu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GIRMap extends FragmentActivity implements OnMapReadyCallback {

    final private String GIRMapFile = "Gender Inclusive Restrooms at UT-Austin.kml", GIRMapURL = "https://www.google.com/maps/d/u/0/kml?mid=1lA-3jWjNar_DWJsmYojIXn5-ZNw&forcekml=1&cid=mp&cv=LLS4f3GpivQ.en.";
    private GoogleMap mMap;
    LatLng center = new LatLng(30.28565, -97.73921);//Set default location to the UT Tower
    LatLngBounds campus = new LatLngBounds(new LatLng(30.278620,-97.742686), new LatLng(30.291998,-97.725392));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girmap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Set up location services
        LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Create map
        ArrayList<String> files = new ArrayList<String>();
        files.add(GIRMapURL);
        //files.add(GIRMapFile);
        MapMaker maker = new MapMaker(this, googleMap, locationManager);
        maker.setURL(GIRMapURL);
        maker.setFileName(GIRMapFile);
        maker.setBounds(campus);
        maker.setSnippetPrefix("Rooms(s): ");
        maker.setSnippetName("Room Numbers");
        mMap = maker.getMap();

        //Request location updates
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER, 0, 0, maker);
    }

    public void launchHome(View view) {
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}
