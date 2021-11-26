package com.example.wr.http;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class RunInfo {
    private String time;
    private String distance;
    private String steps;
    private String pace;
    private String cadence;
    private List<LatLng> coordinates;

    public RunInfo(String t, String d, String s, String p, String c, List<LatLng> a){
        time = t;
        distance = d;
        steps = s;
        pace = p;
        cadence = c;
        coordinates = a;
    }
}
