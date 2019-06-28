package com.tovo.eat.ui.cart.refund;

public interface RefundListNavigator {

    void handleError(Throwable throwable);

    void addNewAddress();
    void editAddress();
    void listLoaded();
    void goBack();
    void showToast(String msg);
void addresDeleted();


void noAddress();


}
