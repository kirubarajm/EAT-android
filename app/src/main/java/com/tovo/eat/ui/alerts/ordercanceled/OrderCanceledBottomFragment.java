package com.tovo.eat.ui.alerts.ordercanceled;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.AlertOrderCanceledBinding;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;

import javax.inject.Inject;

public class OrderCanceledBottomFragment extends BaseBottomSheetFragment<AlertOrderCanceledBinding, OrderCanceledViewModel>
        implements OrderCanceledNavigator {

    int foodRating = 0;
    int deliveryRating = 0;
    int orderId = 0;

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


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAlertOrderCanceledBinding = getViewDataBinding();

        if (getArguments() != null) {
            mLoginViewModelMain.message.set(getArguments().getString("message"));

        }

    }


}
