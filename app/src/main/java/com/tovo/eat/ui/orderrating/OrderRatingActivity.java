package com.tovo.eat.ui.orderrating;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityOrderRatingBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.SignUpActivity;
import com.tovo.eat.utilities.AppConstants;

import javax.inject.Inject;

public class OrderRatingActivity extends BaseActivity<ActivityOrderRatingBinding, OrderRatingActivityViewModel>
        implements OrderRatingActivityNavigator {

    @Inject
    OrderRatingActivityViewModel mLoginViewModelMain;
    private ActivityOrderRatingBinding mActivityOrderRatingBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, OrderRatingActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public int getBindingVariable() {
        return BR.orderRatingViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_order_rating;
    }

    @Override
    public OrderRatingActivityViewModel getViewModel() {
        return mLoginViewModelMain;
    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityOrderRatingBinding = getViewDataBinding();
        mLoginViewModelMain.setNavigator(this);

    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
