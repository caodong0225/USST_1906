package com.github.gotify.client.model;

import com.google.gson.annotations.SerializedName;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "The RegisterExternal holds information about a new user.")
public class Register {
    @SerializedName("username")
    private String username = null;

    @SerializedName("password")
    private String password = null;

    @SerializedName("x_session_id")
    private String x_session_id = null;

    @ApiModelProperty(example = "lisa", required = true, value = "The user's name.")
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @ApiModelProperty(example = "123456", required = true, value = "The user's password.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @ApiModelProperty(example = "5", required = true, value = "The user's x_session_id.")
    public String getX_session_id() {
        return x_session_id;
    }

    public void setX_session_id(String x_session_id) {
        this.x_session_id = x_session_id;
    }

    // 构造函数
    public Register(String username, String password, String xSessionId) {
        this.username = username;
        this.password = password;
        this.x_session_id = xSessionId;
    }
}
