package com.example.wr.DTO;

import java.util.List;

public class Competition {
    private boolean success;
    private Info myInfo;
    private Info friendInfo;
    private List<RunInfo> myRecord;
    private List<RunInfo> friendRecord;


    public Info getMyInfo() {
        return myInfo;
    }

    public Info getFriendInfo() {
        return friendInfo;
    }

    public List<RunInfo> getMyRecord() {
        return myRecord;
    }

    public List<RunInfo> getFriendRecord() {
        return friendRecord;
    }
}

