package com.tovo.eat.ui.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartPageResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;


    @SerializedName("message")
    @Expose
    private String message;



    @SerializedName("status")
    @Expose
    private Boolean status;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

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

    public class Amountdetails {

        @SerializedName("grandtotal")
        @Expose
        private Double grandtotal;
        @SerializedName("gstcharge")
        @Expose
        private Double gstcharge;
        @SerializedName("totalamount")
        @Expose
        private Integer totalamount;
        @SerializedName("delivery_charge")
        @Expose
        private Integer deliveryCharge;


        public Double getGrandtotal() {
            return grandtotal;
        }

        public void setGrandtotal(Double grandtotal) {
            this.grandtotal = grandtotal;
        }

        public Double getGstcharge() {
            return gstcharge;
        }

        public void setGstcharge(Double gstcharge) {
            this.gstcharge = gstcharge;
        }

        public Integer getTotalamount() {
            return totalamount;
        }

        public void setTotalamount(Integer totalamount) {
            this.totalamount = totalamount;
        }

        public Integer getDeliveryCharge() {
            return deliveryCharge;
        }

        public void setDeliveryCharge(Integer deliveryCharge) {
            this.deliveryCharge = deliveryCharge;
        }

    }

    public static class Item {

        @SerializedName("makeit_userid")
        @Expose
        private Integer makeitUserid;
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("active_status")
        @Expose
        private String activeStatus;
        @SerializedName("preparetime")
        @Expose
        private String preparetime;
        @SerializedName("vegtype")
        @Expose
        private String vegtype;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("breakfast")
        @Expose
        private Integer breakfast;
        @SerializedName("lunch")
        @Expose
        private Integer lunch;
        @SerializedName("dinner")
        @Expose
        private Integer dinner;
        @SerializedName("monday")
        @Expose
        private Integer monday;
        @SerializedName("tuesday")
        @Expose
        private Integer tuesday;
        @SerializedName("wednesday")
        @Expose
        private Integer wednesday;
        @SerializedName("thrusday")
        @Expose
        private Integer thrusday;
        @SerializedName("friday")
        @Expose
        private Integer friday;
        @SerializedName("saturday")
        @Expose
        private Integer saturday;
        @SerializedName("sunday")
        @Expose
        private Integer sunday;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("cusine")
        @Expose
        private Integer cusine;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("cartquantity")
        @Expose
        private Integer cartquantity;

        @SerializedName("availablity")
        @Expose
        private boolean availablity;

        public boolean isAvailablity() {
            return availablity;
        }

        public void setAvailablity(boolean availablity) {
            this.availablity = availablity;
        }



        public Integer getMakeitUserid() {
            return makeitUserid;
        }

        public void setMakeitUserid(Integer makeitUserid) {
            this.makeitUserid = makeitUserid;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getPreparetime() {
            return preparetime;
        }

        public void setPreparetime(String preparetime) {
            this.preparetime = preparetime;
        }

        public String getVegtype() {
            return vegtype;
        }

        public void setVegtype(String vegtype) {
            this.vegtype = vegtype;
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

        public Integer getBreakfast() {
            return breakfast;
        }

        public void setBreakfast(Integer breakfast) {
            this.breakfast = breakfast;
        }

        public Integer getLunch() {
            return lunch;
        }

        public void setLunch(Integer lunch) {
            this.lunch = lunch;
        }

        public Integer getDinner() {
            return dinner;
        }

        public void setDinner(Integer dinner) {
            this.dinner = dinner;
        }

        public Integer getMonday() {
            return monday;
        }

        public void setMonday(Integer monday) {
            this.monday = monday;
        }

        public Integer getTuesday() {
            return tuesday;
        }

        public void setTuesday(Integer tuesday) {
            this.tuesday = tuesday;
        }

        public Integer getWednesday() {
            return wednesday;
        }

        public void setWednesday(Integer wednesday) {
            this.wednesday = wednesday;
        }

        public Integer getThrusday() {
            return thrusday;
        }

        public void setThrusday(Integer thrusday) {
            this.thrusday = thrusday;
        }

        public Integer getFriday() {
            return friday;
        }

        public void setFriday(Integer friday) {
            this.friday = friday;
        }

        public Integer getSaturday() {
            return saturday;
        }

        public void setSaturday(Integer saturday) {
            this.saturday = saturday;
        }

        public Integer getSunday() {
            return sunday;
        }

        public void setSunday(Integer sunday) {
            this.sunday = sunday;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public Integer getCusine() {
            return cusine;
        }

        public void setCusine(Integer cusine) {
            this.cusine = cusine;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public Integer getCartquantity() {
            return cartquantity;
        }

        public void setCartquantity(Integer cartquantity) {
            this.cartquantity = cartquantity;
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
        private Integer favid;
        @SerializedName("amountdetails")
        @Expose
        private Amountdetails amountdetails;
        @SerializedName("item")
        @Expose
        private List<Item> item = null;

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

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public Amountdetails getAmountdetails() {
            return amountdetails;
        }

        public void setAmountdetails(Amountdetails amountdetails) {
            this.amountdetails = amountdetails;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }

    }

}