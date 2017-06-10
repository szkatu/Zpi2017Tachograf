package com.example.msi.zpi_interfejs;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Harrel on 5/30/2017.
 */

public class PointOfInterest {

    public LatLng pos;
    public String name;
    public int distance;
    public int duration;
    public String durationString;
    public String distanceString;

    public boolean isParking;
    public boolean isGasStation;
    public boolean isHotel;

    public PointOfInterest(LatLng p, String n) {
        pos = p;
        name = n;
        distance = 0;
        duration = 0;
        durationString = new String();
        distanceString = new String();
    }

    public String toString(){
        return name + " - Lat: " + pos.latitude + ", Lng: " + pos.longitude + " - Distance: " + distance + ", Duration: " + duration;
    }
}
