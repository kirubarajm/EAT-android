package com.tovo.eat.ui.signup.namegender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TokenRequest {

@SerializedName("userid")
@Expose
private Integer userid;
@SerializedName("pushid_android")
@Expose
private String pushidAndroid;


    public TokenRequest(Integer userid, String pushidAndroid) {
        this.userid = userid;
        this.pushidAndroid = pushidAndroid;
    }

    public Integer getUserid() {
return userid;
}

public void setUserid(Integer userid) {
this.userid = userid;
}

public String getPushidAndroid() {
return pushidAndroid;
}

public void setPushidAndroid(String pushidAndroid) {
this.pushidAndroid = pushidAndroid;
}

}