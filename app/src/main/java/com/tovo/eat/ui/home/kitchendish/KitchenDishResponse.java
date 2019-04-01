package com.tovo.eat.ui.home.kitchendish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public class Productlist {

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
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg;
        @SerializedName("favid")
        @Expose
        private String favid;
        @SerializedName("productlist")
        @Expose
        private List<Productlist> productlist = null;

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

        public Object getFavid() {
            return favid;
        }

        public void setFavid(String favid) {
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
