package com.tovo.eat.ui.signup.namegender;

import android.databinding.ObservableBoolean;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

public class NameGenderActivityViewModel extends BaseViewModel<NameGenderActivityNavigator> {

    public ObservableBoolean male = new ObservableBoolean();
    public ObservableBoolean haveReferral = new ObservableBoolean();
    public ObservableBoolean referral = new ObservableBoolean();
    public ObservableBoolean regionotherClicked = new ObservableBoolean();

    public ObservableBoolean flagRegion = new ObservableBoolean();
    Response.ErrorListener errorListener;
    int gender = 0;

    public NameGenderActivityViewModel(DataManager dataManager) {
        super(dataManager);
        male.set(true);
    }

    public void proceed() {
        getNavigator().proceedClick();
    }


    public void maleClicked() {
        male.set(true);
        new Analytics().sendClickData(AppConstants.SCREEN_USER_REGISTRATION, AppConstants.CLICK_MALE);

    }


    public void feMaleClicked() {
        male.set(false);
        new Analytics().sendClickData(AppConstants.SCREEN_USER_REGISTRATION, AppConstants.CLICK_FEMALE);
    }


    public void viewReferral() {
        if (referral.get()) {
            referral.set(false);
        } else {
            referral.set(true);
        }

    }


    public void referralCode() {
        haveReferral.set(true);
    }


    public void insertNameGenderServiceCall(String name, int regionId, String referral,String otherRegion) {


        if (male.get()) {
            gender = 1;
        } else {
            gender = 2;
        }

        long userIdMain = getDataManager().getCurrentUserId();
        NameGenderRequest nameGenderRequest;

        if (referral.isEmpty()) {
            nameGenderRequest = new NameGenderRequest(userIdMain, name, gender, regionId);
        } else {

            nameGenderRequest = new NameGenderRequest(userIdMain, name, gender, regionId, referral);
        }


        if (regionId==0){
            if (otherRegion.isEmpty()){
                Toast.makeText(MvvmApp.getInstance(), "Please enter your region", Toast.LENGTH_SHORT).show();
                return;
            }
        }



        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {


            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_NAME_GENDER_INSERT, NameGenderResponse.class, nameGenderRequest, new Response.Listener<NameGenderResponse>() {
                @Override
                public void onResponse(NameGenderResponse response) {
                    if (response != null) {
                        if (getNavigator() != null)
                            if (getNavigator() != null)
                                getNavigator().genderSuccess(response.getMessage());
                        if (response.getStatus()) {

                            getDataManager().updateUserGender(true);
                        }
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().genderFailure("Failed to update");
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }


    public void regionList() {

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_MASTER_REGION_LIST, RegionSearchModel.class, new Response.Listener<RegionSearchModel>() {
                @Override
                public void onResponse(RegionSearchModel response) {

                    if (getNavigator() != null)
                        getNavigator().regionListLoaded(response.getResult());


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            }, AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }

}
