package com.tovo.eat.ui.track.orderdetails;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.account.orderhistory.historylist.OrdersHistoryListResponse;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityResponse;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.cart.CartPageResponse;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.MvvmApp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDetailsViewModel extends BaseViewModel<OrderDetailsNavigator> {

    public final ObservableField<String> kitchenName = new ObservableField<>();
    public final ObservableField<String> home = new ObservableField<>();
    public final ObservableField<String> address = new ObservableField<>();
    public final ObservableField<String> price = new ObservableField<>();
    public final ObservableField<String> paymentType = new ObservableField<>();
    public final ObservableField<String> strPaymentType = new ObservableField<>();
    public final ObservableField<String> actualDeliveryTime = new ObservableField<>();
    public final ObservableField<String> gst = new ObservableField<>();
    public final ObservableField<String> delivery = new ObservableField<>();
    public final ObservableField<String> placedTime = new ObservableField<>();
    public final ObservableField<String> title = new ObservableField<>();

    public final ObservableField<String> locality = new ObservableField<>();

    public final ObservableBoolean zeroBalance=new ObservableBoolean();
    public ObservableList<OrdersHistoryActivityResponse.Cartdetail> billdetails = new ObservableArrayList<>();
    public MutableLiveData<List<OrdersHistoryActivityResponse.Cartdetail>> cartBillLiveData;



    public ObservableList<OrdersHistoryActivityResponse.Result.Item> ordersItemViewModels = new ObservableArrayList<>();
    CartRequestPojo cartRequestPojo;
    List<CartRequestPojo.Cartitem> orderitems;
    private MutableLiveData<List<OrdersHistoryActivityResponse.Result.Item>> ordersItemsLiveData;


    public OrderDetailsViewModel(DataManager dataManager) {
        super(dataManager);
        ordersItemsLiveData = new MutableLiveData<>();
        cartBillLiveData = new MutableLiveData<>();
        cartRequestPojo = new CartRequestPojo();
        orderitems = new ArrayList<>();

        title.set("Order details #"+getDataManager().getOrderId());

       // fetchRepos();
    }

    public MutableLiveData<List<OrdersHistoryActivityResponse.Cartdetail>> getCartBillLiveData() {
        return cartBillLiveData;
    }

    public void setCartBillLiveData(MutableLiveData<List<OrdersHistoryActivityResponse.Cartdetail>> cartBillLiveData) {
        this.cartBillLiveData = cartBillLiveData;
    }

    public void addBillItemsToList(List<OrdersHistoryActivityResponse.Cartdetail> results) {
        billdetails.clear();
        billdetails.addAll(results);

    }
    public void goBack(){
        getNavigator().goBack();
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

        getDataManager().setCartDetails(null);

        Gson gson = new Gson();
        String json = gson.toJson(cartRequestPojo);
        getDataManager().setCartDetails(json);

        getNavigator().orderRepeat();

    }


    public void fetchRepos(String orderId) {


        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_ORDERS_HISTORY_VIEW + orderId, OrdersHistoryActivityResponse.class, new Response.Listener<OrdersHistoryActivityResponse>() {
                @Override
                public void onResponse(OrdersHistoryActivityResponse response) {
                    try {
                        if (response != null && response.getResult() != null && response.getResult().size()>0) {

                            ordersItemsLiveData.setValue(response.getResult().get(0).getItems());
                            kitchenName.set(response.getResult().get(0).getMakeitdetail().getBrandName());
                            address.set(response.getResult().get(0).getLocality());
                            price.set(String.valueOf(response.getResult().get(0).getPrice()));
                            paymentType.set(String.valueOf(response.getResult().get(0).getPaymentType()));
                            locality.set(String.valueOf(response.getResult().get(0).getCusAddress()));
                            gst.set(String.valueOf(response.getResult().get(0).getGst()));
                            delivery.set(String.valueOf(response.getResult().get(0).getDeliveryCharge()));


                            if (response.getResult().get(0).getPrice()>0){
                                zeroBalance.set(false);
                            }else {
                                zeroBalance.set(true);
                            }


                            cartBillLiveData.setValue(response.getResult().get(0).getCartDetails());




                            try {
                                String strDate = response.getResult().get(0).getOrdertime();
                                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                                DateFormat currentFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String outputDateStr = "";
                                //Date  date1 = new Date(strDate);
                                Date date = currentFormat.parse(strDate);
                                outputDateStr = dateFormat.format(date);

                                placedTime.set("Order placed at " + outputDateStr);

                            } catch (ParseException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();

                            }




                            //actualDeliveryTime.set("Order delivered on "+String.valueOf(response.getResult().get(0).getMoveitActualDeliveredTime()));
                            Log.e("----response:---------", String.valueOf(response.getSuccess()));
                            setIsLoading(false);



                             if (response.getResult().get(0).getPaymentType().equals("1")){

                                 strPaymentType.set("Amount paid through "+response.getResult().get(0).getPaymentTypeName());
                             }else {
                                 strPaymentType.set("Amount to be paid through "+response.getResult().get(0).getPaymentTypeName());
                             }



                            // Repeat Order


                            cartRequestPojo.setMakeitUserid(response.getResult().get(0).getMakeitdetail().getUserid());
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

                        setIsLoading(false);
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            },AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }catch (Exception ee){
            ee.printStackTrace();
        }
    }
}
