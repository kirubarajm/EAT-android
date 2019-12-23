package com.tovo.eat.ui.home.homemenu.kitchen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class KitchenResponse  implements Serializable {
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("zoneId")
    @Expose
    private Integer zoneId;
    @SerializedName("zoneName")
    @Expose
    private String zoneName;
    @SerializedName("kitchencount")
    @Expose
    private Integer kitchencount;
    @SerializedName("pagecount")
    @Expose
    private Integer pagecount = 0;
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

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public Integer getKitchencount() {
        return kitchencount;
    }

    public void setKitchencount(Integer kitchencount) {
        this.kitchencount = kitchencount;
    }

    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class CollectionDetail implements Serializable{

        @SerializedName("zone")
        @Expose
        private Integer zone;
        @SerializedName("makeituserid")
        @Expose
        private Long makeituserid;
        @SerializedName("makeitusername")
        @Expose
        private String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        private String makeitbrandname;
        @SerializedName("member_type")
        @Expose
        private Integer memberType;
        @SerializedName("rating")
        @Expose
        private double rating;
        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("costfortwo")
        @Expose
        private Integer costfortwo;
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg = "";
        @SerializedName("unservicable")
        @Expose
        private Integer unservicable;
        @SerializedName("localityname")
        @Expose
        private Object localityname;
        @SerializedName("virtualkey")
        @Expose
        private Integer virtualkey;
        @SerializedName("favid")
        @Expose
        private Integer favid;
        @SerializedName("isfav")
        @Expose
        private String isfav;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("cuisines")
        @Expose
        private List<Cuisine> cuisines = null;
        @SerializedName("eta")
        @Expose
        private String eta;
        @SerializedName("serviceablestatus")
        @Expose
        private Boolean serviceablestatus;
        @SerializedName("kitchenstatus")
        @Expose
        private Integer kitchenstatus;
        @SerializedName("etatime")
        @Expose
        private Integer etatime;
        @SerializedName("member_type_name")
        @Expose
        private String memberTypeName;
        @SerializedName("member_type_icon")
        @Expose
        private String memberTypeIcon;

        public Integer getZone() {
            return zone;
        }

        public void setZone(Integer zone) {
            this.zone = zone;
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

        public Integer getMemberType() {
            return memberType;
        }

        public void setMemberType(Integer memberType) {
            this.memberType = memberType;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
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

        public Integer getCostfortwo() {
            return costfortwo;
        }

        public void setCostfortwo(Integer costfortwo) {
            this.costfortwo = costfortwo;
        }

        public String getMakeitimg() {
            return makeitimg;
        }

        public void setMakeitimg(String makeitimg) {
            this.makeitimg = makeitimg;
        }

        public Integer getUnservicable() {
            return unservicable;
        }

        public void setUnservicable(Integer unservicable) {
            this.unservicable = unservicable;
        }

        public Object getLocalityname() {
            return localityname;
        }

        public void setLocalityname(Object localityname) {
            this.localityname = localityname;
        }

        public Integer getVirtualkey() {
            return virtualkey;
        }

        public void setVirtualkey(Integer virtualkey) {
            this.virtualkey = virtualkey;
        }

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<Cuisine> getCuisines() {
            return cuisines;
        }

        public void setCuisines(List<Cuisine> cuisines) {
            this.cuisines = cuisines;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

        public Boolean getServiceablestatus() {
            return serviceablestatus;
        }

        public void setServiceablestatus(Boolean serviceablestatus) {
            this.serviceablestatus = serviceablestatus;
        }

        public Integer getKitchenstatus() {
            return kitchenstatus;
        }

        public void setKitchenstatus(Integer kitchenstatus) {
            this.kitchenstatus = kitchenstatus;
        }

        public Integer getEtatime() {
            return etatime;
        }

        public void setEtatime(Integer etatime) {
            this.etatime = etatime;
        }

        public String getMemberTypeName() {
            return memberTypeName;
        }

        public void setMemberTypeName(String memberTypeName) {
            this.memberTypeName = memberTypeName;
        }

        public String getMemberTypeIcon() {
            return memberTypeIcon;
        }

        public void setMemberTypeIcon(String memberTypeIcon) {
            this.memberTypeIcon = memberTypeIcon;
        }

    }

    public class Coupon implements Serializable{

        @SerializedName("cid")
        @Expose
        private Integer cid;
        @SerializedName("coupon_name")
        @Expose
        private String couponName;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("numberoftimes")
        @Expose
        private Integer numberoftimes;
        @SerializedName("maxdiscount")
        @Expose
        private Integer maxdiscount;
        @SerializedName("discount_percent")
        @Expose
        private Integer discountPercent;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("img_url")
        @Expose
        private String imgUrl;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("expiry_date")
        @Expose
        private String expiryDate;
        @SerializedName("minprice_limit")
        @Expose
        private Integer minpriceLimit;
        @SerializedName("couponstatus")
        @Expose
        private Boolean couponstatus;
        @SerializedName("clickable")
        @Expose
        private Boolean clickable=true;

        public Boolean getClickable() {
            return clickable;
        }

        public void setClickable(Boolean clickable) {
            this.clickable = clickable;
        }

        public Integer getCid() {
            return cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public Integer getNumberoftimes() {
            return numberoftimes;
        }

        public void setNumberoftimes(Integer numberoftimes) {
            this.numberoftimes = numberoftimes;
        }

        public Integer getMaxdiscount() {
            return maxdiscount;
        }

        public void setMaxdiscount(Integer maxdiscount) {
            this.maxdiscount = maxdiscount;
        }

        public Integer getDiscountPercent() {
            return discountPercent;
        }

        public void setDiscountPercent(Integer discountPercent) {
            this.discountPercent = discountPercent;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public Integer getMinpriceLimit() {
            return minpriceLimit;
        }

        public void setMinpriceLimit(Integer minpriceLimit) {
            this.minpriceLimit = minpriceLimit;
        }

        public Boolean getCouponstatus() {
            return couponstatus;
        }

        public void setCouponstatus(Boolean couponstatus) {
            this.couponstatus = couponstatus;
        }

    }

    public class Cuisine implements Serializable{

        @SerializedName("cuisineid")
        @Expose
        private Integer cuisineid;
        @SerializedName("cuisinename")
        @Expose
        private String cuisinename;

        public Integer getCuisineid() {
            return cuisineid;
        }

        public void setCuisineid(Integer cuisineid) {
            this.cuisineid = cuisineid;
        }

        public String getCuisinename() {
            return cuisinename;
        }

        public void setCuisinename(String cuisinename) {
            this.cuisinename = cuisinename;
        }

    }

    public class Region implements Serializable{

        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("stateid")
        @Expose
        private Integer stateid;
        @SerializedName("lat")
        @Expose
        private Double lat;
        @SerializedName("lon")
        @Expose
        private Double lon;
        @SerializedName("region_image")
        @Expose
        private String regionImage;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("slider_title")
        @Expose
        private String sliderTitle;
        @SerializedName("slider_content")
        @Expose
        private String sliderContent;
        @SerializedName("region_detail_image")
        @Expose
        private String regionDetailImage;
        @SerializedName("special_dish_img")
        @Expose
        private String specialDishImg;
        @SerializedName("identity_img")
        @Expose
        private String identityImg;
        @SerializedName("region_infinity_img")
        @Expose
        private Object regionInfinityImg;
        @SerializedName("tagline")
        @Expose
        private String tagline;
        @SerializedName("specialities_food_content")
        @Expose
        private String specialitiesFoodContent;
        @SerializedName("active_status")
        @Expose
        private Integer activeStatus;
        @SerializedName("statename")
        @Expose
        private String statename;
        @SerializedName("kitchencount")
        @Expose
        private Integer kitchencount;

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

        public Integer getStateid() {
            return stateid;
        }

        public void setStateid(Integer stateid) {
            this.stateid = stateid;
        }

        public Double getLat() {
            return lat;
        }

        public void setLat(Double lat) {
            this.lat = lat;
        }

        public Double getLon() {
            return lon;
        }

        public void setLon(Double lon) {
            this.lon = lon;
        }

        public String getRegionImage() {
            return regionImage;
        }

        public void setRegionImage(String regionImage) {
            this.regionImage = regionImage;
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

        public String getSliderTitle() {
            return sliderTitle;
        }

        public void setSliderTitle(String sliderTitle) {
            this.sliderTitle = sliderTitle;
        }

        public String getSliderContent() {
            return sliderContent;
        }

        public void setSliderContent(String sliderContent) {
            this.sliderContent = sliderContent;
        }

        public String getRegionDetailImage() {
            return regionDetailImage;
        }

        public void setRegionDetailImage(String regionDetailImage) {
            this.regionDetailImage = regionDetailImage;
        }

        public String getSpecialDishImg() {
            return specialDishImg;
        }

        public void setSpecialDishImg(String specialDishImg) {
            this.specialDishImg = specialDishImg;
        }

        public String getIdentityImg() {
            return identityImg;
        }

        public void setIdentityImg(String identityImg) {
            this.identityImg = identityImg;
        }

        public Object getRegionInfinityImg() {
            return regionInfinityImg;
        }

        public void setRegionInfinityImg(Object regionInfinityImg) {
            this.regionInfinityImg = regionInfinityImg;
        }

        public String getTagline() {
            return tagline;
        }

        public void setTagline(String tagline) {
            this.tagline = tagline;
        }

        public String getSpecialitiesFoodContent() {
            return specialitiesFoodContent;
        }

        public void setSpecialitiesFoodContent(String specialitiesFoodContent) {
            this.specialitiesFoodContent = specialitiesFoodContent;
        }

        public Integer getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(Integer activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getStatename() {
            return statename;
        }

        public void setStatename(String statename) {
            this.statename = statename;
        }


        public Integer getKitchencount() {
            return kitchencount;
        }

        public void setKitchencount(Integer kitchencount) {
            this.kitchencount = kitchencount;
        }

    }

    public class Collection implements Serializable{

        @SerializedName("cid")
        @Expose
        private Integer cid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("active_status")
        @Expose
        private String activeStatus;
        @SerializedName("category")
        @Expose
        private Integer category;
        @SerializedName("img_url")
        @Expose
        private String imgUrl;
        @SerializedName("heading")
        @Expose
        private String heading;
        @SerializedName("subheading")
        @Expose
        private String subheading;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("icon")
        @Expose
        private String icon;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("collectionstatus")
        @Expose
        private Boolean collectionstatus;

        public Integer getCid() {
            return cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(String activeStatus) {
            this.activeStatus = activeStatus;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getSubheading() {
            return subheading;
        }

        public void setSubheading(String subheading) {
            this.subheading = subheading;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Boolean getCollectionstatus() {
            return collectionstatus;
        }

        public void setCollectionstatus(Boolean collectionstatus) {
            this.collectionstatus = collectionstatus;
        }

    }


    public class Story implements Serializable{

        @SerializedName("storyid")
        @Expose
        private Integer storyid;
        @SerializedName("thumb")
        @Expose
        private String thumb;
        @SerializedName("thumb_title")
        @Expose
        private String thumbTitle;
        @SerializedName("title")
        @Expose
        private Object title;
        @SerializedName("description")
        @Expose
        private Object description;
        @SerializedName("story_img")
        @Expose
        private String storyImg;
        @SerializedName("story_big_img")
        @Expose
        private String storyBigImg;
        @SerializedName("first_story_duration")
        @Expose
        private Integer firstStoryDuration;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;
        @SerializedName("stories")
        @Expose
        private List<Stories> stories = null;

        public Integer getStoryid() {
            return storyid;
        }

        public void setStoryid(Integer storyid) {
            this.storyid = storyid;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getThumbTitle() {
            return thumbTitle;
        }

        public void setThumbTitle(String thumbTitle) {
            this.thumbTitle = thumbTitle;
        }

        public Object getTitle() {
            return title;
        }

        public void setTitle(Object title) {
            this.title = title;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
        }

        public String getStoryImg() {
            return storyImg;
        }

        public void setStoryImg(String storyImg) {
            this.storyImg = storyImg;
        }

        public String getStoryBigImg() {
            return storyBigImg;
        }

        public void setStoryBigImg(String storyBigImg) {
            this.storyBigImg = storyBigImg;
        }

        public Integer getFirstStoryDuration() {
            return firstStoryDuration;
        }

        public void setFirstStoryDuration(Integer firstStoryDuration) {
            this.firstStoryDuration = firstStoryDuration;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public List<Stories> getStories() {
            return stories;
        }

        public void setStories(List<Stories> stories) {
            this.stories = stories;
        }


    }

    public class Stories implements Serializable{

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("mediatype")
        @Expose
        private Integer mediatype;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("storyid")
        @Expose
        private Integer storyid;
        @SerializedName("pos")
        @Expose
        private Object pos;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("subtitle")
        @Expose
        private String subtitle;
        @SerializedName("cat_type")
        @Expose
        private Integer catType;
        @SerializedName("cat_ids")
        @Expose
        private Long catIds;
        @SerializedName("duration")
        @Expose
        private Integer duration;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private Object updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getMediatype() {
            return mediatype;
        }

        public void setMediatype(Integer mediatype) {
            this.mediatype = mediatype;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Integer getStoryid() {
            return storyid;
        }

        public void setStoryid(Integer storyid) {
            this.storyid = storyid;
        }

        public Object getPos() {
            return pos;
        }

        public void setPos(Object pos) {
            this.pos = pos;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Integer getCatType() {
            return catType;
        }

        public void setCatType(Integer catType) {
            this.catType = catType;
        }

        public Long getCatIds() {
            return catIds;
        }

        public void setCatIds(Long catIds) {
            this.catIds = catIds;
        }

        public Integer getDuration() {
            return duration*1000;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

    }


    public static class Result implements Serializable{

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("subtitle")
        @Expose
        private String subtitle;
        @SerializedName("type")
        @Expose
        private Integer type = 0;
        @SerializedName("zone")
        @Expose
        private Integer zone;
        @SerializedName("makeituserid")
        @Expose
        private Long makeituserid;
        @SerializedName("makeitusername")
        @Expose
        private String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        private String makeitbrandname;
        @SerializedName("member_type")
        @Expose
        private Integer memberType;
        @SerializedName("rating")
        @Expose
        private double rating = 0.0;
        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("costfortwo")
        @Expose
        private Integer costfortwo;
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg;
        @SerializedName("unservicable")
        @Expose
        private Integer unservicable;
        @SerializedName("localityname")
        @Expose
        private Object localityname;
        @SerializedName("virtualkey")
        @Expose
        private Integer virtualkey;
        @SerializedName("favid")
        @Expose
        private Integer favid;
        @SerializedName("isfav")
        @Expose
        private String isfav;
        @SerializedName("distance")
        @Expose
        private String distance;
        @SerializedName("cuisines")
        @Expose
        private List<KitchenResponse.Cuisine> cuisines = null;
        @SerializedName("eta")
        @Expose
        private String eta;
        @SerializedName("serviceablestatus")
        @Expose
        private Boolean serviceablestatus = true;
        @SerializedName("kitchenstatus")
        @Expose
        private Integer kitchenstatus;
        @SerializedName("etatime")
        @Expose
        private Integer etatime;
        @SerializedName("member_type_name")
        @Expose
        private String memberTypeName;
        @SerializedName("member_type_icon")
        @Expose
        private String memberTypeIcon;
        @SerializedName("collection")
        @Expose
        private List<Collection> collection = null;
        @SerializedName("story")
        @Expose
        private List<Story> story = null;
        @SerializedName("regions")
        @Expose
        private List<Region> regions = null;
        @SerializedName("collection_details")
        @Expose
        private List<KitchenResponse.CollectionDetail> collectionDetails = null;
        @SerializedName("coupon")
        @Expose
        private List<KitchenResponse.Coupon> coupon = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getZone() {
            return zone;
        }

        public void setZone(Integer zone) {
            this.zone = zone;
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

        public Integer getMemberType() {
            return memberType;
        }

        public void setMemberType(Integer memberType) {
            this.memberType = memberType;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
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

        public Integer getCostfortwo() {
            return costfortwo;
        }

        public void setCostfortwo(Integer costfortwo) {
            this.costfortwo = costfortwo;
        }

        public String getMakeitimg() {
            return makeitimg;
        }

        public void setMakeitimg(String makeitimg) {
            this.makeitimg = makeitimg;
        }

        public Integer getUnservicable() {
            return unservicable;
        }

        public void setUnservicable(Integer unservicable) {
            this.unservicable = unservicable;
        }

        public Object getLocalityname() {
            return localityname;
        }

        public void setLocalityname(Object localityname) {
            this.localityname = localityname;
        }

        public Integer getVirtualkey() {
            return virtualkey;
        }

        public void setVirtualkey(Integer virtualkey) {
            this.virtualkey = virtualkey;
        }

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<KitchenResponse.Cuisine> getCuisines() {
            return cuisines;
        }

        public void setCuisines(List<KitchenResponse.Cuisine> cuisines) {
            this.cuisines = cuisines;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

        public Boolean getServiceablestatus() {
            return serviceablestatus;
        }

        public void setServiceablestatus(Boolean serviceablestatus) {
            this.serviceablestatus = serviceablestatus;
        }

        public Integer getKitchenstatus() {
            return kitchenstatus;
        }

        public void setKitchenstatus(Integer kitchenstatus) {
            this.kitchenstatus = kitchenstatus;
        }

        public Integer getEtatime() {
            return etatime;
        }

        public void setEtatime(Integer etatime) {
            this.etatime = etatime;
        }

        public String getMemberTypeName() {
            return memberTypeName;
        }

        public void setMemberTypeName(String memberTypeName) {
            this.memberTypeName = memberTypeName;
        }

        public String getMemberTypeIcon() {
            return memberTypeIcon;
        }

        public void setMemberTypeIcon(String memberTypeIcon) {
            this.memberTypeIcon = memberTypeIcon;
        }

        public List<Collection> getCollection() {
            return collection;
        }

        public void setCollection(List<Collection> collection) {
            this.collection = collection;
        }

        public List<Story> getStory() {
            return story;
        }

        public void setStory(List<Story> story) {
            this.story = story;
        }

        public List<Region> getRegions() {
            return regions;
        }

        public void setRegions(List<Region> regions) {
            this.regions = regions;
        }

        public List<KitchenResponse.CollectionDetail> getCollectionDetails() {
            return collectionDetails;
        }

        public void setCollectionDetails(List<KitchenResponse.CollectionDetail> collectionDetails) {
            this.collectionDetails = collectionDetails;
        }

        public List<KitchenResponse.Coupon> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<KitchenResponse.Coupon> coupon) {
            this.coupon = coupon;
        }

    }
}




































/*public class KitchenResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("kitchencount")
    @Expose
    private int kitchenCount;

    @SerializedName("pagecount")
    @Expose
    private int pageCount;


    @SerializedName("result")
    @Expose
    private List<Result> result = null;


    public int getKitchenCount() {
        return kitchenCount;
    }

    public void setKitchenCount(int kitchenCount) {
        this.kitchenCount = kitchenCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

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

    public static class Result {

        @SerializedName("success")
        @Expose
        private Boolean success;

        @SerializedName("status")
        @Expose
        private Boolean status;

        @SerializedName("makeituserid")
        @Expose
        private Long makeituserid;
        @SerializedName("makeitusername")
        @Expose
        private String makeitusername;
        @SerializedName("makeitbrandname")
        @Expose
        private String makeitbrandname;
        @SerializedName("rating")
        @Expose
        private Double rating;
        @SerializedName("region")
        @Expose
        private Integer region;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("costfortwo")
        @Expose
        private Integer costfortwo;
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg;
        @SerializedName("localityname")
        @Expose
        private String localityname;
        @SerializedName("favid")
        @Expose
        private Integer favid;
        @SerializedName("serviceablestatus")
        @Expose
        private boolean serviceableStatus = true;
        @SerializedName("isfav")
        @Expose
        private String isfav;
        @SerializedName("distance")
        @Expose
        private Double distance;
        @SerializedName("cuisines")
        @Expose
        private List<Cuisine> cuisines = null;
        @SerializedName("collection")
        @Expose
        private List<Collection> collection = null;
        @SerializedName("coupons")
        @Expose
        private List<CouponListResponse.Result> coupons = null;
        @SerializedName("eta")
        @Expose
        private String eta;

        public boolean isServiceableStatus() {
            return serviceableStatus;
        }

        public void setServiceableStatus(boolean serviceableStatus) {
            this.serviceableStatus = serviceableStatus;
        }

        public List<CouponListResponse.Result> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<CouponListResponse.Result> coupons) {
            this.coupons = coupons;
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

        public List<Collection> getCollection() {
            return collection;
        }

        public void setCollection(List<Collection> collection) {
            this.collection = collection;
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

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public Integer getRegion() {
            return region;
        }

        public void setRegion(Integer region) {
            this.region = region;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }

        public Integer getCostfortwo() {
            return costfortwo;
        }

        public void setCostfortwo(Integer costfortwo) {
            this.costfortwo = costfortwo;
        }

        public String getMakeitimg() {
            return makeitimg;
        }

        public void setMakeitimg(String makeitimg) {
            this.makeitimg = makeitimg;
        }

        public String getLocalityname() {
            return localityname;
        }

        public void setLocalityname(String localityname) {
            this.localityname = localityname;
        }

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public Double getDistance() {
            return distance;
        }

        public void setDistance(Double distance) {
            this.distance = distance;
        }

        public List<Cuisine> getCuisines() {
            return cuisines;
        }

        public void setCuisines(List<Cuisine> cuisines) {
            this.cuisines = cuisines;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

    }

    public class Cuisine {

        @SerializedName("cuisineid")
        @Expose
        private Integer cuisineid;
        @SerializedName("cuisinename")
        @Expose
        private String cuisinename;

        public Integer getCuisineid() {
            return cuisineid;
        }

        public void setCuisineid(Integer cuisineid) {
            this.cuisineid = cuisineid;
        }

        public String getCuisinename() {
            return cuisinename;
        }

        public void setCuisinename(String cuisinename) {
            this.cuisinename = cuisinename;
        }

    }

    public class Collection {

        @SerializedName("cid")
        @Expose
        private Integer cid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("active_status")
        @Expose
        private boolean activeStatus;
        @SerializedName("collectionstatus")
        @Expose
        private boolean collectionStatus;
        @SerializedName("query")
        @Expose
        private String query;
        @SerializedName("category")
        @Expose
        private Integer category;
        @SerializedName("img_url")
        @Expose
        private String imgUrl;
        @SerializedName("heading")
        @Expose
        private String heading;
        @SerializedName("subheading")
        @Expose
        private String subheading;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("icon")
        @Expose
        private String icon;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public boolean isCollectionStatus() {
            return collectionStatus;
        }

        public void setCollectionStatus(boolean collectionStatus) {
            this.collectionStatus = collectionStatus;
        }

        public Integer getCid() {
            return cid;
        }

        public void setCid(Integer cid) {
            this.cid = cid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isActiveStatus() {
            return activeStatus;
        }

        public void setActiveStatus(boolean activeStatus) {
            this.activeStatus = activeStatus;
        }

        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getHeading() {
            return heading;
        }

        public void setHeading(String heading) {
            this.heading = heading;
        }

        public String getSubheading() {
            return subheading;
        }

        public void setSubheading(String subheading) {
            this.subheading = subheading;
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
    }

}*/
