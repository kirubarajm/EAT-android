package com.tovo.eat.ui.home.homemenu;

public interface HomeTabNavigator {

    void handleError(Throwable throwable);

    void selectAddress();

    void filter();
    void favourites();

    void disconnectGps();
    void loaded();

}
