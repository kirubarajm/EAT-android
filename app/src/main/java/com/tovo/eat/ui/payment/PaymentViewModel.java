/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.tovo.eat.ui.payment;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.ui.cart.PlaceOrderRequestPojo;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CartRequestPojo;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentViewModel extends BaseViewModel<PaymentNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableField<String> amount = new ObservableField<>();
    public final ObservableBoolean cardClicked = new ObservableBoolean();
    public final ObservableBoolean netbankingClicked = new ObservableBoolean();
    public final ObservableBoolean upiClicked = new ObservableBoolean();
    public final ObservableBoolean codClicked = new ObservableBoolean();
    public final ObservableBoolean walletClicked = new ObservableBoolean();
    public final ObservableField<String> brandname = new ObservableField<>();
    public final ObservableField<String> products = new ObservableField<>();

    public Long orderid ;
    public int refundBalance = 0;
    public int price = 0;
    public String razorpayCustomerId = null;


    public String transactionId = null;
    public int paymentStatus = 0;
public boolean paymentSuccessNotSent=false;






    public PaymentViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void goBack() {
        getNavigator().goBack();
    }

    public void card() {
        getNavigator().clickCard();
        cardClicked.set(true);
        netbankingClicked.set(false);
        upiClicked.set(false);
        codClicked.set(false);
        walletClicked.set(false);

    }

    public void netbanking() {
        getNavigator().clickNetbanking();
        cardClicked.set(false);
        netbankingClicked.set(true);
        upiClicked.set(false);
        codClicked.set(false);
        walletClicked.set(false);
    }

    public void upi() {
        getNavigator().clickUPI();
        cardClicked.set(false);
        netbankingClicked.set(false);
        upiClicked.set(true);
        codClicked.set(false);
        walletClicked.set(false);
    }

    public void cod() {
        getNavigator().clickCOD();
        cardClicked.set(false);
        netbankingClicked.set(false);
        upiClicked.set(false);
        codClicked.set(true);
        walletClicked.set(false);

    }

    public void wallet() {
        getNavigator().clickCOD();
        cardClicked.set(false);
        netbankingClicked.set(false);
        upiClicked.set(false);
        codClicked.set(false);
        walletClicked.set(true);

    }


    public void cashOnDelivery() {

        new Analytics().sendClickData(AppConstants.SCREEN_PAYMENT, AppConstants.CLICK_COD);

        try {


            if (getDataManager().getAddressId() != 0) {


                if (!MvvmApp.getInstance().onCheckNetWork()) return;

                //   AlertDialog.Builder builder=new AlertDialog.Builder(CartActivity.this.getApplicationContext() );


                List<CartRequestPojo.Cartitem> cartitems = new ArrayList<>();
                List<PlaceOrderRequestPojo.Orderitem> orderitems = new ArrayList<>();

                PlaceOrderRequestPojo placeOrderRequestPojo = new PlaceOrderRequestPojo();

                PlaceOrderRequestPojo.Orderitem orderitem;


                Gson sGson = new GsonBuilder().create();
                CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

                cartitems.addAll(cartRequestPojo.getCartitems());


                for (int i = 0; i < cartitems.size(); i++) {

                    orderitem = new PlaceOrderRequestPojo.Orderitem();
                    orderitem.setProductid(cartitems.get(i).getProductid());
                    orderitem.setQuantity(cartitems.get(i).getQuantity());
                    orderitems.add(orderitem);

                }


                placeOrderRequestPojo.setOrderitems(orderitems);

                placeOrderRequestPojo.setMakeitUserId(cartRequestPojo.getMakeitUserid());

                PlaceOrderRequestPojo placeOrderRequestPojo1 = new PlaceOrderRequestPojo(getDataManager().getCurrentUserId(), cartRequestPojo.getMakeitUserid(), 0, getDataManager().getAddressId(), getDataManager().getRefundId(), getDataManager().getCouponId(), orderitems);


                Gson gson = new Gson();
                String json = gson.toJson(placeOrderRequestPojo1);


                setIsLoading(true);


                JsonObjectRequest jsonObjectRequest = null;
                try {
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CREATE_ORDER_URL, new JSONObject(json), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            if (response != null) {

                                Gson gson = new Gson();
                                CartPaymentResponse cartPaymentResponse = gson.fromJson(response.toString(), CartPaymentResponse.class);

                                if (cartPaymentResponse != null) {

                                    if (cartPaymentResponse.getStatus()) {

                                        long sorderId = cartPaymentResponse.getOrderid();
                                        if (sorderId!=0)
                                        getDataManager().setOrderId(sorderId);


                                        if (cartPaymentResponse.getPrice() != null)
                                            new Analytics().orderPlaced(sorderId, Integer.parseInt(amount.get()));
                                        new Analytics().proceedToPay(sorderId, Integer.parseInt(amount.get()));


                                        getDataManager().currentOrderId(sorderId);
                                        getDataManager().setCartDetails(null);
                                        getNavigator().orderCompleted();


                                    } else {
                                        getNavigator().showToast(cartPaymentResponse.getMessage());
                                        if (cartPaymentResponse.getResult() != null) {

                                            if (cartPaymentResponse.getResult().size() > 0) {
                                                Long orderId = cartPaymentResponse.getResult().get(0).getOrderid();
                                                if (orderId != null)
                                                    getDataManager().setOrderId(orderId);
                                                price = cartPaymentResponse.getResult().get(0).getPrice();
                                                getNavigator().orderGenerated(orderId, getDataManager().getRazorpayCustomerId(), price);

                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }) {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                        }
                    };
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

            } else {
                //  getNavigator().showToast("Please select the address...");
            }
        } catch (Exception ee) {

            ee.printStackTrace();

        }

    }


    public void cashMode() {

        if (getDataManager().getTotalOrders() == 0) {

            cashOnDelivery();
        } else {
            if (getDataManager().getEmailStatus()) {
                cashOnDelivery();
            } else {
                getNavigator().postRegistration(AppConstants.COD_REQUESTCODE);
            }
        }
    }


    public void paymentModeCheck() {

        if (getDataManager().getEmailStatus()) {

            payOnline();

        } else {
            getNavigator().postRegistration(AppConstants.ONLINE_REQUESTCODE);
        }

    }


    public void payOnline() {


        new Analytics().sendClickData(AppConstants.SCREEN_PAYMENT, AppConstants.CLICK_ONLINE);


        if (getDataManager().getAddressId() != 0) {


            if (!MvvmApp.getInstance().onCheckNetWork()) return;

            List<CartRequestPojo.Cartitem> cartitems = new ArrayList<>();
            List<PlaceOrderRequestPojo.Orderitem> orderitems = new ArrayList<>();

            PlaceOrderRequestPojo placeOrderRequestPojo = new PlaceOrderRequestPojo();

            PlaceOrderRequestPojo.Orderitem orderitem;


            Gson sGson = new GsonBuilder().create();
            CartRequestPojo cartRequestPojo = sGson.fromJson(getDataManager().getCartDetails(), CartRequestPojo.class);

            cartitems.addAll(cartRequestPojo.getCartitems());


            for (int i = 0; i < cartitems.size(); i++) {

                orderitem = new PlaceOrderRequestPojo.Orderitem();
                orderitem.setProductid(cartitems.get(i).getProductid());
                orderitem.setQuantity(cartitems.get(i).getQuantity());
                orderitems.add(orderitem);
            }

            placeOrderRequestPojo.setOrderitems(orderitems);

            placeOrderRequestPojo.setMakeitUserId(cartRequestPojo.getMakeitUserid());

            PlaceOrderRequestPojo placeOrderRequestPojo1 = new PlaceOrderRequestPojo(getDataManager().getCurrentUserId(), cartRequestPojo.getMakeitUserid(), 1, getDataManager().getAddressId(), getDataManager().getRefundId(), getDataManager().getCouponId(), orderitems);


            Gson gson = new Gson();
            String json = gson.toJson(placeOrderRequestPojo1);

            Log.e("sfsdfd", json);

            setIsLoading(true);

            JsonObjectRequest jsonObjectRequest = null;
            try {
                jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CREATE_ORDER_URL, new JSONObject(json), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Long sorderId = null;
                        if (response!=null) {

                            Gson gson = new Gson();
                            CartPaymentResponse cartPaymentResponse = gson.fromJson(response.toString(), CartPaymentResponse.class);

                            if (cartPaymentResponse!=null)
                            if (cartPaymentResponse.getStatus()) {
                                if (cartPaymentResponse.getOrderid()!=null) {
                                     sorderId = cartPaymentResponse.getOrderid();
                                     if (sorderId!=null) {
                                         orderid = sorderId;

                                         getDataManager().setOrderId(sorderId);

                                         new Analytics().proceedToPay(orderid, cartPaymentResponse.getPrice());
                                     }
                                }
                                if (getDataManager().getRefundId() != 0) {
                                    if (cartPaymentResponse.getRefundBalance()!=null) {
                                        refundBalance = cartPaymentResponse.getRefundBalance();

                                        getDataManager().saveRefundBalance(refundBalance);
                                    }

                                }
                                if (cartPaymentResponse.getRazerCustomerid()!=null) {
                                    razorpayCustomerId = cartPaymentResponse.getRazerCustomerid();

                                    getDataManager().saveRazorpayCustomerId(razorpayCustomerId);

                                    price = cartPaymentResponse.getPrice();
                                    getNavigator().orderGenerated(sorderId, razorpayCustomerId, price);
                                }

                            } else {

                                if (null != cartPaymentResponse.getResult() && cartPaymentResponse.getResult().size() > 0) {
                                    Long orderId = cartPaymentResponse.getResult().get(0).getOrderid();
                                    if (orderId!=null)
                                    getDataManager().setOrderId(orderId);
                                    price = cartPaymentResponse.getResult().get(0).getPrice();


                                    getNavigator().orderGenerated(orderId, getDataManager().getRazorpayCustomerId(), price);


                                } else {
                                    if (cartPaymentResponse.getMessage()!=null)
                                    getNavigator().showToast(cartPaymentResponse.getMessage());
                                }

                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Log.e("test", error.getMessage());
                        //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                    }
                }) {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                    }
                };
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ee) {

                ee.printStackTrace();

            }

            MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

        }
    }

    public void paymentSuccessData(String paymentId, Integer status,boolean success){

       paymentStatus=status;
       transactionId=paymentId;
       paymentSuccessNotSent=success;
    }


    public void paymentSuccess(String paymentId, Integer status) {

        paymentSuccessData(paymentId,status,true);




        if (status == 1) {

            new Analytics().paymentSuccess(orderid, price);

        } else {
            new Analytics().paymentFailed(orderid, price);
        }


        JsonObjectRequest jsonObjectRequest = null;
        try {

            JSONObject json = new JSONObject();
            json.put("transactionid", paymentId);
            json.put("payment_status", status);
            json.put("orderid", getDataManager().getOrderId());
          //  json.put("price", price);


            if (getDataManager().getCouponId() != 0) {
                json.put("cid", getDataManager().getCouponId());
            }

            if (getDataManager().getRefundId() != 0) {
                json.put("rcid", getDataManager().getRefundId());
                json.put("refund_balance", refundBalance);
            }


            Log.e("asdaf", json.toString());

            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_ORDER_SUCCESS, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response!=null)
                        if (response.getBoolean("status")) {

                            new Analytics().orderPlaced(orderid, price);

                            paymentSuccessData(null,0,false);

                            getNavigator().paymentSuccessed(true);
                            getDataManager().setCartDetails(null);
                            getDataManager().saveRefundId(0);
                            getDataManager().saveCouponId(0);
                        } else {
                            getNavigator().paymentSuccessed(false);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                }
            };
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ee) {

            ee.printStackTrace();

        }

        MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

    }

}
