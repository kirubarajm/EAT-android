package com.tovo.eat.ui.home.region.viewmore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegionListRequest {
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
    @SerializedName("vegtype")
    @Expose
    private Integer vegtype;

    public Integer getVegtype() {
        return vegtype;
    }

    public void setVegtype(Integer vegtype) {
        this.vegtype = vegtype;
    }

    public RegionListRequest(String lat, String lon, Integer eatuserid, Integer regionid,Integer vegtype) {
        this.lat = lat;
        this.lon = lon;
        this.eatuserid = eatuserid;
        this.regionid = regionid;
        this.vegtype=vegtype;
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