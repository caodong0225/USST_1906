package com.github.gotify.client.api;

import com.github.gotify.client.model.Register;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RegisterApi {
    @Headers({
            "Content-Type:application/json"
    })
    @POST("register")
    Call<Register> createRegister(
            @retrofit2.http.Body Register body
    );
}
