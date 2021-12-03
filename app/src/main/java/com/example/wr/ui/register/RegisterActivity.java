package com.example.wr.ui.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wr.DTO.RegisterRequest;
import com.example.wr.DTO.Verification;
import com.example.wr.MainActivity;
import com.example.wr.R;
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
    private boolean sended = false;
    private boolean verified = false;
    private String sex;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LayoutRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EditText etRegisterId = binding.etRegisterId;
        EditText etRegisterPw = binding.etRegisterPw;
        EditText etRegisterPwC = binding.etRegisterPwC;
        EditText etRegisterName = binding.etRegisterName;
        Spinner spRegisterSex = binding.spRegisterSex;
        EditText etRegisterBirth = binding.etRegisterBirth;
        EditText etRegisterPhone = binding.etRegisterPhone;
        EditText etRegisterCode = binding.etRegisterCode;
        Button btnRegister = binding.btnRegister;
        Button btnSend = binding.btnRegisterSend;
        Button btnVerify = binding.btnRegisterVerify;

        TextView hint = binding.tvSpHint;
        String[] list = {"성별", "남", "여"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.register_spinner, list);

        spRegisterSex.setAdapter(adapter);
        spRegisterSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        sex = "";
                        hint.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        sex = "male";
                        hint.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        sex = "female";
                        hint.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verification verification = new Verification(etRegisterPhone.getText().toString(), etRegisterCode.getText().toString());
                Call<Success> call = RetrofitClient.getApiService().postPhoneVerification(verification);
                call.enqueue(new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "인증번호 전송", Toast.LENGTH_SHORT).show();
                        sended = true;
                    }
                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
//                token = "connection failed";
                        Log.e("연결실패", t.getMessage());
                        Toast.makeText(getApplicationContext(), "인증번호 전송 실패", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verification verification = new Verification(etRegisterPhone.getText().toString(), etRegisterCode.getText().toString());
                Call<Success> call = RetrofitClient.getApiService().postVerificationCode(verification);
                call.enqueue(new Callback<Success>() {
                    @Override
                    public void onResponse(Call<Success> call, Response<Success> response) {
                        if(!response.isSuccessful()){
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                            Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "인증 완료", Toast.LENGTH_SHORT).show();
                        verified = true;
                    }
                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
//                token = "connection failed";
                        Log.e("연결실패", t.getMessage());
                        Toast.makeText(getApplicationContext(), "인증 실패", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etRegisterId.getText().toString();
                String pw = etRegisterPw.getText().toString();
                String pwC = etRegisterPwC.getText().toString();
                String name = etRegisterName.getText().toString();
//                String sex = spRegisterSex..toString();
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
                            Toast.makeText(getApplicationContext(), "연결 실패", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(getApplicationContext(), "회원가입 완료", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        finish();
                        startActivity(intent);
                        return;
                    }
                    @Override
                    public void onFailure(Call<Success> call, Throwable t) {
//                token = "connection failed";
                        Log.e("연결실패", t.getMessage());
                        Toast.makeText(getApplicationContext(), "회원가입 실패", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}
