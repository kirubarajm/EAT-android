package com.tovo.eat.ui.track.help;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCancelRequest {

    @SerializedName("orderid")
    @Expose
    public Integer orderid;

    @SerializedName("cancel_reason")
    @Expose
    public String reason;

    public OrderCancelRequest(Integer orderid, String reason) {
        this.orderid = orderid;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }
}
