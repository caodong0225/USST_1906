package com.github.gotify.client.api;

import com.github.gotify.client.model.GlobalResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface AutoSignApi {
    @Headers({
            "Content-Type:application/json"
    })
    @GET("is_auto")
    Call<GlobalResponse> isAutoSign(
            @retrofit2.http.Query("token") String token
    );

    @Headers({
            "Content-Type:application/json"
    })
    @GET("set_auto")
    Call<GlobalResponse> setAutoSign(
            @retrofit2.http.Query("token") String token,
            @retrofit2.http.Query("is_auto") int auto
    );
}
