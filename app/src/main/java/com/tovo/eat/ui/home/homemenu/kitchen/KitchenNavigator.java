package com.tovo.eat.ui.home.homemenu.kitchen;

public interface KitchenNavigator {

    void handleError(Throwable throwable);

    void gotoJobCompleted();
    void gotoInJobCompleted();


    void kitchenListLoaded();

}
