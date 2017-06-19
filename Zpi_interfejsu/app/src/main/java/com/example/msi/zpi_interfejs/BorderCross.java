package com.example.msi.zpi_interfejs;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Harrel on 25.04.2017.
 */

public class BorderCross {
    public String country1;
    public String country2;
    public LatLng point;
    public int radius; /*In meters*/

    public BorderCross(String c1, String c2, LatLng pt, int r) {
        country1 = c1;
        country2 = c2;
        point = pt;
        radius = r;
    }

    public boolean containsCountry(String ctr) {
        return ctr.equals(country1) || ctr.equals(country2);
    }

    public Location getLocation() {
        Location loc = new Location("BorderCross location");
        loc.setLatitude(point.latitude);
        loc.setLongitude(point.longitude);
        return loc;
    }
}
