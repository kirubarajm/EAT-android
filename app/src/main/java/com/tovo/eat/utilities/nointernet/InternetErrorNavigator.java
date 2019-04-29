package com.tovo.eat.utilities.nointernet;

public interface InternetErrorNavigator {

    void handleError(Throwable throwable);

    void retry();


}
