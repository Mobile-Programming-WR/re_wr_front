package com.example.wr.ui.record;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Chronometer;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.wr.R;
import com.example.wr.http.RunInfo;
import com.google.android.gms.maps.GoogleMap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordListAdapter extends BaseAdapter implements ExpandableListAdapter {
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


        //mLayoutInflater = LayoutInflater.from(mContext);
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

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.elistview_record, parent, false);
        TextView tvRecordDistance = (TextView)view.findViewById(R.id.tvRecordDistance);
        Chronometer chronometerRecordTime = (Chronometer) view.findViewById(R.id.chronometerRecordTime);
        TextView tvRecordDate = (TextView) view.findViewById(R.id.tvRecordDate);
/*
        tvRecordDistance.setText(toString().valueOf(recordsList.get(position).getDistance())+"km");
        chronometerRecordTime.setBase(SystemClock.elapsedRealtime()-recordsList.get(position).getTime());
        Log.d("getDate", recordsList.get(position).getDate()+"");
        long now = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = recordsList.get(position).getDate();
        String strDate = dateFormat.format(date);
        tvRecordDate.setText(strDate);

*/
        return view;
    }

    @Override
    public int getGroupCount() {
        return recordsList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return recordsList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return recordsListItem.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.elistview_record, parent, false);


        //View view = mLayoutInflater.inflate(R.layout.elistview_record, null);

        TextView tvRecordDistance = (TextView)view.findViewById(R.id.tvRecordDistance);
        Chronometer chronometerRecordTime = (Chronometer) view.findViewById(R.id.chronometerRecordTime);
        TextView tvRecordDate = (TextView) view.findViewById(R.id.tvRecordDate);


        tvRecordDistance.setText(toString().valueOf(recordsList.get(groupPosition).getDistance())+"km");
        chronometerRecordTime.setBase(SystemClock.elapsedRealtime()-recordsList.get(groupPosition).getTime());
        Log.d("getDate", recordsList.get(groupPosition).getDate()+"");
        long now = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = recordsList.get(groupPosition).getDate();
        String strDate = dateFormat.format(date);
        tvRecordDate.setText(strDate);

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.elistview_record_item, parent, false);


        TextView distanceTitle = (TextView) v.findViewById(R.id.tvListViewItemRecordDistanceTitle);
        TextView distance = (TextView) v.findViewById(R.id.tvListViewItemRecordDistance);
        TextView pace = (TextView) v.findViewById(R.id.tvListViewItemRecordPace);
        TextView time = (TextView) v.findViewById(R.id.tvListViewItemRecordTime);
        TextView steps = (TextView) v.findViewById(R.id.tvListViewItemRecordSteps);
        TextView cadence = (TextView) v.findViewById(R.id.tvListViewItemRecordCadence);
        //GoogleMap googleMap = (GoogleMap) v.findViewById(R.id.googleMap);


        distanceTitle.setText(toString().valueOf(recordsList.get(groupPosition).getDistance()) + "km");
        distance.setText(toString().valueOf(recordsList.get(groupPosition).getDistance()));
        pace.setText(toString().valueOf(recordsList.get(groupPosition).getPace()));
        time.setText(toString().valueOf(recordsList.get(groupPosition).getTime()));
        steps.setText(toString().valueOf(recordsList.get(groupPosition).getSteps()));
        cadence.setText(toString().valueOf(recordsList.get(groupPosition).getCadence()));

        return v;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long l, long l1) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long l) {
        return 0;
    }
}
