package com.tovo.eat.ui.signup.opt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

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

    public class Result {

        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("phoneno")
        @Expose
        public String phoneno;
        @SerializedName("referalcode")
        @Expose
        public Object referalcode;
        @SerializedName("Locality")
        @Expose
        public Object locality;
        @SerializedName("gender")
        @Expose
        public Integer gender;


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

        public Object getReferalcode() {
            return referalcode;
        }

        public void setReferalcode(Object referalcode) {
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
    }


}
