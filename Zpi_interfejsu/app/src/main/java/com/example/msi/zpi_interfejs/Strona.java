package com.example.msi.zpi_interfejs;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Strona extends Fragment {

    ArrayList<PointOfInterest> list;
    ListView listView;
    CustomPostojeAdapter adapter;
    ProgressIndicator mProgressIndicator;
    TextView tekst;
    TextView tekst2;
    ImageButton j,p,o,ip;

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

        mProgressIndicator = (ProgressIndicator) view.findViewById(R.id.progress_indicator);
        listView =(ListView) view.findViewById(R.id.listview2);
        tekst = (TextView)view.findViewById(R.id.textView3);
        tekst2 = (TextView)view.findViewById(R.id.textView2);
        mProgressIndicator.setForegroundColor(Color.parseColor("#D3D3D3"));
        mProgressIndicator.setBackgroundColor(Color.parseColor("#3F51B5"));

        mProgressIndicator.setValue(0f);


        j = (ImageButton)view.findViewById(R.id.jazda);
        p = (ImageButton)view.findViewById(R.id.przerwa);
        o = (ImageButton)view.findViewById(R.id.odpoczynek);
        ip = (ImageButton)view.findViewById(R.id.innaPraca);

        list = new ArrayList<PointOfInterest>();
        adapter = new CustomPostojeAdapter((Activity)view.getContext(), list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PointOfInterest p = (PointOfInterest)listView.getItemAtPosition(position);

                AlertDialog.Builder builder1 = new AlertDialog.Builder((Activity)view.getContext());
                builder1.setMessage("Czy chcesz uruchomić nawigacje do miejsca: " + p.name + "?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Tak",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String format = "google.navigation:q=" + p.pos.latitude + "," + p.pos.longitude;

                                Uri uri = Uri.parse(format);

                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        });

                builder1.setNegativeButton(
                        "Nie",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        return view;
    }

    public void setTekst(String s)
    {
        tekst.setText(s);
    }

    public void setTekst2(String s)
    {
        tekst2.setText(s);
    }

    void updatePOIs(List<PointOfInterest> pois)
    {
        list.clear();
        for(PointOfInterest p : pois){

            list.add(p);
        }
        adapter.notifyDataSetChanged();
    }
}
