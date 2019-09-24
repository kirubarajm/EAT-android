package com.tovo.eat.ui.home.homemenu.kitchen;

public interface KitchenNavigator {

    void handleError(Throwable throwable);

    void kitchenListLoaded();

    void kitchenListLoading();

    void filter();

    void toastMessage(String msg);
}
