package com.tovo.eat.ui.home.homemenu.kitchen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KitchenResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("makeituserid")
        @Expose
        private Integer makeituserid;
        @SerializedName("makeitusername")
        @Expose
        private String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        private String makeitbrandname;
        @SerializedName("rating")
        @Expose
        private Double rating;
        @SerializedName("region")
        @Expose
        private Integer region;
        @SerializedName("costfortwo")
        @Expose
        private Integer costfortwo;
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg;


        @SerializedName("regionname")
        @Expose
        private String regionname;

        @SerializedName("isfav")
        @Expose
        private String isfav;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("eta")
        @Expose
        private String eta;

        @SerializedName("favid")
        @Expose
        private Integer favid;


        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public Integer getMakeituserid() {
            return makeituserid;
        }

        public void setMakeituserid(Integer makeituserid) {
            this.makeituserid = makeituserid;
        }

        public String getMakeitusername() {
            return makeitusername;
        }

        public void setMakeitusername(String makeitusername) {
            this.makeitusername = makeitusername;
        }

        public String getMakeitbrandname() {
            return makeitbrandname;
        }

        public void setMakeitbrandname(String makeitbrandname) {
            this.makeitbrandname = makeitbrandname;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public Integer getRegion() {
            return region;
        }

        public void setRegion(Integer region) {
            this.region = region;
        }

        public Integer getCostfortwo() {
            return costfortwo;
        }

        public void setCostfortwo(Integer costfortwo) {
            this.costfortwo = costfortwo;
        }

        public String getMakeitimg() {
            return makeitimg;
        }

        public void setMakeitimg(String makeitimg) {
            this.makeitimg = makeitimg;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }
    }


}
