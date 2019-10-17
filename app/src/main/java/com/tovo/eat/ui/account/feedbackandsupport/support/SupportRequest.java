package com.tovo.eat.ui.account.feedbackandsupport.support;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportRequest {

    @SerializedName("question")
    @Expose
    public String question;
    @SerializedName("type")
    @Expose
    public Integer type;
    @SerializedName("userid")
    @Expose
    public Long userid;
 @SerializedName("orderid")
    @Expose
    public Long orderid;
    @SerializedName("querytype")
    @Expose
    public Integer queryType;

    public SupportRequest(String question, Integer type, Long userid) {
        this.question = question;
        this.type = type;
        this.userid = userid;
    }


    public SupportRequest(String question, Integer type, Long userid, Long orderid) {
        this.question = question;
        this.type = type;
        this.userid = userid;
        this.orderid = orderid;
    }


    public SupportRequest(String question, Integer type, Long userid, Long orderid, Integer queryType) {
        this.question = question;
        this.type = type;
        this.userid = userid;
        this.orderid = orderid;
        this.queryType = queryType;
    }

    public Integer getQueryType() {
        return queryType;
    }

    public void setQueryType(Integer queryType) {
        this.queryType = queryType;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
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
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
