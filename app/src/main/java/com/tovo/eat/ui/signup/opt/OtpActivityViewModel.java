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
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class OtpActivityViewModel extends BaseViewModel<OtpActivityNavigator> {

    public final ObservableBoolean otp = new ObservableBoolean();
    public final ObservableField<String> userId = new ObservableField<>();
    public final ObservableField<String> oId = new ObservableField<>();
    public boolean passwordstatus;
    public boolean otpStatus;
    public boolean genderstatus;
    Response.ErrorListener errorListener;

    public OtpActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void continueClick() {
        getNavigator().continueClick();
    }

    public void forgotPasswordClick() {
        getNavigator().forgotPassword();
    }

    public void login(String strPhoneNumber, String strPassword) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_LOGIN_MAIN, LoginResponse.class, new LoginRequest(strPhoneNumber, strPassword), new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                if (response != null) {
                    if (response.getResult() != null && response.getResult().size() > 0) {
                        try {
                            int userId = response.getResult().get(0).getUserid();
                            String UserName = response.getResult().get(0).getName();
                            String UserEmail = response.getResult().get(0).getEmail();
                            String userPhoneNumber = response.getResult().get(0).getPhoneno();
                            String userReferralCode = response.getResult().get(0).getPhoneno();

                            getDataManager().updateUserInfo(userId, UserName, UserEmail, userPhoneNumber, userReferralCode);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    getNavigator().openHomeActivity(true);
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
                getNavigator().openHomeActivity(false);
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }

    public void loginClick() {
        getNavigator().login();
    }

    public void userContinueClick(String phoneNumber, int otp, int otpId) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_OTP_VERIFICATION, OtpResponse.class, new OtpRequest(phoneNumber, otp, otpId), new Response.Listener<OtpResponse>() {
            @Override
            public void onResponse(OtpResponse response) {
                int CurrentuserId = 0;
                if (response != null) {
                    passwordstatus = response.getPasswordstatus();
                    otpStatus = response.getOtpstatus();
                    genderstatus = response.getGenderstatus();
                    oId.set(String.valueOf(response.getOid()));
                    userId.set(String.valueOf(response.getUserid()));
                    CurrentuserId = response.getUserid();

                    if (genderstatus) {
                        getNavigator().openHomeActivity(true);
                    } else {
                        getNavigator().nameGenderScreen();
                    }
                }

                getDataManager().updateUserInfo(CurrentuserId, null, null, null, null);

                int userId = getDataManager().getCurrentUserId();
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
