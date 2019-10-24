package com.tovo.eat.ui.address;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultAddressRequest {

    @SerializedName("userid")
    @Expose
    private Long userid;
    @SerializedName("aid")
    @Expose
    private Long aid;

    public DefaultAddressRequest(Long userid, Long aid) {
        this.userid = userid;
        this.aid = aid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

}