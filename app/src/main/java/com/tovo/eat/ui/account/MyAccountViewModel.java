package com.tovo.eat.ui.account;


import android.databinding.ObservableField;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

import dagger.Module;

@Module
public class MyAccountViewModel extends BaseViewModel<MyAccountNavigator> {

    public final ObservableField<String> toolbarTitle = new ObservableField<>();
    public final ObservableField<String> userName = new ObservableField<>();
    public final ObservableField<String> userEmail = new ObservableField<>();
    public final ObservableField<String> userPhoneNo = new ObservableField<>();

    public MyAccountViewModel(DataManager dataManager) {
        super(dataManager);
        userName.set(getDataManager().getCurrentUserName());
        userEmail.set(getDataManager().getCurrentUserEmail());
        userPhoneNo.set(getDataManager().getCurrentUserPhNo());
    }

    public void manageAddress() {
        getNavigator().manageAddress();
    }


    public void orderHistory() {
        getNavigator().orderHistory();
    }


    public void favourites() {
        getDataManager().setIsFav(true);
        getNavigator().favourites();
    }


    public void referrals() {
        getNavigator().referrals();
    }


    public void offers() {
        getNavigator().offers();
    }

    public void logOut() {
        getNavigator().logout();
    }

    public void feedbackAndSupport() {
        getNavigator().feedbackAndSupport();
    }

    public void editProfile() {
        getNavigator().editProfile();
    }


    public void logOutSession() {
        getDataManager().setLogout();
    }


}
