package com.tovo.eat.ui.pendingpayment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderRatingBinding;
import com.tovo.eat.databinding.AlertPendingPaymentBinding;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

public class PendingPaymentAlert extends BaseBottomSheetFragment<AlertPendingPaymentBinding, PendingPaymentViewModel>
        implements PendingPaymentNavigator {


    Long orderId = 0L;
    int price = 0;

    PaymentListener paymentListener;
    Analytics analytics;
    String  pageName= AppConstants.SCREEN_PAYMENT_RETRY;

    @Inject
    PendingPaymentViewModel mLoginViewModelMain;
    private AlertPendingPaymentBinding mAlertPendingPaymentBinding;

    public static PendingPaymentAlert newInstance() {
        Bundle args = new Bundle();
        PendingPaymentAlert fragment = new PendingPaymentAlert();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        paymentListener = (PaymentListener) context;
        super.onAttach(context);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void retry() {


        paymentListener.paymentRetry();
        dismiss();

/*
        final Activity activity = getActivity();

        final Checkout co = new Checkout();

        // co.setImage(R.mipmap.ic_launcher);

        co.setFullScreenDisable(true);
        try {
            JSONObject options = new JSONObject();
            options.put("name", getString(R.string.app_name));
            options.put("description", getString(R.string.orderid) + orderId);
            options.put("currency", "INR");
            options.put("amount", price * 100);
            options.put("customer_id", mLoginViewModelMain.getDataManager().getRazorpayCustomerId());
            JSONObject ReadOnly = new JSONObject();
            ReadOnly.put("email", "true");
            ReadOnly.put("contact", "true");
            options.put("readonly", ReadOnly);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }


        dismiss();*/

    }

    @Override
    public void cancel() {
        paymentListener.paymentCancel();
        dismiss();

    }

    @Override
    public void paymentSuccessed(boolean status) {

    }


    @Override
    public int getBindingVariable() {
        return BR.pendingPaymentViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.alert_pending_payment;
    }

    @Override
    public PendingPaymentViewModel getViewModel() {
        return mLoginViewModelMain;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModelMain.setNavigator(this);

        analytics=new Analytics(getActivity(),pageName);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAlertPendingPaymentBinding = getViewDataBinding();


        if (getArguments() != null) {
            mLoginViewModelMain.order.set("Order #" + String.valueOf(getArguments().getLong("orderid")));
            mLoginViewModelMain.kitchen.set(getArguments().getString("brandname"));
            mLoginViewModelMain.amount.set(String.valueOf(getArguments().getInt("price")));
            mLoginViewModelMain.products.set(getArguments().getString("products"));

        }


    }
}
