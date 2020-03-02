package com.tovo.eat.ui.track.help;

public interface OrderHelpNavigator {

    void handleError(Throwable throwable);
    void goBack();
    void callDelivery();
    void gotoSupport();
    void orderCanceled();
    void orderCancelClicked();
    void orderCancelFailed();
    void showToast(String msg);


    void createChat(String department,String tag,String note);
    void mapChat(String department,String tag,String note,int issueid,int tid);


}
