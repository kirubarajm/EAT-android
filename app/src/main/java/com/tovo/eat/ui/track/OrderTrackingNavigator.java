package com.tovo.eat.ui.track;

public interface OrderTrackingNavigator {


    void callDeliveryMan(String number);

    void tracking(String cusLat, String cusLng, double moveitLat, double moveitLng);

    void orderPickedUp(Integer MoveitId);

    void clickBack();



    void showToast(String msg);

}
