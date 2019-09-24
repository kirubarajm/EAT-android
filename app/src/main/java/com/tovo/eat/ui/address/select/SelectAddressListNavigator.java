package com.tovo.eat.ui.address.select;

public interface SelectAddressListNavigator {

    void handleError(Throwable throwable);

    void addNewAddress();

    void editAddress();

    void listLoaded();

    void goBack();

}
