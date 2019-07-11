package com.tovo.eat.ui.signup.tandc;


import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class TermsAndConditionViewModel extends BaseViewModel<TermsAndConditionNavigator> {
    public TermsAndConditionViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void accept(){
        getNavigator().accept();
    }


    public void goBack(){
        getNavigator().goBack();
    }

}
