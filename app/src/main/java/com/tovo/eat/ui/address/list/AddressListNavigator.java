package com.tovo.eat.ui.address.list;

public interface AddressListNavigator {

    void handleError(Throwable throwable);

    void addNewAddress();
    void editAddress();
    void listLoaded();
    void goBack();
    void showToast(String msg);
void addresDeleted();


void noAddress();


}
