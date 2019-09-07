package com.tovo.eat.ui.account.favorites.tab;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityTabFavoritesBinding;
import com.tovo.eat.ui.account.favorites.favdish.CartFavListener;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;


public class FavoritesTabActivity extends BaseActivity<ActivityTabFavoritesBinding, FavoritesTabActivityViewModel> implements FavoritesTabActivityNavigator, HasSupportFragmentInjector, CartFavListener {

    ActivityTabFavoritesBinding mActivityTabFavoritesBinding;
    @Inject
    FavoritesTabActivityViewModel mFavoritesActivityViewModel;
    @Inject
    FavoritesTabAdapter mFavoritesTabAdapter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    Analytics analytics;
    String  pageName="Favourites list";

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent = InternetErrorFragment.newIntent(FavoritesTabActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    public static Intent newIntent(Context context) {

        return new Intent(context, FavoritesTabActivity.class);
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
        mFavoritesActivityViewModel.favClicked(false);

    }

    @Override
    public void viewCart() {
        Intent intent = MainActivity.newIntent(FavoritesTabActivity.this);
        intent.putExtra("cart", true);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mFavoritesActivityViewModel.favClicked(false);
    }

    @Override
    public int getBindingVariable() {
        return BR.favTabViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_tab_favorites;
    }

    @Override
    public FavoritesTabActivityViewModel getViewModel() {
        return mFavoritesActivityViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavoritesActivityViewModel.setNavigator(this);
        mActivityTabFavoritesBinding = getViewDataBinding();
        setUp();

        analytics=new Analytics(this,pageName);

    }

    private void setUp() {
        mFavoritesTabAdapter.setCount(2);
        mActivityTabFavoritesBinding.viewPagerFavorites.setAdapter(mFavoritesTabAdapter);
        mActivityTabFavoritesBinding.tabFavorites.addTab(mActivityTabFavoritesBinding.tabFavorites.newTab().setText(getString(R.string.kitchen)));
        mActivityTabFavoritesBinding.tabFavorites.addTab(mActivityTabFavoritesBinding.tabFavorites.newTab().setText(getString(R.string.dish)));
        mActivityTabFavoritesBinding.viewPagerFavorites.setOffscreenPageLimit(mActivityTabFavoritesBinding.tabFavorites.getTabCount());
        mActivityTabFavoritesBinding.viewPagerFavorites.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mActivityTabFavoritesBinding.tabFavorites));
        mActivityTabFavoritesBinding.tabFavorites.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mActivityTabFavoritesBinding.viewPagerFavorites.setCurrentItem(tab.getPosition());
                //mActivityTabFavoritesBinding.tabFavorites.setSelectedTabIndicatorColor(Color.parseColor("#0F5E9E"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mFavoritesActivityViewModel.favClicked(false);
    }

    @Override
    public void checkCart() {
        mFavoritesActivityViewModel.totalCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFavoritesActivityViewModel.totalCart();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.INTERNET_ERROR_REQUEST_CODE) {
            setUp();
        }
    }

}
