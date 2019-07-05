package com.tovo.eat.ui.home.region.viewmore;

public interface RegionListNavigator {

    void handleError(Throwable throwable);

    void listLoaded();
    void goBack();
    void showToast(String msg);



}
