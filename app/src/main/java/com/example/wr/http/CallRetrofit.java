package com.example.wr.http;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wr.ui.login.LoginActivity;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallRetrofit {
    public void callLogin(String id, String password, String token) {
        //Retrofit 호출
        LoginRequest loginRequest = new LoginRequest(id, password);
        LoginResponse loginResponse = new LoginResponse();
        Call<LoginResponse> call = RetrofitClient.getApiService().postOverlapCheck(loginRequest);
//        new NetworkCall().execute(call);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
//                token = response.body().getToken();
//                Log.d("연결이 성공적, token : ", token);
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                token = "connection failed";
                Log.e("연결실패", t.getMessage());
            }
        });
    }
//    private class NetworkCall extends AsyncTask<Call, Void, String> {
//        @Override
//        protected String doInBackground(Call[] params) {
//            try {
//                Call<LoginResponse> call = params[0];
//                Response<LoginResponse> response = call.execute();
//                return response.body().getToken();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            Log.d("token is ", result);
//        }
//    }
}
