package com.tovo.eat.ui.signup.opt;

public interface OtpActivityNavigator {

    void handleError(Throwable throwable);

    void usersLoginMain();

    void openLoginMainActivity();

    void loginSuccess(String strSuccess);

    void loginError(String strError);
}
