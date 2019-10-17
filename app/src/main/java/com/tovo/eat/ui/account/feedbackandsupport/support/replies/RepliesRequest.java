package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RepliesRequest {
    @SerializedName("type")
    @Expose
    public Integer type;
    @SerializedName("userid")
    @Expose
    public Long userid;


    public RepliesRequest(Integer type, Long userid) {
        this.type = type;
        this.userid = userid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
