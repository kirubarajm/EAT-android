package com.tovo.eat.ui.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentSuccessRequest {

    @SerializedName("orderid")
    @Expose
    private Integer orderid;
    @SerializedName("payment_status")
    @Expose
    private Integer paymentStatus;
    @SerializedName("transactionid")
    @Expose
    private String transactionid;


    public PaymentSuccessRequest(Integer orderid, Integer paymentStatus, String transactionid) {
        this.orderid = orderid;
        this.paymentStatus = paymentStatus;
        this.transactionid = transactionid;
    }


    public PaymentSuccessRequest(Integer orderid, Integer paymentStatus) {
        this.orderid = orderid;
        this.paymentStatus = paymentStatus;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid;
    }
}
