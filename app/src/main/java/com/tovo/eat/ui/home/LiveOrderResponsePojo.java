package com.tovo.eat.ui.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveOrderResponsePojo {

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

        @SerializedName("orderid")
        @Expose
        private Integer orderid;
        @SerializedName("orderstatus")
        @Expose
        private Integer orderstatus;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("makeituserid")
        @Expose
        private Integer makeituserid;
        @SerializedName("makeitusername")
        @Expose
        private String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        private String makeitbrandname;
        @SerializedName("makeitimage")
        @Expose
        private String makeitimage;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("eta")
        @Expose
        private String eta;

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
            this.orderid = orderid;
        }

        public Integer getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(Integer orderstatus) {
            this.orderstatus = orderstatus;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
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

        public String getMakeitimage() {
            return makeitimage;
        }

        public void setMakeitimage(String makeitimage) {
            this.makeitimage = makeitimage;
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