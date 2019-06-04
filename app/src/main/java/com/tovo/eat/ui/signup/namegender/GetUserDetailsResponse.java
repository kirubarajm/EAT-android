package com.tovo.eat.ui.signup.namegender;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserDetailsResponse {

    @SerializedName("success")
    @Expose
    public Boolean success;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
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
        @SerializedName("Locality")
        @Expose
        private Object locality;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("virtualkey")
        @Expose
        private Integer virtualkey;
        @SerializedName("gender")
        @Expose
        private Integer gender;
        @SerializedName("regionname")
        @Expose
        private String regionname;

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

        public Object getLocality() {
            return locality;
        }

        public void setLocality(Object locality) {
            this.locality = locality;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getVirtualkey() {
            return virtualkey;
        }

        public void setVirtualkey(Integer virtualkey) {
            this.virtualkey = virtualkey;
        }

        public Integer getGender() {
            return gender;
        }

        public void setGender(Integer gender) {
            this.gender = gender;
        }

        public String getRegionname() {
            return regionname;
        }

        public void setRegionname(String regionname) {
            this.regionname = regionname;
        }
    }
}
