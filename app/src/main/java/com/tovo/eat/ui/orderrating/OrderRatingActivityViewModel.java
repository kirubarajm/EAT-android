package com.tovo.eat.ui.orderrating;

import android.databinding.ObservableField;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;

public class OrderRatingActivityViewModel extends BaseViewModel<OrderRatingActivityNavigator> {

    public final ObservableField<String> order = new ObservableField<>();
    public final ObservableField<String> kitchen = new ObservableField<>();
    public long orderID = 0L;
    Response.ErrorListener errorListener;


    public OrderRatingActivityViewModel(DataManager dataManager) {
        super(dataManager);
    }


    public void submitClick() {
        getNavigator().submit();
    }

    public void foodLow() {
        getNavigator().foodSmileyLow();
    }

    public void foodMedium() {
        getNavigator().foodSmileyMedium();
    }

    public void foodHigh() {
        getNavigator().foodSmileyHigh();
    }

    public void deliveryLow() {
        getNavigator().deliverySmileyLow();
    }

    public void deliveryMedium() {
        getNavigator().deliverySmileyMedium();
    }

    public void deliveryHigh() {
        getNavigator().deliverySmileyHigh();
    }

    public void orderRatingSubmit(int foodRating, int deliveryRating, String strFood, String strDelivery) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_ORDER_RATING, OrderRatingResponse.class, new OrderRatingRequest(foodRating, deliveryRating, strFood, strDelivery, orderID), new Response.Listener<OrderRatingResponse>() {
                @Override
                public void onResponse(OrderRatingResponse response) {
                    if (response != null) {
                        if (getNavigator() != null)
                            getNavigator().ratingSuccess();
                        getDataManager().saveRatingSkipDate(0);
                    }


                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                    getNavigator().ratingFailure();
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }
    }

    public void maybeLater() {

        new Analytics().sendClickData(AppConstants.SCREEN_ORDER_RATING, AppConstants.CLICK_NOT_NOW);

        getDataManager().saveRatingAppStatus(false);
        getDataManager().saveRatingSkipDate(getDataManager().getRatingSkips() + 1);
        getNavigator().maybeLater();


        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_ORDER_RATING_SKIP, OrderRatingResponse.class, new OrderRatingRequest(orderID), new Response.Listener<OrderRatingResponse>() {
                @Override
                public void onResponse(OrderRatingResponse response) {
                    if (response != null) {
                        if (getNavigator() != null)
                        getNavigator().ratingSuccess();
                        getDataManager().saveRatingSkipDate(0);
                    }

                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    if (getNavigator() != null)
                    getNavigator().ratingFailure();
                }
            }, AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


}
