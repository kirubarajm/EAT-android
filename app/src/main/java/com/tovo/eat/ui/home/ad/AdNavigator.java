package com.tovo.eat.ui.home.ad;

public interface AdNavigator {

    void handleError(Throwable throwable);

    void openRegActivity();
    void goBack();
}
