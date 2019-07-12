package com.tovo.eat.ui.track.help;

public interface OrderHelpNavigator {

    void handleError(Throwable throwable);
    void goBack();
    void callDelivery();
    void gotoSupport();
    void orderCanceled();
    void orderCancelFailed();
    void showToast(String msg);


}
