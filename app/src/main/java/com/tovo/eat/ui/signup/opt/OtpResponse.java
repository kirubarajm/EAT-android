package com.tovo.eat.ui.signup.opt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("passwordstatus")
    @Expose
    public Boolean passwordstatus;
    @SerializedName("otpstatus")
    @Expose
    public Boolean otpstatus;
    @SerializedName("genderstatus")
    @Expose
    public Boolean genderstatus;
    @SerializedName("oid")
    @Expose
    public Integer oid;
    @SerializedName("userid")
    @Expose
    public Integer userid;



    @SerializedName("addressdetails")
    @Expose
    private List<Addressdetails> addressdetails = null;

    public List<Addressdetails> getAddressdetails() {
        return addressdetails;
    }

    public void setAddressdetails(List<Addressdetails> addressdetails) {
        this.addressdetails = addressdetails;
    }







    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getPasswordstatus() {
        return passwordstatus;
    }

    public void setPasswordstatus(Boolean passwordstatus) {
        this.passwordstatus = passwordstatus;
    }

    public Boolean getOtpstatus() {
        return otpstatus;
    }

    public void setOtpstatus(Boolean otpstatus) {
        this.otpstatus = otpstatus;
    }

    public Boolean getGenderstatus() {
        return genderstatus;
    }

    public void setGenderstatus(Boolean genderstatus) {
        this.genderstatus = genderstatus;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }


    public class Addressdetails{

        @SerializedName("aid")
        @Expose
        private Integer aid;
        @SerializedName("address_title")
        @Expose
        private String addressTitle;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lon")
        @Expose
        private Double lon;

        public Integer getAid() {
            return aid;
        }

        public void setAid(Integer aid) {
            this.aid = aid;
        }

        public String getAddressTitle() {
            return addressTitle;
        }

        public void setAddressTitle(String addressTitle) {
            this.addressTitle = addressTitle;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }


    }


}
