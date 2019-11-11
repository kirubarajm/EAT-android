package com.tovo.eat.ui.signup;

import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.AppSignatureHashHelper;
import com.tovo.eat.utilities.MvvmApp;

public class SignUpActivityViewModel extends BaseViewModel<SignUpActivityNavigator> {

    public final ObservableField<String> otp = new ObservableField<>();
    public boolean passwordstatus;
    public boolean otpStatus;
    public boolean genderstatus;
    long OtpId;
    long userId;
    Response.ErrorListener errorListener;

    public SignUpActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void usersLoginMain() {
        getNavigator().verifyUser();
    }


    public void faqs() {

        getNavigator().faqs();

    }


    public void privacy() {

        getNavigator().privacy();


    }

    public void termsandconditions() {

        getNavigator().termsandconditions();


    }


    public void users(String phoneNumber) {


        AppSignatureHashHelper appSignatureHashHelper = new AppSignatureHashHelper(MvvmApp.getInstance());

        // This code requires one time to get Hash keys do comment and share key
        Log.e("OTP", "Apps Hash Key: " + appSignatureHashHelper.getAppSignatures().get(0));

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SIGN_UP, SignUpResponse.class, new SignUpRequest(phoneNumber, appSignatureHashHelper.getAppSignatures().get(0)), new Response.Listener<SignUpResponse>() {
                @Override
                public void onResponse(SignUpResponse response) {
                    if (response != null) {
                        if (response.getStatus()) {
                            passwordstatus = response.getPasswordstatus();
                            otpStatus = response.getOtpstatus();
                            genderstatus = response.getGenderstatus();
                            getDataManager().updateUserGender(genderstatus);
                            getDataManager().updateUserPasswordStatus(passwordstatus);
                            if (response.getOid() != null) {
                                OtpId = response.getOid();

                                if (getNavigator() != null)
                                    getNavigator().otpScreenFalse(OtpId);
                            }
                        }
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getNavigator().loginError(false);
                    setIsLoading(false);
                }
            }, AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {
            ee.printStackTrace();

        }

    }

}
