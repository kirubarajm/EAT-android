package com.tovo.eat.ui.home.homemenu.kitchen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KitchenFavRequest {

    @SerializedName("eatuserid")
    @Expose
    private String eatuserid;
    @SerializedName("makeit_userid")
    @Expose
    private String makeit_userid;


    public KitchenFavRequest(String eatuserid, String makeit_userid) {
        this.eatuserid = eatuserid;
        this.makeit_userid = makeit_userid;
    }


    public String getEatuserid() {
        return eatuserid;
    }

    public void setEatuserid(String eatuserid) {
        this.eatuserid = eatuserid;
    }

    public String getMakeit_userid() {
        return makeit_userid;
    }

    public void setMakeit_userid(String makeit_userid) {
        this.makeit_userid = makeit_userid;
    }
}