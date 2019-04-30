package com.tovo.eat.ui.home.homemenu.kitchen;

public interface KitchenNavigator {

    void handleError(Throwable throwable);

    void gotoJobCompleted();
    void gotoInJobCompleted();


    void kitchenListLoaded();
    void kitchenListLoading();



    void addressAdded1();
    void noAddressFound1();

    void toastMessage(String msg);
}
