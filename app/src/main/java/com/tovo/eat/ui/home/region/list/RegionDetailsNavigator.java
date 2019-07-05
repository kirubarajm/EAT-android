package com.tovo.eat.ui.home.region.list;

public interface RegionDetailsNavigator {

    void handleError(Throwable throwable);

    void listLoaded();
    void goBack();
    void showToast(String msg);



}
