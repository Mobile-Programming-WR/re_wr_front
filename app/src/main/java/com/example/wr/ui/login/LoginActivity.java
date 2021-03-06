package com.example.wr.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wr.MainActivity;
import com.example.wr.databinding.LayoutLoginBinding;
import com.example.wr.http.LoginRequest;
import com.example.wr.http.LoginResponse;
import com.example.wr.http.RetrofitClient;
import com.example.wr.ui.register.RegisterActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private LayoutLoginBinding binding;

    private SharedPreferences preferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button loginBtn = binding.btnLogin;
        Button RegisterBtn = binding.btnGoRegister;
        EditText loginId = binding.etLoginId;
        EditText loginPw = binding.etLoginPw;
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(), RegisterActivity.class);
                finish();
                startActivity(intent);
                return;
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = loginId.getText().toString();
                String password = loginPw.getText().toString();
                if(id.equals("")){
                    showAlert("아이디를 입력해주세요");
                    return;
                }
                else if(password.equals("")){
                    showAlert("비밀번호를 입력해주세요");
                    return;
                }
                LoginRequest loginRequest = new LoginRequest(id, password);
                LoginResponse loginResponse = new LoginResponse();
                Call<LoginResponse> call = RetrofitClient.getApiService().postLogin(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            return;
                        }
                        String token = response.body().getToken();
                        String name = response.body().getName();
                        Log.d("연결이 성공적, token : ", token);
                        preferences = getSharedPreferences("UserInfo", MODE_PRIVATE);
                        //Editor를 preferences에 쓰겠다고 연결
                        SharedPreferences.Editor editor = preferences.edit();
                        //putString(KEY,VALUE)
                        editor.putString("token",token);
                        editor.putString("name", name);
                        //항상 commit & apply 를 해주어야 저장이 된다.
                        editor.commit();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        finish();
                        startActivity(intent);
                        return;
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
//                token = "connection failed";
                        Log.e("연결실패", t.getMessage());
                    }
                });
//                if(token.equals("")){
//                    showAlert("아이디 또는 비밀번호가 잘못되었습니다");
//                }


            }
        });
    }
    private void showAlert(String string) {
        android.app.AlertDialog.Builder msgBuilder = new android.app.AlertDialog.Builder(this);
        msgBuilder.setMessage(string);
        msgBuilder.setNegativeButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        android.app.AlertDialog msgDlg = msgBuilder.create();
        msgDlg.show();
    }
    public void setToken(String token){
        SharedPreferences pref = getSharedPreferences("mine",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();

        editor.putString("token", token);

        editor.commit();
    }
}
