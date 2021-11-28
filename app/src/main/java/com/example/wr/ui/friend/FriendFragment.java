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
import com.example.wr.DTO.FriendsList;
import com.example.wr.http.RetrofitClient;
import com.example.wr.http.Success;

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
                Call<Success> call = RetrofitClient.getApiService().getAddFriend(token, name);
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
        Call<FriendsList> callRequestList = RetrofitClient.getApiService().getRequestList(token);
        // 친구 신청 목록 가져오
        callRequestList.enqueue(new Callback<FriendsList>() {
            @Override
            public void onResponse(Call<FriendsList> call, Response<FriendsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                ListView listView = binding.lvRequestList;
                FriendsList fl = response.body();
                final RequestListAdapter requestListAdapter = new RequestListAdapter(getContext(), fl.getList(), fragment);
                listView.setAdapter(requestListAdapter);
                // 신청 목록이 비어있으면 친구목록창 삭제
                if(requestListAdapter.getCount()==0){
                    binding.tvFriendRequest.setVisibility(View.GONE);
                } else {
                    binding.tvFriendRequest.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(Call<FriendsList> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });
        Call<FriendsList> callCompetitionRequest = RetrofitClient.getApiService().getCompetitionRequest(token);
        callCompetitionRequest.enqueue(new Callback<FriendsList>() {
            @Override
            public void onResponse(Call<FriendsList> call, Response<FriendsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                FriendsList fl = response.body();
                ListView listView = binding.lvCompetitionRequestList;
                final CompetitionRequestListAdapter competitionRequestListAdapter = new CompetitionRequestListAdapter(getContext(), fl.getList(), fragment);
                // 신청 목록이 비어있으면 친구목록창 삭제
                if(competitionRequestListAdapter.getCount()==0){
                    binding.tvCompetitionRequest.setVisibility(View.GONE);
                } else {
                    binding.tvCompetitionRequest.setVisibility(View.VISIBLE);
                }
                listView.setAdapter(competitionRequestListAdapter);
//                Log.d("success :", fl.toString());
            }
            @Override
            public void onFailure(Call<FriendsList> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });
        Call<FriendsList> callFriend = RetrofitClient.getApiService().getFriendsList(token);
        callFriend.enqueue(new Callback<FriendsList>() {
            @Override
            public void onResponse(Call<FriendsList> call, Response<FriendsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                FriendsList fl = response.body();
                ListView listView = binding.lvFriendsList;
                final FriendsListAdapter friendsListAdapter = new FriendsListAdapter(getContext(), fl.getList(), fragment);
                listView.setAdapter(friendsListAdapter);
            }

            @Override
            public void onFailure(Call<FriendsList> call, Throwable t) {

            }
        });
    }

}
