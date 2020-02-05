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


    public class Cuisine {

        @SerializedName("cuisineid")
        @Expose
        private Integer cuisineid;
        @SerializedName("cuisinename")
        @Expose
        private String cuisinename;

        public Integer getCuisineid() {
            return cuisineid;
        }

        public void setCuisineid(Integer cuisineid) {
            this.cuisineid = cuisineid;
        }

        public String getCuisinename() {
            return cuisinename;
        }

        public void setCuisinename(String cuisinename) {
            this.cuisinename = cuisinename;
        }

    }

    public class Kitchenlist {

        @SerializedName("makeituserid")
        @Expose
        private Long makeituserid;
        @SerializedName("makeitusername")
        @Expose
        private String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        private String makeitbrandname;
        @SerializedName("rating")
        @Expose
        private Double rating;
        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("costfortwo")
        @Expose
        private Integer costfortwo;
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg;
        @SerializedName("localityname")
        @Expose
        private String localityname;
        @SerializedName("member_type")
        @Expose
        private String memberType;
        @SerializedName("about")
        @Expose
        private String about;
        @SerializedName("favid")
        @Expose
        private Integer favid;
        @SerializedName("isfav")
        @Expose
        private String isfav;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("cuisines")
        @Expose
        private List<Cuisine> cuisines = null;
        @SerializedName("eta")
        @Expose
        private String eta;

        public Long getMakeituserid() {
            return makeituserid;
        }

        public void setMakeituserid(Long makeituserid) {
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

        public String getLocalityname() {
            return localityname;
        }

        public void setLocalityname(String localityname) {
            this.localityname = localityname;
        }

        public String getMemberType() {
            return memberType;
        }

        public void setMemberType(String memberType) {
            this.memberType = memberType;
        }

        public String getAbout() {
            return about;
        }

        public void setAbout(String about) {
            this.about = about;
        }

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public List<Cuisine> getCuisines() {
            return cuisines;
        }

        public void setCuisines(List<Cuisine> cuisines) {
            this.cuisines = cuisines;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

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
        @SerializedName("statename")
        @Expose
        private String statename;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("kitchencount")
        @Expose
        private Integer kitchencount;
        @SerializedName("kitchenlist")
        @Expose
        private List<Kitchenlist> kitchenlist = null;
        @SerializedName("clickable")
        @Expose
        private boolean clickable=true;

        public boolean isClickable() {
            return clickable;
        }

        public void setClickable(boolean clickable) {
            this.clickable = clickable;
        }


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

        public String getStatename() {
            return statename;
        }

        public void setStatename(String statename) {
            this.statename = statename;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public Integer getKitchencount() {
            return kitchencount;
        }

        public void setKitchencount(Integer kitchencount) {
            this.kitchencount = kitchencount;
        }

        public List<Kitchenlist> getKitchenlist() {
            return kitchenlist;
        }

        public void setKitchenlist(List<Kitchenlist> kitchenlist) {
            this.kitchenlist = kitchenlist;
        }

    }
}

