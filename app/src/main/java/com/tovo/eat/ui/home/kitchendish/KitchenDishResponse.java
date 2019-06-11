package com.tovo.eat.ui.home.kitchendish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;

import java.util.List;

public class KitchenDishResponse {
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

    public static class Productlist {

        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("vegtype")
        @Expose
        private String vegtype;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("productimage")
        @Expose
        private String productimage;

        @SerializedName("cuisinename")
        @Expose
        private String cuisinename;

        @SerializedName("favid")
        @Expose
        private Integer favid;

        @SerializedName("isfav")
        @Expose
        private String isfav;


        public String getCuisinename() {
            return cuisinename;
        }

        public void setCuisinename(String cuisinename) {
            this.cuisinename = cuisinename;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getVegtype() {
            return vegtype;
        }

        public void setVegtype(String vegtype) {
            this.vegtype = vegtype;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductimage() {
            return productimage;
        }

        public void setProductimage(String productimage) {
            this.productimage = productimage;
        }

    }

    public static class Result {

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
        private List<KitchenResponse.Cuisine> cuisines = null;
        @SerializedName("eta")
        @Expose
        private String eta;

        @SerializedName("productlist")
        @Expose
        private List<Productlist> productlist = null;


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

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public List<KitchenResponse.Cuisine> getCuisines() {
            return cuisines;
        }

        public void setCuisines(List<KitchenResponse.Cuisine> cuisines) {
            this.cuisines = cuisines;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

        public String getLocalityname() {
            return localityname;
        }

        public void setLocalityname(String localityname) {
            this.localityname = localityname;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
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

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public List<Productlist> getProductlist() {
            return productlist;
        }

        public void setProductlist(List<Productlist> productlist) {
            this.productlist = productlist;
        }

    }
}
