package com.tovo.eat.ui.account.edit;

import android.databinding.ObservableBoolean;
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


    public  ObservableBoolean male=new ObservableBoolean();
    public  ObservableBoolean female=new ObservableBoolean();
    int gender=0;



    public EditAccountViewModel(DataManager dataManager) {
        super(dataManager);
    }

    public void proceed() {
        getNavigator().proceedClick();
    }


    public void maleClicked(){
        male.set(true);

    }

    public void feMaleClicked(){
        male.set(false);

    }


    public void insertNameGenderServiceCall(String name,String email,int regionId) {


        if (male.get()){
             gender=1;
        }else {
             gender=2;
        }


        int userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_NAME_GENDER_INSERT, EditAccountResponse.class, new EditAccountRequest(userIdMain, name,email, gender,regionId), new Response.Listener<EditAccountResponse>() {
            @Override
            public void onResponse(EditAccountResponse response) {
                if (response != null) {
                    Log.i("", "" + response.getSuccess());
                    if (response.getStatus()) {
                        getDataManager().updateUserGender(true);
                        getNavigator().genderSuccess(response.getMessage());
                    }
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setIsLoading(false);
                getNavigator().genderFailure("Failed to update");
            }
        },AppConstants.API_VERSION_ONE);
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
            },AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee){

        ee.printStackTrace();

    }


    }

}
