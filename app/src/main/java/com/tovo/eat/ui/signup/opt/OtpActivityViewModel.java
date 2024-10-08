package com.tovo.eat.ui.signup.opt;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.MotionEvent;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.signup.SignUpRequest;
import com.tovo.eat.ui.signup.SignUpResponse;
import com.tovo.eat.ui.signup.namegender.TokenRequest;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

public class OtpActivityViewModel extends BaseViewModel<OtpActivityNavigator> {

    public final ObservableBoolean otp = new ObservableBoolean();
    public final ObservableField<String> userId = new ObservableField<>();
    public final ObservableField<String> oId = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();
    public final ObservableField<String> number = new ObservableField<>();
    public final ObservableField<String> timer = new ObservableField<>();

    public long OtpId=0;

    public boolean passwordstatus;
    public boolean otpStatus;
    public boolean genderstatus;
    Response.ErrorListener errorListener;

    public OtpActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void continueClick() {
        if (getNavigator()!=null)
        getNavigator().continueClick();
    }

    public void forgotPasswordClick() {
        getNavigator().forgotPassword();
    }




/*
    @BindingAdapter("touchListener")
    public void setTouchListener(View self, boolean value){
        self.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                // Check if the button is PRESSED
                if (event.getAction() == MotionEvent.ACTION_DOWN){
                    //do some thing
                }// Check if the button is RELEASED
                else if (event.getAction() == MotionEvent.ACTION_UP) {
                    //do some thing
                }
                return false;
            }
        });
    }*/


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
                                    long userId = response.getResult().get(0).getUserid();
                                    String UserName = response.getResult().get(0).getName();
                                    String UserEmail = response.getResult().get(0).getEmail();
                                    String userPhoneNumber = response.getResult().get(0).getPhoneno();
                                    String userReferralCode = response.getResult().get(0).getPhoneno();

                                    getDataManager().updateUserInformation(userId, UserName, UserEmail, userPhoneNumber, userReferralCode);

                                    if (response.getResult().get(0).getAid() != null) {
                                        getDataManager().setCurrentAddressTitle(response.getResult().get(0).getAddressTitle());
                                        getDataManager().setCurrentLat(response.getResult().get(0).getLat());
                                        getDataManager().setCurrentLng(response.getResult().get(0).getLon());
                                        getDataManager().setAddressId(response.getResult().get(0).getAid());
                                        getDataManager().setCurrentAddress(response.getResult().get(0).getAddress());

                                    }

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                            }

                            getNavigator().loginSuccess();
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
            },AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void loginClick() {
        getNavigator().login();
    }

    public void userContinueClick(String phoneNumber, int otp) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;



        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_OTP_VERIFICATION, OtpResponse.class, new OtpRequest(phoneNumber, otp, OtpId), new Response.Listener<OtpResponse>() {
                @Override
                public void onResponse(OtpResponse response) {

                    try {
                        long CurrentuserId = 0;

                        if (response != null) {

                            if (response.getStatus() != null)
                                if (response.getStatus()) {
                                    passwordstatus = response.getPasswordstatus();
                                    otpStatus = response.getOtpstatus();
                                    genderstatus = response.getGenderstatus();


                                    new Analytics().userLogin(response.getUserid(),phoneNumber);


                                    getDataManager().saveApiToken(response.getToken());

                                    getDataManager().setRazorpayCustomerId(response.getRazerCustomerid());


                                    getDataManager().updateEmailStatus(response.getEmailstatus());


                                    getDataManager().setCurrentUserId(response.getUserid());


                                  /*  FreshchatUser freshUser= Freshchat.getInstance(MvvmApp.getInstance()).getUser();
                                    freshUser.setPhone("+91", String.valueOf(phoneNumber));
                                    Freshchat.getInstance(MvvmApp.getInstance()).setUser(freshUser);*/



                                    userId.set(String.valueOf(response.getUserid()));
                                    CurrentuserId = response.getUserid();


                                    if (response.getRegionid() == null) {
                                        getDataManager().saveRegionId(0);
                                    }else {
                                        getDataManager().saveRegionId(response.getRegionid());
                                    }


                                    if (response.getResult()!=null&& response.getResult().size() > 0) {


                                        if (response.getResult().get(0).getAid()!=null&&response.getResult().get(0).getAid()!=0) {

                                            getDataManager().setCurrentAddressTitle(response.getResult().get(0).getAddressTitle());
                                            getDataManager().setCurrentLat(response.getResult().get(0).getLat());
                                            getDataManager().setCurrentLng(response.getResult().get(0).getLon());
                                            getDataManager().setAddressId(response.getResult().get(0).getAid());
                                            getDataManager().setCurrentAddress(response.getResult().get(0).getAddress());
                                            getDataManager().setCurrentAddressArea(response.getResult().get(0).getLocality());

                                        }


                                        long cuserid = response.getResult().get(0).getUserid();
                                        String UserName = response.getResult().get(0).getName();
                                        String UserEmail = response.getResult().get(0).getEmail();
                                        String userPhoneNumber = response.getResult().get(0).getPhoneno();
                                        String userReferralCode = response.getResult().get(0).getReferalcode();
                                        getDataManager().updateUserInformation(cuserid, UserName, UserEmail, userPhoneNumber, userReferralCode);
                                        getDataManager().setRegionId(response.getResult().get(0).getRegionid());


                                    }





                            //        getDataManager().updateUserInformation(CurrentuserId, null, null, null, null);

                                    if (genderstatus) {
                                        getNavigator().openHomeActivity(true);
                                    } else {
                                        getNavigator().nameGenderScreen();
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
                    getNavigator().loginFailure();
                }
            },AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void resendClick(){

        getNavigator().resend();

    }

    public void resendOtp() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_SIGN_UP, SignUpResponse.class, new SignUpRequest(number.get()), new Response.Listener<SignUpResponse>() {
                @Override
                public void onResponse(SignUpResponse response) {
                    if (response != null) {

                         OtpId = response.getOid();
                        setIsLoading(false);
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);
                }
            },AppConstants.API_VERSION_ONE);

        /*gsonRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 2000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 2;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });*/
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }
    public void goBack() {
        getNavigator().goBack();
    }

    public void saveToken(String token) {
        long userIdMain = getDataManager().getCurrentUserId();
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
            },AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }
}
