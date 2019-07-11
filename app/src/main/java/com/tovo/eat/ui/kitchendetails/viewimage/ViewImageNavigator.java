package com.tovo.eat.ui.kitchendetails.viewimage;

import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;

public interface ViewImageNavigator {

    void handleError(Throwable throwable);


    void toastMessage(String msg);

    void dishListLoaded(KitchenDishResponse response);

    void viewCart();


    void goBack();

    void animChanges(boolean status);

    void update(int count);



}
