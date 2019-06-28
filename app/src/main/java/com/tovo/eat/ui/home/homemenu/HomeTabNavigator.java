package com.tovo.eat.ui.home.homemenu;

import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.registration.RegionResponse;

public interface HomeTabNavigator {

    void handleError(Throwable throwable);

    void selectAddress();

    void filter();
    void favourites();

    void disconnectGps();
    void loaded();

    void storiesLoaded(RegionsResponse regionResponse);


}
