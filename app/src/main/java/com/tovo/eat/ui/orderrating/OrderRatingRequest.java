package com.tovo.eat.ui.orderrating;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderRatingRequest {

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

    public OrderRatingRequest(Integer ratingFood, Integer ratingDelivery, String descFood, String descDelivery, Integer orderid) {
        this.ratingFood = ratingFood;
        this.ratingDelivery = ratingDelivery;
        this.descFood = descFood;
        this.descDelivery = descDelivery;
        this.orderid = orderid;
    }


    public OrderRatingRequest(Integer orderid) {
        this.orderid = orderid;
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
}
