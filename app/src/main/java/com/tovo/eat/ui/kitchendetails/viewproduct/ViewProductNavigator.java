package com.tovo.eat.ui.kitchendetails.viewproduct;

import com.tovo.eat.ui.kitchendetails.KitchenDetailsResponse;

public interface ViewProductNavigator {

    void handleError(Throwable throwable);
    void toastMessage(String msg);
    void viewCart();

    void goBack();

}
