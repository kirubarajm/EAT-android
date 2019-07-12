package com.tovo.eat.ui.track.help;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderDetailsBinding;
import com.tovo.eat.databinding.ActivityOrderHelpBinding;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityItemAdapter;
import com.tovo.eat.ui.account.orderhistory.ordersview.OrdersHistoryActivityResponse;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.orderplaced.OrderPlacedActivity;

import javax.inject.Inject;

public class OrderHelpActivity extends BaseActivity<ActivityOrderHelpBinding, OrderHelpViewModel> implements OrderHelpNavigator{

    @Inject
    OrderHelpViewModel mOrderHelpViewModel;
    ActivityOrderHelpBinding mActivityOrderHelpBinding;
    String strOrderId;
    public static Intent newIntent(Context context) {

        return new Intent(context, OrderHelpActivity.class);
    }


    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void goBack() {
       finish();
    }

    @Override
    public void callDelivery() {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + Uri.encode(mOrderHelpViewModel.deliveryNumber.get().trim())));
        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(callIntent);


    }

    @Override
    public void gotoSupport() {

    }

    @Override
    public void orderCanceled() {

        Intent intent= MainActivity.newIntent(OrderHelpActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        Toast.makeText(this, "Order canceled", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void orderCancelFailed() {
        finish();
        Toast.makeText(this, "Sorry! Cannot cancel this order", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getBindingVariable() {
        return BR.orderHelpViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_help;
    }

    @Override
    public OrderHelpViewModel getViewModel() {
        return mOrderHelpViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrderHelpBinding = getViewDataBinding();
        mOrderHelpViewModel.setNavigator(this);


        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){
             mOrderHelpViewModel.deliveryName.set(getIntent().getExtras().getString("name"));
             mOrderHelpViewModel.deliveryNumber.set(getIntent().getExtras().getString("number"));
             mOrderHelpViewModel.deliveryAssigned.set(getIntent().getExtras().getBoolean(   "status"));
        }


    }

}

