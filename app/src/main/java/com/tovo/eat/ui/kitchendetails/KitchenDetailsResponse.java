package com.tovo.eat.ui.kitchendetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KitchenDetailsResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
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

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class KitchenPage {

        @SerializedName("header_content")
        @Expose
        private String headerContent;
        @SerializedName("header_icon_url")
        @Expose
        private String headerIconUrl;
        @SerializedName("header_color_code")
        @Expose
        private String headerColorCode;

        public String getHeaderContent() {
            return headerContent;
        }

        public void setHeaderContent(String headerContent) {
            this.headerContent = headerContent;
        }

        public String getHeaderIconUrl() {
            return headerIconUrl;
        }

        public void setHeaderIconUrl(String headerIconUrl) {
            this.headerIconUrl = headerIconUrl;
        }

        public String getHeaderColorCode() {
            return headerColorCode;
        }

        public void setHeaderColorCode(String headerColorCode) {
            this.headerColorCode = headerColorCode;
        }

    }

    public class Product {

        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("type")
        @Expose
        private Integer type;
        @SerializedName("product_list")
        @Expose
        private List<ProductList> productList = null;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public List<ProductList> getProductList() {
            return productList;
        }

        public void setProductList(List<ProductList> productList) {
            this.productList = productList;
        }

    }

    public class ProductList {

        @SerializedName("makeit_userid")
        @Expose
        private Long makeitUserid;
        @SerializedName("productid")
        @Expose
        private Integer productid;
        @SerializedName("product_tag")
        @Expose
        private Integer product_tag;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("product_name")
        @Expose
        private String productName;
        @SerializedName("productimage")
        @Expose
        private String productimage;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("vegtype")
        @Expose
        private String vegtype;
        @SerializedName("prod_desc")
        @Expose
        private String prodDesc;
        @SerializedName("isfav")
        @Expose
        private Integer isfav;
        @SerializedName("favid")
        @Expose
        private Integer favid;
        @SerializedName("cuisinename")
        @Expose
        private String cuisinename;
        @SerializedName("next_available")
        @Expose
        private Integer nextAvailable;
        @SerializedName("next_available_time")
        @Expose
        private String nextAvailableTime;

        @SerializedName("serviceablestatus")
        @Expose
        private boolean serviceablestatus;

        public Integer getProduct_tag() {
            return product_tag;
        }

        public void setProduct_tag(Integer product_tag) {
            this.product_tag = product_tag;
        }

        public boolean isServiceablestatus() {
            return serviceablestatus;
        }

        public void setServiceablestatus(boolean serviceablestatus) {
            this.serviceablestatus = serviceablestatus;
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

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getVegtype() {
            return vegtype;
        }

        public void setVegtype(String vegtype) {
            this.vegtype = vegtype;
        }

        public String getProdDesc() {
            return prodDesc;
        }

        public void setProdDesc(String prodDesc) {
            this.prodDesc = prodDesc;
        }

        public Integer getIsfav() {
            return isfav;
        }

        public void setIsfav(Integer isfav) {
            this.isfav = isfav;
        }

        public Integer getFavid() {
            return favid;
        }

        public void setFavid(Integer favid) {
            this.favid = favid;
        }

        public String getCuisinename() {
            return cuisinename;
        }

        public void setCuisinename(String cuisinename) {
            this.cuisinename = cuisinename;
        }

        public boolean getNextAvailable() {
            if (nextAvailable==1){
                return true;
            }else {
                return false;
            }

        }

        public void setNextAvailable(Integer nextAvailable) {
            this.nextAvailable = nextAvailable;
        }

        public String getNextAvailableTime() {
            return nextAvailableTime;
        }

        public void setNextAvailableTime(String nextAvailableTime) {
            this.nextAvailableTime = nextAvailableTime;
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
        @SerializedName("member_type")
        @Expose
        private Integer memberType;
        @SerializedName("regionid")
        @Expose
        private Integer regionid;
        @SerializedName("localityname")
        @Expose
        private String localityname;
        @SerializedName("regionname")
        @Expose
        private String regionname;
        @SerializedName("makeitimg")
        @Expose
        private String makeitimg;

        @SerializedName("kitchen_page_header_content1")
        @Expose
        private String kitchen_page_header_content1;

        @SerializedName("kitchen_page_header_content2")
        @Expose
        private String kitchen_page_header_content2;



        @SerializedName("favid")
        @Expose
        private Integer favid;
        @SerializedName("isfav")
        @Expose
        private String isfav;
        @SerializedName("serviceablestatus")
        @Expose
        private Boolean serviceablestatus;
        @SerializedName("product")
        @Expose
        private List<Product> product = null;
        @SerializedName("kitchen_page")
        @Expose
        private List<KitchenPage> kitchenPage = null;


        public String getKitchen_page_header_content1() {
            return kitchen_page_header_content1;
        }

        public void setKitchen_page_header_content1(String kitchen_page_header_content1) {
            this.kitchen_page_header_content1 = kitchen_page_header_content1;
        }

        public String getKitchen_page_header_content2() {
            return kitchen_page_header_content2;
        }

        public void setKitchen_page_header_content2(String kitchen_page_header_content2) {
            this.kitchen_page_header_content2 = kitchen_page_header_content2;
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

        public Integer getRegionid() {
            return regionid;
        }

        public void setRegionid(Integer regionid) {
            this.regionid = regionid;
        }

        public String getLocalityname() {
            return localityname;
        }

        public void setLocalityname(String localityname) {
            this.localityname = localityname;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
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

        public String getIsfav() {
            return isfav;
        }

        public void setIsfav(String isfav) {
            this.isfav = isfav;
        }

        public Boolean getServiceablestatus() {
            return serviceablestatus;
        }

        public void setServiceablestatus(Boolean serviceablestatus) {
            this.serviceablestatus = serviceablestatus;
        }

        public List<Product> getProduct() {
            return product;
        }

        public void setProduct(List<Product> product) {
            this.product = product;
        }

        public List<KitchenPage> getKitchenPage() {
            return kitchenPage;
        }

        public void setKitchenPage(List<KitchenPage> kitchenPage) {
            this.kitchenPage = kitchenPage;
        }

    }
}