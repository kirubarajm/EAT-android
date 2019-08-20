package com.tovo.eat.ui.cart.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CouponListResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class    Result {

        @SerializedName("cid")
        @Expose
        private Integer cid;
        @SerializedName("coupon_name")
        @Expose
        private String couponName;
        @SerializedName("img_url")
        @Expose
        private String imgUrl;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("numberoftimes")
        @Expose
        private Integer numberoftimes;
        @SerializedName("maxdiscount")
        @Expose
        private Integer maxdiscount;
        @SerializedName("discount_percent")
        @Expose
        private Integer discountPercent;
        @SerializedName("minprice_limit")
        @Expose
        private Integer minpriceLimit;
        @SerializedName("expiry_date")
        @Expose
        private String expiryDate;
        @SerializedName("couponstatus")
        @Expose
        private boolean couponStatus;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;

        public boolean isCouponStatus() {
            return couponStatus;
        }

        public void setCouponStatus(boolean couponStatus) {
            this.couponStatus = couponStatus;
        }

        public Integer getCid() {
            return cid;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public Integer getNumberoftimes() {
            return numberoftimes;
        }

        public void setNumberoftimes(Integer numberoftimes) {
            this.numberoftimes = numberoftimes;
        }

        public Integer getMaxdiscount() {
            return maxdiscount;
        }

        public void setMaxdiscount(Integer maxdiscount) {
            this.maxdiscount = maxdiscount;
        }

        public Integer getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(Integer discountPercent) {
            this.discountPercent = discountPercent;
        }

        public Integer getMinpriceLimit() {
            return minpriceLimit;
        }

        public void setMinpriceLimit(Integer minpriceLimit) {
            this.minpriceLimit = minpriceLimit;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
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
