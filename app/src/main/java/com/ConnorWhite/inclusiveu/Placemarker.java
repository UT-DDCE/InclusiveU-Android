package com.ConnorWhite.inclusiveu;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Placemarker {

    private ArrayList<DataField> dataFields;
    private MarkerOptions markerOptions;
    private Marker marker;
    private String link;

    public Placemarker(){
        markerOptions = new MarkerOptions();
        dataFields = new ArrayList<DataField>();
    }
    public void setName(String n){
        markerOptions.title(n);
    }
    public void setLocation(String ll){
        String[] s = ll.split(",");
        LatLng loc = new LatLng(0,0);
        if(s.length >= 2)//Should always pass if proper formatting passed in
            loc = new LatLng(Double.parseDouble(s[1]), Double.parseDouble(s[0]));
        setLocation(loc);
    }
    public void setLocation(LatLng ll){
        markerOptions.position(ll);
    }
    public void setLink(String ln){
        link = getData(ln);
    }
    public void setMarker(Marker m){
        marker = m;
    }
    public void setData(String d){
        String[] s = d.split("<br>");
        for(int i=0; i<s.length; i++) {//For each data field
            DataField field = new DataField();
            int index = s[i].indexOf(": ");
            if(index > -1) {
                field.setType(s[i].substring(0, index));
                if (index + 2 < s[i].length())
                    field.setData(s[i].substring(index+2, s[i].length()));
            }
            dataFields.add(field);
        }
    }
    public String getData(String type){//Returns given data type, or "" if does not exist
        for(int i=0; i<dataFields.size(); i++){//Search for data type
            if(dataFields.get(i).getType().equals(type))
               return dataFields.get(i).getData();
        }
        return "";//Field not found
    }
    public String getLink(){
        return link;
    }
    public boolean compareMarker(Marker m){
        if(marker.equals(m))
            return true;
        return false;
    }
    public MarkerOptions getMarker(){
        return markerOptions;
    }
}