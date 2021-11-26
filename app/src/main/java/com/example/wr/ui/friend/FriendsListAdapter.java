package com.example.wr.ui.friend;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wr.R;
import com.example.wr.http.Friend;

import java.util.ArrayList;
import java.util.List;

public class FriendsListAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<Friend> friendsList;
    public FriendsListAdapter(Context context, List<Friend> data) {
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
        View view = mLayoutInflater.inflate(R.layout.listview_custom, null);

        TextView tvFriendName = (TextView)view.findViewById(R.id.tvFriendName);
        Button btnAddCompetition = (Button)view.findViewById(R.id.btnAddCompetition);
        tvFriendName.setText(friendsList.get(position).getName());
        btnAddCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("id", friendsList.get(position).getId());
            }
        });

        return view;
    }
}
