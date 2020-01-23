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

    @SerializedName("hometownid")
    @Expose
    public Integer hometownId;

    @SerializedName("other_region")
    @Expose
    public String otherRegion;
    @SerializedName("other_hometown")
    @Expose
    public String otherHometown;

    public EditAccountRequest(Long userid, String name, String email, Integer gender, Integer hometownId,String otherHometown) {
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.hometownId = hometownId;
        this.otherHometown = otherHometown;
    }


    public Integer getHometownId() {
        return hometownId;
    }

    public void setHometownId(Integer hometownId) {
        this.hometownId = hometownId;
    }

    public String getOtherHometown() {
        return otherHometown;
    }

    public void setOtherHometown(String otherHometown) {
        this.otherHometown = otherHometown;
    }

    public String getOtherRegion() {
        return otherRegion;
    }

    public void setOtherRegion(String otherRegion) {
        this.otherRegion = otherRegion;
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
