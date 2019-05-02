package com.tovo.eat.ui.orderplaced;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityReferralsBinding;
import com.tovo.eat.databinding.OrderPlacedBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;

import javax.inject.Inject;

public class OrderPlacedActivity extends BaseActivity<OrderPlacedBinding, OrderPlacedViewModel> implements OrderPlacedNavigator {

    @Inject
    OrderPlacedViewModel mOrderPlacedViewModel;
    OrderPlacedBinding mOrderPlacedBinding;

    public static Intent newIntent(Context context) {

        return new Intent(context, OrderPlacedActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public int getBindingVariable() {
        return BR.orderPlacedViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.order_placed;
    }

    @Override
    public OrderPlacedViewModel getViewModel() {
        return mOrderPlacedViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderPlacedViewModel.setNavigator(this);
        mOrderPlacedBinding = getViewDataBinding();


        new Handler().postDelayed(() -> {
            Intent intent= MainActivity.newIntent(OrderPlacedActivity.this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        },1000);




    }
}
