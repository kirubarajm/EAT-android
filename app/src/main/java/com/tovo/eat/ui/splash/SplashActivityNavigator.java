package com.tovo.eat.ui.splash;

public interface SplashActivityNavigator {

    void handleError(Throwable throwable);

    void checkForUserLoginMode(boolean trueOrFalse);
}
