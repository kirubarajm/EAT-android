package com.tovo.eat.ui.payment.pendingpaymentpage;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.AlertPaymentRetryBinding;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.ui.pendingpayment.PaymentListener;
import com.tovo.eat.ui.pendingpayment.PendingPaymentNavigator;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

public class PendingPaymentPageAlert extends BaseBottomSheetFragment<AlertPaymentRetryBinding, PendingPaymentPageViewModel>
        implements PendingPaymentNavigator {

    PaymentListener paymentListener;

    Analytics analytics;
    String  pageName=AppConstants.SCREEN_PAYMENT_RETRY;


    @Inject
    PendingPaymentPageViewModel mLoginViewModelMain;
    private AlertPaymentRetryBinding mAlertPendingPaymentBinding;

    public static PendingPaymentPageAlert newInstance() {
        Bundle args = new Bundle();
        PendingPaymentPageAlert fragment = new PendingPaymentPageAlert();
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

        new Analytics().sendClickData(AppConstants.SCREEN_PAYMENT,AppConstants.CLICK_PAYMENT_RETRY);
        paymentListener.paymentRetry();
        dismiss();
    }

    @Override
    public void cancel() {
        new Analytics().sendClickData(AppConstants.SCREEN_PAYMENT,AppConstants.CLICK_PAYMENT_CANCEL);
        paymentListener.paymentCancel();
        dismiss();

    }

    @Override
    public void paymentSuccessed(boolean status) {

    }


    @Override
    public int getBindingVariable() {
        return BR.pendingPaymentPageViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.alert_payment_retry;
    }

    @Override
    public PendingPaymentPageViewModel getViewModel() {
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

    }
}
