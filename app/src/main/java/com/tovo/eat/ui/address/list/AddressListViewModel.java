package com.tovo.eat.ui.address.list;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class AddressListViewModel extends BaseViewModel<AddressListNavigator> {


    public ObservableList<AddressListResponse.Result> addrressListItemViewModels = new ObservableArrayList<>();


    public ObservableBoolean emptyAddress = new ObservableBoolean();


    boolean haveAddress = false;
    private MutableLiveData<List<AddressListResponse.Result>> addrressListItemsLiveData;

    public AddressListViewModel(DataManager dataManager) {
        super(dataManager);
        addrressListItemsLiveData = new MutableLiveData<>();
        fetchRepos();
    }
    public ObservableList<AddressListResponse.Result> getAddrressListItemViewModels() {
        return addrressListItemViewModels;
    }

    public MutableLiveData<List<AddressListResponse.Result>> getAddrressListItemsLiveData() {
        return addrressListItemsLiveData;
    }
    public void addDishItemsToList(List<AddressListResponse.Result> ordersItems) {
        addrressListItemViewModels.clear();
        addrressListItemViewModels.addAll(ordersItems);
    }

    public void goBack() {
        getNavigator().goBack();
    }

    public void addAddress() {
        getNavigator().addNewAddress();
    }

    public void deleteAddress(Long aid) {
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_DELETE_ADDRESS_URL, CommonResponse.class, new AddressDeleteRequest(aid), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        if (getDataManager().getAddressId().equals(aid)) {

                            getDataManager().updateCurrentAddress("Current location", null, 0.0, 0.0, null, null);


                            getDataManager().setCurrentAddress(null);
                        }
                        getNavigator().showToast(response.getMessage());
                        getNavigator().addresDeleted();
                    }
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

    public void fetchRepos() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_ADD_ADDRESS_LIST_URL + getDataManager().getCurrentUserId(), AddressListResponse.class, new Response.Listener<AddressListResponse>() {
                @Override
                public void onResponse(AddressListResponse response) {
                    if (response != null) {

                        if (response.getResult().size() == 0) {

                            haveAddress = false;
                            getNavigator().noAddress();
                            emptyAddress.set(true);
                            getDataManager().setHomeAddressAdded(false);
                            getDataManager().setOfficeAddressAdded(false);

                        } else {
                            emptyAddress.set(false);
                            addrressListItemsLiveData.setValue(response.getResult());
                            haveAddress = true;


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
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }
}
