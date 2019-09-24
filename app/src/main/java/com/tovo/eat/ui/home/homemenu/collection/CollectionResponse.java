package com.tovo.eat.ui.home.homemenu.collection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CollectionResponse {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("collection")
    @Expose
    private List<Collection> collection = null;

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


    public class Collection {

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

    }
}
