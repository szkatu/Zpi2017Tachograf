package com.example.msi.zpi_interfejs;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Harrel on 5/30/2017.
 */

public class PlaceFinder {

    public ArrayList<PointOfInterest> currentPlaces = new ArrayList<PointOfInterest>();
    private OnPlacesLoadedListener listener;

    public void addOnLoadListener(OnPlacesLoadedListener l){
        listener = l;
    }

    public void updatePlaces(final LatLng pos){

        Log.i("E", "UPDATE");
        if(pos == null)
            return;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    currentPlaces.clear();
                    String nextToken = null;
                    boolean load = true;

                    int count = 0;
                    //Send google places request
                    while(count < 3) {
                        String urlString = "https://maps.googleapis.com/maps/api/place/search/json?location=" + pos.latitude + "," + pos.longitude + "&rankby=distance&types=parking|lodging|gas_station&key=AIzaSyB0sdfU9YLdsHW09za7-clJuQ2FidqNtmo";
                        //String urlString = "http://google.com";
                        if(nextToken != null)
                            urlString = urlString + "&pagetoken=" + nextToken;
                        URL url = new URL(urlString);
                        HttpURLConnection con = (HttpURLConnection) url.openConnection();
                        con.setRequestMethod("GET");
                        con.connect();
                        int statuscode = con.getResponseCode();
                        if (statuscode == HttpURLConnection.HTTP_OK) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            StringBuilder sb = new StringBuilder();
                            String line = br.readLine();
                            while (line != null) {
                                sb.append(line);
                                line = br.readLine();
                            }
                            //Convert string to JSON object
                            String jsonString = sb.toString();
                            //Log.d("ERR", jsonString);
                            JSONObject json = new JSONObject(jsonString);

                            try {
                                nextToken = json.getString("next_page_token");
                            } catch (Exception e) {
                                load = false;
                            }
                            count++;

                            //Update places data with location, name and type
                            JSONArray resultArray = json.getJSONArray("results");
                            for (int i = 0; i < resultArray.length(); i++) {

                                double lat = resultArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
                                double lng = resultArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
                                LatLng pos = new LatLng(lat, lng);
                                String name = resultArray.getJSONObject(i).getString("name");
                                PointOfInterest tmp = new PointOfInterest(pos, name);
                                JSONArray typesArray = resultArray.getJSONObject(i).getJSONArray("types");
                                for (int j = 0; j < typesArray.length(); j++) {
                                    if (typesArray.getString(j).equals("parking")) {
                                        tmp.isParking = true;
                                        tmp.icon = R.mipmap.parking;
                                        tmp.typeString = "Parking";
                                    }
                                    else if (typesArray.getString(j).equals("lodging")) {
                                        tmp.isHotel = true;
                                        tmp.icon = R.mipmap.hotel;
                                        tmp.typeString = "Hotel";
                                    }
                                    else if (typesArray.getString(j).equals("gas_station")) {
                                        tmp.isGasStation = true;
                                        tmp.icon = R.mipmap.gas_station;
                                        tmp.typeString = "Stacja paliw";
                                    }
                                }
                                currentPlaces.add(tmp);
                            }
                        }
                        else {
                            load = false;
                            Log.i("HTTP", "E: " + statuscode);
                        }
                    }

                    //Construct new url string
                    String distanceString = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + pos.latitude + ", " + pos.longitude + "&destinations=";
                    for(PointOfInterest pt : currentPlaces) {
                        distanceString = distanceString + pt.pos.latitude + ", " + pt.pos.longitude + "|";
                    }

                    //Send google distance matrix request
                    URL url = new URL(distanceString);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.connect();
                    int statuscode = con.getResponseCode();
                    if(statuscode == HttpURLConnection.HTTP_OK){
                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line = br.readLine();
                        while (line != null) {
                            sb.append(line);
                            line = br.readLine();
                        }
                        //Convert string to JSON object
                        String jsonString = sb.toString();
                        JSONObject json = new JSONObject(jsonString);

                        //Update places data with est. time and distance
                        JSONArray resultArray = json.getJSONArray("rows").getJSONObject(0).getJSONArray("elements");
                        for(int i = 0; i < resultArray.length(); i++) {

                            int dist = resultArray.getJSONObject(i).getJSONObject("distance").getInt("value");
                            int time = resultArray.getJSONObject(i).getJSONObject("duration").getInt("value");
                            String distStr = resultArray.getJSONObject(i).getJSONObject("distance").getString("text");
                            String timeStr = resultArray.getJSONObject(i).getJSONObject("duration").getString("text");

                            currentPlaces.get(i).distance = dist;
                            currentPlaces.get(i).duration = time;
                            currentPlaces.get(i).distanceString = distStr;
                            currentPlaces.get(i).durationString = timeStr;
                        }
                    }

                }catch(Exception e){
                    Log.i("ERROR", e.toString());
                }

                if(listener != null)
                    listener.onLoad();

                Collections.sort(currentPlaces, new Comparator<PointOfInterest>() {
                    @Override
                    public int compare(PointOfInterest o1, PointOfInterest o2) {
                        return o1.duration > o2.duration ? 1 : -1;
                    }
                });

            }
        });
        t.start();
    }
}
