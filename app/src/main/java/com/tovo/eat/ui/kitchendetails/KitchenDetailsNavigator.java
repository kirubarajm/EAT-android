package com.tovo.eat.ui.kitchendetails;

import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;

import java.util.List;

public interface KitchenDetailsNavigator {

    void handleError(Throwable throwable);


    void toastMessage(String msg);

    void dishListLoaded(KitchenDetailsResponse response);

    void viewCart();



    void goBack();

    void loadError();



}
