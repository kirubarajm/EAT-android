package com.tovo.eat.ui.signup.opt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OtpRequest {


    @SerializedName("phoneno")
    @Expose
    public String phoneno;
    @SerializedName("otp")
    @Expose
    public Integer otp;
    @SerializedName("oid")
    @Expose
    public Integer oid;

    public OtpRequest(String phoneno, Integer otp, Integer oid) {
        this.phoneno = phoneno;
        this.otp = otp;
        this.oid = oid;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public Integer getOtp() {
        return otp;
    }

    public void setOtp(Integer otp) {
        this.otp = otp;
    }

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }
}
