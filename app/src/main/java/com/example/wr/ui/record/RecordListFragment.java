package com.example.wr.ui.record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.wr.R;
import com.example.wr.databinding.FragmentFriendBinding;
import com.example.wr.databinding.FragmentRecordBinding;
import com.example.wr.databinding.ListviewItemRecordBinding;
import com.example.wr.http.FriendsList;
import com.example.wr.http.RecordsList;
import com.example.wr.http.RetrofitClient;
import com.example.wr.http.RunInfo;
import com.example.wr.http.Success;
import com.example.wr.ui.friend.FriendFragment;
import com.example.wr.ui.friend.FriendsListAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecordListFragment extends Fragment {
    private ListviewItemRecordBinding binding;

    private RecordListFragment fragment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ListviewItemRecordBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        fragment = this;

        return root;
    }


}
