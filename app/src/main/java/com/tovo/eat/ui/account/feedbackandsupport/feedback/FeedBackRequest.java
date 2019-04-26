package com.tovo.eat.ui.account.feedbackandsupport.feedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBackRequest {


    @SerializedName("rating")
    @Expose
    public Integer rating;
    @SerializedName("userid")
    @Expose
    public Integer userid;
    @SerializedName("content")
    @Expose
    public String content;


    public FeedBackRequest(Integer rating, Integer userid, String content) {
        this.rating = rating;
        this.userid = userid;
        this.content = content;
    }


    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
