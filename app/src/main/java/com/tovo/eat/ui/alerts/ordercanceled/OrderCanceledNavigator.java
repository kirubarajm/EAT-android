package com.tovo.eat.ui.alerts.ordercanceled;

public interface OrderCanceledNavigator {

    void handleError(Throwable throwable);

    void submit();

}
