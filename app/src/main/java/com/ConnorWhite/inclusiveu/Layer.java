package com.ConnorWhite.inclusiveu;

import java.util.ArrayList;

public class Layer {
    private String name;
    private ArrayList<Placemarker> placemarkers;

    public Layer(){
        name = "";
        placemarkers = new ArrayList<Placemarker>();
    }
    public void setName(String n){
        name = n;
    }
    public void addPlacemarker(Placemarker p){
        placemarkers.add(p);
    }
    public String getName(){
        return name;
    }
    public ArrayList<Placemarker> getPlacemarkers(){
        return placemarkers;
    }
}
