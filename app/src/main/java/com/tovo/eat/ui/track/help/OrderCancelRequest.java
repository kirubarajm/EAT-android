package com.tovo.eat.ui.track.help;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderCancelRequest {

    @SerializedName("orderid")
    @Expose
    public Long orderid;

    @SerializedName("cancel_reason")
    @Expose
    public String reason;

    public OrderCancelRequest(Long orderid, String reason) {
        this.orderid = orderid;
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
}
