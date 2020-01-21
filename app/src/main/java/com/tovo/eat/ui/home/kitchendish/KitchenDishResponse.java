package com.tovo.eat.ui.home.kitchendish;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KitchenDishResponse {
    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public Boolean getStatus() {
        return status;
    }

    public List<Result> getResult() {
        return result;
    }

    public static class Result {

        @SerializedName("makeituserid")
        @Expose
        public Long makeituserid;
        @SerializedName("makeitusername")
        @Expose
        public String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        public String makeitbrandname;
        @SerializedName("rating")
        @Expose
        public Float rating;
        @SerializedName("regionid")
        @Expose
        public Integer regionid;
        @SerializedName("localityname")
        @Expose
        public String localityname;
        @SerializedName("serviceablestatus")
        @Expose
        public boolean serviceableStatus;
        @SerializedName("regionname")
        @Expose
        public String regionname;
        @SerializedName("costfortwo")
        @Expose
        public Integer costfortwo;
        @SerializedName("makeitimg")
        @Expose
        public String makeitimg;
        @SerializedName("member_type_icon")
        @Expose
        public String memberTypeIcon;
        @SerializedName("member_type_name")
        @Expose
        public String memberTypeName;
        @SerializedName("about")
        @Expose
        public String about;
        @SerializedName("member_type")
        @Expose
        public Integer memberType;
        @SerializedName("locality")
        @Expose
        public Object locality;
        @SerializedName("favid")
        @Expose
        public Integer favid;
        @SerializedName("isfav")
        @Expose
        public String isfav;
        @SerializedName("distance")
        @Expose
        public String distance;
        @SerializedName("productlist")
        @Expose
        public List<Productlist> productlist = null;
        @SerializedName("eta")
        @Expose
        public String eta;
        @SerializedName("specialitems")
        @Expose
        public List<Specialitem> specialitems = null;
        @SerializedName("kitcheninfoimage")
        @Expose
        public List<Kitcheninfoimage> kitcheninfoimage = null;
        @SerializedName("kitchenmenuimage")
        @Expose
        public List<Kitchenmenuimage> kitchenmenuimage = null;
        @SerializedName("kitchensignature")
        @Expose
        public String kitchensignature;
        @SerializedName("foodbadge")
        @Expose
        public List<Foodbadge> foodbadge = null;
        @SerializedName("rating_count")
        @Expose
        private Integer ratingCount;

        public String getMemberTypeIcon() {
            return memberTypeIcon;
        }

        public void setMemberTypeIcon(String memberTypeIcon) {
            this.memberTypeIcon = memberTypeIcon;
        }

        public String getMemberTypeName() {
            return memberTypeName;
        }

        public void setMemberTypeName(String memberTypeName) {
            this.memberTypeName = memberTypeName;
        }

        public boolean isServiceableStatus() {
            return serviceableStatus;
        }

        public void setServiceableStatus(boolean serviceableStatus) {
            this.serviceableStatus = serviceableStatus;
        }

        public Integer getRatingCount() {
            return ratingCount;
        }

        public void setRatingCount(Integer ratingCount) {
            this.ratingCount = ratingCount;
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

        public String getMakeitbrandname() {
            return makeitbrandname;
        }

        public Float getRating() {
            return rating;
        }

        public Integer getRegionid() {
            return regionid;
        }

        public String getLocalityname() {
            return localityname;
        }

        public String getRegionname() {
            return regionname;
        }

        public Integer getCostfortwo() {
            return costfortwo;
        }

        public String getMakeitimg() {
            return makeitimg;
        }

        public String getAbout() {
            return about;
        }

        public Object getMemberType() {
            return memberType;
        }

        public Object getLocality() {
            return locality;
        }

        public Integer getFavid() {
            return favid;
        }

        public String getIsfav() {
            return isfav;
        }

        public String getDistance() {
            return distance;
        }

        public List<Productlist> getProductlist() {
            return productlist;
        }

        public String getEta() {
            return eta;
        }

        public List<Specialitem> getSpecialitems() {
            return specialitems;
        }

        public List<Kitcheninfoimage> getKitcheninfoimage() {
            return kitcheninfoimage;
        }

        public List<Kitchenmenuimage> getKitchenmenuimage() {
            return kitchenmenuimage;
        }

        public String getKitchensignature() {
            return kitchensignature;
        }

        public List<Foodbadge> getFoodbadge() {
            return foodbadge;
        }
    }

    public static class Kitchenmenuimage {

        @SerializedName("img_url")
        @Expose
        public String imgUrl;
        @SerializedName("type")
        @Expose
        public Integer type;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }

    public static class Foodbadge {

        @SerializedName("url")
        @Expose
        public String badges;
        @SerializedName("id")
        @Expose
        public Integer id;

        public String getBadges() {
            return badges;
        }

        public void setBadges(String badges) {
            this.badges = badges;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }

    public class Productlist {

        @SerializedName("favid")
        @Expose
        public Integer favid;
        @SerializedName("isfav")
        @Expose
        public Integer isfav;
        @SerializedName("price")
        @Expose
        public Integer price;
        @SerializedName("vegtype")
        @Expose
        public String vegtype;
        @SerializedName("quantity")
        @Expose
        public Integer quantity;
        @SerializedName("productid")
        @Expose
        public Integer productid;
        @SerializedName("cuisinename")
        @Expose
        public String cuisinename;
        @SerializedName("prod_desc")
        @Expose
        public String prodDesc;
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("productimage")
        @Expose
        public String productimage;
        @SerializedName("next_available_time")
        @Expose
        public String nextAvailableTime;

        @SerializedName("makeit_userid")
        @Expose
        public Long makeitUserid;
        @SerializedName("next_available")
        @Expose
        public Integer nextAvailable = 0;

        public String getNextAvailableTime() {
            return nextAvailableTime;
        }

        public void setNextAvailableTime(String nextAvailableTime) {
            this.nextAvailableTime = nextAvailableTime;
        }

        public Boolean getNextAvailable() {

            if (nextAvailable != null) {

                return nextAvailable == 1;
            }
            return false;

        }

        public void setNextAvailable(Integer nextAvailable) {
            this.nextAvailable = nextAvailable;
        }

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

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public Integer getIsfav() {
            return isfav;
        }

        public void setIsfav(Integer isfav) {
            this.isfav = isfav;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getVegtype() {
            return vegtype;
        }

        public void setVegtype(String vegtype) {
            this.vegtype = vegtype;
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

        public String getCuisinename() {
            return cuisinename;
        }

        public void setCuisinename(String cuisinename) {
            this.cuisinename = cuisinename;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductimage() {
            return productimage;
        }

        public void setProductimage(String productimage) {
            this.productimage = productimage;
        }
    }

    public class Kitcheninfoimage {

        @SerializedName("img_url")
        @Expose
        public String imgUrl;
        @SerializedName("type")
        @Expose
        public Integer type;


        public String getImgUrl() {
            return imgUrl;
        }

        public Integer getType() {
            return type;
        }
    }

    public class Specialitem {

        @SerializedName("img_url")
        @Expose
        public String imgUrl;
        @SerializedName("type")
        @Expose
        public Integer type;

        public String getImgUrl() {
            return imgUrl;
        }

        public Integer getType() {
            return type;
        }
    }

}
