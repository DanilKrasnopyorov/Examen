package com.example.examen.network.models;

import com.google.gson.annotations.SerializedName;

public class PolicyResponse {
    @SerializedName("data")
    private DataResponse dataResponse;

    public DataResponse getDataResponse() {
        return dataResponse;
    }

    public void setDataResponse(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }
}