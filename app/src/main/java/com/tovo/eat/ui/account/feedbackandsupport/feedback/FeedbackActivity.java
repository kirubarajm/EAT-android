package com.tovo.eat.ui.account.feedbackandsupport.feedback;

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
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFeedbackBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.signup.opt.OtpActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class FeedbackActivity extends BaseActivity<ActivityFeedbackBinding, FeedbackActivityViewModel> implements FeedbackActivityNavigator {

    @Inject
    FeedbackActivityViewModel mFeedbackAndSupportActivityViewModel;
    ActivityFeedbackBinding mActivityFeedbackBinding;
    int rate = 0;
    String message = "";
    Analytics analytics;
    String  pageName="Feedback";

    public static Intent newIntent(Context context) {

        return new Intent(context, FeedbackActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void submit() {
        rate = (int) mActivityFeedbackBinding.rateApp.getRating();
        message = mActivityFeedbackBinding.edtFeedback.getText().toString();

        if (validForSubmit())
            mFeedbackAndSupportActivityViewModel.insertFeedbackServiceCall(rate, message);
    }

    @Override
    public void feedBackSucess(String strMessage) {
        mActivityFeedbackBinding.edtFeedback.setText("");
        mActivityFeedbackBinding.rateApp.setRating(0);
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
        Intent intent = MainActivity.newIntent(FeedbackActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    public void feedBackFailure(String strMessage) {
        Toast.makeText(getApplicationContext(), strMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public int getBindingVariable() {
        return BR.feedbackViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_feedback;
    }

    @Override
    public FeedbackActivityViewModel getViewModel() {
        return mFeedbackAndSupportActivityViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityFeedbackBinding = getViewDataBinding();
        mFeedbackAndSupportActivityViewModel.setNavigator(this);

        analytics=new Analytics(this,pageName);

    }



    private boolean validForSubmit() {
        if (rate == 0) {
            Toast.makeText(getApplicationContext(), "Please give rating", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mActivityFeedbackBinding.edtFeedback.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Please fill your feedback", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
