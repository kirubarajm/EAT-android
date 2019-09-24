package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

public interface RepliesActivityNavigator {

    void handleError(Throwable throwable);

    void next();

    void onFrefresh();

    void onRefreshSuccess();

    void onRefreshFailure();

    void goBack();
}
