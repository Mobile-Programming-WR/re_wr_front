package com.example.wr.http;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("user/login")
    Call<LoginResponse> postOverlapCheck(@Body LoginRequest loginRequest); //이건 바디 요청시 사용하는거

    @GET("friend/list")
    Call<FriendsList> getFriendsList(@Header("Authorization") String authorization);
}