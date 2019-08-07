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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class PaymentViewModel extends BaseViewModel<PaymentNavigator> {


    public final ObservableBoolean cart = new ObservableBoolean();

    public final ObservableField<String> locationAddress = new ObservableField<>();
    public final ObservableField<String> area = new ObservableField<>();
    public final ObservableField<String> house = new ObservableField<>();
    public final ObservableField<String> landmark = new ObservableField<>();
    public final ObservableField<String> amount = new ObservableField<>();
    public final ObservableBoolean cardClicked = new ObservableBoolean();
    public final ObservableBoolean netbankingClicked = new ObservableBoolean();
    public final ObservableBoolean upiClicked = new ObservableBoolean();
    public final ObservableBoolean codClicked = new ObservableBoolean();
    public final ObservableBoolean walletClicked = new ObservableBoolean();


    public int refundBalance = 0;


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

                Log.e("sfsdfd", json);

                setIsLoading(true);


                JsonObjectRequest jsonObjectRequest = null;
                try {
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CREATE_ORDER_URL, new JSONObject(json), new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {


                                if (response.getBoolean("status")) {

                                    getDataManager().currentOrderId(response.getInt("orderid"));
                                    getDataManager().setCartDetails(null);
                                    getNavigator().orderCompleted();


                                } else {

                                    getNavigator().showToast(response.getString("message"));

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

                        /**
                         * Passing some request headers
                         */
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Content-Type", "application/json");
                            headers.put("accept-version",AppConstants.API_VERSION_ONE);
                            //  headers.put("Authorization","Bearer");
                            headers.put("Authorization","Bearer "+getDataManager().getApiToken());
                            return headers;
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


        if (getDataManager().getTotalOrders() == 0) {
            payOnline();

        } else {
            if (getDataManager().getEmailStatus()) {

                payOnline();

            } else {
                getNavigator().postRegistration(AppConstants.ONLINE_REQUESTCODE);
            }
        }
    }


    public void payOnline() {


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
                        try {


                            if (response.getBoolean("status")) {
                                Integer orderId = response.getInt("orderid");
                                getDataManager().setOrderId(orderId);

                                if (getDataManager().getRefundId() != 0)
                                    refundBalance = response.getInt("refund_balance");

                                getNavigator().orderGenerated(response.getInt("orderid"), response.getString("razer_customerid"), response.getInt("price"));

                            } else {

                                getNavigator().showToast(response.getString("message"));

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  Log.e("test", error.getMessage());
                        //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                    }
                }) {


                    /**
                     * Passing some request headers
                     */
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json");
                        headers.put("accept-version",AppConstants.API_VERSION_ONE);
                        //  headers.put("Authorization","Bearer");
                        headers.put("Authorization","Bearer "+getDataManager().getApiToken());
                        return headers;
                    }
                };
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception ee) {

                ee.printStackTrace();

            }

            MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

        } else {
            //  getNavigator().showToast("Please select the address...");
        }
    }


    public void paymentSuccess(String paymentId, Integer status) {

        JsonObjectRequest jsonObjectRequest = null;
        try {

            JSONObject json = new JSONObject();
            json.put("transactionid", paymentId);
            json.put("payment_status", status);
            json.put("orderid", getDataManager().getOrderId());


            if (getDataManager().getCouponId() != 0) {
                json.put("cid", getDataManager().getCouponId());
            }

            if (getDataManager().getRefundId() != 0) {
                json.put("rcid", getDataManager().getCouponId());
                json.put("refund_balance", refundBalance);
            }


            Log.e("asdaf", json.toString());

            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_ORDER_SUCCESS, json, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        if (response.getBoolean("status")) {
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
                    Log.e("", error.getMessage());
                    //   getNavigator().showToast("Unable to place your order, due to technical issue. Please try again later...");
                }
            }) {

                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json");
                    headers.put("accept-version",AppConstants.API_VERSION_ONE);
                    //  headers.put("Authorization","Bearer");
                    headers.put("Authorization","Bearer "+getDataManager().getApiToken());
                    return headers;
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
