package com.tovo.eat.ui.forgotpassword;


import android.util.Log;

import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;

public class ForgotPasswordActivityViewModel extends BaseViewModel<ForgotPasswordActivityNavigator> {

    public ForgotPasswordActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void checkIsUserLoggedInOrNot(){
        if (getDataManager().getCurrentUserId()!=null)
        {
            int userId = getDataManager().getCurrentUserId();
            Log.e("userId", String.valueOf(userId));
            getNavigator().checkForUserLoginMode(AppConstants.FLAG_TRUE);
        }else {
            getNavigator().checkForUserLoginMode(AppConstants.FLAG_FALSE);
        }

    }

}
