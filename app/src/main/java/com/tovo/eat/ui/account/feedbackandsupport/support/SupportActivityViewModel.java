package com.tovo.eat.ui.account.feedbackandsupport.support;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

import org.json.JSONObject;

import java.util.Map;

public class SupportActivityViewModel extends BaseViewModel<SupportActivityNavigator> {

    public final ObservableBoolean success = new ObservableBoolean();
    public final ObservableBoolean flagCount = new ObservableBoolean();

    public final ObservableBoolean supportNumber = new ObservableBoolean();

    public final ObservableField<String> count = new ObservableField<String>();
    public ObservableField<String> support = new ObservableField<>();
    public String message = "";
    Response.ErrorListener errorListener;
    Long userId;

    public SupportActivityViewModel(DataManager dataManager) {
        super(dataManager);
        support.set("0");
        supportNumber.set(false);
        userId = getDataManager().getCurrentUserId();
        fetchCountSertviceCall(1);
        getSupportContact();

    }


    public void goBack() {
        getNavigator().goBack();
    }

    public void onRepliesClick() {
        getNavigator().repliesOnClick();
    }

    public void onRefreshLayout() {
        getNavigator().onRefreshLayout();
    }

    public void submitClick() {
        getNavigator().submit();
    }

    public void fetchCountSertviceCall(int val) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        if (val == 1) {
            setIsLoading(true);
        }
        try {
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_QUERY_REPLIES_COUNT, RepliesCountResponse.class,
                    new RepliesCountRequest(AppConstants.EAT, userId), new Response.Listener<RepliesCountResponse>() {
                @Override
                public void onResponse(RepliesCountResponse response) {
                    if (response != null) {
                        if (response.getResult().size() > 0) {
                            count.set(String.valueOf(response.getResult().get(0).getCount()));
                            flagCount.set(true);
                        } else {
                            count.set("0");
                            flagCount.set(false);
                        }
                        if (val == 1) {
                            setIsLoading(false);
                        }
                        if (getNavigator() != null)
                            getNavigator().onRefreshSuccess();
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //getNavigator().loginError(error.getMessage());
                    if (val == 1) {
                        setIsLoading(false);
                    }
                    getNavigator().onRefreshSuccess();
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void insertQueriesServiceCall(String strQueries, int type, Long orderid) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        SupportRequest supportRequest = new SupportRequest(strQueries, AppConstants.EAT, userId, orderid, type);
        try {
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_QUERY_INSERT, SupportResponse.class,
                    supportRequest, new Response.Listener<SupportResponse>() {
                @Override
                public void onResponse(SupportResponse response) {
                    if (response != null) {
                        success.set(response.getSuccess());
                        message = response.getMessage();
                        if (!message.equals("")) {
                            getNavigator().success(String.valueOf(message));
                        }
                        setIsLoading(false);
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //getNavigator().loginError(error.getMessage());
                    setIsLoading(false);
                    getNavigator().failure("Failed to insert");
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    public void callAdmin() {
        getNavigator().callAdmin();
    }

    public void getSupportContact() {
        JsonObjectRequest jsonObjectRequest = null;
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, AppConstants.EAT_CUSTOMER_SUPPORT, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                if (response != null) {

                    Gson gson = new Gson();
                    com.tovo.eat.utilities.SupportResponse supportResponse = gson.fromJson(response.toString(), com.tovo.eat.utilities.SupportResponse.class);

                    if (supportResponse.getStatus()) {
                        getDataManager().saveSupportNumber(String.valueOf(supportResponse.getCustomerSupport()));
                        support.set(String.valueOf(supportResponse.getCustomerSupport()));
                        supportNumber.set(true);
                    }

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
        MvvmApp.getInstance().addToRequestQueue(jsonObjectRequest);

    }
}
