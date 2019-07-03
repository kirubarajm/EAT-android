package com.tovo.eat.ui.signup;

public interface SignUpActivityNavigator {

    void handleError(Throwable throwable);

    void verifyUser();


    void faqs();
    void privacy();
    void termsandconditions();

    void loginError(boolean strError);

    void otpScreenFalse(boolean trurOrFalse,int passwordSuccess,int UserId);

    void genderScreenFalse(boolean passwordSuccess);

    void openHomeScreen(boolean passwordSuccess);
}
