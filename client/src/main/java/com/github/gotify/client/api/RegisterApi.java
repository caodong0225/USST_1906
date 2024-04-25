package com.github.gotify.client.api;

import com.github.gotify.client.model.Register;
import com.github.gotify.client.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterApi {
    @Headers({
            "Content-Type:application/x-www-form-urlencoded"
    })
    @POST("register")
    Call<RegisterResponse> createRegister(
            @retrofit2.http.Body Register body
    );
}
