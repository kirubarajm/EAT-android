package com.tovo.eat.ui.address.add;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class    AddressResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("status")
    @Expose
    private Boolean status;


    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("aid")
    @Expose
    private Integer aid;


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

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