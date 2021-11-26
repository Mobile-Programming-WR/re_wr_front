package com.example.wr.ui.friend;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wr.R;
import com.example.wr.databinding.FragmentFriendBinding;
import com.example.wr.databinding.ListviewFriendBinding;
import com.example.wr.http.Friend;
import com.example.wr.http.FriendsList;
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
    public View getView(int position, View converView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_friend, null);
        TextView tvFriendName = (TextView)view.findViewById(R.id.tvFriendName);
        Button btnAddCompetition = (Button)view.findViewById(R.id.btnAddCompetition);
        tvFriendName.setText(friendsList.get(position).getName());
        btnAddCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }
}
