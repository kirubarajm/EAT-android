package com.tovo.eat.ui.account.edit;

import android.databinding.ObservableBoolean;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.ui.signup.namegender.NameGenderResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

public class EditAccountViewModel extends BaseViewModel<EditAccountNavigator> {

    public ObservableBoolean male = new ObservableBoolean();
    public ObservableBoolean female = new ObservableBoolean();
    public ObservableBoolean regionotherClicked = new ObservableBoolean();

    Response.ErrorListener errorListener;
    int gender = 0;


    public EditAccountViewModel(DataManager dataManager) {
        super(dataManager);

        if (getDataManager().getRegionId()==0){
            regionotherClicked.set(true);
        }

    }

    public void proceed() {
        getNavigator().proceedClick();
    }


    public void maleClicked() {
        male.set(true);
        new Analytics().sendClickData(AppConstants.SCREEN_EDIT_MYACCOUNT, AppConstants.CLICK_MALE_SELECTED);
    }

    public void feMaleClicked() {
        male.set(false);
        new Analytics().sendClickData(AppConstants.SCREEN_EDIT_MYACCOUNT, AppConstants.CLICK_FEMALE_SELECTED);

    }


    public void insertNameGenderServiceCall(String name, String email, int regionId,String otherRegion) {


        gender = male.get() ? AppConstants.TYPE_MALE : AppConstants.TYPE_FEMALE;

        if (email.isEmpty()) email = null;


        if (regionId==0){
           /* if (otherRegion.isEmpty()){
                Toast.makeText(MvvmApp.getInstance(), "Please enter your region", Toast.LENGTH_SHORT).show();
                return;
            }*/
        }


        Long userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_NAME_GENDER_INSERT, NameGenderResponse.class, new EditAccountRequest(userIdMain, name, email, gender, regionId,otherRegion), new Response.Listener<NameGenderResponse>() {
                @Override
                public void onResponse(NameGenderResponse response) {
                    if (response != null) {
                        if (response.getStatus() != null && response.getStatus()) {
                            getDataManager().updateUserGender(true);
                            if (response.getMessage() != null)
                                if (getNavigator() != null)
                                    getNavigator().genderSuccess(response.getMessage());
                            getDataManager().saveRegionId(regionId);

                            if (response.getResult()!=null&&response.getResult().size()>0) {
                                long userId = response.getResult().get(0).getUserid();
                                String UserName = response.getResult().get(0).getName();
                                String UserEmail = response.getResult().get(0).getEmail();
                                String userPhoneNumber = response.getResult().get(0).getPhoneno();
                                String userReferralCode = response.getResult().get(0).getReferalcode();
                                getDataManager().updateUserInformation(userId, UserName, UserEmail, userPhoneNumber, userReferralCode);
                                getDataManager().setRegionId(response.getResult().get(0).getRegionid());
                            }

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

    public void goBack() {
        getNavigator().goBack();
    }


    public void regionList() {

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_MASTER_REGION_LIST, RegionSearchModel.class, new Response.Listener<RegionSearchModel>() {
                @Override
                public void onResponse(RegionSearchModel response) {
                    if (response != null)
                        if (response.getResult() != null && response.getResult().size() > 0)
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
