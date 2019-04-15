package com.tovo.eat.ui.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderPlaceRequestPojo {

    @SerializedName("userid")
    @Expose
    private Integer userid;
    @SerializedName("locality")
    @Expose
    private String locality;
    @SerializedName("delivery_charge")
    @Expose
    private String deliveryCharge;
    @SerializedName("ordertype")
    @Expose
    private Integer ordertype;
    @SerializedName("orderstatus")
    @Expose
    private Integer orderstatus;
    @SerializedName("gst")
    @Expose
    private Double gst;
    @SerializedName("payment_type")
    @Expose
    private Integer paymentType;
    @SerializedName("lock_status")
    @Expose
    private Integer lockStatus;
    @SerializedName("makeit_user_id")
    @Expose
    private Integer makeitUserId;
    @SerializedName("payment_status")
    @Expose
    private Integer paymentStatus;
    @SerializedName("cus_lat")
    @Expose
    private String cusLat;
    @SerializedName("cus_lon")
    @Expose
    private String cusLon;
    @SerializedName("cus_address")
    @Expose
    private String cusAddress;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("orderitems")
    @Expose
    private List<Orderitem> orderitems = null;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    public Integer getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Integer orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Double getGst() {
        return gst;
    }

    public void setGst(Double gst) {
        this.gst = gst;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    public Integer getMakeitUserId() {
        return makeitUserId;
    }

    public void setMakeitUserId(Integer makeitUserId) {
        this.makeitUserId = makeitUserId;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getCusLat() {
        return cusLat;
    }

    public void setCusLat(String cusLat) {
        this.cusLat = cusLat;
    }

    public String getCusLon() {
        return cusLon;
    }

    public void setCusLon(String cusLon) {
        this.cusLon = cusLon;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Orderitem> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<Orderitem> orderitems) {
        this.orderitems = orderitems;
    }


    public class Orderitem {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("price")
        @Expose
        private Integer price;

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

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

    }


}