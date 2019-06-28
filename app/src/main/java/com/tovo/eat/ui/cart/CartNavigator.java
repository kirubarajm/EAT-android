package com.tovo.eat.ui.cart;

public interface CartNavigator {

    void handleError(Throwable throwable);

    void gotoJobCompleted();

    void gotoInJobCompleted();


    void dishListLoaded();

    void paymentMode(String mode);

    void selectAddress();

    boolean paymentStatus(String mode);


    void orderCompleted();

    void showToast(String msg);


    void emptyCart();

    void postRegistration(String type, String totalAmount);


    void toastMessage(String msg);


    void paymentGateway(String totalAmount);


    void refundList();

    void promoList();

}
