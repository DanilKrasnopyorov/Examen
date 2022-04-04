package com.example.examen.network.models;
import com.google.gson.annotations.SerializedName;

public class DataResponse {
    @SerializedName ("id")
    private String id;
    @SerializedName ("name")
    private String name;
    @SerializedName ("token")
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

