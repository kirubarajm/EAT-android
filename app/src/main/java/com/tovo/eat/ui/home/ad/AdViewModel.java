package com.tovo.eat.ui.home.ad;


import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class AdViewModel extends BaseViewModel<AdNavigator> {
    public AdViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void onAgreeAndAcceptClick(){
        getNavigator().openRegActivity();
    }


    public void goBack(){
        getNavigator().goBack();
    }

}
