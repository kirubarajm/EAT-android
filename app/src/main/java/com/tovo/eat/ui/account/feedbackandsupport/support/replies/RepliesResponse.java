package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RepliesResponse {

    @SerializedName("sucobj")
    @Expose
    public Boolean sucobj;
    @SerializedName("result")
    @Expose
    public List<Result> result = null;

    public Boolean getSucobj() {
        return sucobj;
    }

    public void setSucobj(Boolean sucobj) {
        this.sucobj = sucobj;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("qid")
        @Expose
        public Integer qid;
        @SerializedName("question")
        @Expose
        public String question;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("admin_read")
        @Expose
        public Integer adminRead;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public Object updatedAt;


        public Integer getQid() {
            return qid;
        }

        public void setQid(Integer qid) {
            this.qid = qid;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getAdminRead() {
            return adminRead;
        }

        public void setAdminRead(Integer adminRead) {
            this.adminRead = adminRead;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Object getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(Object updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
