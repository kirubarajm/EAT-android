package com.tovo.eat.ui.account.feedbackandsupport.feedback;

public interface FeedbackActivityNavigator {

    void handleError(Throwable throwable);

    void submit();

    void feedBackSucess(String strMessage);

    void feedBackFailure(String strMessage);

}
