package com.tovo.eat.ui.account.feedbackandsupport.support.replies;

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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityRepliesBinding;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.chat.ChatActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class RepliesActivity extends BaseActivity<ActivityRepliesBinding, RepliesActivityViewModel> implements RepliesActivityNavigator,RepliesAdapter.RepliesAdapterListener {


    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    RepliesAdapter mRepliesAdapter;
    @Inject
    RepliesActivityViewModel mRepliesActivityViewModel;

    Analytics analytics;
    String  pageName=AppConstants.SCREEN_SUPPORT_REPLIES;

    private ActivityRepliesBinding mActivityRepliesBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, RepliesActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.repliesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_replies;
    }

    @Override
    public RepliesActivityViewModel getViewModel() {
        return mRepliesActivityViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void backClick() {
        finish();
        new Analytics().sendClickData(pageName, AppConstants.CLICK_BACK_BUTTON);
    }

    @Override
    public void next() {

    }

    @Override
    public void onFrefresh() {
        mRepliesActivityViewModel.fetchQueryListServiceCall(1);
    }

    @Override
    public void onRefreshSuccess() {
        mActivityRepliesBinding.swipeQueries.setRefreshing(false);
        mActivityRepliesBinding.loader.setVisibility(View.GONE);
    }

    @Override
    public void onRefreshFailure() {
        mActivityRepliesBinding.swipeQueries.setRefreshing(false);
        mActivityRepliesBinding.loader.setVisibility(View.GONE);
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(pageName,AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRepliesBinding = getViewDataBinding();
        mRepliesActivityViewModel.setNavigator(this);
        mRepliesAdapter.setListener(this);

        analytics=new Analytics(this,pageName);

        mActivityRepliesBinding.loader.setVisibility(View.VISIBLE);



        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRepliesBinding.recyclerReplies.setLayoutManager(new LinearLayoutManager(this));
        mActivityRepliesBinding.recyclerReplies.setAdapter(mRepliesAdapter);
        subscribeToLiveData();

    }

    private void subscribeToLiveData() {
        mRepliesActivityViewModel.getReplies().observe(this, repliesItemViewModels -> mRepliesActivityViewModel.addRepliesItemsToList(repliesItemViewModels));
    }

    @Override
    public void chatList(RepliesResponse.Result replies) {


        new Analytics().sendClickData(pageName,AppConstants.ANALYTICYS_QUERIES);

        Log.e("orders",replies.toString());
        String strQid= String.valueOf(replies.getQid());
        Log.e("orders", strQid);

        Intent intent = ChatActivity.newIntent(this);
        intent.putExtra("qId",strQid);
        intent.putExtra("question",replies.getQuestion());
        intent.putExtra("date",replies.getCreatedAt());
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();
        mRepliesActivityViewModel.fetchQueryListServiceCall(0);
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
