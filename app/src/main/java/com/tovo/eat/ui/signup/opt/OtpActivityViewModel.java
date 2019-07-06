package com.tovo.eat.ui.signup.opt;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.signup.namegender.TokenRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

public class OtpActivityViewModel extends BaseViewModel<OtpActivityNavigator> {

    public final ObservableBoolean otp = new ObservableBoolean();
    public final ObservableField<String> userId = new ObservableField<>();
    public final ObservableField<String> oId = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();


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
        try {

        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_LOGIN_MAIN, LoginResponse.class, new LoginRequest(strPhoneNumber, strPassword), new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse response) {
                if (response != null) {
                    if (response.getStatus()) {
                        if (response.getResult() != null && response.getResult().size() > 0) {
                            try {
                                int userId = response.getResult().get(0).getUserid();
                                String UserName = response.getResult().get(0).getName();
                                String UserEmail = response.getResult().get(0).getEmail();
                                String userPhoneNumber = response.getResult().get(0).getPhoneno();
                                String userReferralCode = response.getResult().get(0).getPhoneno();

                                getDataManager().updateUserInformation(userId, UserName, UserEmail, userPhoneNumber, userReferralCode);

                                if (response.getResult().get(0).getAid()!=null) {
                                    getDataManager().setCurrentAddressTitle(response.getResult().get(0).getAddressTitle());
                                    getDataManager().setCurrentLat(response.getResult().get(0).getLat());
                                    getDataManager().setCurrentLng(response.getResult().get(0).getLon());
                                    getDataManager().setAddressId(response.getResult().get(0).getAid());
                                    getDataManager().setCurrentAddress(response.getResult().get(0).getAddress());

                                }
                                getNavigator().loginSuccess();

                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        getNavigator().loginFailure();
                    }
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
                getNavigator().loginFailure();
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        }catch (Exception ee){

            ee.printStackTrace();

        }
    }

    public void loginClick() {
        getNavigator().login();
    }

    public void userContinueClick(String phoneNumber, int otp, int otpId) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {


        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_OTP_VERIFICATION, OtpResponse.class, new OtpRequest(phoneNumber, otp, otpId), new Response.Listener<OtpResponse>() {
            @Override
            public void onResponse(OtpResponse response) {

                try {
                    int CurrentuserId = 0;

                    if (response != null) {

                        if (response.getStatus() != null)
                            if (response.getStatus()) {
                                passwordstatus = response.getPasswordstatus();
                                otpStatus = response.getOtpstatus();
                                genderstatus = response.getGenderstatus();

                                getDataManager().updateEmailStatus(response.getEmailstatus());

                                userId.set(String.valueOf(response.getUserid()));
                                CurrentuserId = response.getUserid();


                                getDataManager().saveRegionId(response.getRegionid());

                                if (response.getResult().size() != 0) {
                                    getDataManager().setCurrentAddressTitle(response.getResult().get(0).getAddressTitle());
                                    getDataManager().setCurrentLat(response.getResult().get(0).getLat());
                                    getDataManager().setCurrentLng(response.getResult().get(0).getLon());
                                    getDataManager().setAddressId(response.getResult().get(0).getAid());

                                }

                                getDataManager().updateUserInformation(CurrentuserId, null, null, null, null);

                                if (genderstatus) {
                                    getNavigator().openHomeActivity(true);
                                } else {
                                    getNavigator().nameGenderScreen();
                                }


                                if (response.getResult().get(0).getAid()!=null) {
                                    getDataManager().setCurrentAddressTitle(response.getResult().get(0).getAddressTitle());
                                    getDataManager().setCurrentLat(response.getResult().get(0).getLat());
                                    getDataManager().setCurrentLng(response.getResult().get(0).getLon());
                                    getDataManager().setAddressId(response.getResult().get(0).getAid());
                                    getDataManager().setCurrentAddress(response.getResult().get(0).getAddress());

                                }


                            } else {
                                getNavigator().loginFailure();
                            }
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                } catch (Exception ee) {

                    ee.printStackTrace();
                }

            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        }catch (Exception ee){

            ee.printStackTrace();

        }
    }


    public void goBack() {


        getNavigator().goBack();
    }

    public void saveToken(String token) {
        int userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {


        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_FCM_TOKEN_URL, CommonResponse.class, new TokenRequest(userIdMain, token), new Response.Listener<CommonResponse>() {
            @Override
            public void onResponse(CommonResponse response) {
                if (response != null) {

                    if (response.isStatus()) {


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
        }catch (Exception ee){

            ee.printStackTrace();

        }
    }
}
