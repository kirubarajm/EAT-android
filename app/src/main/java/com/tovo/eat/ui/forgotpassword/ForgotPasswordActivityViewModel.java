package com.tovo.eat.ui.forgotpassword;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;


public class ForgotPasswordActivityViewModel extends BaseViewModel<ForgotPasswordActivityNavigator> {

    public final ObservableBoolean flagOtpPass = new ObservableBoolean();
    int userId;
    Response.ErrorListener errorListener;
    int otpId;

    public ForgotPasswordActivityViewModel(DataManager dataManager) {
        super(dataManager);
        flagOtpPass.set(true);
    }

    public void proceedOtpClick() {
        getNavigator().otpVerificationProceed();
    }

    public void submitOtpClick() {
        getNavigator().otpVerificationSubmit();
    }

    public void otpServiceCall(String strPhoneNumber) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_FORGOT_PASSWORD, ForgotPasswordResponse.class, new ForgotPasswordRequest(strPhoneNumber), new Response.Listener<ForgotPasswordResponse>() {
            @Override
            public void onResponse(ForgotPasswordResponse response) {
                if (response != null) {
                    if (response.getSuccess()) {
                        otpId = response.getOid();
                    }
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

    public void otpVerificationServiceCall(String strPhoneNumber, String strOtp) {
        int otp = Integer.parseInt(strOtp);
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_OTP_VERIFICATION, OtpVerificationResponse.class, new OtpVerificationRequest(strPhoneNumber, otp, otpId), new Response.Listener<OtpVerificationResponse>() {
            @Override
            public void onResponse(OtpVerificationResponse response) {
                if (response != null) {
                    if (response.getSuccess()) {
                            flagOtpPass.set(false);
                            userId=response.getUserid();
                    }else {
                        getNavigator().otpInvalid();
                    }
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
    public void passwordVerificationServiceCall(String strPassword) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_SET_CONFIRM_PASSWORD, ConfirmPasswordResponse.class, new ConfirmPasswordRequest(userId,strPassword), new Response.Listener<ConfirmPasswordResponse>() {
            @Override
            public void onResponse(ConfirmPasswordResponse response) {
                if (response != null) {
                    if (response.getSuccess()) {
                        getNavigator().confirmPassSuccess();
                    }
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
                getNavigator().confirmPassFailre();
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }

}
