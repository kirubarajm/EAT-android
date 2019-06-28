package com.tovo.eat.ui.cart.refund;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RefundListResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;


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

        @SerializedName("rcid")
        @Expose
        private Integer rcid;
        @SerializedName("orderid")
        @Expose
        private Integer orderid;
        @SerializedName("refund_used_orderid")
        @Expose
        private Object refundUsedOrderid;
        @SerializedName("rcoupon")
        @Expose
        private String rcoupon;
        @SerializedName("refundamount")
        @Expose
        private Integer refundamount;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("refund_balance")
        @Expose
        private Integer refundBalance;
        @SerializedName("refund_used_date_time")
        @Expose
        private Object refundUsedDateTime;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;

        public Integer getRcid() {
            return rcid;
        }

        public void setRcid(Integer rcid) {
            this.rcid = rcid;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
            this.orderid = orderid;
        }

        public Object getRefundUsedOrderid() {
            return refundUsedOrderid;
        }

        public void setRefundUsedOrderid(Object refundUsedOrderid) {
            this.refundUsedOrderid = refundUsedOrderid;
        }

        public String getRcoupon() {
            return rcoupon;
        }

        public void setRcoupon(String rcoupon) {
            this.rcoupon = rcoupon;
        }

        public Integer getRefundamount() {
            return refundamount;
        }

        public void setRefundamount(Integer refundamount) {
            this.refundamount = refundamount;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getRefundBalance() {
            return refundBalance;
        }

        public void setRefundBalance(Integer refundBalance) {
            this.refundBalance = refundBalance;
        }

        public Object getRefundUsedDateTime() {
            return refundUsedDateTime;
        }

        public void setRefundUsedDateTime(Object refundUsedDateTime) {
            this.refundUsedDateTime = refundUsedDateTime;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
