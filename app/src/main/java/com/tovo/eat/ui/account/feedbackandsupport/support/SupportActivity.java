package com.tovo.eat.ui.account.feedbackandsupport.support;

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
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityQueriesBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.registration.RegistrationActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class SupportActivity extends BaseActivity<ActivityQueriesBinding, SupportActivityViewModel> implements SupportActivityNavigator {

    @Inject
    SupportActivityViewModel mQueriesViewModel;
    private ActivityQueriesBinding mActivityQueriesBinding;
    String strQueries="";
    public static final String TAG = SupportActivity.class.getSimpleName();

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
        finish();
    }

    @Override
    public void repliesOnClick() {
        Intent intent = RepliesActivity.newIntent(this);
        startActivity(intent);
    }

    @Override
    public void onRefreshLayout() {
        mQueriesViewModel.fetchCountSertviceCall(0);
    }

    @Override
    public void submit() {
        strQueries=mActivityQueriesBinding.edtQueries.getText().toString();
        if (!strQueries.equals("")) {
            mQueriesViewModel.insertQueriesServiceCall(strQueries);
        }else {
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
     //   mActivityQueriesBinding.swipeQueries.setRefreshing(false);
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefreshFailure() {
     //   mActivityQueriesBinding.swipeQueries.setRefreshing(false);
        //Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void callAdmin() {

            String number = AppConstants.SUPPORT_NUMBER;
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(number.trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(callIntent);

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
