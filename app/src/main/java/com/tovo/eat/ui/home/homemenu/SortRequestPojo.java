package com.tovo.eat.ui.home.homemenu;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SortRequestPojo {

    @SerializedName("search")
    @Expose
    private String search;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("eatuserid")
    @Expose
    private Integer eatuserid;
    @SerializedName("regionlist")
    @Expose
    private List<Regionlist> regionlist = null;
    @SerializedName("cusinelist")
    @Expose
    private List<Cusinelist> cusinelist = null;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getEatuserid() {
        return eatuserid;
    }

    public void setEatuserid(Integer eatuserid) {
        this.eatuserid = eatuserid;
    }

    public List<Regionlist> getRegionlist() {
        return regionlist;
    }

    public void setRegionlist(List<Regionlist> regionlist) {
        this.regionlist = regionlist;
    }

    public List<Cusinelist> getCusinelist() {
        return cusinelist;
    }

    public void setCusinelist(List<Cusinelist> cusinelist) {
        this.cusinelist = cusinelist;
    }


    public class Regionlist {

        @SerializedName("region")
        @Expose
        private Integer region;

        public Integer getRegion() {
            return region;
        }

        public void setRegion(Integer region) {
            this.region = region;
        }

    }

    public class Cusinelist {

        @SerializedName("cusine")
        @Expose
        private Integer cusine;

        public Integer getCusine() {
            return cusine;
        }

        public void setCusine(Integer cusine) {
            this.cusine = cusine;
        }

    }
}