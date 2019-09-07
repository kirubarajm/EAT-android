package com.tovo.eat.ui.signup.faqs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFaqsBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class FaqActivity extends BaseActivity<ActivityFaqsBinding, FaqFragmentViewModel> implements FaqFragmentNavigator {

    public static final String TAG = FaqActivity.class.getSimpleName();
    @Inject
    FaqFragmentViewModel mFaqViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    FaqsAdapter mFaqsAdapter;
    private ActivityFaqsBinding mActivityFaqsBinding;

    Analytics analytics;
    String  pageName="FAQs";

    public static Intent newIntent(Context context) {
        return new Intent(context, FaqActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.faqsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_faqs;
    }

    @Override
    public FaqFragmentViewModel getViewModel() {
        return mFaqViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void backClick() {
        finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFaqViewModel.setNavigator(this);
        mActivityFaqsBinding = getViewDataBinding();


              analytics=new Analytics(this, pageName);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityFaqsBinding.recyclerFaqs.setLayoutManager(mLayoutManager);
        mActivityFaqsBinding.recyclerFaqs.setAdapter(mFaqsAdapter);
        subscribeToLiveData();
    }


    private void subscribeToLiveData() {
        mFaqViewModel.getFaqs().observe(this,
                FaqFragmentViewModel -> mFaqViewModel.addFaqsItemsToList(FaqFragmentViewModel));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mFaqViewModel.fetchRepos();

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
