package com.tovo.eat.ui.home.homemenu.story;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class StoriesResponse {

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

        @SerializedName("thumbnail")
        @Expose
        public String thumbnail;
        @SerializedName("stories")
        @Expose
        public List<Story> stories = null;
        @SerializedName("url")
        @Expose
        public String url;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("link")
        @Expose
        public Integer link;
        @SerializedName("title")
        @Expose
        public String title;
        @SerializedName("description")
        @Expose
        public String description;

        public String getThumbnail() {
            return thumbnail;
        }

        public List<Story> getStories() {
            return stories;
        }

        public String getUrl() {
            return url;
        }

        public Integer getType() {
            return type;
        }

        public Integer getLink() {
            return link;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public class Story {

            @SerializedName("url")
            @Expose
            public String url;
            @SerializedName("type")
            @Expose
            public Integer type;
            @SerializedName("link")
            @Expose
            public Integer link;
            @SerializedName("title")
            @Expose
            public String title;
            @SerializedName("description")
            @Expose
            public String description;

            public String getUrl() {
                return url;
            }

            public Integer getType() {
                return type;
            }

            public Integer getLink() {
                return link;
            }

            public String getTitle() {
                return title;
            }

            public String getDescription() {
                return description;
            }
        }

    }
}
