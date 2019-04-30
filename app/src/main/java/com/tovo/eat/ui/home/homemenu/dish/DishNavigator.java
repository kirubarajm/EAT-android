package com.tovo.eat.ui.home.homemenu.dish;

public interface DishNavigator {

    void handleError(Throwable throwable);

    void gotoJobCompleted();

    void gotoInJobCompleted();

    void dishListLoaded();

    void addressAdded();

    void noAddressFound();

    void toastMessage(String msg);

    void dishLoading();


}
