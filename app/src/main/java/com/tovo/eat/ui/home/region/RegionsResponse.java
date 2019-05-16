package com.tovo.eat.ui.home.region;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegionsResponse {

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

        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("area")
        @Expose
        private String area;
        @SerializedName("region")
        @Expose
        private String region;
        @SerializedName("kitchen_nos")
        @Expose
        private Integer kitchenNos;
        @SerializedName("aprox")
        @Expose
        private String aprox;
        @SerializedName("kitchens")
        @Expose
        private List<Kitchen> kitchens = null;
        @SerializedName("region_id")
        @Expose
        private Integer regionId;

        public Integer getRegionId() {
            return regionId;
        }

        public void setRegionId(Integer regionId) {
            this.regionId = regionId;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public Integer getKitchenNos() {
            return kitchenNos;
        }

        public void setKitchenNos(Integer kitchenNos) {
            this.kitchenNos = kitchenNos;
        }

        public String getAprox() {
            return aprox;
        }

        public void setAprox(String aprox) {
            this.aprox = aprox;
        }

        public List<Kitchen> getKitchens() {
            return kitchens;
        }

        public void setKitchens(List<Kitchen> kitchens) {
            this.kitchens = kitchens;
        }

    }


    public class Kitchen {

        @SerializedName("brandname")
        @Expose
        private String brandname;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("rating")
        @Expose
        private Integer rating;
        @SerializedName("eta")
        @Expose
        private String eta;
        @SerializedName("costfortwo")
        @Expose
        private String costfortwo;
        @SerializedName("specialists")
        @Expose
        private String specialists;
        @SerializedName("isFav")
        @Expose
        private String isFav;
        @SerializedName("favId")
        @Expose
        private Integer favId;


        @SerializedName("kitchenId")
        @Expose
        private Integer kitchenId;


        public Integer getKitchenId() {
            return kitchenId;
        }

        public void setKitchenId(Integer kitchenId) {
            this.kitchenId = kitchenId;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public Integer getRating() {
            return rating;
        }

        public void setRating(Integer rating) {
            this.rating = rating;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

        public String getCostfortwo() {
            return costfortwo;
        }

        public void setCostfortwo(String costfortwo) {
            this.costfortwo = costfortwo;
        }

        public String getSpecialists() {
            return specialists;
        }

        public void setSpecialists(String specialists) {
            this.specialists = specialists;
        }

        public String getIsFav() {
            return isFav;
        }

        public void setIsFav(String isFav) {
            this.isFav = isFav;
        }

        public Integer getFavId() {
            return favId;
        }

        public void setFavId(Integer favId) {
            this.favId = favId;
        }

    }
}
