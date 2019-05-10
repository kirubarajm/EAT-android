package com.tovo.eat.ui.signup.namegender;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryListResponse;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

public class NameGenderActivityViewModel extends BaseViewModel<NameGenderActivityNavigator> {

    Response.ErrorListener errorListener;

    public NameGenderActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void proceed() {
        getNavigator().proceedClick();
    }

    public void male() {
        getNavigator().male();
    }

    public void feMale() {
        getNavigator().female();
    }

    public void insertNameGenderServiceCall(String name, int gender) {
        int userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_NAME_GENDER_INSERT, NameGenderResponse.class, new NameGenderRequest(userIdMain, name, gender), new Response.Listener<NameGenderResponse>() {
            @Override
            public void onResponse(NameGenderResponse response) {
                if (response != null) {
                    Log.i("", "" + response.getSuccess());
                    getNavigator().genderSuccess(response.getMessage());
                    if (response.getStatus()) {
                        getDataManager().updateUserGender(true);
                    }
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
                getNavigator().genderFailure("Failed to update");
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }




}
