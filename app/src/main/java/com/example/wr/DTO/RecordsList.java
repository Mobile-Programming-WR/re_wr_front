package com.example.wr.DTO;

import java.util.List;

public class RecordsList {
    private boolean success;
    private List<RunInfo> results;

    public List<RunInfo> getResults() {
        return results;
    }

    public boolean isSuccess() {
        return success;
    }
}
