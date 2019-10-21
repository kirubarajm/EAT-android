package com.tovo.eat.ui.registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationRequest {

    @SerializedName("userid")
    @Expose
    public Long userid;
    @SerializedName("hometownid")
    @Expose
    public Integer hometownid;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("password")
    @Expose
    public String password;

    public RegistrationRequest(Long userid, String email) {
        this.userid = userid;
        this.email = email;
    }

    public Integer getHometownid() {
        return hometownid;
    }

    public void setHometownid(Integer hometownid) {
        this.hometownid = hometownid;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
