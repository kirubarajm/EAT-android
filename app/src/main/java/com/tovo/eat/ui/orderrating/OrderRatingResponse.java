package com.tovo.eat.ui.orderrating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderRatingResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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

        @SerializedName("orid")
        @Expose
        public Long orid;
        @SerializedName("rating_food")
        @Expose
        public Integer ratingFood;
        @SerializedName("rating_delivery")
        @Expose
        public Integer ratingDelivery;
        @SerializedName("desc_food")
        @Expose
        public String descFood;
        @SerializedName("desc_delivery")
        @Expose
        public String descDelivery;
        @SerializedName("orderid")
        @Expose
        public Integer orderid;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;


        public Long getOrid() {
            return orid;
        }

        public void setOrid(Long orid) {
            this.orid = orid;
        }

        public Integer getRatingFood() {
            return ratingFood;
        }

        public void setRatingFood(Integer ratingFood) {
            this.ratingFood = ratingFood;
        }

        public Integer getRatingDelivery() {
            return ratingDelivery;
        }

        public void setRatingDelivery(Integer ratingDelivery) {
            this.ratingDelivery = ratingDelivery;
        }

        public String getDescFood() {
            return descFood;
        }

        public void setDescFood(String descFood) {
            this.descFood = descFood;
        }

        public String getDescDelivery() {
            return descDelivery;
        }

        public void setDescDelivery(String descDelivery) {
            this.descDelivery = descDelivery;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
            this.orderid = orderid;
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
