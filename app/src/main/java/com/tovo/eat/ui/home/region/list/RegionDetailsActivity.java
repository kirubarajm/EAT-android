package com.tovo.eat.ui.home.region.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityRegionDetailsBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

public class RegionDetailsActivity extends BaseActivity<ActivityRegionDetailsBinding, RegionDetailsViewModel> implements RegionDetailsNavigator, KitchenAdapter.LiveProductsAdapterListener {

    @Inject
    RegionDetailsViewModel mRegionDetailsViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    KitchenAdapter adapter;

    ActivityRegionDetailsBinding mActivityRegionListBinding;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_REGION_DETAILS;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
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

    public static Intent newIntent(Context context) {

        return new Intent(context, RegionDetailsActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegionListBinding = getViewDataBinding();
        mRegionDetailsViewModel.setNavigator(this);
        adapter.setListener(this);

        setSupportActionBar(mActivityRegionListBinding.toolbar);

        analytics = new Analytics(this, pageName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setHomeAsUpIndicator(backArrow);


        setTitle(mRegionDetailsViewModel.regionName.get());
        mActivityRegionListBinding.toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        mActivityRegionListBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    mActivityRegionListBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    mActivityRegionListBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));

                } else {
                    mActivityRegionListBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
                }
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRegionListBinding.recyclerviewOrders.setLayoutManager(new LinearLayoutManager(this));
        mActivityRegionListBinding.recyclerviewOrders.setAdapter(adapter);


    }

    @Override
    public int getBindingVariable() {
        return BR.regionDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_region_details;
    }

    @Override
    public RegionDetailsViewModel getViewModel() {
        return mRegionDetailsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void listLoaded() {
    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void subscribeToLiveData() {
        mRegionDetailsViewModel.getkitchenListItemsLiveData().observe(this,
                kitchensListItemViewModel -> mRegionDetailsViewModel.addDishItemsToList(kitchensListItemViewModel));
    }

    @Override
    public void onResume() {
        super.onResume();

        registerWifiReceiver();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            mRegionDetailsViewModel.detailImageUrl.set(intent.getExtras().getString("image"));
            mRegionDetailsViewModel.tagline.set(intent.getExtras().getString("tagline"));
            mRegionDetailsViewModel.fetchRepos(intent.getExtras().getInt("id"));
            subscribeToLiveData();
        }
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_REGION_DETAILS, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {

    }

    @Override
    public void offersItemClick(KitchenResponse.Coupon offers) {

    }

    @Override
    public void onItemClickData(Long kitchenId) {

        new Analytics().sendClickData(AppConstants.SCREEN_REGION_DETAILS, AppConstants.CLICK_KITCHEN);
        new Analytics().selectKitchen(AppConstants.ANALYTICYS_REGION_KITCHEN,kitchenId);
        Intent intent = KitchenDetailsActivity.newIntent(RegionDetailsActivity.this);
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);
    }

    @Override
    public void animateView(View view) {

    }

    @Override
    public void removeDishFavourite(Integer favId) {

    }

    @Override
    public void addFav(long id, String fav) {

    }

    @Override
    public void infinityStoryItemClick(KitchenResponse.Story story, int position) {

    }

    @Override
    public void regionCollectionItemClick(KitchenResponse.Region collection) {

    }

    @Override
    public void infinityCollectionDetailItemClick(KitchenResponse.CollectionDetail collection) {

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

