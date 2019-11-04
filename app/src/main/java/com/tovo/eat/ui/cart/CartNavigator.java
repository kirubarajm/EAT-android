package com.tovo.eat.ui.cart;

public interface CartNavigator {

    void handleError(Throwable throwable);

    void cartLoaded();

    void dishListLoaded();

    void paymentMode(String mode);

    void selectAddress();

    void orderCompleted();

    void showToast(String msg);

    void emptyCart();

    void postRegistration(String type, String totalAmount);

    void toastMessage(String msg);

    void paymentGateway(String totalAmount);

    void refundList();

    void checkRefund();

    void promoList();

    void refundAlert();

    void redirectHome();

    void notServicable();
    void showXFactorALert(String msg,String title);
    void funnelAlert();
    void gotoKitchen(Long kitchenid);
}
