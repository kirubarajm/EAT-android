package com.tovo.eat.ui.account.feedbackandsupport.support;


import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class SupportActivityViewModel extends BaseViewModel<SupportActivityNavigator> {

    Response.ErrorListener errorListener;
    int moveItUserId;
    public final ObservableBoolean success = new ObservableBoolean();
    public final ObservableBoolean flagCount = new ObservableBoolean();
    public final ObservableField<String> count = new ObservableField<String>();
    public String message = "";

    public SupportActivityViewModel(DataManager dataManager) {
        super(dataManager);
        //long UserId=getDataManager().getCurrentUserId();
        //moveItUserId = Integer.parseInt(String.valueOf(UserId));
        fetchCountSertviceCall(1);
    }

    public void imgBackClick() {
        getNavigator().backClick();
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

    public void fetchCountSertviceCall(int val){
        //long userId = getDataManager().getCurrentUserId();
        //int UserId = Integer.parseInt(String.valueOf(userId));
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        if (val==1) {
            setIsLoading(true);
        }
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_QUERY_REPLIES_COUNT, RepliesCountResponse.class,
                new RepliesCountRequest( AppConstants.EAT,1), new Response.Listener<RepliesCountResponse>() {
            @Override
            public void onResponse(RepliesCountResponse response) {
                if (response != null) {
                    if (response.getResult().size()>0) {
                        count.set(String.valueOf(response.getResult().get(0).getCount()));
                        flagCount.set(true);
                    }else {
                        count.set("0");
                        flagCount.set(false);
                    }
                    if (val==1) {
                        setIsLoading(false);
                    }
                    getNavigator().onRefreshSuccess();
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //getNavigator().loginError(error.getMessage());
                Log.e("", error.toString());
                if (val==1) {
                    setIsLoading(false);
                }
                getNavigator().onRefreshSuccess();
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);

    }

    public void insertQueriesServiceCall(String strQueries) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_QUERY_INSERT, SupportResponse.class,
                new SupportRequest(strQueries, AppConstants.EAT,1), new Response.Listener<SupportResponse>() {
            @Override
            public void onResponse(SupportResponse response) {
                if (response != null) {
                    success.set(response.getSuccess());
                    message=response.getMessage();
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
                Log.e("", error.toString());
                setIsLoading(false);
                getNavigator().failure("Failed to insert");
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }
}
