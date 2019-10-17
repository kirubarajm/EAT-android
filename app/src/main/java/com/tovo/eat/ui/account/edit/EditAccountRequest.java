package com.tovo.eat.ui.account.edit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditAccountRequest {

    @SerializedName("userid")
    @Expose
    public Long userid;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("gender")
    @Expose
    public Integer gender;

    @SerializedName("regionid")
    @Expose
    public Integer regionId;


    public EditAccountRequest(Long userid, String name, String email, Integer gender, Integer regionId) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.regionId = regionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
