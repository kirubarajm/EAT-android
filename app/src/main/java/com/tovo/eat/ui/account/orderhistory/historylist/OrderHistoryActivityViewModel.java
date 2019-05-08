package com.tovo.eat.ui.account.orderhistory.historylist;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
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

public class OrderHistoryActivityViewModel extends BaseViewModel<OrderHistoryActivityNavigator> {

    public ObservableList<OrdersHistoryListResponse.Result> ordersItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<OrdersHistoryListResponse.Result>> ordersItemsLiveData;
    public final ObservableField<String> makeItLocality = new ObservableField<>();

    public OrderHistoryActivityViewModel(DataManager dataManager) {
        super(dataManager);
        ordersItemsLiveData = new MutableLiveData<>();
        fetchRepos(0);
    }

    public void addOrdersListItemsToList(List<OrdersHistoryListResponse.Result> ordersItems) {
        ordersItemViewModels.clear();
        ordersItemViewModels.addAll(ordersItems);
    }

    public ObservableList<OrdersHistoryListResponse.Result> getOrdersItemViewModels() {
        return ordersItemViewModels;
    }

    public MutableLiveData<List<OrdersHistoryListResponse.Result>> getOrders() {
        return ordersItemsLiveData;
    }

    public void fetchRepos(int val) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            long userId = getDataManager().getCurrentUserId();
            //String strUserId = String.valueOf(userId);
            if (val == 0) {
                setIsLoading(true);
            }
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_ORDERS_HISTORY_LIST+userId, OrdersHistoryListResponse.class, new Response.Listener<OrdersHistoryListResponse>() {
                @Override
                public void onResponse(OrdersHistoryListResponse response) {
                    try {
                        if (response != null && response.getResult() != null) {
                            ordersItemsLiveData.setValue(response.getResult());
                            Log.e("----response:---------", String.valueOf(response.getSuccess()));
                            if (val == 0) {
                                setIsLoading(false);
                            }
                            getNavigator().onRefreshSuccess();
                        }
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        Log.e("", error.getMessage());
                        if (val == 0) {
                            setIsLoading(false);
                        }
                        getNavigator().onRefreshFailure();
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            });

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void onRefreshLayout() {
        getNavigator().onRefreshLayout();
    }



    public void goBack(){
        getNavigator().goBack();

    }



}
