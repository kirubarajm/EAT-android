package com.tovo.eat.ui.update;

public interface UpdateNavigator {

    void handleError(Throwable throwable);

    void checkForUserLoginMode(boolean trueOrFalse);
    void update();
    void checkForUserGenderStatus(boolean trueOrFalse);

}
