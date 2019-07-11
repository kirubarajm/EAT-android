package com.tovo.eat.ui.splash;


import android.util.Log;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;

public class SplashActivityViewModel extends BaseViewModel<SplashActivityNavigator> {

    public SplashActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void checkIsUserLoggedInOrNot(){
        if (getDataManager().getCurrentUserId()!=null)
        {
            int userId = getDataManager().getCurrentUserId();
            Log.e("userId", String.valueOf(userId));
            boolean genderStatus = getDataManager().getisGenderStatus();
            if (genderStatus) {
                getNavigator().checkForUserLoginMode(AppConstants.FLAG_TRUE);
            }else {
                getNavigator().checkForUserGenderStatus(false);
            }
        }else {
            getNavigator().checkForUserLoginMode(AppConstants.FLAG_FALSE);
        }

    }

}
