package net.uprin.mayiuseit.rest;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class ApiError {
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    public ApiError() {
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
