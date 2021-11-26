package com.example.wr.ui.friend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wr.databinding.FragmentFriendBinding;
import com.example.wr.databinding.FragmentSettingBinding;
import com.example.wr.http.FriendsList;
import com.example.wr.http.LoginResponse;
import com.example.wr.http.RetrofitClient;
import com.example.wr.ui.setting.SettingViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {
    private FragmentFriendBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFriendBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ListView listView = binding.lvFriendsList;
        SharedPreferences preferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = "bearer "+preferences.getString("token","");
        Call<FriendsList> call = RetrofitClient.getApiService().getFriendsList(token);
        call.enqueue(new Callback<FriendsList>() {
            @Override
            public void onResponse(Call<FriendsList> call, Response<FriendsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }

                FriendsList fl = response.body();
                final FriendsListAdapter friendsListAdapter = new FriendsListAdapter(getContext(), fl.getList());
                listView.setAdapter(friendsListAdapter);
//                Log.d("success :", fl.toString());
            }
            @Override
            public void onFailure(Call<FriendsList> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });
        return root;

    }

}
