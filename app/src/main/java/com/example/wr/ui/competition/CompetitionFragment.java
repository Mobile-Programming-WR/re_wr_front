package com.example.wr.ui.competition;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wr.DTO.Friend;
import com.example.wr.DTO.FriendsList;
import com.example.wr.databinding.FragmentChallengeBinding;
import com.example.wr.databinding.FragmentCompetitionBinding;

import com.example.wr.http.RetrofitClient;
import com.example.wr.http.Success;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.charts.LineChart;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompetitionFragment extends Fragment {
    private FragmentCompetitionBinding binding;

    private LineChart lineChart;
    private CompetitionFragment fragment;

//    ArrayList<Entry> myChart = new ArrayList<>();
//    ArrayList<Entry> friendChart = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCompetitionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        LinearLayout layout = binding.layoutCompetition;
        layout.setVisibility(View.GONE);
        fragment = this;
        Spinner spinner = binding.spinnerName;
        SharedPreferences preferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = "bearer "+preferences.getString("token","");
        Call<FriendsList> call = RetrofitClient.getApiService().getCompetitionList(token);
        call.enqueue(new Callback<FriendsList>() {
            @Override
            public void onResponse(Call<FriendsList> call, Response<FriendsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                FriendsList fl = response.body();

            }
            @Override
            public void onFailure(Call<FriendsList> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
        lineChart = binding.chart;
        LineData chartData = new LineData();

//
//        myChart.add(new Entry(1,100));
//        myChart.add(new Entry(2,50));
//        myChart.add(new Entry(3,20));
//        myChart.add(new Entry(4,80));
//        myChart.add(new Entry(5,40));
//        myChart.add(new Entry(6,30));
//        myChart.add(new Entry(7,70));
//        myChart.add(new Entry(8,60));
//        myChart.add(new Entry(9,90));
//        myChart.add(new Entry(10,10));
//        friendChart.add(new Entry(1,10));
//        friendChart.add(new Entry(2,80));
//        friendChart.add(new Entry(3,50));
//        friendChart.add(new Entry(4,40));
//        friendChart.add(new Entry(5,70));
//        friendChart.add(new Entry(6,60));
//        friendChart.add(new Entry(7,20));
//        friendChart.add(new Entry(8,10));
//        friendChart.add(new Entry(9,100));
//        friendChart.add(new Entry(10,90));
//
//        LineDataSet myDataSet = new LineDataSet(myChart, "My Distance");
//        LineDataSet friendDataSet = new LineDataSet(friendChart, "Friend Distance");
//
//        chartData.addDataSet(myDataSet);
//        chartData.addDataSet(friendDataSet);
//
//        myDataSet.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
//        myDataSet.setCircleColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
//        myDataSet.setHighLightColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
//        myDataSet.setValueTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
//        myDataSet.setLineWidth(2f);
//        friendDataSet.enableDashedLine(4f, 10f, 10f);
//        friendDataSet.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
//        friendDataSet.setCircleColor(ContextCompat.getColor(getContext(), android.R.color.black));
//        friendDataSet.setHighLightColor(ContextCompat.getColor(getContext(), android.R.color.black));
//        friendDataSet.setValueTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
//
//        lineChart.setData(chartData);
//        lineChart.invalidate();


        return root;
    }
}
