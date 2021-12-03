package com.example.wr.DTO;

public class Verification {
    private String phone;
    private String verifyCode;

    public Verification(String phone, String verifyCode){
        this.phone = phone;
        this.verifyCode = verifyCode;
    }
    public String getPhone() {
        return phone;
    }

    public String getVerifyCode() {
        return verifyCode;
    }
}
