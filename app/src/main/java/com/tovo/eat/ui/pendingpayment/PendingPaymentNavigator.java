package com.tovo.eat.ui.pendingpayment;

public interface PendingPaymentNavigator {

    void handleError(Throwable throwable);

    void retry();
    void cancel();
    void paymentSuccessed(boolean status);
}
