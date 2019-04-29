package com.tovo.eat.ui.signup.opt;

public interface OtpActivityNavigator {

    void handleError(Throwable throwable);

    void continueClick();

    void openHomeActivity(boolean trueOrFalse);

    void nameGenderScreen();

    void login();

}
