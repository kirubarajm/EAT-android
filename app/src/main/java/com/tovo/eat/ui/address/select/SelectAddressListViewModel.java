package com.tovo.eat.ui.address.select;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import java.util.List;

public class SelectAddressListViewModel extends BaseViewModel<SelectAddressListNavigator> {


    public ObservableList<SelectAddressListResponse.Result> addrressListItemViewModels = new ObservableArrayList<>();

    private MutableLiveData<List<SelectAddressListResponse.Result>>addrressListItemsLiveData;


    public ObservableList<SelectAddressListResponse.Result> getAddrressListItemViewModels() {
        return addrressListItemViewModels;
    }

    public void setAddrressListItemViewModels(ObservableList<SelectAddressListResponse.Result> addrressListItemViewModels) {
        this.addrressListItemViewModels = addrressListItemViewModels;
    }

    public MutableLiveData<List<SelectAddressListResponse.Result>> getAddrressListItemsLiveData() {
        return addrressListItemsLiveData;
    }

    public void setAddrressListItemsLiveData(MutableLiveData<List<SelectAddressListResponse.Result>> addrressListItemsLiveData) {
        this.addrressListItemsLiveData = addrressListItemsLiveData;
    }

    public SelectAddressListViewModel(DataManager dataManager) {
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

    public void addDishItemsToList(List<SelectAddressListResponse.Result> ordersItems) {
        addrressListItemViewModels.clear();
        addrressListItemViewModels.addAll(ordersItems);

    }


    public void goBack(){

        getNavigator().goBack();
    }




    public void addAddress(){

        getNavigator().addNewAddress();


    }


    public void fetchRepos() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;

     //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_ADD_ADDRESS_LIST_URL+12, SelectAddressListResponse.class,new Response.Listener<SelectAddressListResponse>() {
                @Override
                public void onResponse(SelectAddressListResponse response) {
                    if (response != null) {



                        addrressListItemsLiveData.setValue(response.getResult());
                        Log.e("----response:---------", response.toString());


                        SelectAddressListViewModel.this.getNavigator().listLoaded();


                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("", error.getMessage());
                }
            });




            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }



}
