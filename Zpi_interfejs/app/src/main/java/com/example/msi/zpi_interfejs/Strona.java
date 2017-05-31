package com.example.msi.zpi_interfejs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class Strona extends Fragment {

    ArrayList<String> list;
    ListView listView;
    ArrayAdapter adapter;
    ProgressIndicator mProgressIndicator;

    public Strona() {
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
        View view = inflater.inflate(R.layout.fragment_strona, container, false);
        start();

        mProgressIndicator = (ProgressIndicator) view.findViewById(R.id.progress_indicator);
        listView =(ListView) view.findViewById(R.id.listview2);

        mProgressIndicator.setForegroundColor(Color.parseColor("#D3D3D3"));
        mProgressIndicator.setBackgroundColor(Color.parseColor("#3F51B5"));

        mProgressIndicator.setValue(0.4f);


        adapter = new ArrayAdapter<String>((Activity)view.getContext(), R.layout.simple_listview, list);
        listView.setAdapter(adapter);

        return view;
    }

    void start()
    {
        list = new ArrayList<>();
        list.add("150m: Stacja paliw Orlen");
        list.add("500m: Motel Rezydent");//NAZWA TYP WIELKOSC ODLEGLOSC
        list.add("600m: Parking");
    }
}
