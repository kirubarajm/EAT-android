package com.tovo.eat.ui.alerts.ordercanceled;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.AlertOrderCanceledBinding;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

public class OrderCanceledBottomFragment extends BaseBottomSheetFragment<AlertOrderCanceledBinding, OrderCanceledViewModel>
        implements OrderCanceledNavigator {
    Analytics analytics;
    String pageName = AppConstants.SCREEN_ORDER_CANCELED;

    @Inject
    OrderCanceledViewModel mLoginViewModelMain;
    private AlertOrderCanceledBinding mAlertOrderCanceledBinding;

    public static OrderCanceledBottomFragment newInstance() {
        Bundle args = new Bundle();
        OrderCanceledBottomFragment fragment = new OrderCanceledBottomFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void submit() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_CLOSE);

        dismiss();
    }

    @Override
    public int getBindingVariable() {
        return BR.orderCanceledViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.alert_order_canceled;
    }

    @Override
    public OrderCanceledViewModel getViewModel() {
        return mLoginViewModelMain;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginViewModelMain.setNavigator(this);

        analytics = new Analytics(getActivity(), pageName);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAlertOrderCanceledBinding = getViewDataBinding();

        if (getArguments() != null) {
            mLoginViewModelMain.message.set(getArguments().getString("message"));


            if (null != getArguments().getString("paymenttype") && getArguments().getString("paymenttype").equals("1")) {

                mLoginViewModelMain.codPayment.set(false);

            } else {
                mLoginViewModelMain.codPayment.set(true);
            }


        }

    }


}
