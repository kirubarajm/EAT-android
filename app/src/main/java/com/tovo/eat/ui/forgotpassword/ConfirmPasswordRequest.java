package com.tovo.eat.ui.forgotpassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ConfirmPasswordRequest {
    @SerializedName("userid")
    @Expose
    public Integer userid;
    @SerializedName("password")
    @Expose
    public String password;

    public ConfirmPasswordRequest(Integer userid, String password) {
        this.userid = userid;
        this.password = password;
    }
}
