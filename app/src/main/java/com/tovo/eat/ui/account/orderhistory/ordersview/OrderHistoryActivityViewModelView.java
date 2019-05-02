package com.tovo.eat.ui.account.orderhistory.ordersview;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.MvvmApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderHistoryActivityViewModelView extends BaseViewModel<OrderHistoryActivityViewNavigator> {

    public final ObservableField<String> kitchenName = new ObservableField<>();
    public final ObservableField<String> home = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> paymentType = new ObservableField<>();
    public final ObservableField<String> strPaymentType = new ObservableField<>();
    public final ObservableField<String> actualDeliveryTime = new ObservableField<>();
    public ObservableList<OrdersHistoryActivityResponse.Result.Item> ordersItemViewModels = new ObservableArrayList<>();
    CartRequestPojo cartRequestPojo;
    List<CartRequestPojo.Cartitem> orderitems;
    private MutableLiveData<List<OrdersHistoryActivityResponse.Result.Item>> ordersItemsLiveData;


    public OrderHistoryActivityViewModelView(DataManager dataManager) {
        super(dataManager);
        ordersItemsLiveData = new MutableLiveData<>();
        cartRequestPojo = new CartRequestPojo();
        orderitems = new ArrayList<>();


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


    public void orderRepeat() {

        if (getDataManager().getCartDetails() != null) {
            getNavigator().clearCart();
        } else {
            orderAvailable();
        }

    }

    public void orderAvailable() {

        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        getDataManager().setCartDetails(json);

        getNavigator().orderRepeat();


    }


    public void fetchRepos() {
        int userId = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);

            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_ORDERS_HISTORY_VIEW + userId, OrdersHistoryActivityResponse.class, new Response.Listener<OrdersHistoryActivityResponse>() {
                @Override
                public void onResponse(OrdersHistoryActivityResponse response) {
                    try {
                        if (response != null && response.getResult() != null) {
                            ordersItemsLiveData.setValue(response.getResult().get(0).getItems());

                            kitchenName.set(response.getResult().get(0).getMakeitdetail().getName());
                            address.set(response.getResult().get(0).getLocality());
                            price.set(String.valueOf(response.getResult().get(0).getPrice()));
                            paymentType.set(String.valueOf(response.getResult().get(0).getPaymentType()));
                            //actualDeliveryTime.set("Order delivered on "+String.valueOf(response.getResult().get(0).getMoveitActualDeliveredTime()));
                            Log.e("----response:---------", String.valueOf(response.getSuccess()));
                            setIsLoading(false);
                            if (paymentType.equals("0")) {
                                strPaymentType.set("Cash On Delivery");
                            } else {
                                strPaymentType.set("Online Payment");
                            }

                            try {
                                if (response.getResult().get(0).getMoveitActualDeliveredTime() != null) {
                                    String strDate = response.getResult().get(0).getMoveitActualDeliveredTime();
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                    DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    String outputDateStr = "";
                                    //Date  date1 = new Date(strDate);
                                    Date date = currentFormat.parse(strDate);
                                    outputDateStr = dateFormat.format(date);
                                    actualDeliveryTime.set("Order delivered on " + outputDateStr);
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            // Repeat Order


                            cartRequestPojo.setMakeitUserid(response.getResult().get(0).getMakeitdetail().userid);
                            for (int i = 0; i < response.getResult().get(0).getItems().size(); i++) {

                                CartRequestPojo.Cartitem cartitem = new CartRequestPojo.Cartitem();
                                cartitem.setProductid(response.getResult().get(0).getItems().get(i).getProductid());
                                cartitem.setQuantity(response.getResult().get(0).getItems().get(i).getQuantity());
                                orderitems.add(cartitem);
                            }

                            cartRequestPojo.setCartitems(orderitems);


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
