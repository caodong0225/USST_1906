package com.github.gotify.client.api;

import com.github.gotify.client.model.Error;
import com.github.gotify.client.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * @author jyzxc
 */
public interface RegisterApi {
    @FormUrlEncoded
    @Headers({
            "Content-Type:application/x-www-form-urlencoded"
    })
    @POST("user")
    Call<User> createRegister(
            @Field("name") String username,
            @Field("pass") String password,
            @Field("x_session_id") String xSessionId
    );
}
