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
