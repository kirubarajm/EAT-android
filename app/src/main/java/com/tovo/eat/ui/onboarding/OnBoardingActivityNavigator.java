package com.tovo.eat.ui.onboarding;

public interface OnBoardingActivityNavigator {

    void handleError(Throwable throwable);


    void checkForUserLoginMode(boolean trueOrFalse);

    void checkForUserGenderStatus(boolean trueOrFalse);

}
