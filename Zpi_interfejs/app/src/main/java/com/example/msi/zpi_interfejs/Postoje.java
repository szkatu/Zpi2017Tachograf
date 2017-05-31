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
        start();

        listView=(ListView)view.findViewById(R.id.listview3);

        adapter = new CustomPostojeAdapter((Activity)view.getContext(), list);
        listView.setAdapter(adapter);

        return view;
    }

    void start()
    {
        list = new ArrayList<>();
        list.add(new MiejscePostojowe("Stacja paliw Orlen", "Stacja paliw", "Sredni", "150m", R.mipmap.gas_station));
        list.add(new MiejscePostojowe("Motel Rezydent", "Stacja paliw", "Sredni", "500m", R.mipmap.hotel));
        list.add(new MiejscePostojowe("Parking", "Stacja paliw", "Duży", "600m", R.mipmap.parking));
    }

}
