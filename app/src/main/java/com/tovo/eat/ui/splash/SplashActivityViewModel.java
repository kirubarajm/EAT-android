package com.tovo.eat.ui.splash;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Observable;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.update.UpdateRequest;
import com.tovo.eat.ui.update.UpdateResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class SplashActivityViewModel extends BaseViewModel<SplashActivityNavigator> {




    public final ObservableField<String> version=new ObservableField<>();





    public SplashActivityViewModel(DataManager dataManager) {
        super(dataManager);




    }

    public void checkIsUserLoggedInOrNot() {
        if (getDataManager().getCurrentUserId() != 0) {
            int userId = getDataManager().getCurrentUserId();
            Log.e("userId", String.valueOf(userId));
            boolean genderStatus = getDataManager().getisGenderStatus();
            if (genderStatus) {
                getNavigator().checkForUserLoginMode(AppConstants.FLAG_TRUE);
            } else {
                getNavigator().checkForUserGenderStatus(false);
            }
        } else {
            getNavigator().checkForUserLoginMode(AppConstants.FLAG_FALSE);
        }

    }


    public void checkUpdate() {
        /*   MvvmApp.getInstance().getVersionCode()*/


        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FCM_FORCE_UPDATE, UpdateResponse.class, new UpdateRequest(MvvmApp.getInstance().getVersionCode()), new Response.Listener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse response) {


                if (response.getStatus()) {
                    getNavigator().update(response.getResult().getVersionstatus(), response.getResult().getEatforceupdate());
                }else {
                    getNavigator().update(false, false);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }, AppConstants.API_VERSION_ONE);
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);


    }


}
