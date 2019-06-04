package com.tovo.eat.ui.track.orderdetails;

public interface OrderDetailsNavigator {

    void handleError(Throwable throwable);


    void clearCart();
    void orderRepeat();

void goBack();


}
