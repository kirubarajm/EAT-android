package com.tovo.eat.ui.signup;

public interface SignUpActivityNavigator {

    void handleError(Throwable throwable);

    void usersLoginMain();

    void openLoginMainActivity();

    void loginSuccess(String strSuccess);

    void loginError(String strError);
}
