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
    private Integer makeit_userid;



    @SerializedName("eatuserid")
    @Expose
    private Integer eatuserid;
    @SerializedName("vegtype")
    @Expose
    private Integer vegtype;


    public Integer getEatuserid() {
        return eatuserid;
    }

    public void setEatuserid(Integer eatuserid) {
        this.eatuserid = eatuserid;
    }

    public KitchenDetailsListRequest(String lat, String lon, Integer makeit_userid, Integer eatuserid, Integer vegtype) {
        this.lat = lat;
        this.lon = lon;
        this.makeit_userid = makeit_userid;
        this.eatuserid = eatuserid;
        this.vegtype = vegtype;
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


    public KitchenDetailsListRequest(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public KitchenDetailsListRequest(String lat, String lon, Integer makeit_userid) {
        this.lat = lat;
        this.lon = lon;
        this.makeit_userid = makeit_userid;
    }

    public Integer getMakeit_userid() {
        return makeit_userid;
    }

    public void setMakeit_userid(Integer makeit_userid) {
        this.makeit_userid = makeit_userid;
    }
}
