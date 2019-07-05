package com.tovo.eat.ui.home.region.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionDetailsRequest {
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("eatuserid")
    @Expose
    private Integer eatuserid;
    @SerializedName("regionid")
    @Expose
    private Integer regionid;


    public RegionDetailsRequest(String lat, String lon, Integer eatuserid, Integer regionid) {
        this.lat = lat;
        this.lon = lon;
        this.eatuserid = eatuserid;
        this.regionid = regionid;
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

    public Integer getEatuserid() {
        return eatuserid;
    }

    public void setEatuserid(Integer eatuserid) {
        this.eatuserid = eatuserid;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }

}