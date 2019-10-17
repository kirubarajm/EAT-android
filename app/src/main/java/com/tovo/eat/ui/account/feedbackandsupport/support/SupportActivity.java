package com.tovo.eat.ui.account.feedbackandsupport.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityQueriesBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class SupportActivity extends BaseActivity<ActivityQueriesBinding, SupportActivityViewModel> implements SupportActivityNavigator {

    public static final String TAG = SupportActivity.class.getSimpleName();
    @Inject
    SupportActivityViewModel mQueriesViewModel;
    String strQueries = "";
    Analytics analytics;
    String pageName = AppConstants.SCREEN_SUPPORT;

    Long orderid=null;

    int type=AppConstants.QUERY_TYPE_GENERAL;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(inIntent);

            }
        }
    };
    private ActivityQueriesBinding mActivityQueriesBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, SupportActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.queriesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_queries;
    }

    @Override
    public SupportActivityViewModel getViewModel() {
        return mQueriesViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void backClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public void repliesOnClick() {

        new Analytics().sendClickData(pageName, AppConstants.CLICK_REPLIES);


        Intent intent = RepliesActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void onRefreshLayout() {
        mQueriesViewModel.fetchCountSertviceCall(0);
    }

    @Override
    public void submit() {
        new Analytics().sendClickData(AppConstants.SCREEN_SUPPORT, AppConstants.CLICK_QUERY_SUBMIT);

        strQueries = mActivityQueriesBinding.edtQueries.getText().toString();
        if (!strQueries.equals("")) {
            mQueriesViewModel.insertQueriesServiceCall(strQueries,type,orderid);

            new Analytics().makeQuery(strQueries);

        } else {
            Toast.makeText(this, AppConstants.TOAST_ENTER_QUERY_TO_SEND, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void success(String strSuccess) {
        Toast.makeText(this, strSuccess, Toast.LENGTH_SHORT).show();
        mActivityQueriesBinding.edtQueries.setText("");
        Intent intent = RepliesActivity.newIntent(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void failure(String strFailure) {
        Toast.makeText(this, strFailure, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshSuccess() {

    }

    @Override
    public void onRefreshFailure() {
    }

    @Override
    public void callAdmin() {

        new Analytics().sendClickData(AppConstants.SCREEN_SUPPORT, AppConstants.CLICK_CALL_SUPPORT);

        //String number = AppConstants.SUPPORT_NUMBER;
        String number = mQueriesViewModel.support.get();

        assert number != null;
        if (!number.equals("0")) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);
        }
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityQueriesBinding = getViewDataBinding();
        mQueriesViewModel.setNavigator(this);

        analytics = new Analytics(this, pageName);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            orderid = intent.getExtras().getLong("orderid",0);
            type = intent.getExtras().getInt("type",AppConstants.QUERY_TYPE_GENERAL);
        }




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
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

}
