package com.tovo.eat.ui.signup.fagsandsupport;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFaqsSupportBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.SupportActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.signup.faqs.FaqActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class FaqsAndSupportActivity extends BaseActivity<ActivityFaqsSupportBinding, FaqsAndSupportViewModel> implements FaqsAndSupportNavigator {

    @Inject
    FaqsAndSupportViewModel mFaqsAndSupportViewModel;
    ActivityFaqsSupportBinding mActivityFaqsSupportBinding;


    Analytics analytics;
    String  pageName="FAQs and support";


    public static Intent newIntent(Context context) {

        return new Intent(context, FaqsAndSupportActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void feedBackClick() {
        Intent intent = FaqActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void supportClick() {
        Intent intent = SupportActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void callCusstomerCare() {


            String number = AppConstants.SUPPORT_NUMBER;
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);

    }

    @Override
    public int getBindingVariable() {
        return BR.faqsAndSupportViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_faqs_support;
    }

    @Override
    public FaqsAndSupportViewModel getViewModel() {
        return mFaqsAndSupportViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFaqsAndSupportViewModel.setNavigator(this);
        mActivityFaqsSupportBinding = getViewDataBinding();


        analytics=new Analytics(this, pageName);

    }


    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }


    private  boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance() .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        if (networkInfo != null
                && networkInfo.getType() == ConnectivityManager.TYPE_WIFI
                && networkInfo.isConnected()) {
            return true;
        } else return networkInfo != null
                && networkInfo.isConnected();
    }

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent= InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }
        }
    };
    private  void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }


}
