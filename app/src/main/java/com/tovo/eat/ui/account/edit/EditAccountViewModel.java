package com.tovo.eat.ui.account.edit;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class EditAccountViewModel extends BaseViewModel<EditAccountNavigator> {

    Response.ErrorListener errorListener;

    public EditAccountViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void proceed() {
        getNavigator().proceedClick();
    }

    public void male() {
        getNavigator().male();
    }

    public void feMale() {
        getNavigator().female();
    }

    public void insertNameGenderServiceCall(String name,String email, int gender,int regionId) {
        int userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {




        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_NAME_GENDER_INSERT, EditAccountResponse.class, new EditAccountRequest(userIdMain, name, gender,regionId), new Response.Listener<EditAccountResponse>() {
            @Override
            public void onResponse(EditAccountResponse response) {
                if (response != null) {
                    Log.i("", "" + response.getSuccess());
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
                getNavigator().genderFailure("Failed to update");
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        }catch (Exception ee){

            ee.printStackTrace();

        }
    }

    public void goBack(){
        getNavigator().goBack();
    }


    public void regionList() {

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_MASTER_REGION_LIST, RegionSearchModel.class, new Response.Listener<RegionSearchModel>() {
                @Override
                public void onResponse(RegionSearchModel response) {


                    getNavigator().regionListLoaded(response.getResult());


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee){

        ee.printStackTrace();

    }


    }

}
