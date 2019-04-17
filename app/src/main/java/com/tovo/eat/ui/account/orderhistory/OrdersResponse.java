package com.tovo.eat.ui.account.orderhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrdersResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
        public Integer orderid;
        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("ordertime")
        @Expose
        public String ordertime;
        @SerializedName("locality")
        @Expose
        public String locality;
        @SerializedName("delivery_charge")
        @Expose
        public String deliveryCharge;
        @SerializedName("ordertype")
        @Expose
        public Integer ordertype;
        @SerializedName("orderstatus")
        @Expose
        public Integer orderstatus;
        @SerializedName("gst")
        @Expose
        public Float gst;
        @SerializedName("coupon")
        @Expose
        public Object coupon;
        @SerializedName("payment_type")
        @Expose
        public String paymentType;
        @SerializedName("makeit_user_id")
        @Expose
        public Integer makeitUserId;
        @SerializedName("moveit_user_id")
        @Expose
        public Integer moveitUserId;
        @SerializedName("cus_lat")
        @Expose
        public String cusLat;
        @SerializedName("cus_lon")
        @Expose
        public String cusLon;
        @SerializedName("cus_address")
        @Expose
        public String cusAddress;
        @SerializedName("makeit_status")
        @Expose
        public String makeitStatus;
        @SerializedName("moveit_reached_time")
        @Expose
        public Object moveitReachedTime;
        @SerializedName("moveit_expected_delivered_time")
        @Expose
        public Object moveitExpectedDeliveredTime;
        @SerializedName("moveit_actual_delivered_time")
        @Expose
        public Object moveitActualDeliveredTime;
        @SerializedName("moveit_remarks_order")
        @Expose
        public Object moveitRemarksOrder;
        @SerializedName("makeit_expected_preparing_time")
        @Expose
        public Object makeitExpectedPreparingTime;
        @SerializedName("makeit_actual_preparing_time")
        @Expose
        public Object makeitActualPreparingTime;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("price")
        @Expose
        public Float price;
        @SerializedName("payment_status")
        @Expose
        public Integer paymentStatus;
        @SerializedName("lock_status")
        @Expose
        public Integer lockStatus;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;
        @SerializedName("order_assigned_time")
        @Expose
        public Object orderAssignedTime;

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

        public Float getGst() {
            return gst;
        }

        public void setGst(Float gst) {
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

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Float getPrice() {
            return price;
        }

        public void setPrice(Float price) {
            this.price = price;
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

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Object getOrderAssignedTime() {
            return orderAssignedTime;
        }

        public void setOrderAssignedTime(Object orderAssignedTime) {
            this.orderAssignedTime = orderAssignedTime;
        }
    }
}
