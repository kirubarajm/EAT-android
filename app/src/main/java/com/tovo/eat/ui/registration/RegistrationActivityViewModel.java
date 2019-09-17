package com.tovo.eat.ui.registration;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import java.util.List;

public class RegistrationActivityViewModel extends BaseViewModel<RegistrationActivityNavigator> {

    public ObservableList<RegionResponse.Result> regionItemViewModels = new ObservableArrayList<>();
    List<RegionResponse.Result> regionList;
    Response.ErrorListener errorListener;
    private MutableLiveData<List<RegionResponse.Result>> regionItemsLiveData;

    public RegistrationActivityViewModel(DataManager dataManager) {
        super(dataManager);
        regionItemsLiveData = new MutableLiveData<>();
        fetchRegionList();
    }

    public void addRegionListItemsToList(List<RegionResponse.Result> ordersItems) {
        regionItemViewModels.clear();
        regionItemViewModels.addAll(ordersItems);
    }

    public ObservableList<RegionResponse.Result> getRegionItemViewModels() {
        return regionItemViewModels;
    }

    public MutableLiveData<List<RegionResponse.Result>> getRegions() {
        return regionItemsLiveData;
    }

    public void userProceed() {
        new Analytics().sendClickData(AppConstants.SCREEN_GET_EMAIL,AppConstants.CLICK_SAVE);

        getNavigator().usersRegistrationMain();
    }

    public void userRegistrationServiceCall(String strEmail) {
        int userId = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_REGISTRATION, RegistrationResponse.class, new RegistrationRequest(userId, strEmail), new Response.Listener<RegistrationResponse>() {
                @Override
                public void onResponse(RegistrationResponse response) {
                    if (response != null) {
                        if (response.getStatus()) {
                            getDataManager().updateUserPasswordStatus(true);
                            getDataManager().updateEmailStatus(true);
                            getNavigator().regSuccess(response.getMessage());
                        } else {
                            Toast.makeText(MvvmApp.getInstance(), response.getMessage(), Toast.LENGTH_SHORT).show();


                            getDataManager().updateUserPasswordStatus(false);
                            getNavigator().regFailure();
                        }

                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    getNavigator().regFailure();
                }
            },AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void fetchRegionList() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {


            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_REGION_LIST, RegionResponse.class, new Response.Listener<RegionResponse>() {
                @Override
                public void onResponse(RegionResponse response) {
                    if (response != null) {
                        regionList = response.getResult();
                        getNavigator().regionList(regionList);
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
