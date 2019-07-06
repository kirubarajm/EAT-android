package com.tovo.eat.ui.signup.opt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OtpResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("passwordstatus")
    @Expose
    private Boolean passwordstatus;
    @SerializedName("emailstatus")
    @Expose
    private Boolean emailstatus;

    @SerializedName("otpstatus")
    @Expose
    private Boolean otpstatus;
    @SerializedName("genderstatus")
    @Expose
    private Boolean genderstatus;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("regionid")
    @Expose
    private Integer regionid;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;


    public Boolean getEmailstatus() {
        return emailstatus;
    }

    public void setEmailstatus(Boolean emailstatus) {
        this.emailstatus = emailstatus;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getRegionid() {
        return regionid;
    }

    public void setRegionid(Integer regionid) {
        this.regionid = regionid;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
    public class Result {

        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("address_title")
        @Expose
        private String addressTitle;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("flatno")
        @Expose
        private String flatno;
        @SerializedName("locality")
        @Expose
        private String locality;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("aid")
        @Expose
        private Integer aid;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lon")
        @Expose
        private Double lon;
        @SerializedName("landmark")
        @Expose
        private String landmark;
        @SerializedName("address_type")
        @Expose
        private Integer addressType;
        @SerializedName("delete_status")
        @Expose
        private Integer deleteStatus;
        @SerializedName("address_default")
        @Expose
        private Integer addressDefault;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getAddressTitle() {
            return addressTitle;
        }

        public void setAddressTitle(String addressTitle) {
            this.addressTitle = addressTitle;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getFlatno() {
            return flatno;
        }

        public void setFlatno(String flatno) {
            this.flatno = flatno;
        }

        public String getLocality() {
            return locality;
        }

        public void setLocality(String locality) {
            this.locality = locality;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public Integer getAid() {
            return aid;
        }

        public void setAid(Integer aid) {
            this.aid = aid;
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

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public Integer getAddressType() {
            return addressType;
        }

        public void setAddressType(Integer addressType) {
            this.addressType = addressType;
        }

        public Integer getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(Integer deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public Integer getAddressDefault() {
            return addressDefault;
        }

        public void setAddressDefault(Integer addressDefault) {
            this.addressDefault = addressDefault;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

}
