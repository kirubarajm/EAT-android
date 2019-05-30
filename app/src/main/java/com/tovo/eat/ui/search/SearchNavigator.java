package com.tovo.eat.ui.search;

public interface SearchNavigator {

    void handleError(Throwable throwable);

    void toastMessage(String msg);

    void listLoaded();
    void listLoading();
    void searchList(String data);


}
