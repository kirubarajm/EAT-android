package com.tovo.eat.ui.home.homemenu.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CollectionRequest {

    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("eatuserid")
    @Expose
    private Long eatuserid;
    @SerializedName("cid")
    @Expose
    private Integer cid;

    public CollectionRequest(Long eatuserid) {
        this.eatuserid = eatuserid;
    }

    public CollectionRequest(String lat, String lon, Long eatuserid) {
        this.lat = lat;
        this.lon = lon;
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

    public Long getEatuserid() {
        return eatuserid;
    }

    public void setEatuserid(Long eatuserid) {
        this.eatuserid = eatuserid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

}