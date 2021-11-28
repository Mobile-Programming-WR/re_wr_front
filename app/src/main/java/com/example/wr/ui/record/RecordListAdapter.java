package com.example.wr.ui.record;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.TextView;

import com.example.wr.R;
import com.example.wr.http.RecordsList;
import com.example.wr.http.RunInfo;
import com.example.wr.ui.friend.FriendFragment;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecordListAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<RunInfo> recordsList;
    RecordFragment fragment;

    public RecordListAdapter(Context context, List<RunInfo> data, RecordFragment fragment) {
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
    public RunInfo getItem(int position) {
        return recordsList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.listview_record, null);
        TextView tvRecordDistance = (TextView)view.findViewById(R.id.tvRecordDistance);
        Chronometer chronometerRecordTime = (Chronometer) view.findViewById(R.id.chronometerRecordTime);
        TextView tvRecordDate = (TextView) view.findViewById(R.id.tvRecordDate);

        tvRecordDistance.setText(toString().valueOf(recordsList.get(position).getDistance())+"km");
        chronometerRecordTime.setBase(SystemClock.elapsedRealtime()-recordsList.get(position).getTime());
        Log.d("getDate", recordsList.get(position).getDate()+"");
        long now = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = recordsList.get(position).getDate();
        String strDate = dateFormat.format(date);
        tvRecordDate.setText(strDate);
        return view;
    }
}
