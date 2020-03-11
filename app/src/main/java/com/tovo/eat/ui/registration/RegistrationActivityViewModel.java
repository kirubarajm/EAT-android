package com.tovo.eat.ui.registration;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tovo.eat.api.remote.GsonRequest;
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
import java.util.List;
import java.util.Map;

public class RegistrationActivityViewModel extends BaseViewModel<RegistrationActivityNavigator> {

    public ObservableList<RegionResponse.Result> regionItemViewModels = new ObservableArrayList<>();
    public String grandTotal = "";
    List<RegionResponse.Result> regionList;
    Response.ErrorListener errorListener;
    private MutableLiveData<List<RegionResponse.Result>> regionItemsLiveData;


    public RegistrationActivityViewModel(DataManager dataManager) {
        super(dataManager);
        regionItemsLiveData = new MutableLiveData<>();
        fetchRegionList();
    }

    public void addRegionListItemsToList(List<RegionResponse.Result> ordersItems) {
        regionItemViewModels.clear();
        regionItemViewModels.addAll(ordersItems);
    }


    public MutableLiveData<List<RegionResponse.Result>> getRegions() {
        return regionItemsLiveData;
    }

    public void userProceed() {
        new Analytics().sendClickData(AppConstants.SCREEN_GET_EMAIL, AppConstants.CLICK_SAVE);
        if (getNavigator() != null)
            getNavigator().usersRegistrationMain();
    }

    public void userRegistrationServiceCall(String strEmail) {
        long userId = getDataManager().getCurrentUserId();
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_REGISTRATION, RegistrationResponse.class, new RegistrationRequest(userId, strEmail), new Response.Listener<RegistrationResponse>() {
                @Override
                public void onResponse(RegistrationResponse response) {
                    if (response != null) {
                        if (response.getStatus()) {

                            getDataManager().updateUserPasswordStatus(true);
                            getDataManager().updateEmailStatus(true);
                            getDataManager().setCurrentUserEmail(strEmail);

                            if (grandTotal != null && grandTotal.equals("0")) {
                                cashMode();
                            } else {
                                if (getNavigator() != null)
                                    getNavigator().regSuccess(response.getMessage());
                            }

                        } else {

                            Toast.makeText(MvvmApp.getInstance(), response.getMessage(), Toast.LENGTH_SHORT).show();

                            getDataManager().updateUserPasswordStatus(false);
                            if (getNavigator() != null)
                                getNavigator().regFailure();
                        }

                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().regFailure();
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void fetchRegionList() {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {
            GsonRequest gsonRequest = new GsonRequest(Request.Method.GET, AppConstants.URL_REGION_LIST, RegionResponse.class, new Response.Listener<RegionResponse>() {
                @Override
                public void onResponse(RegionResponse response) {
                    if (response != null) {
                        regionList = response.getResult();
                        if (getNavigator() != null)
                            getNavigator().regionList(regionList);
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);

                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }


    public void cashMode() {

        if (getDataManager().getEmailStatus()) {
            try {

                if (getDataManager().getAddressId() != 0L) {


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

                    PlaceOrderRequestPojo placeOrderRequestPojo1 = new PlaceOrderRequestPojo(getDataManager().getCurrentUserId(), cartRequestPojo.getMakeitUserid(), 0, getDataManager().getAddressId(), getDataManager().getRefundId(), getDataManager().getCouponId(), getDataManager().getOrderInstruction(), orderitems);


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

                                        getDataManager().currentOrderId(response.getLong("orderid"));
                                        getDataManager().setCartDetails(null);
                                        getNavigator().orderCompleted();
                                        getDataManager().saveRefundId(0);
                                        getDataManager().saveCouponId(0);

                                    } else {
                                        if (getNavigator() != null)
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
                                return AppConstants.setHeaders(AppConstants.API_VERSION_ONE);
                            }
                        };
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    assert jsonObjectRequest != null;
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(50000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
