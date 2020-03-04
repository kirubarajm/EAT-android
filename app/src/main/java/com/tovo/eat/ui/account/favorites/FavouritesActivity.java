package com.tovo.eat.ui.account.favorites;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFavouritesBinding;
import com.tovo.eat.ui.account.favorites.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.account.favorites.dialog.DialogChangeKitchen;
import com.tovo.eat.ui.account.favorites.favdish.CartFavListener;
import com.tovo.eat.ui.account.favorites.favdish.FavDishFragment;
import com.tovo.eat.ui.account.favorites.favkitchen.FavKitchenFragment;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishFragment;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFragment;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.tovo.eat.utilities.swipe.ItemTouchHelperExtension;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class FavouritesActivity extends BaseActivity<ActivityFavouritesBinding, FavouritesViewModel> implements FavouritesNavigator, CartFavListener, HasSupportFragmentInjector {


    @Inject
    FavouritesViewModel mFavouritesViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    Analytics analytics;
    String  pageName=AppConstants.SCREEN_FAVOURITES;
    ActivityFavouritesBinding mActivityFavouritesBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouritesActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavouritesViewModel.setNavigator(this);
        mActivityFavouritesBinding = getViewDataBinding();
        kitchen();
        analytics=new Analytics(this,pageName);
    }

    @Override
    public int getBindingVariable() {
        return BR.favouritesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_favourites;
    }

    @Override
    public FavouritesViewModel getViewModel() {
        return mFavouritesViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void viewCart() {
        // Open Cart

        new Analytics().kitchenViewcart(AppConstants.CLICK_FAV_VIEW_CART,mFavouritesViewModel.kitchenid);

        Intent intent = MainActivity.newIntent(FavouritesActivity.this);
        intent.putExtra("cart", true);
        intent.putExtra("screenName", AppConstants.SCREEN_FAVOURITES);
        startActivity(intent);
        finish();
    }

    @Override
    public void back() {
        finish();
    }
    @Override
    public void dish() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITES,AppConstants.CLICK_DISH_MENU);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FavDishFragment fragment = new FavDishFragment();
        transaction.replace(R.id.favourite_container, fragment);
        transaction.commit();
    }

    @Override
    public void kitchen() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITES,AppConstants.CLICK_KITCHEN_MENU);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FavKitchenFragment fragment = new FavKitchenFragment();
        transaction.replace(R.id.favourite_container, fragment);
        transaction.commit();
    }

    @Override
    public void goBack() {

        new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITES,AppConstants.CLICK_BACK_BUTTON);

        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
     /*   mActivityFavouritesBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mActivityFavouritesBinding.shimmerViewContainer.startShimmerAnimation();*/
     registerWifiReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITES,AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public void checkCart() {
        mFavouritesViewModel.totalCart();
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


    @Override
    public void canceled() {

    }
}

