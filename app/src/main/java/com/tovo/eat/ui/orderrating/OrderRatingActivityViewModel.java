package com.tovo.eat.ui.orderrating;

import com.android.volley.Response;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;

public class OrderRatingActivityViewModel extends BaseViewModel<OrderRatingActivityNavigator> {

    Response.ErrorListener errorListener;

    public OrderRatingActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }



/*
    public void users(String phoneNumber, String password) {
        if(!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SIGN_IN, SignInResponse.class, new SignInRequest(phoneNumber, password), new Response.Listener<SignInResponse>() {
            @Override
            public void onResponse(SignInResponse response) {
                if (response != null) {
                    Log.i("", "" + response.getSuccess());
                    String strMessage = response.getSuccess();
                    Log.e("strMessage", strMessage);
                    if (strMessage.equalsIgnoreCase(AppConstants.TRUE)) {
                        long strUserId = response.getResult().get(0).getUserid();
                        String strUserName = response.getResult().get(0).getName();
                        String strEmail = String.valueOf(response.getResult().get(0).getEmail());
                        String strPassword = String.valueOf(response.getResult().get(0).getPassword());
                        String strPhoneNumber = String.valueOf(response.getResult().get(0).getPhoneno());
                        getDataManager().updateUserInfo(strPassword, strUserId, DataManager.LoggedInMode.LOGGED_IN_MODE_SERVER, strUserName, strEmail, strPhoneNumber);
                        getNavigator().loginSuccess(response.getSuccess());
                        setIsLoading(false);
                    } else {
                        getNavigator().loginError(response.getSuccess());
                        setIsLoading(false);
                    }
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getNavigator().loginError(error.getMessage());
                setIsLoading(false);
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }
*/
}
