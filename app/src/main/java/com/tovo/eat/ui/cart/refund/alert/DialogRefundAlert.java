package com.tovo.eat.ui.cart.refund.alert;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tovo.eat.R;
import com.tovo.eat.databinding.DialogRefundAlertBinding;
import com.tovo.eat.ui.base.BaseDialog;
import com.tovo.eat.ui.orderplaced.OrderPlacedActivity;
import com.tovo.eat.ui.payment.PaymentActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class DialogRefundAlert extends BaseDialog implements DialogRefundAlertCallBack {

    private static final String TAG = DialogRefundAlert.class.getSimpleName();
    String strProductId = "";
    @Inject
    DialogRefundAlertViewModel mDialogRefundAlertViewModel;
    DialogRefundAlertBinding binding;
    Activity activity;
    String totalAmount;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_REFUND_ALERT;

    public DialogRefundAlert() {

    }


    public static DialogRefundAlert newInstance() {
        DialogRefundAlert fragment = new DialogRefundAlert();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        // fragment.setTargetFragment(new DialogRefundAlert(),0);
        fragment.setCancelable(false);
        return fragment;
    }


    public static DialogRefundAlert newInstance(DialogRefundAlert fragment) {
        return fragment;
    }


    public void show(FragmentManager fragmentManager, Activity activity, String totalAmount) {
        this.activity = activity;
        this.totalAmount = totalAmount;
        super.show(fragmentManager, TAG);
    }

    @Override
    public void dismissDialog() {
        dismissDialog(TAG);
    }

    @Override
    public void confirmClick() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_YES);

        dismissDialog();
        if (Integer.parseInt(totalAmount) > 0) {
            Intent intent = PaymentActivity.newIntent(getContext());
            intent.putExtra("amount", totalAmount);
            startActivity(intent);

        } else {
            mDialogRefundAlertViewModel.cashMode();
        }
    }

    @Override
    public void cancelClick() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_NO);

        dismissDialog();
    }

    @Override
    public void orderCompleted() {
        dismissDialog();
        Intent newIntent = OrderPlacedActivity.newIntent(getContext());
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(newIntent);

    }


    public void show(FragmentManager fragmentManager, String strProductId) {
        super.show(fragmentManager, TAG);
        this.strProductId = strProductId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        analytics = new Analytics(getActivity(), pageName);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_refund_alert, container, false);
        View view = binding.getRoot();
        AndroidSupportInjection.inject(this);
        binding.setDialogRefundAlertViewModel(mDialogRefundAlertViewModel);
        mDialogRefundAlertViewModel.setNavigator(this);
        return view;
    }
}
