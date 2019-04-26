package com.tovo.eat.ui.signup.opt;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.signup.SignUpRequest;
import com.tovo.eat.ui.signup.SignUpResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class OtpActivityViewModel extends BaseViewModel<OtpActivityNavigator> {

    public final ObservableBoolean otp = new ObservableBoolean();
    public final ObservableField<String> userId = new ObservableField<>();
    public boolean passwordstatus;
    public boolean otpStatus;
    public boolean genderstatus;
    Response.ErrorListener errorListener;

    public OtpActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void continueClick() {
        getNavigator().usersLoginMain();
    }

    public void login(String strPhoneNumber){
        String strPassword = "pondy";
        strPhoneNumber = "9856321470";
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_LOGIN_MAIN, LoginResponse.class, new LoginRequest(strPhoneNumber,strPassword), new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                if (response != null) {
                    getNavigator().openHomeActivity();
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

    public void loginClick(){
          getNavigator().login();
    }

    public void users(String phoneNumber, int otp, int otpId) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_OTP_VERIFICATION, OtpResponse.class, new OtpRequest(phoneNumber, otp, otpId), new Response.Listener<OtpResponse>() {
            @Override
            public void onResponse(OtpResponse response) {
                if (response != null) {
                    Log.e("response", String.valueOf(response.getUserid()));
                    passwordstatus = response.getPasswordstatus();
                    otpStatus = response.getOtpstatus();
                    genderstatus = response.getGenderstatus();
                    userId.set(String.valueOf(response.getUserid()));

                    if (otpStatus) {
                        if (genderstatus) {
                            getNavigator().openHomeActivity();
                        } else {
                            getNavigator().nameGenderScreen();
                        }
                    }
                }
                getDataManager().updateUserInfo(response.getUserid());

                int userId=getDataManager().getCurrentUserId();
                Log.e("userId", String.valueOf(userId));
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
