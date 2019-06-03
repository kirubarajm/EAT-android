package com.tovo.eat.ui.address.list;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.address.DefaultAddressRequest;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.filter.FilterRequestPojo;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class AddressListViewModel extends BaseViewModel<AddressListNavigator> {


    public ObservableList<AddressListResponse.Result> addrressListItemViewModels = new ObservableArrayList<>();
    boolean haveAddress = false;
    private MutableLiveData<List<AddressListResponse.Result>> addrressListItemsLiveData;

    public AddressListViewModel(DataManager dataManager) {
        super(dataManager);
        addrressListItemsLiveData = new MutableLiveData<>();

        //    AlertDialog.Builder builder=new AlertDialog.Builder(getDataManager().);
       /* ConnectivityManager cm =
                (ConnectivityManager)getDataManager(). getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();*/

        fetchRepos();
    }

    public ObservableList<AddressListResponse.Result> getAddrressListItemViewModels() {
        return addrressListItemViewModels;
    }

    public void setAddrressListItemViewModels(ObservableList<AddressListResponse.Result> addrressListItemViewModels) {
        this.addrressListItemViewModels = addrressListItemViewModels;
    }

    public MutableLiveData<List<AddressListResponse.Result>> getAddrressListItemsLiveData() {
        return addrressListItemsLiveData;
    }

    public void setAddrressListItemsLiveData(MutableLiveData<List<AddressListResponse.Result>> addrressListItemsLiveData) {
        this.addrressListItemsLiveData = addrressListItemsLiveData;
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


    public void deleteAddress(Integer aid) {
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_DELETE_ADDRESS_URL, CommonResponse.class, new AddressDeleteRequest(aid), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        getNavigator().showToast(response.getMessage());
                        getNavigator().addresDeleted();
                    }
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


    public void setCurrentAddress(AddressListResponse.Result request) {
        getDataManager().updateCurrentAddress(request.getAddressTitle(), request.getAddress(), request.getLat(), request.getLon(), request.getLocality(), request.getAid());



        defaultAddress(request.getAid());


        FilterRequestPojo filterRequestPojo;

        Gson sGson = new GsonBuilder().create();
        filterRequestPojo = sGson.fromJson(getDataManager().getFilterSort(), FilterRequestPojo.class);

        filterRequestPojo.setEatuserid(getDataManager().getCurrentUserId());
        filterRequestPojo.setLat(getDataManager().getCurrentLat());
        filterRequestPojo.setLat(getDataManager().getCurrentLng());

        Gson gson = new Gson();
        String json = gson.toJson(filterRequestPojo);
        getDataManager().setFilterSort(json);
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

                            getDataManager().updateCurrentAddress(null, null, 0.0, 0.0, null, 0);

                            getDataManager().setCurrentAddressTitle(null);

                            getNavigator().listLoaded();

                            addrressListItemsLiveData.setValue(response.getResult());


                            haveAddress = false;

                            getNavigator().noAddress();


                        } else {

                            addrressListItemsLiveData.setValue(response.getResult());
                            Log.e("----response:---------", response.toString());

                            haveAddress = true;

                            getNavigator().listLoaded();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                 //   Log.e("", error.getMessage());
                }
            });


            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception ee){

        ee.printStackTrace();

    }
    }

    public void defaultAddress(Integer aid ){

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.EAT_DEFAULT_ADDRESS, CommonResponse.class,new DefaultAddressRequest(getDataManager().getCurrentUserId(),aid), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {



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
