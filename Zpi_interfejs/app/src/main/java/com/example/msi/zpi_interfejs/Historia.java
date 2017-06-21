package com.example.msi.zpi_interfejs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import java.io.FileInputStream;
import java.util.ArrayList;

public class Historia extends AppCompatActivity {

    ArrayList<Element> list;
    ListView listView;
    CustomListAdapter adapter;
    FileInputStream fIs;

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
        String x = "";
        int size;
        try
        {
            fIs = openFileInput("historia");
            while((size = fIs.read()) != -1)
            {
                x += Character.toString((char) size);
            }
            Log.i("LIFECYCLE",x);
            fIs.close();
        }catch(Exception e){e.printStackTrace();}
        int t;
        String p,k,c;
        if(x != "")
        {
            while(x.length() != 0)
            {
                t = Integer.parseInt(x.substring(0,1));
                x = x.substring(2);
                p = x.substring(0,x.indexOf("|"));
                x = x.substring(x.indexOf("|")+1);
                k = x.substring(0,x.indexOf("|"));
                x = x.substring(x.indexOf("|")+1);
                c = x.substring(0,x.indexOf("#"));
                x = x.substring(x.indexOf("#")+1);
                list.add(new Element(t,p,k,c));
            }
        }
    }
}
