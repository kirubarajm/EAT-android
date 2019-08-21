package com.tovo.eat.ui.payment;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartPaymentResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("orderid")
    @Expose
    private Integer orderid;
    @SerializedName("refund_balance")
    @Expose
    private Integer refundBalance;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("razer_customerid")
    @Expose
    private String razerCustomerid;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;


    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

    public Integer getRefundBalance() {
        return refundBalance;
    }

    public void setRefundBalance(Integer refundBalance) {
        this.refundBalance = refundBalance;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRazerCustomerid() {
        return razerCustomerid;
    }

    public void setRazerCustomerid(String razerCustomerid) {
        this.razerCustomerid = razerCustomerid;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("orderid")
        @Expose
        private Integer orderid;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("ordertime")
        @Expose
        private String ordertime;
        @SerializedName("locality")
        @Expose
        private String locality;
        @SerializedName("delivery_charge")
        @Expose
        private String deliveryCharge;
        @SerializedName("ordertype")
        @Expose
        private Integer ordertype;
        @SerializedName("orderstatus")
        @Expose
        private Integer orderstatus;
        @SerializedName("gst")
        @Expose
        private Integer gst;
        @SerializedName("coupon")
        @Expose
        private Object coupon;
        @SerializedName("payment_type")
        @Expose
        private String paymentType;
        @SerializedName("makeit_user_id")
        @Expose
        private Integer makeitUserId;
        @SerializedName("moveit_user_id")
        @Expose
        private Integer moveitUserId;
        @SerializedName("cus_lat")
        @Expose
        private String cusLat;
        @SerializedName("cus_lon")
        @Expose
        private String cusLon;
        @SerializedName("cus_address")
        @Expose
        private String cusAddress;
        @SerializedName("makeit_status")
        @Expose
        private String makeitStatus;
        @SerializedName("moveit_reached_time")
        @Expose
        private Object moveitReachedTime;
        @SerializedName("moveit_expected_delivered_time")
        @Expose
        private Object moveitExpectedDeliveredTime;
        @SerializedName("moveit_actual_delivered_time")
        @Expose
        private Object moveitActualDeliveredTime;
        @SerializedName("moveit_remarks_order")
        @Expose
        private Object moveitRemarksOrder;
        @SerializedName("moveit_accept_time")
        @Expose
        private Object moveitAcceptTime;
        @SerializedName("moveit_status")
        @Expose
        private Object moveitStatus;
        @SerializedName("makeit_expected_preparing_time")
        @Expose
        private Object makeitExpectedPreparingTime;
        @SerializedName("makeit_actual_preparing_time")
        @Expose
        private Object makeitActualPreparingTime;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("makeit_earnings")
        @Expose
        private Integer makeitEarnings;
        @SerializedName("payment_status")
        @Expose
        private Integer paymentStatus;
        @SerializedName("lock_status")
        @Expose
        private Integer lockStatus;
        @SerializedName("order_assigned_time")
        @Expose
        private Object orderAssignedTime;
        @SerializedName("transactionid")
        @Expose
        private Object transactionid;
        @SerializedName("transaction_status")
        @Expose
        private Object transactionStatus;
        @SerializedName("transaction_time")
        @Expose
        private Object transactionTime;
        @SerializedName("item_missing")
        @Expose
        private Integer itemMissing;
        @SerializedName("cancel_by")
        @Expose
        private Integer cancelBy;
        @SerializedName("cancel_reason")
        @Expose
        private Object cancelReason;
        @SerializedName("item_missing_reason")
        @Expose
        private Object itemMissingReason;
        @SerializedName("original_price")
        @Expose
        private Integer originalPrice;
        @SerializedName("refund_amount")
        @Expose
        private Integer refundAmount;
        @SerializedName("discount_amount")
        @Expose
        private Integer discountAmount;
        @SerializedName("address_title")
        @Expose
        private String addressTitle;
        @SerializedName("locality_name")
        @Expose
        private String localityName;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
            this.orderid = orderid;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public String getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(String deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

        public Integer getOrdertype() {
            return ordertype;
        }

        public void setOrdertype(Integer ordertype) {
            this.ordertype = ordertype;
        }

        public Integer getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(Integer orderstatus) {
            this.orderstatus = orderstatus;
        }

        public Integer getGst() {
            return gst;
        }

        public void setGst(Integer gst) {
            this.gst = gst;
        }

        public Object getCoupon() {
            return coupon;
        }

        public void setCoupon(Object coupon) {
            this.coupon = coupon;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public Integer getMakeitUserId() {
            return makeitUserId;
        }

        public void setMakeitUserId(Integer makeitUserId) {
            this.makeitUserId = makeitUserId;
        }

        public Integer getMoveitUserId() {
            return moveitUserId;
        }

        public void setMoveitUserId(Integer moveitUserId) {
            this.moveitUserId = moveitUserId;
        }

        public String getCusLat() {
            return cusLat;
        }

        public void setCusLat(String cusLat) {
            this.cusLat = cusLat;
        }

        public String getCusLon() {
            return cusLon;
        }

        public void setCusLon(String cusLon) {
            this.cusLon = cusLon;
        }

        public String getCusAddress() {
            return cusAddress;
        }

        public void setCusAddress(String cusAddress) {
            this.cusAddress = cusAddress;
        }

        public String getMakeitStatus() {
            return makeitStatus;
        }

        public void setMakeitStatus(String makeitStatus) {
            this.makeitStatus = makeitStatus;
        }

        public Object getMoveitReachedTime() {
            return moveitReachedTime;
        }

        public void setMoveitReachedTime(Object moveitReachedTime) {
            this.moveitReachedTime = moveitReachedTime;
        }

        public Object getMoveitExpectedDeliveredTime() {
            return moveitExpectedDeliveredTime;
        }

        public void setMoveitExpectedDeliveredTime(Object moveitExpectedDeliveredTime) {
            this.moveitExpectedDeliveredTime = moveitExpectedDeliveredTime;
        }

        public Object getMoveitActualDeliveredTime() {
            return moveitActualDeliveredTime;
        }

        public void setMoveitActualDeliveredTime(Object moveitActualDeliveredTime) {
            this.moveitActualDeliveredTime = moveitActualDeliveredTime;
        }

        public Object getMoveitRemarksOrder() {
            return moveitRemarksOrder;
        }

        public void setMoveitRemarksOrder(Object moveitRemarksOrder) {
            this.moveitRemarksOrder = moveitRemarksOrder;
        }

        public Object getMoveitAcceptTime() {
            return moveitAcceptTime;
        }

        public void setMoveitAcceptTime(Object moveitAcceptTime) {
            this.moveitAcceptTime = moveitAcceptTime;
        }

        public Object getMoveitStatus() {
            return moveitStatus;
        }

        public void setMoveitStatus(Object moveitStatus) {
            this.moveitStatus = moveitStatus;
        }

        public Object getMakeitExpectedPreparingTime() {
            return makeitExpectedPreparingTime;
        }

        public void setMakeitExpectedPreparingTime(Object makeitExpectedPreparingTime) {
            this.makeitExpectedPreparingTime = makeitExpectedPreparingTime;
        }

        public Object getMakeitActualPreparingTime() {
            return makeitActualPreparingTime;
        }

        public void setMakeitActualPreparingTime(Object makeitActualPreparingTime) {
            this.makeitActualPreparingTime = makeitActualPreparingTime;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getMakeitEarnings() {
            return makeitEarnings;
        }

        public void setMakeitEarnings(Integer makeitEarnings) {
            this.makeitEarnings = makeitEarnings;
        }

        public Integer getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(Integer paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public Integer getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(Integer lockStatus) {
            this.lockStatus = lockStatus;
        }

        public Object getOrderAssignedTime() {
            return orderAssignedTime;
        }

        public void setOrderAssignedTime(Object orderAssignedTime) {
            this.orderAssignedTime = orderAssignedTime;
        }

        public Object getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(Object transactionid) {
            this.transactionid = transactionid;
        }

        public Object getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(Object transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        public Object getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(Object transactionTime) {
            this.transactionTime = transactionTime;
        }

        public Integer getItemMissing() {
            return itemMissing;
        }

        public void setItemMissing(Integer itemMissing) {
            this.itemMissing = itemMissing;
        }

        public Integer getCancelBy() {
            return cancelBy;
        }

        public void setCancelBy(Integer cancelBy) {
            this.cancelBy = cancelBy;
        }

        public Object getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(Object cancelReason) {
            this.cancelReason = cancelReason;
        }

        public Object getItemMissingReason() {
            return itemMissingReason;
        }

        public void setItemMissingReason(Object itemMissingReason) {
            this.itemMissingReason = itemMissingReason;
        }

        public Integer getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(Integer originalPrice) {
            this.originalPrice = originalPrice;
        }

        public Integer getRefundAmount() {
            return refundAmount;
        }

        public void setRefundAmount(Integer refundAmount) {
            this.refundAmount = refundAmount;
        }

        public Integer getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(Integer discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getAddressTitle() {
            return addressTitle;
        }

        public void setAddressTitle(String addressTitle) {
            this.addressTitle = addressTitle;
        }

        public String getLocalityName() {
            return localityName;
        }

        public void setLocalityName(String localityName) {
            this.localityName = localityName;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

    }
}