package com.tovo.eat.ui.home.homemenu;

import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.registration.RegionResponse;

public interface HomeTabNavigator {

    void handleError(Throwable throwable);

    void selectAddress();

    void filter();
    void favourites();

    void disconnectGps();
    void loaded();

    void regionsLoaded(RegionsResponse regionResponse);
    void dataLoaded();

    void goBack();
    void collectionLoaded();
    void kitchenLoaded();

    void getFullStories(StoriesResponse storiesResponse);
    void trackLiveOrder(long orderId);

    void closeAddressAlert();

    void scrollToTop();


}
