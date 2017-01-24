package com.ConnorWhite.inclusiveu;

import android.content.Context;
import android.content.res.AssetManager;
import android.location.LocationManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.os.Bundle;
import android.location.Location;
import android.location.LocationListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class MapMaker implements LocationListener, GoogleMap.OnInfoWindowClickListener {

    private Context context;
    private GoogleMap map;
    private LocationManager manager;
    private LatLng center, me;
    private LatLngBounds bounds;
    private String snippetName, linkName, snippetPrefix, snippet, snippetSuffix, fileName;
    private int zoom;
    private WebHandler webHandler;
    private ArrayList<Placemarker> placemarkers;
    private URL url;

    public MapMaker(Context c, GoogleMap gm, LocationManager lm){
        context = c;
        map = gm;
        manager = lm;
        zoom = 15;
        snippet = "";
        snippetPrefix = "";
        snippetSuffix = "";
        placemarkers = new ArrayList<Placemarker>();
        setSettings(map);
    }
    public void setBounds(LatLngBounds b){
        bounds = b;
        if(center == null)//Checks if center has already been set, as center has priority over bounds.getCenter()
            map.moveCamera(CameraUpdateFactory.newLatLng(bounds.getCenter()));
    }
    public void setCenter(LatLng ll){
        center = ll;
        map.moveCamera(CameraUpdateFactory.newLatLng(center));
    }
    public void setLinkName(String ln){
        webHandler = new WebHandler(context);
        linkName = ln;
    }
    public void setSnippetName(String sn) {
        snippetName = sn;
    }
    public void setSnippetPrefix(String p){
        snippetPrefix = p;
    }
    public void setSnippetSuffix(String s){
        snippetSuffix = s;
    }
    public void setURL(String u){
        try {
            url = new URL(u);
        }catch(Exception e){}
    }
    public void setFileName(String fn){
        fileName = fn;
    }
    public void setZoom(int z){
        zoom = z;
        map.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }
    private void setSettings(GoogleMap m){
        //Enable controls
        m.getUiSettings().setAllGesturesEnabled(true);
        m.getUiSettings().setCompassEnabled(true);
        m.getUiSettings().setMapToolbarEnabled(true);
        m.getUiSettings().setMyLocationButtonEnabled(true);
        m.getUiSettings().setIndoorLevelPickerEnabled(true);
        m.getUiSettings().setZoomControlsEnabled(true);
        m.moveCamera(CameraUpdateFactory.zoomTo(zoom));
    }
    public GoogleMap getMap(){
        populate();
        return map;
    }

    private void populate(){
        //Create a new KML file reader
        AssetManager assetManager = context.getAssets();
        InputStream in;
        KMLReader reader = new KMLReader();

        //Read data file
        try {//First try to read from url
            in = url.openStream();
            reader = new KMLReader(in);
            cacheFile(in);//Save file
            in.close();
        } catch (Exception e) {
            try{//Second try to read from cached file
                in = new FileInputStream(context.getFilesDir() + fileName);
                reader = new KMLReader(in);
                in.close();
            }catch(Exception e2) {//Finally read from default file
                try {
                    in = assetManager.open(fileName);
                    reader = new KMLReader(in);
                    in.close();
                } catch (Exception e3) {
                }
            }
        }

        //Get placemarkers from reader
        placemarkers = reader.getPlacemarkers();

        //Place markers on map
        for(int i=0; i<placemarkers.size(); i++) {
            Placemarker mark = placemarkers.get(i);
            MarkerOptions markerOptions = mark.getMarker();
            if(snippetName != null) {
                snippet = mark.getData(snippetName);
                markerOptions.snippet(snippetPrefix + snippet + snippetSuffix);
            }
            if(linkName != null){
                mark.setLink(linkName);
            }
            mark.setMarker(map.addMarker(markerOptions));
        }
        if(linkName != null){
            map.setOnInfoWindowClickListener(this);
        }
    }

    private void cacheFile(InputStream in) {
        //Delete old file
        context.deleteFile(fileName);
        //Create new file
        File file = new File(context.getFilesDir(), fileName);
        Scanner sc = new Scanner(in);
        FileOutputStream outputStream;
        //Write new file
        try {
            outputStream = new FileOutputStream(context.getFilesDir() + fileName);
            while(sc.hasNextLine())
                outputStream.write((sc.nextLine() + "\n").getBytes());
            outputStream.close();
        } catch (Exception e) {}
    }

    @Override
    public void onLocationChanged(Location loc) {
        LatLng newLocation = getLatLng(loc);
        if(bounds == null || bounds.contains(newLocation))
            me = newLocation;//Update location
    }

    private LatLng getLatLng(Location location){
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        for(int i=0; i<placemarkers.size(); i++){
            Placemarker mark = placemarkers.get(i);
            if(mark.compareMarker(marker))
                webHandler.goToURL(mark.getLink());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}
    @Override
    public void onProviderEnabled(String provider) {}
    @Override
    public void onProviderDisabled(String provider) {}
}
