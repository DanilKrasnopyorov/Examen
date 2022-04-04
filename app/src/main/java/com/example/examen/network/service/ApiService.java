package com.example.examen.network.service;
import com.example.examen.network.models.PolicyResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/login/?&")
    Call<PolicyResponse> doLogin(@Query("login") String login, @Query("password") String password);
}
