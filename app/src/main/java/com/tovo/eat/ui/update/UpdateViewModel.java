package com.tovo.eat.ui.update;


import android.databinding.ObservableBoolean;
import android.util.Log;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

public class UpdateViewModel extends BaseViewModel<UpdateNavigator> {


    public ObservableBoolean forceUpdate = new ObservableBoolean();


    public UpdateViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void checkIsUserLoggedInOrNot(){

        new Analytics().sendClickData(AppConstants.SCREEN_FORCE_UPDATE, AppConstants.CLICK_NOT_NOW);

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

    public void update(){

        getNavigator().update();


    }

}
