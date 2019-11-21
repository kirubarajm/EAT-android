package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

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
    public ObservableBoolean emptyReplies = new ObservableBoolean();
    Response.ErrorListener errorListener;
    Long userId;
    private MutableLiveData<List<RepliesResponse.Result>> ordersItemsLiveData;


    public RepliesActivityViewModel(DataManager dataManager) {
        super(dataManager);
        userId = getDataManager().getCurrentUserId();

        ordersItemsLiveData = new MutableLiveData<>();

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

    public void goBack() {
        getNavigator().goBack();
    }


    public void fetchQueryListServiceCall(int val) {
        //int UserId = Integer.parseInt(String.valueOf(userId));
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        setIsLoading(true);
        try {

            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_QUERY_QUERIES_LIST, RepliesResponse.class,
                    new RepliesRequest(AppConstants.EAT, userId), new Response.Listener<RepliesResponse>() {
                @Override
                public void onResponse(RepliesResponse response) {
                    if (response != null)
                        if (response.getResult() != null && response.getResult().size() > 0) {
                            ordersItemsLiveData.setValue(response.getResult());

                            emptyReplies.set(false);

                        } else {
                            emptyReplies.set(true);
                        }

                    if (getNavigator() != null)
                        getNavigator().onRefreshSuccess();

                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                    getNavigator().onRefreshFailure();
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {
            getNavigator().onRefreshFailure();
            ee.printStackTrace();
        }
    }

    public void onRefreshLayout() {
        getNavigator().onFrefresh();
    }
}
