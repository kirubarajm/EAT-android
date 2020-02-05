package com.tovo.eat.ui.home.ad.bottom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SusiAravind on 04,February,2020
 */
public class PromotionResponse {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private Result result;

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

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }


    public class Result {

        @SerializedName("show_status")
        @Expose
        private Boolean showStatus;
        @SerializedName("content_type")
        @Expose
        private Integer contentType;
        @SerializedName("full_screen")
        @Expose
        private Boolean fullScreen;
        @SerializedName("url")
        @Expose
        private String url;

        public Boolean getShowStatus() {
            return showStatus;
        }

        public void setShowStatus(Boolean showStatus) {
            this.showStatus = showStatus;
        }

        public Integer getContentType() {
            return contentType;
        }

        public void setContentType(Integer contentType) {
            this.contentType = contentType;
        }

        public Boolean getFullScreen() {
            return fullScreen;
        }

        public void setFullScreen(Boolean fullScreen) {
            this.fullScreen = fullScreen;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

}
