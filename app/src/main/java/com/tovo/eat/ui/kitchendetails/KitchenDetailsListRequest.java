package com.tovo.eat.ui.kitchendetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KitchenDetailsListRequest {

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("makeit_userid")
    @Expose
    private Long makeit_userid;


    @SerializedName("eatuserid")
    @Expose
    private Long eatuserid;
    @SerializedName("vegtype")
    @Expose
    private Integer vegtype;


    public KitchenDetailsListRequest(String lat, String lon, Long makeit_userid, Long eatuserid, Integer vegtype) {
        this.lat = lat;
        this.lon = lon;
        this.makeit_userid = makeit_userid;
        this.eatuserid = eatuserid;
        this.vegtype = vegtype;
    }

    public KitchenDetailsListRequest(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public KitchenDetailsListRequest(String lat, String lon, Long makeit_userid) {
        this.lat = lat;
        this.lon = lon;
        this.makeit_userid = makeit_userid;
    }

    public Long getEatuserid() {
        return eatuserid;
    }

    public void setEatuserid(Long eatuserid) {
        this.eatuserid = eatuserid;
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

    public Long getMakeit_userid() {
        return makeit_userid;
    }

    public void setMakeit_userid(Long makeit_userid) {
        this.makeit_userid = makeit_userid;
    }
}
