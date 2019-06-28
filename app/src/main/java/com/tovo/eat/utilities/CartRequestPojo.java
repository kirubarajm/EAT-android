package com.tovo.eat.utilities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartRequestPojo {

    @SerializedName("makeit_user_id")
    @Expose
    private Integer makeitUserid;
    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("rcid")
    @Expose
    private Integer rcid;

    @SerializedName("orderitems")
    @Expose
    private List<Cartitem> cartitems = null;


    public Integer getRcid() {
        return rcid;
    }

    public void setRcid(Integer rcid) {
        this.rcid = rcid;
    }





    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getMakeitUserid() {
        return makeitUserid;
    }

    public void setMakeitUserid(Integer makeitUserid) {
        this.makeitUserid = makeitUserid;
    }

    public List<Cartitem> getCartitems() {
        return cartitems;
    }

    public void setCartitems(List<Cartitem> cartitems) {
        this.cartitems = cartitems;
    }


    public static class Cartitem {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;

        @SerializedName("price")
        @Expose
        private Integer price;



        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getProductid() {
            return productid;
        }

        public void setProductid(Integer productid) {
            this.productid = productid;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

    }

}