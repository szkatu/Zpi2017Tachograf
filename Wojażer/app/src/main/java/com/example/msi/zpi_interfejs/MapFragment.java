package com.example.msi.zpi_interfejs;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MapFragment extends Fragment implements OnMapReadyCallback {

    public CustomLocationProvider provider;

    private GoogleMap mMap;
    private ArrayList<Marker> markers;
    private Context context;

    public MapFragment() {
        // Required empty public constructor
        Log.d("INIT", "Constr");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("INIT", "OnCreate");
        markers = new ArrayList<Marker>();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_map, container, false);

        Log.d("INIT", "OnCreateView");

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance){
        super.onActivityCreated(savedInstance);

        Log.d("INIT", "OnActivityCreated");

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if(mapFragment == null)
            Log.d("INIT", "NULLED");
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context = activity;
        Log.d("INIT", "Attached");
    }

    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("INIT", "MapRdy");

        //Set custom info window (marker description) to display all the snippets properly
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View v = ((LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.info_window_layout, null);

                ImageView imgView = (ImageView) v.findViewById(R.id.imageInfo);
                TextView tvTitle = (TextView) v.findViewById(R.id.tv_title);
                TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                String snippet = marker.getSnippet();

                tvTitle.setText(marker.getTitle());
                if(snippet != null) {
                    String[] split = snippet.split("\n");
                    tvLat.setText(split[0]);
                    tvLng.setText(split[1]);
                    switch(split[2]){
                        case "G":
                            imgView.setImageResource(R.mipmap.gas_station);
                            //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                            break;
                        case "H":
                            imgView.setImageResource(R.mipmap.hotel);
                            //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            break;
                        case "P":
                            imgView.setImageResource(R.mipmap.parking);
                            //marker.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            break;
                    }
                }
                else{
                    tvLat.setVisibility(View.GONE);
                    tvLng.setVisibility(View.GONE);
                }

                return v;
            }
        });

        LatLng cross = new LatLng(51.666874, 14.752655);
        mMap.addMarker(new MarkerOptions().position(cross).title("Przejście graniczne 1"));

        LatLng cross2 = new LatLng(51.180793, 15.008730);
        mMap.addMarker(new MarkerOptions().position(cross2).title("Przejście graniczne 2"));

        provider = new CustomLocationProvider(context);
        provider.allCrosses = new BorderCross[2];
        provider.allCrosses[0] = new BorderCross("Polska", "Niemcy", cross, 100);
        provider.allCrosses[1] = new BorderCross("Polska", "Niemcy", cross2, 100);
        mMap.setLocationSource(provider);
        mMap.setMyLocationEnabled(true);
    }

    public void updatePOIs(ArrayList<PointOfInterest> pois){
        ArrayList<PointOfInterest> poislocal= new ArrayList<> (pois);
        if(mMap != null) {
            for (Marker m : markers) {
                m.remove();
            }
            markers.clear();

            int cp = 0, ch = 0, cg = 0;
            //Add new POI markers
            for (PointOfInterest p : poislocal) {

                String type;
                float color;
                if (p.isGasStation) {
                    type = "G";
                    color = BitmapDescriptorFactory.HUE_RED;
                } else if (p.isHotel) {
                    type = "H";
                    color = BitmapDescriptorFactory.HUE_GREEN;
                } else {
                    type = "P";
                    color = BitmapDescriptorFactory.HUE_BLUE;
                }
                markers.add(mMap.addMarker(new MarkerOptions().position(p.pos)
                        .title(p.name)
                        .snippet("ETA: " + p.durationString + "\nDistance: " + p.distanceString + "\n" + type)
                        .icon(BitmapDescriptorFactory.defaultMarker(color))));
            }
        }
        Log.i("MARKER", "MMMMM");
    }

    public void zoomOncurrentLocation(){
        if(provider.getLastLocation() != null)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(provider.getLastLocation(), 14));
    }

}
