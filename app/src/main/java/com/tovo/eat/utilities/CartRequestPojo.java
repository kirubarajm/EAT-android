package com.tovo.eat.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartRequestPojo {

    @SerializedName("eat_id")
    @Expose
    private Integer eatId;
    @SerializedName("kitchen_id")
    @Expose
    private Integer kitchenId;
    @SerializedName("kitchen_Name")
    @Expose
    private String kitchenName;
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

        @SerializedName("kitchen_Name")
        @Expose
        private String kitchenName;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("product_quantity")
        @Expose
        private Integer productQuantity;
        @SerializedName("product_type")
        @Expose
        private String productType;
        @SerializedName("product_Name")
        @Expose
        private String productName;
        @SerializedName("product_price")
        @Expose
        private Integer productPrice;
        @SerializedName("product_image")
        @Expose
        private String productImage;

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

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(Integer productQuantity) {
            this.productQuantity = productQuantity;
        }

        public String getProductType() {
            return productType;
        }

        public void setProductType(String productType) {
            this.productType = productType;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(Integer productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

    }
}