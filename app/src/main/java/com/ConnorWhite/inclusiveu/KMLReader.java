package com.ConnorWhite.inclusiveu;

import java.io.InputStream;;
import java.util.Scanner;
import java.util.ArrayList;

public class KMLReader {

    final private String[] layerTag = {"<Folder>", "</Folder>"}, placeTag = {"<Placemark", "</Placemark"}, nameTag = {"<name>", "</name>"}, descriptionTag = {"<description><![CDATA[", "]]></description>"}, pointTag = {"<Point>", "</Point>"}, coorsTag = {"<coordinates>", "</coordinates>"};
    private ArrayList<Layer> layers;

    public KMLReader(){
        layers = new ArrayList<Layer>();
    }
    public KMLReader(InputStream in) {//Builds list of layers containing data from an InputStream
        Scanner sc = new Scanner(in);
        layers = new ArrayList<Layer>();

        while(sc.hasNextLine()) {
            if(isTag(sc.nextLine().trim(), layerTag)){//Found a new layer
                Layer layer = new Layer();
                String line;
                do{
                    line = sc.nextLine().trim();
                    if(isTag(line, nameTag))
                        layer.setName(extract(line, nameTag));
                    else if (isTag(line, placeTag)) {//Found a new placemaker
                        Placemarker placemarker = new Placemarker();
                        do{
                            line = sc.nextLine().trim();
                            if(isTag(line, nameTag))
                                placemarker.setName(extract(line, nameTag));
                            else if(isTag(line, descriptionTag))
                                placemarker.setData(extract(line, descriptionTag));
                            else if(isTag(line, pointTag)) {//Found a new point
                                do {
                                    line = sc.nextLine().trim();
                                    if(isTag(line, coorsTag))
                                        placemarker.setLocation(extract(line, coorsTag));
                                }while(!line.startsWith(pointTag[1]) && sc.hasNext());
                            }
                        }while(!line.startsWith(placeTag[1]) && sc.hasNext());
                        layer.addPlacemarker(placemarker);
                    }
                }while(!line.startsWith(layerTag[1]) && sc.hasNext());
                layers.add(layer);
            }
        }
    }
    private boolean isTag(String l, String[] tag){//Checks if a string matches the format of a given tag
        if(l.startsWith(tag[0]))
            return true;
        return false;
    }
    private String extract(String l, String[] tag){//Extracts the string from within a tag
        return l.replace(tag[0], "").replace(tag[1], "");
    }
    public ArrayList<Placemarker> getPlacemarkers(){//Gets collection of all placemarkers from all layers
        ArrayList<Placemarker> placemarkers = new ArrayList<Placemarker>();
        for(int i=0; i<layers.size(); i++){
            placemarkers.addAll(layers.get(i).getPlacemarkers());
        }
        return placemarkers;
    }
    public ArrayList<Placemarker> getPlacemarkers(String layerName){//Gets collection of placemarkers in one layer
        ArrayList<Placemarker> placemarkers = new ArrayList<Placemarker>();
        for(int i=0; i<layers.size(); i++){
            Layer layer = layers.get(i);
            if(layer.getName().equals(layerName))
                placemarkers = layer.getPlacemarkers();
        }
        return placemarkers;
    }
}