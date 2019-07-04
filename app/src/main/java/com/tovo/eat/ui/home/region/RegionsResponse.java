package com.tovo.eat.ui.home.region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionsResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("stateid")
        @Expose
        private Integer stateid;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lon")
        @Expose
        private Double lon;
        @SerializedName("region_image")
        @Expose
        private String regionImage;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("slider_title")
        @Expose
        private String sliderTitle;
        @SerializedName("slider_content")
        @Expose
        private String sliderContent;
        @SerializedName("region_detail_image")
        @Expose
        private String regionDetailImage;
        @SerializedName("special_dish_img")
        @Expose
        private String specialDishImg;
        @SerializedName("identity_img")
        @Expose
        private String identityImg;
        @SerializedName("tagline")
        @Expose
        private String tagline;
        @SerializedName("specialities_food_content")
        @Expose
        private String specialitiesFoodContent;
        @SerializedName("distance")
        @Expose
        private Double distance;


        public Integer getRegionid() {
            return regionid;
        }

        public void setRegionid(Integer regionid) {
            this.regionid = regionid;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public Integer getStateid() {
            return stateid;
        }

        public void setStateid(Integer stateid) {
            this.stateid = stateid;
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

        public String getRegionImage() {
            return regionImage;
        }

        public void setRegionImage(String regionImage) {
            this.regionImage = regionImage;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getSliderTitle() {
            return sliderTitle;
        }

        public void setSliderTitle(String sliderTitle) {
            this.sliderTitle = sliderTitle;
        }

        public String getSliderContent() {
            return sliderContent;
        }

        public void setSliderContent(String sliderContent) {
            this.sliderContent = sliderContent;
        }

        public String getRegionDetailImage() {
            return regionDetailImage;
        }

        public void setRegionDetailImage(String regionDetailImage) {
            this.regionDetailImage = regionDetailImage;
        }

        public String getSpecialDishImg() {
            return specialDishImg;
        }

        public void setSpecialDishImg(String specialDishImg) {
            this.specialDishImg = specialDishImg;
        }

        public String getIdentityImg() {
            return identityImg;
        }

        public void setIdentityImg(String identityImg) {
            this.identityImg = identityImg;
        }

        public String getTagline() {
            return tagline;
        }

        public void setTagline(String tagline) {
            this.tagline = tagline;
        }

        public String getSpecialitiesFoodContent() {
            return specialitiesFoodContent;
        }

        public void setSpecialitiesFoodContent(String specialitiesFoodContent) {
            this.specialitiesFoodContent = specialitiesFoodContent;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }


    }
}

