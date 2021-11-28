package com.example.wr.ui.record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wr.R;
import com.example.wr.databinding.FragmentFriendBinding;
import com.example.wr.databinding.FragmentRecordBinding;
import com.example.wr.databinding.ListviewItemRecordBinding;
import com.example.wr.http.FriendsList;
import com.example.wr.http.RecordsList;
import com.example.wr.http.RetrofitClient;
import com.example.wr.http.RunInfo;
import com.example.wr.http.Success;
import com.example.wr.ui.friend.FriendFragment;
import com.example.wr.ui.friend.FriendsListAdapter;

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
                ListView listView = binding.lvRecordList;
                RecordsList rl = response.body();
                final RecordListAdapter recordListAdapter = new RecordListAdapter(getContext(), rl.getResults(), fragment);
                listView.setAdapter(recordListAdapter);
                ViewGroup viewGroup = (ViewGroup) binding.lvRecordList.getParent();
//                Log.d("success :", fl.toString());
            }
            @Override
            public void onFailure(Call<RecordsList> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });

        binding.lvRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Intent intent = new Intent(getActivity(), RecordListFragment.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //startActivity(intent);
            }
        });
        return root;
    }


}
