package com.eatl.onlineattendance.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttendanceModel {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("app_message")
    @Expose
    private String appMessage;
    @SerializedName("user_message")
    @Expose
    private String userMessage;
    @SerializedName("data")
    @Expose
    private DataInput data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getAppMessage() {
        return appMessage;
    }

    public void setAppMessage(String appMessage) {
        this.appMessage = appMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public DataInput getData() {
        return data;
    }

    public void setData(DataInput data) {
        this.data = data;
    }
}
