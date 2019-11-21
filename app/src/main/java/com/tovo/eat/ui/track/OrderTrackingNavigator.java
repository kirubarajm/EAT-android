package com.tovo.eat.ui.track;

public interface OrderTrackingNavigator {


    void callDeliveryMan(String number);

    void tracking(String cusLat, String cusLng, double moveitLat, double moveitLng);
    void DunzoTracking(double runnerLat, double runnerLng);

    void orderPickedUp(Integer MoveitId);

    void clickBack();
    void orderDetails(Long orderId);
    void showToast(String msg);
void startTrackingTimer(int minuts);
}
