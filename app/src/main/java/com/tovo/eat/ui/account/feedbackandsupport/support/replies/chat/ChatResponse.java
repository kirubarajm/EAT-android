package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatResponse {

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

        @SerializedName("aid")
        @Expose
        public Integer aid;
        @SerializedName("qid")
        @Expose
        public Integer qid;
        @SerializedName("answer")
        @Expose
        public String answer;
        @SerializedName("type")
        @Expose
        public Integer type;
        @SerializedName("adminid")
        @Expose
        public Integer adminid;
        @SerializedName("userid")
        @Expose
        public Integer userid;
        @SerializedName("user_read")
        @Expose
        public Integer userRead;
        @SerializedName("admin_read")
        @Expose
        public Integer adminRead;
        @SerializedName("created_at")
        @Expose
        public String createdAt;
        @SerializedName("updated_at")
        @Expose
        public String updatedAt;


        public Integer getAid() {
            return aid;
        }

        public void setAid(Integer aid) {
            this.aid = aid;
        }

        public Integer getQid() {
            return qid;
        }

        public void setQid(Integer qid) {
            this.qid = qid;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }

        public Integer getAdminid() {
            return adminid;
        }

        public void setAdminid(Integer adminid) {
            this.adminid = adminid;
        }

        public Integer getUserid() {
            return userid;
        }

        public void setUserid(Integer userid) {
            this.userid = userid;
        }

        public Integer getUserRead() {
            return userRead;
        }

        public void setUserRead(Integer userRead) {
            this.userRead = userRead;
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

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
