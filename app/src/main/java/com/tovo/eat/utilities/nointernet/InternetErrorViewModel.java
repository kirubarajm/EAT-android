package com.tovo.eat.utilities.nointernet;


import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.MvvmApp;

import dagger.Module;

@Module
public class InternetErrorViewModel extends BaseViewModel<InternetErrorNavigator> {


    public InternetErrorViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void retry() {
        getNavigator().retry();

    }


    public boolean checkInternet() {

        return MvvmApp.getInstance().onCheckNetWork();


    }


}
