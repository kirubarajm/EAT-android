package com.tovo.eat.ui.address.list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressListResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("location_radius")
    @Expose
    private int locationRadius=0;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    public int getLocationRadius() {
        return locationRadius;
    }
    public void setLocationRadius(int locationRadius) {
        this.locationRadius = locationRadius;
    }
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

        @SerializedName("userid")
        @Expose
        private Long userid;
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
        private Long aid;
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
        private String addressType;
        @SerializedName("delete_status")
        @Expose
        private String deleteStatus;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;


        public Long getUserid() {
            return userid;
        }

        public void setUserid(Long userid) {
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

        public Long getAid() {
            return aid;
        }

        public void setAid(Long aid) {
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

        public String getAddressType() {
            return addressType;
        }

        public void setAddressType(String addressType) {
            this.addressType = addressType;
        }

        public String getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(String deleteStatus) {
            this.deleteStatus = deleteStatus;
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
