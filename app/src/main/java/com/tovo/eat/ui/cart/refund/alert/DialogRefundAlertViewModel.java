package com.tovo.eat.ui.cart.refund.alert;

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

public class DialogRefundAlertViewModel extends BaseViewModel<DialogRefundAlertCallBack> {


    public DialogRefundAlertViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void dialogConfirm() {
        getNavigator().confirmClick();
    }

    public void dialogCancel() {
        getNavigator().cancelClick();
    }



    public void cashMode() {
        if (getDataManager().getEmailStatus()) {
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
                                        getDataManager().setCartDetails("");
                                        getNavigator().orderCompleted();
                                        getDataManager().saveRefundId(0);
                                        getDataManager().saveCouponId(0);
                                        getDataManager().saveCouponCode(null);

                                    } else {



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

    }
}
