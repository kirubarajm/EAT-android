package com.tovo.eat.ui.kitchendetails;

import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;

public interface KitchenDetailsNavigator {

    void handleError(Throwable throwable);


    void toastMessage(String msg);

    void dishListLoaded(KitchenDishResponse response);

    void viewCart();


    void back();


}
