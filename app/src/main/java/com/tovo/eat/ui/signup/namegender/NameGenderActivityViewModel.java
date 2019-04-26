package com.tovo.eat.ui.signup.namegender;

import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class NameGenderActivityViewModel extends BaseViewModel<NameGenderActivityNavigator> {

    Response.ErrorListener errorListener;

    public NameGenderActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void proceed() {
        getNavigator().proceedClick();
    }

    public void insertNameGenderServiceCall(int userId,String name, int gender) {
        int userIdMain = getDataManager().getCurrentUserId();
        if(!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_NAME_GENDER_INSERT, NameGenderResponse.class, new NameGenderRequest(userIdMain, name,1), new Response.Listener<NameGenderResponse>() {
            @Override
            public void onResponse(NameGenderResponse response) {
                if (response != null) {
                    Log.i("", "" + response.getSuccess());
                    getNavigator().openActivity(response.getMessage());
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }
}
