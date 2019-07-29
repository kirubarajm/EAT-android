package com.tovo.eat.ui.home.homemenu.story;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StoriesResponse implements Serializable {

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

    public static class Result implements Serializable {

        @SerializedName("storyid")
        @Expose
        public Integer storyid;
        @SerializedName("thumb")
        @Expose
        public String thumb;
        @SerializedName("thumb_title")
        @Expose
        public String thumbTitle;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("seen")
        @Expose
        public boolean seen;
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("story_img")
        @Expose
        public String storyImg;
        @SerializedName("duration")
        @Expose
        public Integer duration;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;
        @SerializedName("stories")
        @Expose
        public List<Story> stories = null;

        public Integer getDuration() {
            return duration;
        }

        public void setDuration(Integer duration) {
            this.duration = duration;
        }

        public boolean isSeen() {
            return seen;
        }

        public void setSeen(boolean seen) {
            this.seen = seen;
        }

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStoryImg() {
            return storyImg;
        }

        public void setStoryImg(String storyImg) {
            this.storyImg = storyImg;
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

        public List<Story> getStories() {
            return stories;
        }

        public void setStories(List<Story> stories) {
            this.stories = stories;
        }

        public static class Story implements Serializable {

            @SerializedName("id")
            @Expose
            public Integer id;
            @SerializedName("mediatype")
            @Expose
            public Integer mediatype;
            @SerializedName("url")
            @Expose
            public String url;
            @SerializedName("storyid")
            @Expose
            public Integer storyid;
            @SerializedName("pos")
            @Expose
            public String pos;
            @SerializedName("duration")
            @Expose
            public Integer duration;
            @SerializedName("title")
            @Expose
            public String title;
            @SerializedName("subtitle")
            @Expose
            public String subtitle;
            @SerializedName("cat_type")
            @Expose
            public Integer   catType;
            @SerializedName("cat_ids")
            @Expose
            public Integer catIds;
            @SerializedName("created_at")
            @Expose
            public String createdAt;
            @SerializedName("updated_at")
            @Expose
            public String updatedAt;


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

            public String getPos() {
                return pos;
            }

            public void setPos(String pos) {
                this.pos = pos;
            }

            public Integer getDuration() {
                return duration*1000;
            }

            public void setDuration(Integer duration) {
                this.duration = duration;
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

            public Integer getCatIds() {
                return catIds;
            }

            public void setCatIds(Integer catIds) {
                this.catIds = catIds;
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

    }
}
