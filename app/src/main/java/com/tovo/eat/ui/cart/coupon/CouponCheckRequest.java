package com.tovo.eat.ui.cart.coupon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CouponCheckRequest {

    @SerializedName("coupon_name")
    @Expose
    private String couponName;
    @SerializedName("userid")
    @Expose
    private Integer userid;


    public CouponCheckRequest(Integer userid,String couponName) {
        this.couponName = couponName;
        this.userid = userid;
    }
 public CouponCheckRequest(Integer userid) {
        this.userid = userid;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}