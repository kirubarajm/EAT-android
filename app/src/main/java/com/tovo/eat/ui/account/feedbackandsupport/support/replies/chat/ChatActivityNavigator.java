package com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat;

import java.util.List;

public interface ChatActivityNavigator {

    void handleError();

    void send();

    void onRefreshLayout();
    void apiLoaded();

    void onRefreshSuccess(List<ChatRepliesReadRequest.Aidlist> aidlist);

    void onRefreshFailure(String strFailure);

    void sendSuccess(String strFailure);

    void sendFailure(String strFailure);



    void goBack();
}
