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

        @SerializedName("kitchen_Name")
        @Expose
        private String kitchenName;
        @SerializedName("kitchen_type")
        @Expose
        private String kitchenType;
        @SerializedName("ratings")
        @Expose
        private Double ratings;
        @SerializedName("eta")
        @Expose
        private String eta;
        @SerializedName("offer")
        @Expose
        private String offer;
        @SerializedName("favourite")
        @Expose
        private String favourite;
        @SerializedName("kitchen_url")
        @Expose
        private String kitchenUrl;


        @SerializedName("kitchen_id")
        @Expose
        private Integer kitchenId;


        public Integer getKitchenId() {
            return kitchenId;
        }

        public void setKitchenId(Integer kitchenId) {
            this.kitchenId = kitchenId;
        }

        public String getKitchenName() {
            return kitchenName;
        }

        public void setKitchenName(String kitchenName) {
            this.kitchenName = kitchenName;
        }

        public String getKitchenType() {
            return kitchenType;
        }

        public void setKitchenType(String kitchenType) {
            this.kitchenType = kitchenType;
        }

        public Double getRatings() {
            return ratings;
        }

        public void setRatings(Double ratings) {
            this.ratings = ratings;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

        public String getOffer() {
            return offer;
        }

        public void setOffer(String offer) {
            this.offer = offer;
        }

        public String getFavourite() {
            return favourite;
        }

        public void setFavourite(String favourite) {
            this.favourite = favourite;
        }

        public String getKitchenUrl() {
            return kitchenUrl;
        }

        public void setKitchenUrl(String kitchenUrl) {
            this.kitchenUrl = kitchenUrl;
        }

    }


}
