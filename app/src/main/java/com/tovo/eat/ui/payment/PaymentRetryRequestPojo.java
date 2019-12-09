package com.tovo.eat.ui.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRetryRequestPojo {

    @SerializedName("userid")
    @Expose
    private Long userid;
   @SerializedName("orderid")
    @Expose
    private Long orderId;

    public PaymentRetryRequestPojo(Long userid, Long orderId) {
        this.userid = userid;
        this.orderId = orderId;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
