package com.example.msi.zpi_interfejs;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class Postoje extends Fragment {

    ArrayList<MiejscePostojowe> list;
    ListView listView;
    CustomPostojeAdapter adapter;
    ProgressIndicator mProgressIndicator;

    public Postoje() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_postoje, container, false);

        listView=(ListView)view.findViewById(R.id.listview3);
        list = new ArrayList<>();
        adapter = new CustomPostojeAdapter((Activity)view.getContext(), list);
        listView.setAdapter(adapter);

        return view;
    }

    void updatePOIs(ArrayList<PointOfInterest> pois)
    {
        list.clear();
        for(PointOfInterest p : pois){
            String type;
            int icon;
            if(p.isGasStation) {
                type = "Stacja Paliw";
                icon = R.mipmap.gas_station;
            }
            else if(p.isHotel) {
                type = "Hotel";
                icon = R.mipmap.hotel;
            }
            else {
                type = "Parking";
                icon = R.mipmap.parking;
            }
            list.add(new MiejscePostojowe(p.name, type, p.durationString, p.distanceString, icon));
        }
        adapter.notifyDataSetChanged();
    }

}
