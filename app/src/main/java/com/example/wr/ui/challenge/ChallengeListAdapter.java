package com.example.wr.ui.challenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.wr.R;
import com.example.wr.http.Ranker;
import com.example.wr.ui.friend.FriendFragment;

import java.util.List;

public class ChallengeListAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<Ranker> recordsList;
    FriendFragment fragment;
    public ChallengeListAdapter(Context context, List<Ranker> data, FriendFragment fragment) {
        this.fragment = fragment;
        mContext = context;
        recordsList = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return recordsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Ranker getItem(int position) {
        return recordsList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        recordsList.get(position).setName("엄유상");
        recordsList.get(position).setDistance(32.6);
        View view = mLayoutInflater.inflate(R.layout.listview_challenge, null);
        TextView tvChallengeIndex = (TextView)view.findViewById(R.id.tvChallengeIndex);
        TextView tvChallengeName = (TextView)view.findViewById(R.id.tvChallengeName);
        TextView tvChallengeDistance = (TextView)view.findViewById(R.id.tvChallengeDistance);
        tvChallengeIndex.setText(position + 1);
        tvChallengeName.setText(recordsList.get(position).getName());
        tvChallengeDistance.setText(toString().valueOf(recordsList.get(position).getDistance())+"km");
        return view;
    }
}
