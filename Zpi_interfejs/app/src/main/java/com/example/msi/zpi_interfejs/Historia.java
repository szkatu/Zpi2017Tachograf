package com.example.msi.zpi_interfejs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.util.ArrayList;

public class Historia extends AppCompatActivity {

    ArrayList<Element> list;
    ListView listView;
    CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historia);
        start();

        listView=(ListView)findViewById(R.id.listview);

        adapter = new CustomListAdapter(this, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view,
                                    int position, long id) {

            }
        });
    }

    void start()
    {
        list = new ArrayList<>();
        list.add(new Element(1, "12.02.16 06:31", "12.02.16 11:20", "4:45"));
        list.add(new Element(2, "12.02.16 11:20", "12.02.16 11:50", "0:30"));
        list.add(new Element(1, "12.02.16 11:50", "12.02.16 18:00", "4:45"));
        list.add(new Element(3, "12.02.16 18:00", "12.02.17 07:00", "13:00"));
        list.add(new Element(4, "12.02.17 07:00", "12.02.17 07:26", "0:26"));
        list.add(new Element(1, "12.02.16 06:31", "12.02.16 11:20", "4:45"));
        list.add(new Element(2, "12.02.16 11:20", "12.02.16 11:50", "0:30"));
        list.add(new Element(1, "12.02.16 11:50", "12.02.16 18:00", "4:45"));
        list.add(new Element(3, "12.02.16 18:00", "12.02.17 07:00", "13:00"));
        list.add(new Element(4, "12.02.17 07:00", "12.02.17 07:26", "0:26"));
        list.add(new Element(1, "12.02.16 06:31", "12.02.16 11:20", "4:45"));
        list.add(new Element(2, "12.02.16 11:20", "12.02.16 11:50", "0:30"));
        list.add(new Element(1, "12.02.16 11:50", "12.02.16 18:00", "4:45"));
        list.add(new Element(3, "12.02.16 18:00", "12.02.17 07:00", "13:00"));
        list.add(new Element(4, "12.02.17 07:00", "12.02.17 07:26", "0:26"));
    }
}
