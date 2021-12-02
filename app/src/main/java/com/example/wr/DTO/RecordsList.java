package com.example.wr.DTO;

import com.example.wr.http.RunInfo;

import java.util.List;

public class RecordsList {
    private boolean success;
    private List<RunInfo> results;
    private int position;
    public List<RunInfo> getResults() {
        return results;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getPosition() {
        return position;
    }
}
