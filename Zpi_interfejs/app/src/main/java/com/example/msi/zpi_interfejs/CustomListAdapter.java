package com.example.msi.zpi_interfejs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<Element> list;
    private static LayoutInflater inflater = null;

    public CustomListAdapter(Activity activity, ArrayList<Element> list) {
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
            view = inflater.inflate(R.layout.list_row, null);

        TextView ikona = (TextView)view.findViewById(R.id.ikona_list);
        TextView title = (TextView)view.findViewById(R.id.title_list);
        TextView date = (TextView)view.findViewById(R.id.date_list);
        TextView duration = (TextView)view.findViewById(R.id.duration);

        Element element = list.get(position);

        String s1 = "", s2 = "";
        int id =0;
        switch(element.typ)
        {
            case 1:
                s1 = "J";
                s2 = "Czas jazdy";
                id = R.drawable.blue;
                break;
            case 2:
                s1 = "P";
                s2 = "Przerwa";
                id = R.drawable.purple;
                break;
            case 3:
                s1 = "O";
                s2 = "Odpoczynek";
                id = R.drawable.orange;
                break;
            case 4:
                s1 = "I";
                s2 = "Inna praca";
                id = R.drawable.green;
                break;
        }

        ikona.setText(s1);
        ikona.setBackgroundResource(id);
        //ikona.setBackgroundDrawable( getResources().getDrawable(id) );
        title.setText(s2);
        date.setText(element.start + " - " + element.stop);
        duration.setText(element.czas);

        return view;
    }
}
