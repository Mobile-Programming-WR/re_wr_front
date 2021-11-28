package com.example.wr.ui.record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wr.databinding.FragmentRecordBinding;
import com.example.wr.http.RecordsList;
import com.example.wr.http.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordFragment extends Fragment {
    private FragmentRecordBinding binding;
    private RecordFragment fragment;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fragment = this;

        SharedPreferences preferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = "bearer "+preferences.getString("token","");

        Call<RecordsList> call = RetrofitClient.getApiService().getRecordsList(token);
        call.enqueue(new Callback<RecordsList>() {
            @Override
            public void onResponse(Call<RecordsList> call, Response<RecordsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                ExpandableListView expandableListView = binding.elvRecordList;
                RecordsList rl = response.body();
                final RecordListAdapter recordListAdapter = new RecordListAdapter(getContext(), rl.getResults(), fragment);
                expandableListView.setAdapter((ExpandableListAdapter) recordListAdapter);
                ViewGroup viewGroup = (ViewGroup) binding.elvRecordList.getParent();
//                Log.d("success :", fl.toString());
            }
            @Override
            public void onFailure(Call<RecordsList> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });

        binding.elvRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
        return root;
    }


}
