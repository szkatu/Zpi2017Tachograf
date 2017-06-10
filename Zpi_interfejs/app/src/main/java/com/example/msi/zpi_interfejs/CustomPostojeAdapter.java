package com.example.msi.zpi_interfejs;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomPostojeAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<MiejscePostojowe> list;
    private static LayoutInflater inflater = null;

    public CustomPostojeAdapter(Activity activity, ArrayList<MiejscePostojowe> list) {
        this.activity = activity;
        this.list = list;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if(convertView == null)
            view = inflater.inflate(R.layout.list_row2, null);

        ImageView ikona = (ImageView)view.findViewById(R.id.ikona_list2);
        TextView title = (TextView)view.findViewById(R.id.title_list2);
        TextView type = (TextView)view.findViewById(R.id.type_list2);
        TextView distance = (TextView)view.findViewById(R.id.distance2);
        TextView time = (TextView)view.findViewById(R.id.time2);

        MiejscePostojowe element = list.get(position);

        if(element.obraz != -1){
            ikona.setImageResource(element.obraz);
        }
        title.setText(element.nazwa);
        type.setText(element.typ);
        distance.setText(element.odleglosc);
        time.setText(element.czas);

        return view;
    }
}
