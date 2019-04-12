package com.tovo.eat.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatLngPojo {

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("eatuserid")
    @Expose
    private Integer eatId;


    public LatLngPojo(String lat, String lon, Integer eatId) {
        this.lat = lat;
        this.lon = lon;
        this.eatId = eatId;
    }

    public Integer getEatId() {
        return eatId;
    }

    public void setEatId(Integer eatId) {
        this.eatId = eatId;
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


    public LatLngPojo(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
