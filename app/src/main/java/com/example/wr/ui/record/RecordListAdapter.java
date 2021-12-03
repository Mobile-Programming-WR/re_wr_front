package com.example.wr.ui.record;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.example.wr.R;
import com.example.wr.DTO.RunInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class RecordListAdapter extends BaseAdapter implements ListAdapter, OnMapReadyCallback {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    List<RunInfo> recordsList;
    List<List<RunInfo>> recordsListItem;
    RecordFragment fragment;

    public RecordListAdapter(Context context, List<RunInfo> data, RecordFragment fragment) {
        this.fragment = fragment;
        mContext = context;
        recordsList = data;
        ArrayList tmp = new ArrayList(data);
        recordsListItem = tmp;
        mLayoutInflater = ( LayoutInflater ) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        ViewHolder holder = new ViewHolder();
        LinearLayout layout = new LinearLayout(mContext);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        for (int i = 0; i < recordsList.size(); i++) {
            holder.map = new MapView(mContext);
            layout.addView(holder.map);
            holder.map.getMapAsync(this);
        }
        //convertView.setTag(holder);
        LayoutInflater inflater = ( LayoutInflater ) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.elistview_record, parent, false);
        TextView tvRecordDistance = ( TextView ) view.findViewById(R.id.tvRecordDistance);
        Chronometer chronometerRecordTime = ( Chronometer ) view.findViewById(R.id.chronometerRecordTime);
        TextView tvRecordDate = ( TextView ) view.findViewById(R.id.tvRecordDate);
        holder = ( ViewHolder ) view.getTag();

        tvRecordDistance.setText(toString().valueOf(recordsList.get(position).getDistance()) + "km");
        chronometerRecordTime.setBase(SystemClock.elapsedRealtime() - recordsList.get(position).getTime());
        Log.d("getDate", recordsList.get(position).getDate() + "");
        long now = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = recordsList.get(position).getDate();
        String strDate = dateFormat.format(date);
        tvRecordDate.setText(strDate);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("onMapReady", "onMapReady 실행");
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));
    }

    class ViewHolder {
        MapView map;
    }


}

