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
        public Integer makeituserid;
        @SerializedName("makeitusername")
        @Expose
        public String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        public String makeitbrandname;
        @SerializedName("rating")
        @Expose
        public Float  rating;
        @SerializedName("regionid")
        @Expose
        public Integer regionid;
        @SerializedName("localityname")
        @Expose
        public String localityname;
        @SerializedName("regionname")
        @Expose
        public String regionname;
        @SerializedName("costfortwo")
        @Expose
        public Integer costfortwo;
        @SerializedName("makeitimg")
        @Expose
        public String makeitimg;
        @SerializedName("about")
        @Expose
        public String about;
        @SerializedName("member_type")
        @Expose
        public Object memberType;
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
        public List<Object> kitchensignature = null;

        @Expose
        public List<Foodbadge> foodbadge = null;
        @SerializedName("foodbadge")


        public Integer getMakeituserid() {
            return makeituserid;
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

        public List<Object> getKitchensignature() {
            return kitchensignature;
        }

        public List<Foodbadge> getFoodbadge() {
            return foodbadge;
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
        @SerializedName("product_name")
        @Expose
        public String productName;
        @SerializedName("productimage")
        @Expose
        public String productimage;

        @SerializedName("makeit_userid")
        @Expose
        public Integer makeitUserid;

        public Integer getMakeitUserid() {
            return makeitUserid;
        }

        public Integer getFavid() {
            return favid;
        }

        public Integer getIsfav() {
            return isfav;
        }

        public Integer getPrice() {
            return price;
        }

        public String getVegtype() {
            return vegtype;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public Integer getProductid() {
            return productid;
        }

        public String getCuisinename() {
            return cuisinename;
        }

        public String getProductName() {
            return productName;
        }

        public String getProductimage() {
            return productimage;
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

        @SerializedName("badges")
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
