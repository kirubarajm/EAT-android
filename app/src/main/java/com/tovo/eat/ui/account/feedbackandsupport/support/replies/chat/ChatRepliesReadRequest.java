package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import com.android.volley.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatRepliesReadRequest {

    @SerializedName("aidlist")
    @Expose
    public List<Aidlist> aidlist = null;

    public ChatRepliesReadRequest(List<Aidlist> aidlist) {
        this.aidlist = aidlist;
    }

    public ChatRepliesReadRequest(Response.Listener<ChatReplyResponse> chatReplyResponseListener, Object o) {

    }
    public List<Aidlist> getAidlist() {
        return aidlist;
    }

    public void setAidlist(List<Aidlist> aidlist) {
        this.aidlist = aidlist;
    }

    public static class Aidlist {

        @SerializedName("aid")
        @Expose
        public Integer aid;


        public Integer getAid() {
            return aid;
        }

        public void setAid(Integer aid) {
            this.aid = aid;
        }
    }

}
