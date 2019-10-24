package com.tovo.eat.ui.cart;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartPageResponse {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Amountdetails {

        @SerializedName("grandtotal")
        @Expose
        private Integer grandtotal;
        @SerializedName("original_price")
        @Expose
        private Integer originalPrice;
        @SerializedName("refund_balance")
        @Expose
        private Integer refundBalance;
        @SerializedName("gstcharge")
        @Expose
        private Integer gstcharge;
        @SerializedName("grandtotaltitle")
        @Expose
        private String grandTotalTitle;
        @SerializedName("delivery_charge")
        @Expose
        private Integer deliveryCharge;
        @SerializedName("refund_coupon_adjustment")
        @Expose
        private Integer refundCouponAdjustment;
        @SerializedName("product_orginal_price")
        @Expose
        private Integer productOrginalPrice;
        @SerializedName("totalamount")
        @Expose
        private Integer totalamount;
        @SerializedName("couponstatus")
        @Expose
        private Boolean couponstatus;
        @SerializedName("refundcouponstatus")
        @Expose
        private Boolean refundcouponstatus;
        @SerializedName("coupon_discount_amount")
        @Expose
        private Integer couponDiscountAmount;

        public String getGrandTotalTitle() {
            return grandTotalTitle;
        }

        public void setGrandTotalTitle(String grandTotalTitle) {
            this.grandTotalTitle = grandTotalTitle;
        }

        public Integer getGrandtotal() {
            return grandtotal;
        }


        public Integer getRefundBalance() {
            return refundBalance;
        }


        public Integer getGstcharge() {
            return gstcharge;
        }


        public Integer getDeliveryCharge() {
            return deliveryCharge;
        }


        public Integer getRefundCouponAdjustment() {
            return refundCouponAdjustment;
        }


        public Integer getProductOrginalPrice() {
            return productOrginalPrice;
        }


        public Boolean getCouponstatus() {
            return couponstatus;
        }


        public Boolean getRefundcouponstatus() {
            return refundcouponstatus;
        }


        public Integer getCouponDiscountAmount() {
            return couponDiscountAmount;
        }
    }

    public class Cuisine {

        @SerializedName("cid")
        @Expose
        private Integer cid;
        @SerializedName("cuisineid")
        @Expose
        private Integer cuisineid;
        @SerializedName("cuisinename")
        @Expose
        private String cuisinename;

        public Integer getCid() {
            return cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public String getCuisinename() {
            return cuisinename;
        }
    }

    public class Item {

        @SerializedName("makeit_userid")
        @Expose
        private Long makeitUserid;
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("prod_desc")
        @Expose
        private String prodDesc;
        @SerializedName("preparetime")
        @Expose
        private Object preparetime;
        @SerializedName("vegtype")
        @Expose
        private String vegtype;
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
        @SerializedName("cuisine")
        @Expose
        private Integer cuisine;
        @SerializedName("delete_status")
        @Expose
        private Integer deleteStatus;
        @SerializedName("approved_status")
        @Expose
        private Integer approvedStatus;
        @SerializedName("approved_time")
        @Expose
        private Object approvedTime;
        @SerializedName("approvedby")
        @Expose
        private Object approvedby;
        @SerializedName("clusterid")
        @Expose
        private Integer clusterid;
        @SerializedName("approval_status")
        @Expose
        private Object approvalStatus;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("cuisinename")
        @Expose
        private String cuisinename;
        @SerializedName("availablity")
        @Expose
        private Boolean availablity;
        @SerializedName("amount")
        @Expose
        private Integer amount;
        @SerializedName("cartquantity")
        @Expose
        private Integer cartquantity;

        public String getProdDesc() {
            return prodDesc;
        }

        public void setProdDesc(String prodDesc) {
            this.prodDesc = prodDesc;
        }

        public Long getMakeitUserid() {
            return makeitUserid;
        }

        public void setMakeitUserid(Long makeitUserid) {
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

        public Object getPreparetime() {
            return preparetime;
        }

        public void setPreparetime(Object preparetime) {
            this.preparetime = preparetime;
        }

        public String getVegtype() {
            return vegtype;
        }

        public void setVegtype(String vegtype) {
            this.vegtype = vegtype;
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

        public Integer getCuisine() {
            return cuisine;
        }

        public void setCuisine(Integer cuisine) {
            this.cuisine = cuisine;
        }

        public Integer getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(Integer deleteStatus) {
            this.deleteStatus = deleteStatus;
        }

        public Integer getApprovedStatus() {
            return approvedStatus;
        }

        public void setApprovedStatus(Integer approvedStatus) {
            this.approvedStatus = approvedStatus;
        }

        public Object getApprovedTime() {
            return approvedTime;
        }

        public void setApprovedTime(Object approvedTime) {
            this.approvedTime = approvedTime;
        }

        public Object getApprovedby() {
            return approvedby;
        }

        public void setApprovedby(Object approvedby) {
            this.approvedby = approvedby;
        }

        public Integer getClusterid() {
            return clusterid;
        }

        public void setClusterid(Integer clusterid) {
            this.clusterid = clusterid;
        }

        public Object getApprovalStatus() {
            return approvalStatus;
        }

        public void setApprovalStatus(Object approvalStatus) {
            this.approvalStatus = approvalStatus;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCuisinename() {
            return cuisinename;
        }

        public void setCuisinename(String cuisinename) {
            this.cuisinename = cuisinename;
        }

        public Boolean getAvailablity() {
            return availablity;
        }

        public void setAvailablity(Boolean availablity) {
            this.availablity = availablity;
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
        private Long makeituserid;
        @SerializedName("makeitusername")
        @Expose
        private String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        private String makeitbrandname;
        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("localityname")
        @Expose
        private String localityname;
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg;
        @SerializedName("isfav")
        @Expose
        private String isFav = "0";

        @SerializedName("first_tunnel")
        @Expose
        private  Integer funnelStatus = 0;

        @SerializedName("favid")
        @Expose
        private Integer favid;
        @SerializedName("cuisines")
        @Expose
        private List<Cuisine> cuisines = null;
        @SerializedName("amountdetails")
        @Expose
        private Amountdetails amountdetails;
        @SerializedName("item")
        @Expose
        private List<Item> item = null;
        @SerializedName("cartdetails")
        @Expose
        private List<Cartdetail> cartDetails = null;
        @SerializedName("ordercount")
        @Expose
        private Integer ordercount;
        @SerializedName("isAvaliablekitchen")
        @Expose
        private boolean isAvaliablekitchen;

        public boolean isAvaliablekitchen() {
            return isAvaliablekitchen;
        }

        public void setAvaliablekitchen(boolean avaliablekitchen) {
            isAvaliablekitchen = avaliablekitchen;
        }

        public Integer getFunnelStatus() {
            return funnelStatus;
        }

        public void setFunnelStatus(Integer funnelStatus) {
            this.funnelStatus = funnelStatus;
        }

        public String getIsFav() {
            return isFav;
        }

        public void setIsFav(String isFav) {
            this.isFav = isFav;
        }

        public List<Cartdetail> getCartDetails() {
            return cartDetails;
        }

        public void setCartDetails(List<Cartdetail> cartDetails) {
            this.cartDetails = cartDetails;
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

        public Integer getRegionid() {
            return regionid;
        }

        public void setRegionid(Integer regionid) {
            this.regionid = regionid;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public String getLocalityname() {
            return localityname;
        }

        public void setLocalityname(String localityname) {
            this.localityname = localityname;
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

        public List<Cuisine> getCuisines() {
            return cuisines;
        }

        public void setCuisines(List<Cuisine> cuisines) {
            this.cuisines = cuisines;
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

        public Integer getOrdercount() {
            return ordercount;
        }

        public void setOrdercount(Integer ordercount) {
            this.ordercount = ordercount;
        }

    }

    public class Cartdetail {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("charges")
        @Expose
        private Integer charges;
        @SerializedName("status")
        @Expose
        private Boolean status;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getCharges() {
            return charges;
        }

        public void setCharges(Integer charges) {
            this.charges = charges;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

    }
}