package com.tovo.eat.ui.account.chatsupport;

public interface HistoryHelpNavigator {

    void handleError(Throwable throwable);
    void goBack();
    void showToast(String msg);

    void createChat(String department,String tag,String note);
    void mapChat(String department,String tag,String note,int issueid,int tid);


}
