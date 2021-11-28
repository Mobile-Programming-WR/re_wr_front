package com.example.wr.http;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("user/login")
    Call<LoginResponse> postOverlapCheck(@Body LoginRequest loginRequest); //이건 바디 요청시 사용하는거

    @GET("friend/list")
    Call<FriendsList> getFriendsList(@Header("Authorization") String authorization);
    @GET("friend/requestlist")
    Call<FriendsList> getRequestList(@Header("Authorization") String authorization);
    @GET("friend/accept/{id}")
    Call<Success> getAccept(@Header("Authorization") String authorization, @Path("id") String name);

    @POST("friend/add/{id}")
    Call<Success> postAddFriend(@Header("Authorization") String authorization, @Path("id") String name);

    @POST("record")
    Call<Success> postAddRecord(@Header("Authorization") String authorization, @Body RunInfo runInfo);

    @GET("record/list")
    Call<RecordsList> getRecordsList(@Header("Authorization") String authorization);

    @GET("record/read/{UID}")
    Call<RecordsList> getRecord(@Header("Authorization") String authorization, @Path("UID") String uid);


}