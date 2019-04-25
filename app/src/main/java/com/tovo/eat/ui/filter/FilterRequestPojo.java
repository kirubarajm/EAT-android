package com.tovo.eat.ui.filter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterRequestPojo {
    @SerializedName("search")
    @Expose
    private String search;




    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lon")
    @Expose
    private String lon;
    @SerializedName("eatuserid")
    @Expose
    private Integer eatuserid;
    @SerializedName("regionlist")
    @Expose
    private List<Regionlist> regionlist = null;
    @SerializedName("cuisinelist")
    @Expose
    private List<Cusinelist> cusinelist = null;
    @SerializedName("sortlist")
    @Expose
    private List<Sortlist> sortlist = null;

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

    public List<Sortlist> getSortlist() {
        return sortlist;
    }

    public void setSortlist(List<Sortlist> sortlist) {
        this.sortlist = sortlist;
    }

    public static class Cusinelist {
        public Cusinelist(Integer cusine) {
            this.cusine = cusine;
        }

        @SerializedName("cuisine")
        @Expose
        private Integer cusine;

        public Integer getCusine() {
            return cusine;
        }

        public void setCusine(Integer cusine) {
            this.cusine = cusine;
        }

    }

    public static class Regionlist {

        public Regionlist(Integer region) {
            this.region = region;
        }

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

    public static class Sortlist {


        public Sortlist(Integer sort) {
            this.sort = sort;
        }

        @SerializedName("sort")
        @Expose
        private Integer sort;

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

    }
}