package com.example.wr.ui.record;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wr.DTO.RecordsList;
import com.example.wr.databinding.FragmentRecordBinding;
import com.example.wr.http.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordFragment extends Fragment implements OnMapReadyCallback {
    private FragmentRecordBinding binding;
    private RecordFragment fragment;
    private GoogleMap mMap;
    MapView m = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fragment = this;

        SharedPreferences preferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = "bearer " + preferences.getString("token", "");

        Call<RecordsList> call = RetrofitClient.getApiService().getRecordsList(token);
        call.enqueue(new Callback<RecordsList>() {
            @Override
            public void onResponse(Call<RecordsList> call, Response<RecordsList> response) {
                if (!response.isSuccessful()) {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                ListView expandableListView = binding.elvRecordList;
                //ExpandableListView expandableListView = binding.elvRecordList;
                RecordsList rl = response.body();
                final RecordListAdapter recordListAdapter = new RecordListAdapter(getContext(), rl.getResults(), fragment);
                expandableListView.setAdapter((ListAdapter) recordListAdapter);
                //expandableListView.setAdapter(( ExpandableListAdapter ) recordListAdapter);
                ViewGroup viewGroup = ( ViewGroup ) binding.elvRecordList.getParent();
//                Log.d("success :", fl.toString());
            }

            @Override
            public void onFailure(Call<RecordsList> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });
        //SupportMapFragment supportMapFragment = ( SupportMapFragment ) getActivity().getSupportFragmentManager().findFragmentById(R.id.map);
        //m = fragment.m;
//        supportMapFragment.getMapAsync(this);
        //m.onCreate(savedInstanceState);
//        m.onSaveInstanceState(savedInstanceState);
        //m = binding.map;
        //m.getMapAsync(this);

        //binding.elvRecordList.setGroupIndicator(null);
        binding.elvRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), MapsActivity.class);
                intent.putExtra("position",i);
                startActivity(intent);;
            }
        });
        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
