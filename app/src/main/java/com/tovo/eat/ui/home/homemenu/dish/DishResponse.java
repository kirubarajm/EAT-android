package com.tovo.eat.ui.home.homemenu.dish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tovo.eat.data.DataManager;

import java.util.List;

public class DishResponse {

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
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("makeit_userid")
        @Expose
        private Integer makeitUserid;
        @SerializedName("makeit_username")
        @Expose
        private String makeitUsername;
        @SerializedName("brandname")
        @Expose
        private String brandname;
        @SerializedName("makeit_image")
        @Expose
        private String makeitImage;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("producttype")
        @Expose
        private String producttype;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("favid")
        @Expose
        private Integer favid;
        @SerializedName("isfav")
        @Expose
        private String isfav;
        @SerializedName("cusinename")
        @Expose
        private String cusinename;
        @SerializedName("localityname")
        @Expose
        private String localityname;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("eta")
        @Expose
        private String eta;

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

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

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public String getMakeitImage() {
            return makeitImage;
        }

        public void setMakeitImage(String makeitImage) {
            this.makeitImage = makeitImage;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
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

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
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

        public String getCusinename() {
            return cusinename;
        }

        public void setCusinename(String cusinename) {
            this.cusinename = cusinename;
        }

        public String getLocalityname() {
            return localityname;
        }

        public void setLocalityname(String localityname) {
            this.localityname = localityname;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

    }
}
