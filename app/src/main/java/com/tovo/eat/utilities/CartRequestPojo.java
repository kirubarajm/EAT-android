package com.tovo.eat.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartRequestPojo {

    @SerializedName("eat_id")
    @Expose
    private Integer eatId;
    @SerializedName("makeit_userid")
    @Expose
    private Integer makeit_userid;
    @SerializedName("makeit_username")
    @Expose
    private String makeit_username;
    @SerializedName("kitchen_type")
    @Expose
    private String kitchenType;
    @SerializedName("kitchen_image")
    @Expose
    private String kitchenImage;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Integer getEatId() {
        return eatId;
    }

    public void setEatId(Integer eatId) {
        this.eatId = eatId;
    }

    public Integer getMakeit_userid() {
        return makeit_userid;
    }

    public void setMakeit_userid(Integer makeit_userid) {
        this.makeit_userid = makeit_userid;
    }

    public String getMakeit_username() {
        return makeit_username;
    }

    public void setMakeit_username(String makeit_username) {
        this.makeit_username = makeit_username;
    }

    public String getKitchenType() {
        return kitchenType;
    }

    public void setKitchenType(String kitchenType) {
        this.kitchenType = kitchenType;
    }

    public String getKitchenImage() {
        return kitchenImage;
    }

    public void setKitchenImage(String kitchenImage) {
        this.kitchenImage = kitchenImage;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public static class Result {

        @SerializedName("makeit_username")
        @Expose
        private String makeit_username;
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("product_quantity")
        @Expose
        private Integer productQuantity;
        @SerializedName("producttype")
        @Expose
        private String producttype;
        @SerializedName("product_name")
        @Expose
        private String product_name;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("image")
        @Expose
        private String image;

        @SerializedName("makeit_userid")
        @Expose
        private Integer makeit_userid;

        public String getMakeit_username() {
            return makeit_username;
        }

        public void setMakeit_username(String makeit_username) {
            this.makeit_username = makeit_username;
        }

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public Integer getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(Integer productQuantity) {
            this.productQuantity = productQuantity;
        }

        public String getProducttype() {
            return producttype;
        }

        public void setProducttype(String producttype) {
            this.producttype = producttype;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getMakeit_userid() {
            return makeit_userid;
        }

        public void setMakeit_userid(Integer makeit_userid) {
            this.makeit_userid = makeit_userid;
        }
    }
}