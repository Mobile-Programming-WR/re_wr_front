package com.example.wr.ui.friend;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wr.DTO.FriendsList;
import com.example.wr.R;
import com.example.wr.DTO.Friend;
import com.example.wr.http.RetrofitClient;
import com.example.wr.http.Success;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FriendsListAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<Friend> friendsList;
    FriendFragment fragment;
    public FriendsListAdapter(Context context, List<Friend> data, FriendFragment fragment) {
        this.fragment = fragment;
        mContext = context;
        friendsList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return friendsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Friend getItem(int position) {
        return friendsList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_friend, null);
        TextView tvFriendName = (TextView)view.findViewById(R.id.tvFriendName);
        Button btnAddCompetition = (Button)view.findViewById(R.id.btnAddCompetition);
        Button btnRemoveFriend = (Button) view.findViewById(R.id.btnRemoveFriend);
        tvFriendName.setText(friendsList.get(position).getName());
        SharedPreferences preferences = mContext.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = "bearer "+preferences.getString("token","");
        btnAddCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Success> call = RetrofitClient.getApiService().getAddCompetition(token, friendsList.get(position).getId());
                call.enqueue(new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            return;
                        }
                        Toast.makeText(mContext, "겨루기신청 완료", Toast.LENGTH_LONG);
                        fragment.refreshFragment(token);
                    }
                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
//                token = "connection failed";
                        Log.e("연결실패", t.getMessage());
                    }
                });

            }
        });
        btnRemoveFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Success> callRemove = RetrofitClient.getApiService().deleteFriend(token, friendsList.get(position).getId());
                callRemove.enqueue(new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            return;
                        }
                        Toast.makeText(mContext, "친구 삭제", Toast.LENGTH_LONG);
                        fragment.refreshFragment(token);
                    }
                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
//                token = "connection failed";
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });
        return view;
    }
}
