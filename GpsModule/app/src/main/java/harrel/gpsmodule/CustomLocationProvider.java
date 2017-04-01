package harrel.gpsmodule;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Harrel on 01.04.2017.
 */

public class CustomLocationProvider implements LocationSource, LocationListener
{
    private LocationSource.OnLocationChangedListener listener;
    private LocationManager locationManager;
    private Handler handler;
    private Context context;

    private LatLng[] dataSet1 = new LatLng[50];
    private LatLng[] dataSet2 = new LatLng[50];
    private LatLng firstPoint;
    public Location cross;
    public double radius;


    public CustomLocationProvider(Context context, Handler handler){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        this.handler = handler;
        this.context = context;
        firstPoint = null;
        cross = new Location("cross");
        cross.setLatitude(51.666874);
        cross.setLongitude(14.752655);
        radius = 100;
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

    public void makeToastOnUI(final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getUpdatesfromTestData(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                for(LatLng pos : dataSet2){
                    try{
                        Location loc = new Location("test");
                        loc.setLatitude(pos.latitude);
                        loc.setLongitude(pos.longitude);
                        onLocationChanged(loc);
                        Thread.sleep(500);
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
//        LocationProvider gpsProvider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
//        if(gpsProvider != null) {
//            locationManager.requestLocationUpdates(gpsProvider.getName(), 500, 0, this);
//        }
        getUpdatesfromTestData();
    }

    @Override
    public void deactivate(){
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location){

        if(firstPoint == null && location.distanceTo(cross) <= radius){
            firstPoint = new LatLng(location.getLatitude(), location.getLongitude());
            makeToastOnUI("Entered the radius!");
        }
        else if(firstPoint != null && location.distanceTo(cross) > radius){
            Location tmp = new Location("tmp");
            tmp.setLatitude(firstPoint.latitude);
            tmp.setLongitude(firstPoint.longitude);

            if(location.distanceTo(tmp) > radius){
                makeToastOnUI("Escaped the radius and crossed the border!");
            }
            else{
                makeToastOnUI("Escaped the radius and did not cross the border!");
            }
            firstPoint = null;
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


}