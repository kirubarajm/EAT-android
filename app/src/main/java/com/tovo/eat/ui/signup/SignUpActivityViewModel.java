package com.tovo.eat.ui.signup;

import android.databinding.ObservableField;
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

public class SignUpActivityViewModel extends BaseViewModel<SignUpActivityNavigator> {

    public final ObservableField<String> passwordstatus = new ObservableField<>();
    public final ObservableField<String> otpverification = new ObservableField<>();
    public final ObservableField<String> genderstatus = new ObservableField<>();
    public final ObservableField<String> otp = new ObservableField<>();
    Response.ErrorListener errorListener;

    public SignUpActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void usersLoginMain() {
        getNavigator().usersLoginMain();
    }

    public void users(String phoneNumber) {
        if(!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SIGN_UP, SignUpResponse.class, new SignUpRequest(phoneNumber), new Response.Listener<SignUpResponse>() {
            @Override
            public void onResponse(SignUpResponse response) {
                if (response != null) {
                    passwordstatus.set(String.valueOf(response.getPasswordstatus()));
                    otpverification.set(String.valueOf(response.getOtpverification()));
                    genderstatus.set(String.valueOf(response.getGenderstatus()));
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
}
