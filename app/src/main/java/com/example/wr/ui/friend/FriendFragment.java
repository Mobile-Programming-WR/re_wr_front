package com.example.wr.ui.friend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wr.databinding.FragmentFriendBinding;
import com.example.wr.databinding.FragmentSettingBinding;
import com.example.wr.http.Friend;
import com.example.wr.http.FriendsList;
import com.example.wr.http.LoginResponse;
import com.example.wr.http.RetrofitClient;
import com.example.wr.http.Success;
import com.example.wr.ui.setting.SettingViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendFragment extends Fragment {
    private FragmentFriendBinding binding;
    private FriendFragment fragment;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fragment = this;
        binding = FragmentFriendBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences preferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = "bearer "+preferences.getString("token","");
        refreshFragment(token);
        binding.btnAddSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.etFriendId.getText().toString();
                Call<Success> call = RetrofitClient.getApiService().postAddFriend(token, name);
                call.enqueue(new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            Toast.makeText(getContext(), "존재하지 않는 아이디입니다", Toast.LENGTH_LONG);
                            return;
                        }
                        binding.etFriendId.setText("");
                        Toast.makeText(getContext(), "친구신청 완료", Toast.LENGTH_LONG);

//                Log.d("success :", fl.toString());
                    }
                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
//                token = "connection failed";
                        Log.e("연결실패", t.getMessage());
                        Toast.makeText(getContext(), "오류", Toast.LENGTH_LONG);

                    }
                });
            }
        });
        return root;
    }
    public void refreshFragment(String token){
        Log.d("Refresh", "");
        Call<FriendsList> call = RetrofitClient.getApiService().getFriendsList(token);
        call.enqueue(new Callback<FriendsList>() {
            @Override
            public void onResponse(Call<FriendsList> call, Response<FriendsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                ListView listView = binding.lvFriendsList;
                FriendsList fl = response.body();
                final FriendsListAdapter friendsListAdapter = new FriendsListAdapter(getContext(), fl.getList(), fragment);
                listView.setAdapter(friendsListAdapter);
                ViewGroup viewGroup = (ViewGroup) binding.tvFriendRequest.getParent();
//                Log.d("success :", fl.toString());
            }
            @Override
            public void onFailure(Call<FriendsList> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });
        Call<FriendsList> call2 = RetrofitClient.getApiService().getRequestList(token);
        call2.enqueue(new Callback<FriendsList>() {
            @Override
            public void onResponse(Call<FriendsList> call, Response<FriendsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                FriendsList fl = response.body();
                ListView listView = binding.lvRequestList;
                final RequestListAdapter requestListAdapter = new RequestListAdapter(getContext(), fragment, fl.getList());
                if(requestListAdapter.getCount()==0){
                    ViewGroup viewGroup = (ViewGroup) binding.tvFriendRequest.getParent();
                    viewGroup.removeView(binding.tvFriendRequest);
                }
                listView.setAdapter(requestListAdapter);
//                Log.d("success :", fl.toString());
            }
            @Override
            public void onFailure(Call<FriendsList> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });
    }

}
