package com.tovo.eat.ui.signup;

public interface SignUpActivityNavigator {

    void handleError(Throwable throwable);

    void usersLoginMain();

    void loginSuccess(boolean passwordstatus,boolean otpStatus,boolean genderstatus);

    void loginError(boolean strError);


    void otpScreenFalse(boolean trurOrFalse,int passwordSuccess);

    void genderScreenFalse(boolean passwordSuccess);

    void openHomeScreen(boolean passwordSuccess);
}
