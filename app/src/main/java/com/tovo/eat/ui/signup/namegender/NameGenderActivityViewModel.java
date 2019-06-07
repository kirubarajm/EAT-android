package com.tovo.eat.ui.signup.namegender;

import android.databinding.ObservableBoolean;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryListResponse;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.home.region.RegionSearchModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

public class NameGenderActivityViewModel extends BaseViewModel<NameGenderActivityNavigator> {

    Response.ErrorListener errorListener;

    public ObservableBoolean male=new ObservableBoolean();
    public  ObservableBoolean female=new ObservableBoolean();
    int gender=0;

    public NameGenderActivityViewModel(DataManager dataManager) {
        super(dataManager);
        male.set(true);
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




    public void insertNameGenderServiceCall(String name,int regionId) {



        if (male.get()){
            gender=1;
        }else {
            gender=2;
        }


        int userIdMain = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {


        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_NAME_GENDER_INSERT, NameGenderResponse.class, new NameGenderRequest(userIdMain, name, gender,regionId), new Response.Listener<NameGenderResponse>() {
            @Override
            public void onResponse(NameGenderResponse response) {
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
