package com.tovo.eat.ui.signup.privacy;


import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class PrivacyViewModel extends BaseViewModel<PrivacyNavigator> {
    public PrivacyViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void onAgreeAndAcceptClick(){
        getNavigator().openRegActivity();
    }
}
