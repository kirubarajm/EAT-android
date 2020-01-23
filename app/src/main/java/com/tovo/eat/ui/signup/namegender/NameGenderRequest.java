package com.tovo.eat.ui.signup.namegender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NameGenderRequest {

    @SerializedName("userid")
    @Expose
    public Long userid;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("gender")
    @Expose
    public Integer gender;

    @SerializedName("regionid")
    @Expose
    public Integer regionId;

    @SerializedName("referredby")
    @Expose
    public String  referredby;
    @SerializedName("other_region")
    @Expose
    public String otherRegion;

    @SerializedName("hometownid")
    @Expose
    public Integer hometownId;

    @SerializedName("other_hometown")
    @Expose
    public String otherHometown;



    public String getReferredby() {
        return referredby;
    }

    public void setReferredby(String referredby) {
        this.referredby = referredby;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public NameGenderRequest(Long userid, String name, Integer gender) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
    }

    public NameGenderRequest(Long userid, String name, Integer gender, Integer hometownId,String referredby,String otherHometown) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.hometownId = hometownId;
        this.referredby=referredby;
        this.otherHometown=otherHometown;
    }

    public NameGenderRequest(Long userid, String name, Integer gender, Integer hometownId,String otherHometown) {
        this.userid = userid;
        this.name = name;
        this.gender = gender;
        this.hometownId = hometownId;
        this.otherHometown=otherHometown;
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
