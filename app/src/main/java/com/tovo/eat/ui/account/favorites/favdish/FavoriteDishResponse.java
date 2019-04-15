package com.tovo.eat.ui.account.favorites.favdish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FavoriteDishResponse {

    @SerializedName("success")
    @Expose
    public String success;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
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
        public Integer productid;
        @SerializedName("makeit_userid")
        @Expose
        public Integer makeitUserid;
        @SerializedName("makeit_username")
        @Expose
        public String makeitUsername;
        @SerializedName("brandname")
        @Expose
        public String brandname;
        @SerializedName("makeit_image")
        @Expose
        public String makeitImage;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("image")
        @Expose
        public String image;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("producttype")
        @Expose
        public String producttype;
        @SerializedName("quantity")
        @Expose
        public Integer quantity;
        @SerializedName("favid")
        @Expose
        public Integer favid;
        @SerializedName("isfav")
        @Expose
        public String isfav;
        @SerializedName("cusinename")
        @Expose
        public String cusinename;
        @SerializedName("localityname")
        @Expose
        public String localityname;


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
    }
}
