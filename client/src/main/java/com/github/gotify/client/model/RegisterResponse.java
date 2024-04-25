package com.github.gotify.client.model;

import com.google.gson.annotations.SerializedName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "The RegisterInfo holds information about the response of the server.")
public class RegisterResponse {
    @SerializedName("code")
    private String code = null;

    @SerializedName("message")
    private String message = null;

    @ApiModelProperty(example = "200", required = true, value = "The response code.")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @ApiModelProperty(example = "注册成功", required = true, value = "The response message.")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
