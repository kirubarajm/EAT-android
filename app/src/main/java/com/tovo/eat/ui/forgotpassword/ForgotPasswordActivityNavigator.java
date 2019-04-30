package com.tovo.eat.ui.forgotpassword;

public interface ForgotPasswordActivityNavigator {

    void handleError(Throwable throwable);

    void checkForUserLoginMode(boolean trueOrFalse);
}
