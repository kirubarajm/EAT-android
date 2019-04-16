package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import java.util.List;


public class RepliesActivityViewModel extends BaseViewModel<RepliesActivityNavigator> {

    public ObservableList<RepliesResponse.Result> repliesItemViewModels = new ObservableArrayList<>();
    Response.ErrorListener errorListener;
    private MutableLiveData<List<RepliesResponse.Result>> ordersItemsLiveData;

    public RepliesActivityViewModel(DataManager dataManager) {
        super(dataManager);
        ordersItemsLiveData = new MutableLiveData<>();
        fetchQueryListServiceCall(0);
    }

    public MutableLiveData<List<RepliesResponse.Result>> getReplies() {
        return ordersItemsLiveData;
    }

    public void addRepliesItemsToList(List<RepliesResponse.Result> ordersItems) {
        repliesItemViewModels.clear();
        repliesItemViewModels.addAll(ordersItems);
    }

    public ObservableList<RepliesResponse.Result> getRepliesItemViewModels() {
        return repliesItemViewModels;
    }

    public void imgBackClick() {
        getNavigator().backClick();
    }

    public void nextClick() {
        getNavigator().next();
    }

    public void fetchQueryListServiceCall(int val) {
        //long userId = getDataManager().getCurrentUserId();
        //int UserId = Integer.parseInt(String.valueOf(userId));
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_QUERY_QUERIES_LIST, RepliesResponse.class,
                new RepliesRequest(AppConstants.EAT, 1), new Response.Listener<RepliesResponse>() {
            @Override
            public void onResponse(RepliesResponse response) {
                if (response != null) {
                    ordersItemsLiveData.setValue(response.getResult());
                    if (val == 1) {
                        getNavigator().onRefreshSuccess();
                    }
                }
            }
        }, errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("", error.toString());
                setIsLoading(false);
                if (val == 1) {
                    getNavigator().onRefreshFailure();
                }
            }
        });
        MvvmApp.getInstance().addToRequestQueue(gsonRequest);
    }

    public void onRefreshLayout() {
        getNavigator().onFrefresh();
    }
}
