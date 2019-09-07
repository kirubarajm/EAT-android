package com.tovo.eat.ui.account.referrals;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityReferralsBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class ReferralsActivity extends BaseActivity<ActivityReferralsBinding, ReferralsActivityViewModel> implements ReferralsActivityNavigator {

    @Inject
    ReferralsActivityViewModel mFeedbackAndSupportActivityViewModel;
    ActivityReferralsBinding mActivityReferralsBinding;
    String stringReferral = "";

    Analytics analytics;
    String  pageName="Referral";

    public static Intent newIntent(Context context) {

        return new Intent(context, ReferralsActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void sendReferralsClick() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
            String shareMessage = "";
            //shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            shareMessage = shareMessage + stringReferral;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            e.toString();
        }
    }

    @Override
    public void success(String strMessage) {
        stringReferral = strMessage;
    }

    @Override
    public void failure(String strMessage) {
        stringReferral = strMessage;
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.referralsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_referrals;
    }

    @Override
    public ReferralsActivityViewModel getViewModel() {
        return mFeedbackAndSupportActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedbackAndSupportActivityViewModel.setNavigator(this);
        mActivityReferralsBinding = getViewDataBinding();

        analytics=new Analytics(this,pageName);

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
