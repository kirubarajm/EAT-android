package com.tovo.eat.ui.account.feedbackandsupport.feedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBackResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("faqid")
    @Expose
    public Integer faqid;


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getFaqid() {
        return faqid;
    }

    public void setFaqid(Integer faqid) {
        this.faqid = faqid;
    }
}
