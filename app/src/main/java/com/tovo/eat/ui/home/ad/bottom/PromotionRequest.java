package com.tovo.eat.ui.home.ad.bottom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromotionRequest {


    @SerializedName("userid")
    @Expose
    public Long userId;

    public PromotionRequest(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
