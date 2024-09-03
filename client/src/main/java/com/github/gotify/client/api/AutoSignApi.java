package com.github.gotify.client.api;


import com.github.gotify.client.model.Application;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * @author jyzxc
 */
public interface AutoSignApi {
    @FormUrlEncoded
    @Headers({
            "Content-Type:application/x-www-form-urlencoded"
    })
    @PUT("application/auto/{id}")
    Call<Application> setAutoSign(
            @Field("isAuto") Boolean isAuto, @retrofit2.http.Path("id") Long id
    );

}
