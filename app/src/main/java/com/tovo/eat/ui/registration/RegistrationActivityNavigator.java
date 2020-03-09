package com.tovo.eat.ui.registration;

import java.util.List;

public interface RegistrationActivityNavigator {

    void handleError(Throwable throwable);

    void usersRegistrationMain();

    void openMainActivity();

    void regSuccess(String strSucess);

    void regFailure();

    void orderCompleted();

    void regionList(List<RegionResponse.Result> regionList);

    void showToast(String msg);


}
