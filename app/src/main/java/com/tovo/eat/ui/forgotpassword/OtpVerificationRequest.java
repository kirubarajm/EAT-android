package com.tovo.eat.ui.forgotpassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpVerificationRequest {
    @SerializedName("phoneno")
    @Expose
    public String phoneno;
    @SerializedName("otp")
    @Expose
    public Integer otp;
    @SerializedName("oid")
    @Expose
    public Integer oid;


    public OtpVerificationRequest(String phoneno, Integer otp, Integer oid) {
        this.phoneno = phoneno;
        this.otp = otp;
        this.oid = oid;
    }
}
