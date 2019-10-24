package com.tovo.eat.ui.cart.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponCheckRequest {

    @SerializedName("coupon_name")
    @Expose
    private String couponName;
    @SerializedName("userid")
    @Expose
    private Long userid;


    public CouponCheckRequest(Long userid,String couponName) {
        this.couponName = couponName;
        this.userid = userid;
    }
 public CouponCheckRequest(Long userid) {
        this.userid = userid;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

}