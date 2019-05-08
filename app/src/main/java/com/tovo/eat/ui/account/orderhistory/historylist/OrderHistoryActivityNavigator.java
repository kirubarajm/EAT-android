package com.tovo.eat.ui.account.orderhistory.historylist;

public interface OrderHistoryActivityNavigator {

    void handleError(Throwable throwable);

    void onRefreshLayout();

    void onRefreshSuccess();

    void onRefreshFailure();


    void goBack();
}
