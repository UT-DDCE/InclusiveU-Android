package com.ConnorWhite.inclusiveu;

public class DataField {

    private String type;
    private String data;

    public DataField(){
        type = "";
        data = "";
    }
    public DataField(String t, String d){
        type = t;
        data = d;
    }
    public void setType(String t){
        type = t;
    }
    public void setData(String d){
        data = d;
    }
    public String getType(){
        return type;
    }
    public String getData(){
        return data;
    }
}