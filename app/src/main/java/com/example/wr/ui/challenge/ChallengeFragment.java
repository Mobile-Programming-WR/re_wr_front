package com.example.wr.ui.challenge;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.wr.DTO.FriendsList;
import com.example.wr.databinding.FragmentChallengeBinding;
import com.example.wr.DTO.Challenge;
import com.example.wr.http.RetrofitClient;
import com.example.wr.ui.friend.RequestListAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChallengeFragment extends Fragment {
    private FragmentChallengeBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentChallengeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        SharedPreferences preferences = getContext().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String token = "bearer "+preferences.getString("token","");
        refreshChallenge(token);
        return root;
    }
    private void refreshChallenge(String token) {
        Call<Challenge> callRequestList = RetrofitClient.getApiService().getChallenge(token);
        // 챌린지 가져오기
        callRequestList.enqueue(new Callback<Challenge>() {
            @Override
            public void onResponse(Call<Challenge> call, Response<Challenge> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                TextView tvRank = binding.tvChallengeRank;
                ListView listView = binding.lvChallengeRankList;
                Challenge challenge = response.body();
                tvRank.setText("상위 " + String.format("%.1f", challenge.getRank()*100) + "%");
                final ChallengeListAdapter challengeListAdapter = new ChallengeListAdapter(getContext(), challenge.getRecordsList());
                listView.setAdapter(challengeListAdapter);

            }
            @Override
            public void onFailure(Call<Challenge> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });
    }

}
