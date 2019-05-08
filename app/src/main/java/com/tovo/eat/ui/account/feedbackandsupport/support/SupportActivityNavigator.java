package com.tovo.eat.ui.account.feedbackandsupport.support;

public interface SupportActivityNavigator {

    void handleError(Throwable throwable);

    void backClick();

    void repliesOnClick();

    void onRefreshLayout();

    void submit();

    void success(String strSuccess);

    void failure(String strFailure);

    void onRefreshSuccess();

    void onRefreshFailure();

    void callAdmin();

    void goBack();

}
