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
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("eta")
        @Expose
        private String eta;
        @SerializedName("favid")
        @Expose
        private String favid;


        public String getFavid() {
            return favid;
        }

        public void setFavid(String favid) {
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

        public String getMakeitimg() {
            return makeitimg;
        }

        public void setMakeitimg(String makeitimg) {
            this.makeitimg = makeitimg;
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
