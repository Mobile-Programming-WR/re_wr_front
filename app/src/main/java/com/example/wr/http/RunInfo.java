package com.example.wr.http;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.List;

public class RunInfo {
    private Date date;
    private int time;
    private double distance;
    private int steps;
    private int pace;
    private int cadence;
    private List<LatLng> coordinates;

    public RunInfo(int t, double d, int s, int p, int c, List<LatLng> a){
        //this.date = date;
        time = t;
        distance = d;
        steps = s;
        pace = p;
        cadence = c;
        coordinates = a;
    }

    public double getDistance() {
        return distance;
    }

    public int getTime() {
        return time;
    }

    public Date getDate() {
        return date;
    }
}
