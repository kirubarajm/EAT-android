package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChatReplyRequest {

    @SerializedName("qid")
    @Expose
    public Long qid;
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
    public Long userid;

    public ChatReplyRequest(Long qid, String answer, Integer type, Integer adminid, Long userid) {
        this.qid = qid;
        this.answer = answer;
        this.type = type;
        this.adminid = adminid;
        this.userid = userid;
    }

    public Long getQid() {
        return qid;
    }

    public void setQid(Long qid) {
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

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
