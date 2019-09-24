package com.tovo.eat.ui.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartDishResponse {

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

        @SerializedName("makeit_userid")
        @Expose
        private Integer makeitUserid;
        @SerializedName("makeit_username")
        @Expose
        private String makeitUsername;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("producttype")
        @Expose
        private String producttype;
        @SerializedName("distance")
        @Expose
        private Double distance;

        public Integer getMakeitUserid() {
            return makeitUserid;
        }

        public void setMakeitUserid(Integer makeitUserid) {
            this.makeitUserid = makeitUserid;
        }

        public String getMakeitUsername() {
            return makeitUsername;
        }

        public void setMakeitUsername(String makeitUsername) {
            this.makeitUsername = makeitUsername;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getProducttype() {
            return producttype;
        }

        public void setProducttype(String producttype) {
            this.producttype = producttype;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

    }
}
