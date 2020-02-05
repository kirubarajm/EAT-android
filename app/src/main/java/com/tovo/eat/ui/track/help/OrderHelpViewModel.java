package com.tovo.eat.ui.track.help;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.tovo.eat.api.remote.GsonRequest;
import com.tovo.eat.data.DataManager;
import com.tovo.eat.ui.base.BaseViewModel;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CommonResponse;
import com.tovo.eat.utilities.MvvmApp;

public class OrderHelpViewModel extends BaseViewModel<OrderHelpNavigator> {

    public final ObservableField<String> deliveryName = new ObservableField<>();
    public final ObservableField<String> deliveryNumber = new ObservableField<>();
    public final ObservableField<String> serviceCharges = new ObservableField<>();
    public final ObservableField<String> cancelationMessage = new ObservableField<>();
    public final ObservableField<String> orderId = new ObservableField<>();


    public ObservableBoolean cancelClicked = new ObservableBoolean();
    public ObservableBoolean deliveryClicked = new ObservableBoolean();
    public ObservableBoolean deliveryAssigned = new ObservableBoolean();
    public ObservableBoolean otherReason = new ObservableBoolean();


    public OrderHelpViewModel(DataManager dataManager) {
        super(dataManager);
        otherReason.set(false);
    }


    public void goBack() {
        getNavigator().goBack();
    }


    public void cancelDetails() {

        if (cancelClicked.get()) {
            cancelClicked.set(false);
        } else {
            cancelClicked.set(true);
            deliveryClicked.set(false);
        }


    }

    public void deliveryDetails() {
        if (deliveryAssigned.get()) {

            if (deliveryClicked.get()) {
                deliveryClicked.set(false);

            } else {
                cancelClicked.set(false);
                deliveryClicked.set(true);
            }


            cancelClicked.set(false);
            deliveryClicked.set(true);


        } else {

            getNavigator().showToast("Delivery executive yet to be assigned");
        }

    }

    public void contactCare() {
        if (getNavigator() != null)
            getNavigator().gotoSupport();

    }

    public void supportQuery() {
        if (getNavigator() != null)
            getNavigator().gotoSupport();
    }

    public void calldelivery() {
        if (getNavigator() != null)
            getNavigator().callDelivery();
    }


    public void cancelOrderButton() {
        if (getNavigator() != null)
            getNavigator().orderCancelClicked();
    }


    public void cancelOrder1() {
        cancelOrder(AppConstants.CANCEL_ORDER_MEAASAGE_1);
    }

    public void cancelOrder2() {

        cancelOrder(AppConstants.CANCEL_ORDER_MEAASAGE_2);

    }

    public void cancelOrder3() {

        cancelOrder(AppConstants.CANCEL_ORDER_MEAASAGE_3);

    }


    public void cancelOrder(String reason) {


        if (!MvvmApp.getInstance().onCheckNetWork()) return;
        try {


            setIsLoading(true);
            GsonRequest gsonRequest = new GsonRequest(Request.Method.PUT, AppConstants.URL_CANCEL_ORDER, CommonResponse.class, new OrderCancelRequest(getDataManager().getOrderId(), reason), new Response.Listener<CommonResponse>() {
                @Override
                public void onResponse(CommonResponse response) {
                    if (response != null) {

                        Toast.makeText(MvvmApp.getInstance(), response.getMessage(), Toast.LENGTH_SHORT).show();

                        if (response.isStatus()) {
                            if (getNavigator() != null)
                                getNavigator().orderCanceled();
                        } else {
                            if (getNavigator() != null)
                                getNavigator().orderCancelFailed();
                        }


                    } else {
                        if (getNavigator() != null)
                            getNavigator().orderCancelFailed();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    setIsLoading(false);
                    if (getNavigator() != null)
                        getNavigator().orderCancelFailed();
                }
            }, AppConstants.API_VERSION_ONE);

            MvvmApp.getInstance().addToRequestQueue(gsonRequest);

        } catch (Exception ee) {

            ee.printStackTrace();

        }


    }


}
