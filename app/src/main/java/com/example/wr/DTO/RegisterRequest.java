package com.example.wr.DTO;

public class RegisterRequest {
    private String id;
    private String password;
    private String name;
    private String sex;
    private String birth;
    private String phone;
    public RegisterRequest(String id, String password, String name, String sex, String birth, String phone){
        this.id = id;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
    }
}
