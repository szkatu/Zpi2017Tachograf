package com.example.msi.zpi_interfejs;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by Harrel on 01.04.2017.
 */

public class CustomLocationProvider implements LocationSource, LocationListener
{
    private OnLocationChangedListener listener;
    private LocationManager locationManager;
    private Context context;

    private LatLng lastLocation;

    /*Example data sets*/
    private LatLng[] dataSet1 = new LatLng[50];
    private LatLng[] dataSet2 = new LatLng[50];

    /*Border cross detection variables*/
    private LatLng firstPoint;
    private BorderCross nearestCross;
    private String currentCountry;
    private BorderCross[] currentCountryCrosses;

    /*Provide theses 2 arrays and then call 'activate' method*/
    public BorderCross[] allCrosses;
    public LatLng[] dataSet;




    public CustomLocationProvider(Context context){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        this.context = context;
        firstPoint = null;
        currentCountry = "Polska";


        /*Initialize 2 example data sets*/
        for(int i = 0; i < dataSet1.length; i++){
            dataSet1[i] = new LatLng(51.666874, 14.762655 - i * 0.0005);
        }

        for(int i = 0; i < dataSet2.length; i++){
            if(i < 20)
                dataSet2[i] = new LatLng(51.666874, 14.762655 - i * 0.0005);
            else
                dataSet2[i] = new LatLng(51.666874, 14.742655 + i * 0.0005);
        }
    }


    public void getUpdatesfromTestData(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(LatLng pos : dataSet){
                    try{
                        Location loc = new Location("test");
                        loc.setLatitude(pos.latitude);
                        loc.setLongitude(pos.longitude);
                        onLocationChanged(loc);
                        //Thread.sleep(500);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
    }

    @Override
    public void activate(OnLocationChangedListener listener){
        this.listener = listener;

        /*Requests location updates from real GPS provider*/
        LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
        if(gpsProvider != null) {
            locationManager.requestLocationUpdates(gpsProvider.getName(), 500, 0, this);
        }

        /*Requests location updates from previously prepared data set of LatLngs*/
        //getUpdatesfromTestData();
    }

    @Override
    public void deactivate(){
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location){

        lastLocation = new LatLng(location.getLatitude(), location.getLongitude());
        if(allCrosses != null) {
            if (nearestCross == null) {
                for (BorderCross cross : allCrosses) {
                    if (firstPoint == null && location.distanceTo(cross.getLocation()) <= cross.radius) {
                        firstPoint = new LatLng(location.getLatitude(), location.getLongitude());
                        nearestCross = cross;
                        //makeToastOnUI("Entered the radius!");
                        break;
                    }

                }
            } else {
                if (firstPoint != null && location.distanceTo(nearestCross.getLocation()) > nearestCross.radius) {
                    Location tmp = new Location("tmp");
                    tmp.setLatitude(firstPoint.latitude);
                    tmp.setLongitude(firstPoint.longitude);

                    if (location.distanceTo(tmp) > nearestCross.radius) {
                        this.setCurrentCountry(nearestCross.country1 == this.getCurrentCountry() ? nearestCross.country2 : nearestCross.country1);
                        //makeToastOnUI("Escaped the radius and crossed the border!");
                    } else {
                        //makeToastOnUI("Escaped the radius and did not cross the border!");
                    }
                    nearestCross = null;
                    firstPoint = null;
                }
            }
        }


         /* Push location updates to the registered listener..
            (this ensures that my-location layer will set the blue dot at the new/received location) */
        if(listener != null && location != null){
            listener.onLocationChanged(location);

        }
    }

    @Override
    public void onProviderDisabled(String provider)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        // TODO Auto-generated method stub

    }

    /*Getter and setters*/
    public String getCurrentCountry() {
        return currentCountry;
    }

    public void setCurrentCountry(String currentCountry) {
        ArrayList<BorderCross> temp = new ArrayList<>();
        for(BorderCross crs : allCrosses)
        {
            if(crs.containsCountry(currentCountry))
                temp.add(crs);
        }
        currentCountryCrosses = (BorderCross[])temp.toArray();
        this.currentCountry = currentCountry;
    }

    public LatLng getLastLocation() {
        return lastLocation;
    }

}