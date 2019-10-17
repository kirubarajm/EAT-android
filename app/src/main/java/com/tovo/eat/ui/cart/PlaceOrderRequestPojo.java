package com.tovo.eat.ui.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceOrderRequestPojo {
    @SerializedName("userid")
    @Expose
    private Long userid;
    @SerializedName("ordertype")
    @Expose
    private Integer ordertype;
    @SerializedName("payment_type")
    @Expose
    private Integer paymentType;
    @SerializedName("makeit_user_id")
    @Expose
    private Long makeitUserId;
    @SerializedName("payment_status")
    @Expose
    private Integer paymentStatus;
    @SerializedName("address_type")
    @Expose
    private Integer addressType;

    @SerializedName("aid")
    @Expose
    private Long aid;

    @SerializedName("rcid")
    @Expose
    private Integer rcid;
  @SerializedName("cid")
    @Expose
    private Integer cid;

    @SerializedName("orderitems")
    @Expose
    private List<Orderitem> orderitems = null;


    public PlaceOrderRequestPojo() {
    }

    public PlaceOrderRequestPojo(Long userid,  Long makeitUserid, Integer paymentType, Long aid,Integer rcid,Integer cid, List<Orderitem> orderitems) {
        this.userid = userid;
        this.makeitUserId = makeitUserid;
        this.paymentType = paymentType;
        this.aid = aid;
        this.orderitems = orderitems;
        this.rcid=rcid;
        this.cid=cid;
    }


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getRcid() {
        return rcid;
    }

    public void setRcid(Integer rcid) {
        this.rcid = rcid;
    }

    public Long getAid() {
        return aid;
    }

    public void setAid(Long aid) {
        this.aid = aid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getOrdertype() {
        return ordertype;
    }

    public void setOrdertype(Integer ordertype) {
        this.ordertype = ordertype;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public Long getMakeitUserId() {
        return makeitUserId;
    }

    public void setMakeitUserId(Long makeitUserId) {
        this.makeitUserId = makeitUserId;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getAddressType() {
        return addressType;
    }

    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    public List<Orderitem> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<Orderitem> orderitems) {
        this.orderitems = orderitems;
    }

    public static class Orderitem {

        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;


        public Orderitem() {

        }


        public Orderitem(Integer productid, Integer quantity) {
            this.productid = productid;
            this.quantity = quantity;
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