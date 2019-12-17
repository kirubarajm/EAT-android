package com.tovo.eat.ui.signup.namegender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NameGenderResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("result")
    @Expose
    private List<Result> result = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class Result {

        @SerializedName("userid")
        @Expose
        private Integer userid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("phoneno")
        @Expose
        private String phoneno;
        @SerializedName("referalcode")
        @Expose
        private String referalcode;
        @SerializedName("Locality")
        @Expose
        private Object locality;
        @SerializedName("gender")
        @Expose
        private Integer gender;
        @SerializedName("virtualkey")
        @Expose
        private Integer virtualkey;
        @SerializedName("regionid")
        @Expose
        private Integer regionid;

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
        }

        public String getReferalcode() {
            return referalcode;
        }

        public void setReferalcode(String referalcode) {
            this.referalcode = referalcode;
        }

        public Object getLocality() {
            return locality;
        }

        public void setLocality(Object locality) {
            this.locality = locality;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public Integer getVirtualkey() {
            return virtualkey;
        }

        public void setVirtualkey(Integer virtualkey) {
            this.virtualkey = virtualkey;
        }

        public Integer getRegionid() {
            return regionid;
        }

        public void setRegionid(Integer regionid) {
            this.regionid = regionid;
        }

    }
}
