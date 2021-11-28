package com.example.wr.DTO;

import java.util.List;

public class Challenge {
    private boolean success;
    private float rank;
    private List<Ranker> recordsList;

    public float getRank() {
        return rank;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Ranker> getRecordsList() {
        return recordsList;
    }
}
