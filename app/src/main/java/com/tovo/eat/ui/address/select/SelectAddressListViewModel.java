package com.tovo.eat.ui.address.select;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.address.DefaultAddressRequest;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class SelectAddressListViewModel extends BaseViewModel<SelectAddressListNavigator> {

    public ObservableList<SelectAddressListResponse.Result> selectAddrressListItemViewModels = new ObservableArrayList<>();
    public ObservableBoolean emptyAddress = new ObservableBoolean();
    private MutableLiveData<List<SelectAddressListResponse.Result>> selectAddrressListItemsLiveData;

    public SelectAddressListViewModel(DataManager dataManager) {
        super(dataManager);
        selectAddrressListItemsLiveData = new MutableLiveData<>();
        fetchRepos();
    }

    public ObservableList<SelectAddressListResponse.Result> getSelectAddrressListItemViewModels() {
        return selectAddrressListItemViewModels;
    }

    public MutableLiveData<List<SelectAddressListResponse.Result>> getSelectAddrressListItemsLiveData() {
        return selectAddrressListItemsLiveData;
    }

    public void addDishItemsToList(List<SelectAddressListResponse.Result> ordersItems) {
        selectAddrressListItemViewModels.clear();
        selectAddrressListItemViewModels.addAll(ordersItems);
    }


    public void goBack() {
        getNavigator().goBack();
    }


    public void addAddress() {
        getNavigator().addNewAddress();
    }

    public void defaultAddress(Long aid) {

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_DEFAULT_ADDRESS, CommonResponse.class, new DefaultAddressRequest(getDataManager().getCurrentUserId(), aid), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            }, AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }


    public void updateCurrentAddress(String title, String address, double lat, double lng, String area, Long aid) {

        getDataManager().updateCurrentAddress(title, address, lat, lng, area, aid);

        getDataManager().setCurrentAddress(address);

        defaultAddress(aid);

    }


    public void fetchRepos() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_ADD_ADDRESS_LIST_URL + getDataManager().getCurrentUserId(), SelectAddressListResponse.class, new Response.Listener<SelectAddressListResponse>() {
                @Override
                public void onResponse(SelectAddressListResponse response) {
                    if (response != null) {

                        if (response.getResult().size() == 0) {

                            getNavigator().listLoaded();

                            emptyAddress.set(true);

                        } else {
                            emptyAddress.set(false);
                            selectAddrressListItemsLiveData.setValue(response.getResult());

                        }

                    }
                    getNavigator().listLoaded();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    getNavigator().listLoaded();
                }
            }, AppConstants.API_VERSION_ONE);


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
            getNavigator().listLoaded();
        }
    }


}
