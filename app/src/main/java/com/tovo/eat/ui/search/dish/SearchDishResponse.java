package com.tovo.eat.ui.search.dish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchDishResponse {


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


    public class Productlist {

        @SerializedName("favid")
        @Expose
        private Object favid;
        @SerializedName("isfav")
        @Expose
        private Integer isfav;
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
        @SerializedName("cuisinename")
        @Expose
        private String cuisinename;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("productimage")
        @Expose
        private String productimage;

        public Object getFavid() {
            return favid;
        }

        public void setFavid(Object favid) {
            this.favid = favid;
        }

        public Integer getIsfav() {
            return isfav;
        }

        public void setIsfav(Integer isfav) {
            this.isfav = isfav;
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

        public String getCuisinename() {
            return cuisinename;
        }

        public void setCuisinename(String cuisinename) {
            this.cuisinename = cuisinename;
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
        @SerializedName("rating")
        @Expose
        private Double rating;
        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("localityname")
        @Expose
        private Object localityname;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("costfortwo")
        @Expose
        private Integer costfortwo;
        @SerializedName("makeitimg")
        @Expose
        private Object makeitimg;
        @SerializedName("favid")
        @Expose
        private Object favid;
        @SerializedName("isfav")
        @Expose
        private String isfav;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("productlist")
        @Expose
        private List<Productlist> productlist = null;
        @SerializedName("eta")
        @Expose
        private String eta;

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

        public Object getLocalityname() {
            return localityname;
        }

        public void setLocalityname(Object localityname) {
            this.localityname = localityname;
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

        public Object getMakeitimg() {
            return makeitimg;
        }

        public void setMakeitimg(Object makeitimg) {
            this.makeitimg = makeitimg;
        }

        public Object getFavid() {
            return favid;
        }

        public void setFavid(Object favid) {
            this.favid = favid;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<Productlist> getProductlist() {
            return productlist;
        }

        public void setProductlist(List<Productlist> productlist) {
            this.productlist = productlist;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

    }

}
