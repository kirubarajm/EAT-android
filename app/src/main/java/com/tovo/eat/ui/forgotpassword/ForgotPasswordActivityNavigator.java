package com.tovo.eat.ui.forgotpassword;

public interface ForgotPasswordActivityNavigator {

    void handleError(Throwable throwable);

    void otpVerificationProceed();

    void otpVerificationSubmit();

    void confirmPassSuccess();

    void confirmPassFailre();

    void otpInvalid();

    void goBack();


}
