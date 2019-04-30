package com.tovo.eat.ui.registration;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class RegistrationActivityViewModel extends BaseViewModel<RegistrationActivityNavigator> {

    Response.ErrorListener errorListener;

    public RegistrationActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void userProceed() {
        getNavigator().usersRegistrationMain();
    }

    public void userRegistrationServiceCall(String strEmail, String strReTypePass) {
        int userId = getDataManager().getCurrentUserId();
        if(!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_REGISTRATION, RegistrationResponse.class, new RegistrationRequest(userId,strEmail, strReTypePass), new Response.Listener<RegistrationResponse>() {
            @Override
            public void onResponse(RegistrationResponse response) {
                if (response != null) {
                    getNavigator().regSuccess(response.getMessage());
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
                getNavigator().regFailure();
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }
}
