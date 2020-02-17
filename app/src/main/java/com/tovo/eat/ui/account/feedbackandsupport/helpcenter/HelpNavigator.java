package com.tovo.eat.ui.account.feedbackandsupport.helpcenter;

public interface HelpNavigator {

    void handleError(Throwable throwable);
    void goBack();
    void callDelivery();
    void gotoSupport();
    void orderCanceled();
    void orderCancelClicked();
    void orderCancelFailed();
    void showToast(String msg);


    void createChat(String department,String tag,String note);


}
