package com.example.wr.ui.running;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wr.R;
import com.example.wr.databinding.FragmentRunningBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
/*

import com.pedro.library.AutoPermissionsListener;
*/


public class RunningFragment extends Fragment
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener/*, AutoPermissionsListener*/, SensorEventListener {
/*
    private RunningViewModel runningViewModel;
*/
    private @NonNull FragmentRunningBinding binding;
    private long pauseOffset;
    private int running = 0;
    private Chronometer chronometer, timeVal;
    private ImageButton btnRunning, btnRestart;
    private TextView distanceVal, stepsVal, paceVal, cadenceVal;
    SensorManager sensorManager;
    Sensor stepsCountSensor;
    int currentSteps = 0, paceMin = 0, paceSec = 0;
    double dist = 0, totDist = 0, textDist = 0, pace = 0, cadence = 0, timeInSec = 0;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private MyLocationCallBack locationCallBack;
    private MarkerOptions markerOptions;
    private PolylineOptions polylineOptions = new PolylineOptions().width(50f).color(Color.RED);
    private LatLng fromLatLng, toLatLng;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRunningBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        chronometer = binding.timer;
        chronometer.setFormat("%s");
        timeVal = binding.timeValue;
        timeVal.setFormat("%s");
        btnRunning = binding.btnRunning;
        btnRestart = binding.btnRestart;
        distanceVal = binding.distanceValue;
        stepsVal = binding.stepsValue;
        paceVal = binding.paceValue;
        cadenceVal = binding.cadenceValue;
        /*getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);*/

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        });
        locationInit();

        // 걸음
        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, 0);
        }

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        stepsCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);

        if (stepsCountSensor == null) {
            Toast.makeText(getActivity().getApplicationContext(), "No Step Sensor", Toast.LENGTH_SHORT).show();
        }


        //timer btn
        btnRunning.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                btnRestart.setVisibility(View.VISIBLE);
                if (running == 0) {
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    timeVal.setBase(SystemClock.elapsedRealtime());
                    pauseOffset = 0;
                    btnRunning.setBackgroundResource(R.drawable.btn_pause);
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    timeVal.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    timeVal.start();
                    totDist = 0;
                    dist = 0;
                    distanceVal.setText("0.0");
                    currentSteps = 0;
                    stepsVal.setText(String.valueOf(currentSteps));
                    cadence = 0;
                    pace = 0;
                    paceMin = 0;
                    paceSec = 0;
                    paceVal.setText("0'0''");
                    cadenceVal.setText("0");
                    running = 1;

                } else if (running == 1) {
                    btnRunning.setBackgroundResource(R.drawable.btn_play);
                    chronometer.stop();
                    timeVal.stop();
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
                    running = 2;
                    stepsVal.setText(String.valueOf(currentSteps));
                } else if (running == 2) {
                    btnRunning.setBackgroundResource(R.drawable.btn_pause);
                    chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    chronometer.start();
                    timeVal.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                    timeVal.start();
                    running = 1;
                }
            }
        });
        btnRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnRunning.setBackgroundResource(R.drawable.btn_play);
                btnRestart.setVisibility(View.GONE);
                pauseOffset = 0;
                totDist = 0;
                dist = 0;
                distanceVal.setText("0.0");
                currentSteps = 0;
                stepsVal.setText("0");
                cadence = 0;
                pace = 0;
                paceMin = 0;
                paceSec = 0;
                paceVal.setText("0'0''");
                cadenceVal.setText("0");
                chronometer.stop();
                timeVal.stop();
                pauseOffset = 0;
                chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                timeVal.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                running = 0;
            }
        });
        return root;
    }

    private void locationInit() {
        fusedLocationProviderClient = new FusedLocationProviderClient(getActivity().getApplicationContext());
        locationCallBack = new MyLocationCallBack();
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
    }

    private class MyLocationCallBack extends LocationCallback {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            Location location = locationResult.getLastLocation();
            if (location != null && running == 1) {
                fromLatLng = toLatLng;
                toLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                if(fromLatLng == null) {
                    fromLatLng = toLatLng;
                    polylineOptions.add(fromLatLng);
                }

/*
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f));
*/
                CalDistance calDistance = new CalDistance(fromLatLng.latitude, fromLatLng.longitude, toLatLng.latitude, toLatLng.longitude);
                dist = calDistance.getDistance();
                Log.d("MapsActivity", "totdist: "+totDist+"dist:"+dist);

                dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
                //dist = dist * 1000.0;      // 단위  km 에서 m 로 변환

                totDist += dist;
                textDist = (int)(totDist * 100)/100.0;

                timeInSec = (float) ((SystemClock.elapsedRealtime() - chronometer.getBase()) / 1000);
                pace = timeInSec/totDist;
                Log.d("pace",pace+"'");

                paceMin = (int)pace/60;
                paceSec = (int)pace%60;
                cadence = currentSteps/timeInSec*60;

                Log.d("pace",pace/60+"'"+pace%60+"''");
                distanceVal.setText(String.valueOf(textDist));
                paceVal.setText(String.valueOf((int)pace/60)+"\'"+String.valueOf((int)pace%60)+"\'\'");

                cadenceVal.setText(String.valueOf((int)cadence));
                Log.d("MapsActivity", "위도: " + location.getLatitude() + ", 경도: " + location.getLongitude());

                polylineOptions.add(toLatLng);

/*
                mMap.addPolyline(polylineOptions);
*/
            }
        }
    }

    ActivityResultLauncher<String[]> locationPermissionRequest =
            registerForActivityResult(new ActivityResultContracts
                            .RequestMultiplePermissions(), result -> {
                        @SuppressLint({"NewApi", "LocalSuppress"}) Boolean fineLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION, false);
                        @SuppressLint({"NewApi", "LocalSuppress"}) Boolean coarseLocationGranted = result.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION, false);
                        if (fineLocationGranted != null && fineLocationGranted) {
                            // Precise location access granted.
                        } else if (coarseLocationGranted != null && coarseLocationGranted) {
                            // Only approximate location access granted.
                        } else {
                            // No location access granted.
                        }
                    }
            );

    @SuppressLint("MissingPermission")
    public void addLocationListener() {
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
    }

    private void removeLocationListener() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (stepsCountSensor != null) {
            sensorManager.registerListener(this, stepsCountSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
        addLocationListener();
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        removeLocationListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (running == 1) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
                if (sensorEvent.values[0] == 1.0f) {
                    currentSteps++;
                    stepsVal.setText(String.valueOf(currentSteps));
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }


    private class CalDistance {
        public double theta, dist;
        public double fromLat, fromLng, toLat, toLng;

        public CalDistance(double fromLat, double fromLng, double toLat, double toLng){
            this.theta = 0;
            this.dist = 0;
            this.fromLat = fromLat;
            this.fromLng = fromLng;
            this.toLat = toLat;
            this.toLng = toLng;
        }

        public double getDistance(){
            theta = fromLng - toLng;
            dist = Math.sin(deg2rad(fromLat)) * Math.sin(deg2rad(toLat))
                    + Math.cos(deg2rad(fromLat)) * Math.cos(deg2rad(toLat)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;    // 단위 mile 에서 km 변환.
            //dist = dist * 1000.0;      // 단위  km 에서 m 로 변환
            return dist; // 단위 m
        }


        // 주어진 도(degree) 값을 라디언으로 변환
        private double deg2rad(double deg){
            return (double)(deg * Math.PI / (double)180d);
        }

        // 주어진 라디언(radian) 값을 도(degree) 값으로 변환
        private double rad2deg(double rad){
            return (double)(rad * (double)180d / Math.PI);
        }
    }
}

