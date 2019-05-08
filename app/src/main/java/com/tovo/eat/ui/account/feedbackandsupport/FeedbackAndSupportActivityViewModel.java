package com.tovo.eat.ui.account.feedbackandsupport;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class FeedbackAndSupportActivityViewModel extends BaseViewModel<FeedbackAndSupportActivityNavigator> {

    public FeedbackAndSupportActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void feedbackClick(){
        getNavigator().feedBackClick();
    }
    public void supportClick(){
        getNavigator().supportClick();
    }


    public void goBack(){



        getNavigator().goBack();

    }

}
