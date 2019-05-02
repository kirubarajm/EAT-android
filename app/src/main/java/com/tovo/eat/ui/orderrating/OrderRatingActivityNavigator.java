package com.tovo.eat.ui.orderrating;

public interface OrderRatingActivityNavigator {

    void handleError(Throwable throwable);

    void submit();

    void ratingSuccess();

    void ratingFailure();

    void foodSmileyLow();
    void foodSmileyMedium();
    void foodSmileyHigh();

    void deliverySmileyLow();
    void deliverySmileyMedium();
    void deliverySmileyHigh();
}
