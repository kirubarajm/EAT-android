package com.tovo.eat.ui.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultAddressRequest {

    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("aid")
    @Expose
    private Integer aid;

    public DefaultAddressRequest(Integer userid, Integer aid) {
        this.userid = userid;
        this.aid = aid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

}