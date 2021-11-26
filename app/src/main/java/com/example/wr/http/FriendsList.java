package com.example.wr.http;

import java.util.List;

public class FriendsList {
    private String success;
    private List<Friend> results;
    public String toString(){
        return success;
    }
    public List<Friend> getList(){
        return results;
    }
}

