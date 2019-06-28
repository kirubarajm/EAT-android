package com.tovo.eat.ui.cart.refund;

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

public class RefundListViewModel extends BaseViewModel<RefundListNavigator> {


    public ObservableList<RefundListResponse.Result> refundListItemViewModels = new ObservableArrayList<>();
    boolean haveAddress = false;
    private MutableLiveData<List<RefundListResponse.Result>> refundListItemsLiveData;

    public RefundListViewModel(DataManager dataManager) {
        super(dataManager);
        refundListItemsLiveData = new MutableLiveData<>();

        fetchRepos();
    }



    public void saveRefundId(int id){
        getDataManager().saveRefundId(id);
    }


    public ObservableList<RefundListResponse.Result> getRefundListItemViewModels() {
        return refundListItemViewModels;
    }

    public void setRefundListItemViewModels(ObservableList<RefundListResponse.Result> refundListItemViewModels) {
        this.refundListItemViewModels = refundListItemViewModels;
    }

    public MutableLiveData<List<RefundListResponse.Result>> getRefundListItemsLiveData() {
        return refundListItemsLiveData;
    }

    public void setRefundListItemsLiveData(MutableLiveData<List<RefundListResponse.Result>> refundListItemsLiveData) {
        this.refundListItemsLiveData = refundListItemsLiveData;
    }

    public void addDishItemsToList(List<RefundListResponse.Result> ordersItems) {
        refundListItemViewModels.clear();
        refundListItemViewModels.addAll(ordersItems);

    }

    public void goBack() {

        getNavigator().goBack();
    }

    public void fetchRepos() {

        if (!MvvmApp.getInstance().onCheckNetWork()) return;

        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.EAT_REFUND_LIST_URL + getDataManager().getCurrentUserId(), RefundListResponse.class, new Response.Listener<RefundListResponse>() {
                @Override
                public void onResponse(RefundListResponse response) {
                    if (response != null) {

                        if (response.getResult().size() == 0) {

                            getNavigator().noAddress();

                        } else {

                            refundListItemsLiveData.setValue(response.getResult());

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



}
