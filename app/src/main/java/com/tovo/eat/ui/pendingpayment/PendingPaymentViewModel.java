package com.tovo.eat.ui.pendingpayment;

import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PendingPaymentViewModel extends BaseViewModel<PendingPaymentNavigator> {

    Response.ErrorListener errorListener;


    public final ObservableField<String> order = new ObservableField<>();
    public final ObservableField<String> kitchen = new ObservableField<>();




    public PendingPaymentViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void retryPayment() {
        getNavigator().retry();
    }



    public void cancel(){
        getNavigator().cancel();

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
                json.put("rcid", getDataManager().getRefundId());
                json.put("refund_balance", getDataManager().getRefundBalance());
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
                    headers.put("accept-version", AppConstants.API_VERSION_ONE);
                    //  headers.put("Authorization","Bearer");
                    headers.put("Authorization", "Bearer " + getDataManager().getApiToken());
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
