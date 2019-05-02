package com.tovo.eat.ui.signup.namegender;

public interface NameGenderActivityNavigator {

    void handleError(Throwable throwable);

    void proceedClick();

    void genderSuccess(String strMessage);

    void genderFailure(String strMessage);

    void male();

    void female();

}
