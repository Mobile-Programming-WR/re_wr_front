package com.example.wr.ui.competition;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wr.databinding.FragmentChallengeBinding;
import com.example.wr.databinding.FragmentCompetitionBinding;

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

public class CompetitionFragment extends Fragment {
    private FragmentCompetitionBinding binding;

    private LineChart lineChart;
    ArrayList<Entry> myChart = new ArrayList<>();
    ArrayList<Entry> friendChart = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCompetitionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lineChart = binding.chart;
        LineData chartData = new LineData();


        myChart.add(new Entry(1,100));
        myChart.add(new Entry(2,50));
        myChart.add(new Entry(3,20));
        myChart.add(new Entry(4,80));
        myChart.add(new Entry(5,40));
        myChart.add(new Entry(6,30));
        myChart.add(new Entry(7,70));
        myChart.add(new Entry(8,60));
        myChart.add(new Entry(9,90));
        myChart.add(new Entry(10,10));
        friendChart.add(new Entry(1,10));
        friendChart.add(new Entry(2,80));
        friendChart.add(new Entry(3,50));
        friendChart.add(new Entry(4,40));
        friendChart.add(new Entry(5,70));
        friendChart.add(new Entry(6,60));
        friendChart.add(new Entry(7,20));
        friendChart.add(new Entry(8,10));
        friendChart.add(new Entry(9,100));
        friendChart.add(new Entry(10,90));

        LineDataSet myDataSet = new LineDataSet(myChart, "My Distance");
        LineDataSet friendDataSet = new LineDataSet(friendChart, "Friend Distance");

        chartData.addDataSet(myDataSet);
        chartData.addDataSet(friendDataSet);

        myDataSet.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
        myDataSet.setCircleColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
        myDataSet.setHighLightColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
        myDataSet.setValueTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
        myDataSet.setLineWidth(2f);
        friendDataSet.enableDashedLine(4f, 10f, 10f);
        friendDataSet.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
        friendDataSet.setCircleColor(ContextCompat.getColor(getContext(), android.R.color.black));
        friendDataSet.setHighLightColor(ContextCompat.getColor(getContext(), android.R.color.black));
        friendDataSet.setValueTextColor(ContextCompat.getColor(getContext(), android.R.color.black));

        lineChart.setData(chartData);
        lineChart.invalidate();


        return root;
    }
}
