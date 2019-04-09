package com.tovo.eat.ui.account;


import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

import dagger.Module;

@Module
public class MyAccountViewModel extends BaseViewModel<MyAccountNavigator> {

    public MyAccountViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void manageAddress(){
        getNavigator().manageAddress();
    }


        public void orderHistory(){
        getNavigator().orderHistory();
    }


    public void favourites(){
        getNavigator().favourites();
    }


    public void referrals(){
        getNavigator().referrals();
    }


    public void offers(){
        getNavigator().offers();
    }

    public void feedbackAndSupport(){
        getNavigator().feedbackAndSupport();
    }

    public void editProfile(){
        getNavigator().editProfile();
    }



}
