package com.tovo.eat.ui.cart.funnel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFunnelBinding;
import com.tovo.eat.databinding.OrderPlacedBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

public class FunnelActivity extends BaseActivity<ActivityFunnelBinding, FunnelViewModel> implements FunnelNavigator {

    @Inject
    FunnelViewModel mFunnelViewModel;
    ActivityFunnelBinding mActivityFunnelBinding;
    Analytics analytics;
    String  pageName= AppConstants.SCREEN_FUNNEL_COUPON;

    public static Intent newIntent(Context context) {
        return new Intent(context, FunnelActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void close() {
        Intent intent= MainActivity.newIntent(FunnelActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        close();
    }

    @Override
    public int getBindingVariable() {
        return BR.funnelViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_funnel;
    }

    @Override
    public FunnelViewModel getViewModel() {
        return mFunnelViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFunnelViewModel.setNavigator(this);
        mActivityFunnelBinding = getViewDataBinding();

        analytics=new Analytics(this,pageName);

       /* new Handler().postDelayed(() -> {
            Intent intent= MainActivity.newIntent(FunnelActivity.this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        },1000);*/
    }

    @Override
    public void canceled() {

    }
}
