package com.tovo.eat.ui.track.help;

public interface OrderHelpNavigator {

    void handleError(Throwable throwable);

    void clearCart();

    void orderRepeat();

    void goBack();

}
