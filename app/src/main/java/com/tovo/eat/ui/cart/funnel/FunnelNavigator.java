package com.tovo.eat.ui.cart.funnel;

public interface FunnelNavigator {

    void handleError(Throwable throwable);

    void close();

}
