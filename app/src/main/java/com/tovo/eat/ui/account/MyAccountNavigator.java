package com.tovo.eat.ui.account;

import com.tovo.eat.ui.signup.namegender.GetUserDetailsResponse;

public interface MyAccountNavigator {

    void handleError(Throwable throwable);


    void manageAddress();

    void orderHistory();

    void favourites();

    void referrals();

    void offers();

    void logout();

    void feedbackAndSupport();

    void editProfile(GetUserDetailsResponse getUserDetailsResponse);

}
