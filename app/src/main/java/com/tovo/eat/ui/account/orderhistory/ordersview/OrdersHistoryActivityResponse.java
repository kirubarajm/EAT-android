package com.tovo.eat.ui.account.orderhistory.ordersview;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tovo.eat.ui.cart.CartPageResponse;

import java.util.List;

public class OrdersHistoryActivityResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;


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

    public class Result {

        @SerializedName("orderid")
        @Expose
        public Integer orderid;
        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("ordertime")
        @Expose
        public String ordertime;
        @SerializedName("locality")
        @Expose
        public String locality;
        @SerializedName("delivery_charge")
        @Expose
        public String deliveryCharge;
        @SerializedName("ordertype")
        @Expose
        public Integer ordertype;
        @SerializedName("orderstatus")
        @Expose
        public Integer orderstatus;
        @SerializedName("gst")
        @Expose
        public Integer gst;
        @SerializedName("coupon")
        @Expose
        public String coupon;
        @SerializedName("payment_type")
        @Expose
        public String paymentType;
        @SerializedName("makeit_user_id")
        @Expose
        public Integer makeitUserId;
        @SerializedName("moveit_user_id")
        @Expose
        public Integer moveitUserId;
        @SerializedName("cus_lat")
        @Expose
        public String cusLat;
        @SerializedName("cus_lon")
        @Expose
        public String cusLon;
        @SerializedName("cus_address")
        @Expose
        public String cusAddress;
        @SerializedName("makeit_status")
        @Expose
        public String makeitStatus;
        @SerializedName("moveit_reached_time")
        @Expose
        public String moveitReachedTime;
        @SerializedName("moveit_expected_delivered_time")
        @Expose
        public String moveitExpectedDeliveredTime;
        @SerializedName("moveit_actual_delivered_time")
        @Expose
        public String moveitActualDeliveredTime;
        @SerializedName("moveit_remarks_order")
        @Expose
        public String moveitRemarksOrder;
        @SerializedName("makeit_expected_preparing_time")
        @Expose
        public String makeitExpectedPreparingTime;
        @SerializedName("makeit_actual_preparing_time")
        @Expose
        public String makeitActualPreparingTime;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("payment_status")
        @Expose
        public Integer paymentStatus;
        @SerializedName("lock_status")
        @Expose
        public String lockStatus;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;
        @SerializedName("address_title")
        @Expose
        public String address_title;
        @SerializedName("payment_type_name")
        @Expose
        public String paymentTypeName;
        @SerializedName("order_assigned_time")
        @Expose
        public String orderAssignedTime;
        @SerializedName("userdetail")
        @Expose
        public Userdetail userdetail;
        @SerializedName("makeitdetail")
        @Expose
        public Makeitdetail makeitdetail;
        @SerializedName("moveitdetail")
        @Expose
        public Moveitdetail moveitdetail;
        @SerializedName("items")
        @Expose
        public List<Item> items = null;
        @SerializedName("cartdetails")
        @Expose
        private List<CartPageResponse.Cartdetail> cartDetails = null;

        public List<CartPageResponse.Cartdetail> getCartDetails() {
            return cartDetails;
        }

        public void setCartDetails(List<CartPageResponse.Cartdetail> cartDetails) {
            this.cartDetails = cartDetails;
        }

        public String getPaymentTypeName() {
            return paymentTypeName;
        }

        public void setPaymentTypeName(String paymentTypeName) {
            this.paymentTypeName = paymentTypeName;
        }

        public String getAddress_title() {
            return address_title;
        }

        public void setAddress_title(String address_title) {
            this.address_title = address_title;
        }

        public Integer getOrderid() {
            return orderid;
        }

        public void setOrderid(Integer orderid) {
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

        public String getCoupon() {
            return coupon;
        }

        public void setCoupon(String coupon) {
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

        public String getMoveitReachedTime() {
            return moveitReachedTime;
        }

        public void setMoveitReachedTime(String moveitReachedTime) {
            this.moveitReachedTime = moveitReachedTime;
        }

        public String getMoveitExpectedDeliveredTime() {
            return moveitExpectedDeliveredTime;
        }

        public void setMoveitExpectedDeliveredTime(String moveitExpectedDeliveredTime) {
            this.moveitExpectedDeliveredTime = moveitExpectedDeliveredTime;
        }

        public String getMoveitActualDeliveredTime() {
            return moveitActualDeliveredTime;
        }

        public void setMoveitActualDeliveredTime(String moveitActualDeliveredTime) {
            this.moveitActualDeliveredTime = moveitActualDeliveredTime;
        }

        public String getMoveitRemarksOrder() {
            return moveitRemarksOrder;
        }

        public void setMoveitRemarksOrder(String moveitRemarksOrder) {
            this.moveitRemarksOrder = moveitRemarksOrder;
        }

        public String getMakeitExpectedPreparingTime() {
            return makeitExpectedPreparingTime;
        }

        public void setMakeitExpectedPreparingTime(String makeitExpectedPreparingTime) {
            this.makeitExpectedPreparingTime = makeitExpectedPreparingTime;
        }

        public String getMakeitActualPreparingTime() {
            return makeitActualPreparingTime;
        }

        public void setMakeitActualPreparingTime(String makeitActualPreparingTime) {
            this.makeitActualPreparingTime = makeitActualPreparingTime;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        public String getLockStatus() {
            return lockStatus;
        }

        public void setLockStatus(String lockStatus) {
            this.lockStatus = lockStatus;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getOrderAssignedTime() {
            return orderAssignedTime;
        }

        public void setOrderAssignedTime(String orderAssignedTime) {
            this.orderAssignedTime = orderAssignedTime;
        }

        public Userdetail getUserdetail() {
            return userdetail;
        }

        public void setUserdetail(Userdetail userdetail) {
            this.userdetail = userdetail;
        }

        public Makeitdetail getMakeitdetail() {
            return makeitdetail;
        }

        public void setMakeitdetail(Makeitdetail makeitdetail) {
            this.makeitdetail = makeitdetail;
        }

        public Moveitdetail getMoveitdetail() {
            return moveitdetail;
        }

        public void setMoveitdetail(Moveitdetail moveitdetail) {
            this.moveitdetail = moveitdetail;
        }

        public List<Item> getItems() {
            return items;
        }

        public void setItems(List<Item> items) {
            this.items = items;
        }

        public class Userdetail {

            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("email")
            @Expose
            public String email;
            @SerializedName("userid")
            @Expose
            public Integer userid;
            @SerializedName("phoneno")
            @Expose
            public String phoneno;
            @SerializedName("locality")
            @Expose
            public Object locality;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Integer getUserid() {
                return userid;
            }

            public void setUserid(Integer userid) {
                this.userid = userid;
            }

            public String getPhoneno() {
                return phoneno;
            }

            public void setPhoneno(String phoneno) {
                this.phoneno = phoneno;
            }

            public Object getLocality() {
                return locality;
            }

            public void setLocality(Object locality) {
                this.locality = locality;
            }
        }

        public class Moveitdetail {

            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("email")
            @Expose
            public String email;
            @SerializedName("userid")
            @Expose
            public Integer userid;
            @SerializedName("phoneno")
            @Expose
            public String phoneno;
            @SerializedName("Vehicle_no")
            @Expose
            public Object vehicleNo;
            @SerializedName("localityid")
            @Expose
            public Integer localityid;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Integer getUserid() {
                return userid;
            }

            public void setUserid(Integer userid) {
                this.userid = userid;
            }

            public String getPhoneno() {
                return phoneno;
            }

            public void setPhoneno(String phoneno) {
                this.phoneno = phoneno;
            }

            public Object getVehicleNo() {
                return vehicleNo;
            }

            public void setVehicleNo(Object vehicleNo) {
                this.vehicleNo = vehicleNo;
            }

            public Integer getLocalityid() {
                return localityid;
            }

            public void setLocalityid(Integer localityid) {
                this.localityid = localityid;
            }
        }

        public class Makeitdetail {

            @SerializedName("lat")
            @Expose
            public Float lat;
            @SerializedName("lon")
            @Expose
            public Float lon;
            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("email")
            @Expose
            public String email;
            @SerializedName("userid")
            @Expose
            public Integer userid;
            @SerializedName("address")
            @Expose
            public String address;
            @SerializedName("phoneno")
            @Expose
            public String phoneno;
            @SerializedName("brandName")
            @Expose
            public String brandName;
            @SerializedName("makeitimg")
            @Expose
            public String makeitimg;
            @SerializedName("localityid")
            @Expose
            public Integer localityid;

            public String getMakeitimg() {
                return makeitimg;
            }

            public void setMakeitimg(String makeitimg) {
                this.makeitimg = makeitimg;
            }

            public Float getLat() {
                return lat;
            }

            public void setLat(Float lat) {
                this.lat = lat;
            }

            public Float getLon() {
                return lon;
            }

            public void setLon(Float lon) {
                this.lon = lon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public Integer getUserid() {
                return userid;
            }

            public void setUserid(Integer userid) {
                this.userid = userid;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhoneno() {
                return phoneno;
            }

            public void setPhoneno(String phoneno) {
                this.phoneno = phoneno;
            }

            public String getBrandName() {
                return brandName;
            }

            public void setBrandName(String brandName) {
                this.brandName = brandName;
            }

            public Integer getLocalityid() {
                return localityid;
            }

            public void setLocalityid(Integer localityid) {
                this.localityid = localityid;
            }
        }

        public class Item {

            @SerializedName("gst")
            @Expose
            public Integer gst;
            @SerializedName("price")
            @Expose
            public Integer price;
            @SerializedName("quantity")
            @Expose
            public Integer quantity;
            @SerializedName("productid")
            @Expose
            public Integer productid;
            @SerializedName("product_name")
            @Expose
            public String productName;
            @SerializedName("vegtype")
            @Expose
            public String vegtype;


            public String getVegtype() {
                return vegtype;
            }

            public void setVegtype(String vegtype) {
                this.vegtype = vegtype;
            }

            public Integer getGst() {
                return gst;
            }

            public void setGst(Integer gst) {
                this.gst = gst;
            }

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
    }
}
