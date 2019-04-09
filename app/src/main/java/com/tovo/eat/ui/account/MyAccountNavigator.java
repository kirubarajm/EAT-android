package com.tovo.eat.ui.account;

public interface MyAccountNavigator {

    void handleError(Throwable throwable);


    void manageAddress();

    void orderHistory();

    void favourites();

    void referrals();

    void offers();

    void feedbackAndSupport();

    void editProfile();

}
