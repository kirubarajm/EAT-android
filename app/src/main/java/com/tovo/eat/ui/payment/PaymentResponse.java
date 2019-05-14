package com.tovo.eat.ui.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("aid")
    @Expose
    private Integer aid;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

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