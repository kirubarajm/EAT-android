package com.tovo.eat.ui.search.dish;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySearchDishBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.kitchendish.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.home.kitchendish.dialog.DialogChangeKitchen;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SearchDishActivity extends BaseActivity<ActivitySearchDishBinding, SearchDishViewModel> implements SearchDishNavigator, SearchDishAdapter.LiveProductsAdapterListener, HasSupportFragmentInjector, AddKitchenDishListener {


    public ActivitySearchDishBinding mActivitySearchDishBinding;
    @Inject
    public SearchDishViewModel mSearchDishViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    SearchDishAdapter adapter;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    Analytics analytics;
    String pageName = AppConstants.SCREEN_EXPLORE_COLLECTION;

    int collectionId;
    String collectionTitle;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MvvmApp.getInstance().startActivity(inIntent);

            }
        }
    };

    public static Intent newIntent(Context context) {

        return new Intent(context, SearchDishActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.searchDishViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_dish;
    }

    @Override
    public SearchDishViewModel getViewModel() {

        return mSearchDishViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_EXPLORE_COLLECTION, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }

    @Override
    public void listLoaded() {
        mActivitySearchDishBinding.recyclerviewList.setVisibility(View.VISIBLE);

    }

    @Override
    public void viewCart() {
        new Analytics().sendClickData(AppConstants.SCREEN_EXPLORE_COLLECTION, AppConstants.CLICK_VIEW_CART);


        new Analytics().kitchenViewcart(AppConstants.CLICK_COLLECTION_VIEW_CART,mSearchDishViewModel.kitchenid);

        Intent intent = MainActivity.newIntent(SearchDishActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("cart", true);
        startActivity(intent);
        finish();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySearchDishBinding = getViewDataBinding();
        mSearchDishViewModel.setNavigator(this);
        adapter.setListener(this);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivitySearchDishBinding.recyclerviewList.setLayoutManager(mLayoutManager);
        mActivitySearchDishBinding.recyclerviewList.setAdapter(adapter);
        subscribeToLiveData();


        analytics = new Analytics(this, pageName);
        // mSearchDishViewModel.fetchRepos("grill");


        mSearchDishViewModel.totalCart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerWifiReceiver();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getExtras().getInt("cid") != 0) {
                collectionId = intent.getExtras().getInt("cid");
                collectionTitle = intent.getExtras().getString("title");
                mSearchDishViewModel.searched.set(collectionTitle);
                mSearchDishViewModel.fetchKitchens(collectionId);
            }
        }

    }

    private void subscribeToLiveData() {
        mSearchDishViewModel.getDishItemsLiveData().observe(this,
                searchItemViewModel -> mSearchDishViewModel.addDishItemsToList(searchItemViewModel));
    }

    @Override
    public void onItemClickData(DishResponse.Result blogUrl) {

    }

    @Override
    public void sendCart() {

        mSearchDishViewModel.totalCart();
    }

    @Override
    public void dishRefresh() {

    }

    @Override
    public void productNotAvailable() {

        Toast.makeText(getApplicationContext(), "Entered quantity not available now", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void addDishFavourite(Integer dishId, String fav) {
        mSearchDishViewModel.addFavourite(dishId, fav);
    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mSearchDishViewModel.removeFavourite(favId);
    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void otherKitchenDish(Long makeitId, Integer productId, Integer quantity, Integer price) {


        DialogChangeKitchen fragment = new DialogChangeKitchen();
        fragment.setCancelable(false);


        DialogChangeKitchen.newInstance().show(getSupportFragmentManager(), this, makeitId, productId, quantity, price);


    }

    @Override
    public void confirmClick(boolean status) {

         //  mSearchDishViewModel.fetchKitchens(collectionId);


           adapter.notifyDataSetChanged();

        mSearchDishViewModel.totalCart();

        if (status) {
            new Analytics().sendClickData(AppConstants.SCREEN_EXPLORE_COLLECTION, AppConstants.CLICK_CHANGE_KITCHEN_YES);
        } else {
            new Analytics().sendClickData(AppConstants.SCREEN_EXPLORE_COLLECTION, AppConstants.CLICK_CHANGE_KITCHEN_NO);

        }


    }

    @Override
    public void onItemClickData(Long kitchenId) {


        new Analytics().sendClickData(AppConstants.SCREEN_EXPLORE_COLLECTION, AppConstants.CLICK_VIEW_KITCHEN);


        new Analytics().selectKitchen(AppConstants.ANALYTICYS_COLLECTION_KITCHEN,kitchenId);

        Intent intent = KitchenDetailsActivity.newIntent(getApplicationContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

    }

    @Override
    public void showMore(Long kitchenId) {
        new Analytics().sendClickData(AppConstants.SCREEN_EXPLORE_COLLECTION, AppConstants.CLICK_VIEW_MENU);

        Intent intent = KitchenDetailsActivity.newIntent(getApplicationContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);


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