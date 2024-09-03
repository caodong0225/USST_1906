package com.github.gotify.client.model;

import com.google.gson.annotations.SerializedName;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "The RegisterExternal holds information about a new user.")
public class Register {
    @SerializedName("name")
    private String name;

    @SerializedName("pass")
    private String pass;

    @SerializedName("x_session_id")
    private String x_session_id;

    @ApiModelProperty(example = "lisa", required = true, value = "The user's name.")
    public String getUsername() {
        return name;
    }
    public void setUsername(String username) {
        this.name = username;
    }
    @ApiModelProperty(example = "123456", required = true, value = "The user's password.")
    public String getPassword() {
        return pass;
    }

    public void setPassword(String password) {
        this.pass = password;
    }
    @ApiModelProperty(example = "5", required = true, value = "The user's x_session_id.")
    public String getX_session_id() {
        return x_session_id;
    }

    public void setX_session_id(String x_session_id) {
        this.x_session_id = x_session_id;
    }

    // 构造函数
}
