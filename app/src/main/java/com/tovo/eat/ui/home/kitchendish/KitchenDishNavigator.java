package com.tovo.eat.ui.home.kitchendish;

public interface KitchenDishNavigator {

    void handleError(Throwable throwable);


    void toastMessage(String msg);

    void dishListLoaded();

    void viewCart();


    void back();


}
