package com.tovo.eat.ui.orderrating;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;

public class OrderRatingActivityViewModel extends BaseViewModel<OrderRatingActivityNavigator> {

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

    public void orderRatingSubmit(int foodRating, int deliveryRating, String strFood, String strDelivery,Integer orderId) {
        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {
            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.POST, AppConstants.URL_ORDER_RATING, OrderRatingResponse.class, new OrderRatingRequest(foodRating, deliveryRating, strFood, strDelivery, orderId), new Response.Listener<OrderRatingResponse>() {
                @Override
                public void onResponse(OrderRatingResponse response) {
                    if (response != null) {
                        getNavigator().ratingSuccess();
                    }
                }
            }, errorListener = new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setIsLoading(false);
                    getNavigator().ratingFailure();
                }
            },AppConstants.API_VERSION_ONE);
            MvvmApp.getInstance().addToRequestQueue(gsonRequest);
        }catch (Exception ee){

            ee.printStackTrace();

        }
    }
}
