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
    public Long orderid;

    public OrderRatingRequest(Integer ratingFood, Integer ratingDelivery, String descFood, String descDelivery, Long orderid) {
        this.ratingFood = ratingFood;
        this.ratingDelivery = ratingDelivery;
        this.descFood = descFood;
        this.descDelivery = descDelivery;
        this.orderid = orderid;
    }


    public OrderRatingRequest(Long orderid) {
        this.orderid = orderid;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }
}
