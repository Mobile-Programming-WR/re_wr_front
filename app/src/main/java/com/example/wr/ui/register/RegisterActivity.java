package com.example.wr.ui.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wr.DTO.RegisterRequest;
import com.example.wr.MainActivity;
import com.example.wr.databinding.LayoutRegisterBinding;
import com.example.wr.http.LoginResponse;
import com.example.wr.http.RetrofitClient;
import com.example.wr.http.Success;
import com.example.wr.ui.login.LoginActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    LayoutRegisterBinding binding;
    private boolean sended = true;
    private boolean verified = true;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EditText etRegisterId = binding.etRegisterId;
        EditText etRegisterPw = binding.etRegisterPw;
        EditText etRegisterPwC = binding.etRegisterPwC;
        EditText etRegisterName = binding.etRegisterName;
        EditText etRegisterSex = binding.etRegisterSex;
        EditText etRegisterBirth = binding.etRegisterBirth;
        EditText etRegisterPhone = binding.etRegisterPhone;
        EditText etRegisterCode = binding.etRegisterCode;
        Button btnRegister = binding.btnRegister;
        Button btnSend = binding.btnRegisterSend;
        Button btnVerify = binding.btnRegisterVerify;

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etRegisterId.getText().toString();
                String pw = etRegisterPw.getText().toString();
                String pwC = etRegisterPwC.getText().toString();
                String name = etRegisterName.getText().toString();
                String sex = etRegisterSex.getText().toString();
                String birth = etRegisterBirth.getText().toString();
                String phone = etRegisterPhone.getText().toString();
                String code = etRegisterCode.getText().toString();
                if(id.equals("")||pw.equals("")||pwC.equals("")||name.equals("")||sex.equals("")
                    ||birth.equals("")||phone.equals("")||code.equals("")){
                    Toast.makeText(getApplicationContext(), "모든 항목을 적어주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!verified) {
                    Toast.makeText(getApplicationContext(), "전화번호를 인증해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!pw.equals(pwC)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                RegisterRequest registerRequest = new RegisterRequest(id, pw, name, sex, birth, phone);
                Call<Success> call = RetrofitClient.getApiService().postRegister(registerRequest);
                call.enqueue(new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        return;
                    }
                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
//                token = "connection failed";
                        Log.e("연결실패", t.getMessage());
                    }
                });

            }
        });
    }
}
