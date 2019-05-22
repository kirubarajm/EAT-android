package com.tovo.eat.ui.home.region;

public interface RegionNavigator {

    void handleError(Throwable throwable);


    void kitchenListLoaded();
    void kitchenListLoading();


    void searchLoaded(RegionSearchModel regionSearchModel);
}
