package com.example.wr.ui.record;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.wr.DTO.RecordsList;
import com.example.wr.R;
import com.example.wr.databinding.ActivityMapsBinding;
import com.example.wr.http.RetrofitClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    Context mContext = null;
    private GoogleMap mMap;
    private int position;
    private PolylineOptions polylineOptions = new PolylineOptions().width(50f).color(Color.RED);
    private ActivityMapsBinding binding;
    private MapsActivity mapsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        mapsActivity = this;
        setContentView(binding.getRoot());

        SharedPreferences preferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = "bearer " + preferences.getString("token", "");
        SupportMapFragment mapFragment = ( SupportMapFragment ) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();

        Call<RecordsList> call = RetrofitClient.getApiService().getRecordsList(token);
        call.enqueue(new Callback<RecordsList>() {
            @Override
            public void onResponse(Call<RecordsList> call, Response<RecordsList> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                TextView distanceTitle = binding.tvListViewItemRecordDistanceTitle;
                TextView distance = binding.tvListViewItemRecordDistance;
                TextView pace = binding.tvListViewItemRecordPace;
                TextView time = binding.tvListViewItemRecordTime;
                TextView steps = binding.tvListViewItemRecordSteps;
                TextView cadence = binding.tvListViewItemRecordCadence;

                RecordsList rl = response.body();
                final MapsAdapter mapsAdapter = new MapsAdapter(mContext, rl.getResults(), mapsActivity);
                position = intent.getIntExtra("position", 0);

                distanceTitle.setText(toString().valueOf(rl.getResults().get(position).getDistance()) + "km");
                distance.setText(toString().valueOf(rl.getResults().get(position).getDistance()) + "km");
                pace.setText(toString().valueOf(rl.getResults().get(position).getPace() / 60 + "'" + rl.getResults().get(position).getPace() % 60 + "''"));
                time.setText(toString().valueOf(rl.getResults().get(position).getTime() / 1000));
                steps.setText(toString().valueOf(rl.getResults().get(position).getSteps()));
                cadence.setText(toString().valueOf(rl.getResults().get(position).getCadence()));

                polylineOptions.addAll(rl.getResults().get(position).getCoordinates());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(rl.getResults().get(position).getCoordinates().get(0), 17f));
                mMap.addPolyline(polylineOptions);
            }

            @Override
            public void onFailure(Call<RecordsList> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("onMapReady", "onMapReady 실행");
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        GooglePlayServicesUtil.isGooglePlayServicesAvailable(MapsActivity.this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
    }
}
