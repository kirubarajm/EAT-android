package com.tovo.eat.ui.cart;

public interface CartNavigator {

    void handleError(Throwable throwable);

    void gotoJobCompleted();
    void gotoInJobCompleted();


    void dishListLoaded();

    void paymentMode(String mode);

    void selectAddress();


    boolean paymentStatus(String mode);

}
