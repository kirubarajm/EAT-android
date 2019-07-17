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

    public Boolean getStatus() {
        return status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result implements Serializable {

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
        @SerializedName("description")
        @Expose
        public String description;
        @SerializedName("story_img")
        @Expose
        public String storyImg;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;
        @SerializedName("stories")
        @Expose
        public List<Story> stories = null;


        public void setStoryid(Integer storyid) {
            this.storyid = storyid;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public void setThumbTitle(String thumbTitle) {
            this.thumbTitle = thumbTitle;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setStoryImg(String storyImg) {
            this.storyImg = storyImg;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }

        public void setStories(List<Story> stories) {
            this.stories = stories;
        }

        public Integer getStoryid() {
            return storyid;
        }

        public String getThumb() {
            return thumb;
        }

        public String getThumbTitle() {
            return thumbTitle;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getStoryImg() {
            return storyImg;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public List<Story> getStories() {
            return stories;
        }

        public class Story implements Serializable {

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
            @SerializedName("created_at")
            @Expose
            public String createdAt;
            @SerializedName("updated_at")
            @Expose
            public Object updatedAt;

            public void setId(Integer id) {
                this.id = id;
            }

            public void setMediatype(Integer mediatype) {
                this.mediatype = mediatype;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setStoryid(Integer storyid) {
                this.storyid = storyid;
            }

            public void setPos(String pos) {
                this.pos = pos;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public void setUpdatedAt(Object updatedAt) {
                this.updatedAt = updatedAt;
            }

            public Integer getId() {
                return id;
            }

            public Integer getMediatype() {
                return mediatype;
            }

            public String getUrl() {
                return url;
            }

            public Integer getStoryid() {
                return storyid;
            }

            public String getPos() {
                return pos;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public Object getUpdatedAt() {
                return updatedAt;
            }
        }

    }

}
