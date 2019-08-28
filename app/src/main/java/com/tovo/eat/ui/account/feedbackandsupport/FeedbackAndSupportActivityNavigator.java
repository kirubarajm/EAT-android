package com.tovo.eat.ui.account.feedbackandsupport;

public interface FeedbackAndSupportActivityNavigator {

    void handleError(Throwable throwable);

    void feedBackClick();

    void supportClick();
 void faqs();

    void goBack();
}
