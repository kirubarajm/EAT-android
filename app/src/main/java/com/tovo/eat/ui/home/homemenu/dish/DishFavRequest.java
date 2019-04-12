package com.tovo.eat.ui.home.homemenu.dish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DishFavRequest {

    @SerializedName("eatuserid")
    @Expose
    private String eatuserid;
    @SerializedName("productid")
    @Expose
    private String productid;


    public DishFavRequest(String eatuserid, String productid) {
        this.eatuserid = eatuserid;
        this.productid = productid;
    }

    public String getEatuserid() {
        return eatuserid;
    }

    public void setEatuserid(String eatuserid) {
        this.eatuserid = eatuserid;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

}