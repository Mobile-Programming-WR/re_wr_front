package com.example.wr.DTO;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
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

    public int getIntDate() {
        int result = 0;
        SimpleDateFormat simpleDate = new SimpleDateFormat("MM-dd");
        String dateString = simpleDate.format(date);
        result = (dateString.charAt(0)-'0')*1000 + (dateString.charAt(1)-'0')*100 + (dateString.charAt(3)-'0')*10 + (dateString.charAt(4)-'0');
        return result;
    }
    public int getCadence() {
        return cadence;
    }

    public int getPace() {
        return pace;
    }

    public List<LatLng> getCoordinates() {
        return coordinates;
    }

    public int getSteps() {
        return steps;
    }
}
