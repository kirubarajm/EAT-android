package com.tovo.eat.ui.account.feedbackandsupport;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFeedbackSupportBinding;
import com.tovo.eat.ui.account.feedbackandsupport.feedback.FeedbackActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.signup.faqs.FaqActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

import zendesk.configurations.Configuration;
import zendesk.support.request.RequestActivity;
import zendesk.support.requestlist.RequestListActivity;
import zendesk.support.requestlist.RequestListConfiguration;

public class FeedbackAndSupportActivity extends BaseActivity<ActivityFeedbackSupportBinding, FeedbackAndSupportActivityViewModel> implements FeedbackAndSupportActivityNavigator {

    @Inject
    FeedbackAndSupportActivityViewModel mFeedbackAndSupportActivityViewModel;
    ActivityFeedbackSupportBinding mActivityFeedbackSupportBinding;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_FEEDBACK_SUPPORT;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);
            }
        }
    };

    public static Intent newIntent(Context context) {

        return new Intent(context, FeedbackAndSupportActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void feedBackClick() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_FEEDBACK);
        Intent intent = FeedbackActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void supportClick() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_SUPPORT);
       /* Intent intent = SupportActivity.newIntent(this);
        startActivity(intent);*/


        /*Intent intent = HelpActivity.newIntent(this);
        startActivity(intent);*/

        openRequestList();


    }

    private void openRequestList() {

        Configuration requestActivityConfig = RequestActivity.builder()
                .withRequestSubject("Query from "+mFeedbackAndSupportActivityViewModel.getDataManager().getCurrentUserName()+"-"+mFeedbackAndSupportActivityViewModel.getDataManager().getCurrentUserId())
                .withTags("userid:"+mFeedbackAndSupportActivityViewModel.getDataManager().getCurrentUserId())
                .config();

        RequestListActivity.builder()
                .show(FeedbackAndSupportActivity.this,requestActivityConfig);

    }
    @Override
    public void faqs() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_FAQ);
        Intent intent = FaqActivity.newIntent(this);
        startActivity(intent);
    }
    @Override
    public void goBack() {
        onBackPressed();
    }
    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }
    @Override
    public int getBindingVariable() {
        return BR.feedBackSupportViewModel;
    }
    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback_support;
    }
    @Override
    public FeedbackAndSupportActivityViewModel getViewModel() {
        return mFeedbackAndSupportActivityViewModel;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFeedbackAndSupportActivityViewModel.setNavigator(this);
        mActivityFeedbackSupportBinding = getViewDataBinding();
        analytics = new Analytics(this, pageName);
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

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);

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

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }

    @Override
    public void canceled() {

    }
}
