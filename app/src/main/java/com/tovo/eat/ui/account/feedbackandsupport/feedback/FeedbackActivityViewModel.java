package com.tovo.eat.ui.account.feedbackandsupport.feedback;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class FeedbackActivityViewModel extends BaseViewModel<FeedbackActivityNavigator> {

    public FeedbackActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void submitClick(){
        getNavigator().submit();
    }

    public void insertFeedbackServiceCall(){

    }
}
