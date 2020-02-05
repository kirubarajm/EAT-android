package com.tovo.eat.ui.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveOrderResponsePojo {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status=false;
    @SerializedName("orderdetails")
    @Expose
    private List<Orderdetail> orderdetails = null;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Orderdetail> getOrderdetails() {
        return orderdetails;
    }

    public void setOrderdetails(List<Orderdetail> orderdetails) {
        this.orderdetails = orderdetails;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("orderid")
        @Expose
        private Long orderid=0L;
        @SerializedName("ordertime")
        @Expose
        private String ordertime;
        @SerializedName("orderstatus")
        @Expose
        private Integer orderstatus;
        @SerializedName("onlinepaymentstatus")
        @Expose
        private boolean onlinePaymentStatus;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("makeituserid")
        @Expose
        private Long makeituserid;
        @SerializedName("moveit_user_id")
        @Expose
        private Integer moveitUserId;
        @SerializedName("makeitusername")
        @Expose
        private String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        private String makeitbrandname;
        @SerializedName("makeitimage")
        @Expose
        private String makeitimage;
        @SerializedName("distance")
        @Expose
        private String distance;


        @SerializedName("delivery_vendor")
        @Expose
        private Integer deliveryVendor=0;

        @SerializedName("items")
        @Expose
        private List<Item> items = null;
        @SerializedName("deliverytime")
        @Expose
        private String deliverytime;
        @SerializedName("eta")
        @Expose
        private String eta;

        public Integer getDeliveryVendor() {
            return deliveryVendor;
        }

        public void setDeliveryVendor(Integer deliveryVendor) {
            this.deliveryVendor = deliveryVendor;
        }

        public Integer getMoveitUserId() {
            return moveitUserId;
        }

        public void setMoveitUserId(Integer moveitUserId) {
            this.moveitUserId = moveitUserId;
        }

        public boolean isOnlinePaymentStatus() {
            return onlinePaymentStatus;
        }

        public void setOnlinePaymentStatus(boolean onlinePaymentStatus) {
            this.onlinePaymentStatus = onlinePaymentStatus;
        }

        public Long getOrderid() {
            return orderid;
        }

        public void setOrderid(Long orderid) {
            this.orderid = orderid;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public Integer getOrderstatus() {
            return orderstatus;
        }

        public void setOrderstatus(Integer orderstatus) {
            this.orderstatus = orderstatus;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Long getMakeituserid() {
            return makeituserid;
        }

        public void setMakeituserid(Long makeituserid) {
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

        public String getMakeitimage() {
            return makeitimage;
        }

        public void setMakeitimage(String makeitimage) {
            this.makeitimage = makeitimage;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public String getDeliverytime() {
            return deliverytime;
        }

        public void setDeliverytime(String deliverytime) {
            this.deliverytime = deliverytime;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

    }


    public class Item {

        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("product_name")
        @Expose
        private String productName;

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
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

    }


    public class Orderdetail {

        @SerializedName("orderid")
        @Expose
        private Long orderid;
        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("ordertime")
        @Expose
        private String ordertime;
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
        private Integer gst;
        @SerializedName("coupon")
        @Expose
        private Object coupon;
        @SerializedName("payment_type")
        @Expose
        private String paymentType;
        @SerializedName("makeit_user_id")
        @Expose
        private Integer makeitUserId;
        @SerializedName("moveit_user_id")
        @Expose
        private Integer moveitUserId;
        @SerializedName("cus_lat")
        @Expose
        private String cusLat;
        @SerializedName("cus_lon")
        @Expose
        private String cusLon;
        @SerializedName("cus_address")
        @Expose
        private String cusAddress;
        @SerializedName("makeit_status")
        @Expose
        private String makeitStatus;
        @SerializedName("moveit_reached_time")
        @Expose
        private Object moveitReachedTime;
        @SerializedName("moveit_expected_delivered_time")
        @Expose
        private Object moveitExpectedDeliveredTime;
        @SerializedName("moveit_actual_delivered_time")
        @Expose
        private Object moveitActualDeliveredTime;
        @SerializedName("moveit_remarks_order")
        @Expose
        private Object moveitRemarksOrder;
        @SerializedName("makeit_expected_preparing_time")
        @Expose
        private Object makeitExpectedPreparingTime;
        @SerializedName("makeit_actual_preparing_time")
        @Expose
        private Object makeitActualPreparingTime;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("payment_status")
        @Expose
        private Integer paymentStatus;
        @SerializedName("lock_status")
        @Expose
        private Integer lockStatus;
        @SerializedName("order_assigned_time")
        @Expose
        private Object orderAssignedTime;
        @SerializedName("transactionid")
        @Expose
        private Object transactionid;
        @SerializedName("transaction_status")
        @Expose
        private Object transactionStatus;
        @SerializedName("transaction_time")
        @Expose
        private Object transactionTime;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("brandname")
        @Expose
        private String brandname;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("rating")
        @Expose
        private Boolean rating;
        @SerializedName("showrating")
        @Expose
        private Boolean showRating;

        public Boolean getShowRating() {
            return showRating;
        }

        public void setShowRating(Boolean showRating) {
            this.showRating = showRating;
        }

        public String getBrandname() {
            return brandname;
        }

        public void setBrandname(String brandname) {
            this.brandname = brandname;
        }

        public Long getOrderid() {
            return orderid;
        }

        public void setOrderid(Long orderid) {
            this.orderid = orderid;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
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

        public Integer getGst() {
            return gst;
        }

        public void setGst(Integer gst) {
            this.gst = gst;
        }

        public Object getCoupon() {
            return coupon;
        }

        public void setCoupon(Object coupon) {
            this.coupon = coupon;
        }

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public Integer getMakeitUserId() {
            return makeitUserId;
        }

        public void setMakeitUserId(Integer makeitUserId) {
            this.makeitUserId = makeitUserId;
        }

        public Integer getMoveitUserId() {
            return moveitUserId;
        }

        public void setMoveitUserId(Integer moveitUserId) {
            this.moveitUserId = moveitUserId;
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

        public String getMakeitStatus() {
            return makeitStatus;
        }

        public void setMakeitStatus(String makeitStatus) {
            this.makeitStatus = makeitStatus;
        }

        public Object getMoveitReachedTime() {
            return moveitReachedTime;
        }

        public void setMoveitReachedTime(Object moveitReachedTime) {
            this.moveitReachedTime = moveitReachedTime;
        }

        public Object getMoveitExpectedDeliveredTime() {
            return moveitExpectedDeliveredTime;
        }

        public void setMoveitExpectedDeliveredTime(Object moveitExpectedDeliveredTime) {
            this.moveitExpectedDeliveredTime = moveitExpectedDeliveredTime;
        }

        public Object getMoveitActualDeliveredTime() {
            return moveitActualDeliveredTime;
        }

        public void setMoveitActualDeliveredTime(Object moveitActualDeliveredTime) {
            this.moveitActualDeliveredTime = moveitActualDeliveredTime;
        }

        public Object getMoveitRemarksOrder() {
            return moveitRemarksOrder;
        }

        public void setMoveitRemarksOrder(Object moveitRemarksOrder) {
            this.moveitRemarksOrder = moveitRemarksOrder;
        }

        public Object getMakeitExpectedPreparingTime() {
            return makeitExpectedPreparingTime;
        }

        public void setMakeitExpectedPreparingTime(Object makeitExpectedPreparingTime) {
            this.makeitExpectedPreparingTime = makeitExpectedPreparingTime;
        }

        public Object getMakeitActualPreparingTime() {
            return makeitActualPreparingTime;
        }

        public void setMakeitActualPreparingTime(Object makeitActualPreparingTime) {
            this.makeitActualPreparingTime = makeitActualPreparingTime;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public Integer getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(Integer paymentStatus) {
            this.paymentStatus = paymentStatus;
        }

        public Integer getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(Integer lockStatus) {
            this.lockStatus = lockStatus;
        }

        public Object getOrderAssignedTime() {
            return orderAssignedTime;
        }

        public void setOrderAssignedTime(Object orderAssignedTime) {
            this.orderAssignedTime = orderAssignedTime;
        }

        public Object getTransactionid() {
            return transactionid;
        }

        public void setTransactionid(Object transactionid) {
            this.transactionid = transactionid;
        }

        public Object getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(Object transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        public Object getTransactionTime() {
            return transactionTime;
        }

        public void setTransactionTime(Object transactionTime) {
            this.transactionTime = transactionTime;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Boolean getRating() {
            return rating;
        }

        public void setRating(Boolean rating) {
            this.rating = rating;
        }

    }

}