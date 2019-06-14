package com.tovo.eat.ui.home.kitchendish;

public interface KitchenDishNavigator {

    void handleError(Throwable throwable);


    void toastMessage(String msg);

    void dishListLoaded(KitchenDishResponse response);

    void viewCart();


    void back();


}
