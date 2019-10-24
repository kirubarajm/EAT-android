package com.tovo.eat.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.ObservableField;

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


    public final ObservableField<String> version = new ObservableField<>();


    public SplashActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }



    public void clearLatLng(){
        try {
            if (getDataManager().getAddressId() == 0) {
                getDataManager().setCurrentLat(0.0);
                getDataManager().setCurrentLng(0.0);
            }
        } catch (Exception e) {

            SharedPreferences settings =MvvmApp.getInstance().getSharedPreferences(AppConstants.PREF_NAME, Context.MODE_PRIVATE);
           // settings.edit().clear().apply();
         int uid= settings.getInt("PREF_KEY_CURRENT_USER_ID",0);
         int aid= settings.getInt("CURRENT_ADDRESS_ID",0);
         int oid= settings.getInt("PREF_KEY_ORDER_ID",0);
         int roid= settings.getInt("RATING_ORDER_ID",0);
            getDataManager().setCurrentUserId((long) uid);
            getDataManager().setAddressId((long) aid);
            getDataManager().setOrderId((long) oid);
            getDataManager().setRatingOrderid((long) roid);

        }

    }


    public void checkIsUserLoggedInOrNot() {

        try {
            if (getDataManager().getCurrentUserId() != 0) {
                boolean genderStatus = getDataManager().getisGenderStatus();
                if (genderStatus) {
                    if (getNavigator() != null)
                        getNavigator().checkForUserLoginMode(AppConstants.FLAG_TRUE);
                } else {
                    if (getNavigator() != null)
                        getNavigator().checkForUserGenderStatus(false);
                }
            } else {
                if (getNavigator() != null)
                    getNavigator().checkForUserLoginMode(AppConstants.FLAG_FALSE);
            }
        } catch (Exception e) {

            if (getNavigator() != null)
                getNavigator().checkForUserLoginMode(AppConstants.FLAG_FALSE);
        }
    }

    public void checkUpdate() {
        /*   MvvmApp.getInstance().getVersionCode()*/

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.EAT_FCM_FORCE_UPDATE, UpdateResponse.class, new UpdateRequest(MvvmApp.getInstance().getVersionCode()), new Response.Listener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse response) {

                if (response != null)
                    if (response.getResult() != null && response.getStatus()) {
                        if (getNavigator() != null)
                            getNavigator().update(response.getResult().getVersionstatus(), response.getResult().getEatforceupdate());

                        getDataManager().saveSupportNumber(response.getResult().getSupportNumber());

                    } else {
                        if (getNavigator() != null)
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
