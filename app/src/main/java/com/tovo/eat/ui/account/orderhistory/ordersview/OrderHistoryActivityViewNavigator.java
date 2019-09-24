package com.tovo.eat.ui.account.orderhistory.ordersview;

public interface OrderHistoryActivityViewNavigator {

    void handleError(Throwable throwable);

    void clearCart();

    void orderRepeat();

    void goBack();

}
