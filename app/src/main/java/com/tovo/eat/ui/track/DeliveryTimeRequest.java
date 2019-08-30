package com.tovo.eat.ui.track;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryTimeRequest {

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("orderid")
    @Expose
    private Integer orderid;

    public DeliveryTimeRequest(String lat, String lon, Integer orderid) {
        this.lat = lat;
        this.lon = lon;
        this.orderid = orderid;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public Integer getOrderid() {
        return orderid;
    }

    public void setOrderid(Integer orderid) {
        this.orderid = orderid;
    }

}