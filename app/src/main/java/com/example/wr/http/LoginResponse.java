package com.example.wr.http;

public class LoginResponse {
    private String token;
    private String name;
    public void setToken(String token) {
        this.token = token;
    }
    public String getToken() {
        return token;
    }

    public String getName() { return name; }
}
