package com.tovo.eat.ui.account.orderhistory.ordersview;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
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

public class OrderHistoryActivityViewModelView extends BaseViewModel<OrderHistoryActivityViewNavigator> {

    public ObservableList<OrdersHistoryActivityResponse.Result.Item> ordersItemViewModels = new ObservableArrayList<>();
    private MutableLiveData<List<OrdersHistoryActivityResponse.Result.Item>> ordersItemsLiveData;

    public final ObservableField<String> kitchenName = new ObservableField<>();
    public final ObservableField<String> home = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> paymentType = new ObservableField<>();
    public final ObservableField<String> strPaymentType = new ObservableField<>();

    public OrderHistoryActivityViewModelView(DataManager dataManager) {
        super(dataManager);
        ordersItemsLiveData = new MutableLiveData<>();
        fetchRepos();
    }

    public void addOrdersListItemsToList(List<OrdersHistoryActivityResponse.Result.Item> ordersItems) {
        ordersItemViewModels.clear();
        ordersItemViewModels.addAll(ordersItems);
    }

    public ObservableList<OrdersHistoryActivityResponse.Result.Item> getOrdersItemViewModels() {
        return ordersItemViewModels;
    }

    public MutableLiveData<List<OrdersHistoryActivityResponse.Result.Item>> getOrders() {
        return ordersItemsLiveData;
    }

    public void fetchRepos() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);

            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_ORDERS_HISTORY_VIEW, OrdersHistoryActivityResponse.class, new Response.Listener<OrdersHistoryActivityResponse>() {
                @Override
                public void onResponse(OrdersHistoryActivityResponse response) {
                    try {
                        if (response != null && response.getResult() != null) {
                            ordersItemsLiveData.setValue(response.getResult().get(0).getItems());

                            kitchenName.set(response.getResult().get(0).getMakeitdetail().getName());
                            address.set(response.getResult().get(0).getLocality());
                            price.set(String.valueOf(response.getResult().get(0).getPrice()));
                            paymentType.set(String.valueOf(response.getResult().get(0).getPaymentType()));
                            Log.e("----response:---------", String.valueOf(response.getSuccess()));
                            setIsLoading(false);
                            if (paymentType.equals("0"))
                            {
                                strPaymentType.set("Cash On Delivery");
                            }else {
                                strPaymentType.set("Online Payment");
                            }
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
                        setIsLoading(false);
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
}
