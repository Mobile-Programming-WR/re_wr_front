package com.example.wr.http;

import com.example.wr.DTO.Challenge;
import com.example.wr.DTO.Friend;
import com.example.wr.DTO.FriendsList;
import com.example.wr.DTO.RegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {
    @POST("user/login")
    Call<LoginResponse> postLogin(@Body LoginRequest loginRequest); //이건 바디 요청시 사용하는거

    @POST("user")
    Call<Success> postRegister(@Body RegisterRequest registerRequest);

    // friend
    @GET("friend/list")
    Call<FriendsList> getFriendsList(@Header("Authorization") String authorization);
    @GET("friend/requestlist")
    Call<FriendsList> getRequestList(@Header("Authorization") String authorization);
    @GET("friend/accept/{id}")
    Call<Success> getAccept(@Header("Authorization") String authorization, @Path("id") String id);
    @GET("friend/add/{id}")
    Call<Success> getAddFriend(@Header("Authorization") String authorization, @Path("id") String id);
    @GET("friend/competition/requestlist")
    Call<FriendsList> getCompetitionRequest(@Header("Authorization") String authorization);
    @GET("friend/competition/{id}")
    Call<Success> getAddCompetition(@Header("Authorization") String authorization, @Path("id") String id);
    @GET("friend/competition/accept/{id}")
    Call<Success> getAcceptCompetition(@Header("Authorization") String authorization, @Path("id") String id);
    @GET("friend/competition/{id}")
    Call<FriendsList> getCompetitionList(@Header("Authorization") String authorization);
    @DELETE("friend/{id}")
    Call<Success> deleteFriend(@Header("Authorization") String authorization, @Path("id") String id);
    // record
    @POST("record")
    Call<Success> postAddRecord(@Header("Authorization") String authorization, @Body RunInfo runInfo);
    // challenge
    @GET("challenge")
    Call<Challenge> getChallenge(@Header("Authorization") String authorization);
//    @GET("record/list")
//    Call<RecordsList> getRecordsList(@Header("Authorization") String authorization);
//
//    @GET("record/read/{UID}")
//    Call<RecordsList> getRecord(@Header("Authorization") String authorization, @Path("UID") String uid);


}