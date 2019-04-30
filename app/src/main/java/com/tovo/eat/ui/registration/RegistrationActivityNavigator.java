package com.tovo.eat.ui.registration;

public interface RegistrationActivityNavigator {

    void handleError(Throwable throwable);

    void usersRegistrationMain();

    void openMainActivity();

    void regSuccess(String strSucess);

    void regFailure();

}
