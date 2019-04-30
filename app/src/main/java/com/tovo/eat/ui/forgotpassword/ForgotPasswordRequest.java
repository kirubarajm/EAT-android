package com.tovo.eat.ui.forgotpassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordRequest {

    @SerializedName("phoneno")
    @Expose
    public String phoneno;

    public ForgotPasswordRequest(String phoneno) {
        this.phoneno = phoneno;
    }

}
