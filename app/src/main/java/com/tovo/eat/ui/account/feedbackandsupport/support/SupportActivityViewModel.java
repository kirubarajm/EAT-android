package com.tovo.eat.ui.account.feedbackandsupport.support;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class SupportActivityViewModel extends BaseViewModel<SupportActivityNavigator> {

    public final ObservableBoolean success = new ObservableBoolean();
    public final ObservableBoolean flagCount = new ObservableBoolean();
    public final ObservableField<String> count = new ObservableField<String>();
    public String message = "";
    Response.ErrorListener errorListener;
    int userId;

    public SupportActivityViewModel(DataManager dataManager) {
        super(dataManager);
        userId = getDataManager().getCurrentUserId();
        fetchCountSertviceCall(1);
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

    public void insertQueriesServiceCall(String strQueries,int type,int orderid) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        SupportRequest supportRequest=new SupportRequest(strQueries, AppConstants.EAT, userId,orderid,type);
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
}
