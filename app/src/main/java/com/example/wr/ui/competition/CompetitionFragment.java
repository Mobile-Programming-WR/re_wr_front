package com.example.wr.ui.competition;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wr.DTO.Competition;
import com.example.wr.DTO.Friend;
import com.example.wr.DTO.FriendsList;
import com.example.wr.DTO.Info;
import com.example.wr.DTO.RunInfo;
import com.example.wr.R;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompetitionFragment extends Fragment {
    private FragmentCompetitionBinding binding;
    private CompetitionFragment fragment;
    LineChart lineChart;
    ArrayList<Entry> myChart;
    ArrayList<Entry> friendChart;

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
        LineChart lineChart = (LineChart) binding.chart;
        binding.clickable.setClickable(true);
        ArrayList<Entry> myChart = new ArrayList<>();
        ArrayList<Entry> friendChart = new ArrayList<>();
        // 겨루기 상대 목록 가져오
        Call<FriendsList> call = RetrofitClient.getApiService().getCompetitionList(token);
        call.enqueue(new Callback<FriendsList>() {
            @Override
            public void onResponse(Call<FriendsList> call, Response<FriendsList> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                FriendsList fl = response.body();
                List<Friend> competitionList = fl.getList();
                Log.d("list", fl.getList().toString());
                String[] nameList = new String[competitionList.toArray().length];
                for(int i=0;i<competitionList.toArray().length;i++){
                    nameList[i] = competitionList.get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner, nameList);

                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Call<Competition> call = RetrofitClient.getApiService().getCompetition(token, competitionList.get(position).getId());
                        call.enqueue(new Callback<Competition>() {
                            @Override
                            public void onResponse(Call<Competition> call, Response<Competition> response) {
                                if(!response.isSuccessful()){
                                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                                    return;
                                }
                                Competition c = response.body();
                                Info myInfo = c.getMyInfo();
                                Info friendInfo = c.getFriendInfo();
                                layout.setVisibility(View.VISIBLE);
                                binding.tvCompetitionMyName.setText(myInfo.getName());
                                binding.tvCompetitionMyDistance.setText(
                                        String.format("%.2fkm", myInfo.getDistance()));
                                binding.tvCompetitionFriendDistance.setText(
                                        String.format("%.2fkm", friendInfo.getDistance()));
                                binding.tvCompetitionMySteps.setText(Integer.toString(myInfo.getSteps()));
                                binding.tvCompetitionFriendSteps.setText(Integer.toString(friendInfo.getSteps()));
                                binding.tvCompetitionMyTime.setText(
                                        String.format("%02d", (myInfo.getTime()/1000)/60) + ":" +
                                                String.format("%02d", (myInfo.getTime()/1000)%60)
                                );
                                binding.tvCompetitionFriendTime.setText(
                                        String.format("%02d", (friendInfo.getTime()/1000)/60) + ":" +
                                                String.format("%02d", (friendInfo.getTime()/1000)%60)
                                );
                                // 그래프 그리기
                                LineData chartData = new LineData();
                                List<RunInfo> myRecord = c.getMyRecord();
                                List<RunInfo> friendRecord = c.getFriendRecord();
                                myChart.clear();
                                friendChart.clear();
                                for(int i=9; i>=0; i--){
//                                    myChart.add(new Entry(myRecord.get(i).getIntDate(), (float)myRecord.get(i).getDistance()));
                                    myChart.add(new Entry(10-i, (float)myRecord.get(i).getDistance()));
//                                    friendChart.add(new Entry(friendRecord.get(i).getIntDate(), (float)friendRecord.get(i).getDistance()));
                                    friendChart.add(new Entry(10-i, (float)friendRecord.get(i).getDistance()));
                                }
                                LineDataSet myDataSet = new LineDataSet(myChart, "나");
                                LineDataSet friendDataSet = new LineDataSet(friendChart, "상대");

                                chartData.addDataSet(myDataSet);
                                chartData.addDataSet(friendDataSet);

                                myDataSet.setColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
                                myDataSet.setCircleColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
                                myDataSet.setHighLightColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
                                myDataSet.setValueTextColor(ContextCompat.getColor(getContext(), android.R.color.holo_red_dark));
                                myDataSet.setLineWidth(2f);
                                friendDataSet.setColor(ContextCompat.getColor(getContext(), android.R.color.black));
                                friendDataSet.setCircleColor(ContextCompat.getColor(getContext(), android.R.color.black));
                                friendDataSet.setHighLightColor(ContextCompat.getColor(getContext(), android.R.color.black));
                                friendDataSet.setValueTextColor(ContextCompat.getColor(getContext(), android.R.color.black));
                                XAxis xAxis = lineChart.getXAxis(); //X축 설정
                                xAxis.setDrawAxisLine(false);
                                xAxis.setDrawLabels(false);

                                YAxis yAxisRight = lineChart.getAxisRight(); //Y축의 오른쪽면 설정
                                yAxisRight.setDrawLabels(false);
                                yAxisRight.setDrawAxisLine(false);
                                yAxisRight.setDrawGridLines(false);
                                lineChart.setData(chartData);
                                lineChart.setDescription(null); //차트에서 Description 설정 저는 따로 안했습니다.

                                lineChart.invalidate();
                            }
                            @Override
                            public void onFailure(Call<Competition> call, Throwable t) {
                                Log.e("연결실패", t.getMessage());
                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        layout.setVisibility(View.GONE);
                    }
                });

            }
            @Override
            public void onFailure(Call<FriendsList> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
        return root;
    }
}
