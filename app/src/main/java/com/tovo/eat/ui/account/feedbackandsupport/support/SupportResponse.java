package com.tovo.eat.ui.account.feedbackandsupport.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportResponse {


    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("message")
    @Expose
    public String message;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
