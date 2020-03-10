package com.tovo.eat.ui.kitchendetails.viewproduct;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityViewFavProductBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsResponse;
import com.tovo.eat.ui.kitchendetails.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.kitchendetails.dialog.DialogChangeKitchen;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ViewProductActivity extends BaseActivity<ActivityViewFavProductBinding, ViewProductViewModel> implements ViewProductNavigator,
        AddKitchenDishListener, HasSupportFragmentInjector, ViewProductsImageAdapter.LiveProductsAdapterListener {

    @Inject
    ViewProductViewModel mViewProductViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    ViewProductsImageAdapter mKitchenProductsAdapter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    ActivityViewFavProductBinding mFragmentDishBinding;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_KITCHEN_DETAILS;
    Long kitchenID;

    List<KitchenDetailsResponse.ProductList> productList = new ArrayList<>();

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(ViewProductActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    private TextView[] dots;

    public static Intent newIntent(Context context) {

        return new Intent(context, ViewProductActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewProductViewModel.setNavigator(this);
        mFragmentDishBinding = getViewDataBinding();
        mKitchenProductsAdapter.setListener(this);

        analytics = new Analytics(this, pageName);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            kitchenID = intent.getExtras().getLong("kitchenId");
            mViewProductViewModel.kitchenName.set(intent.getExtras().getString("name"));
            productList = intent.getExtras().getParcelableArrayList("products");
            if (productList != null)
                mViewProductViewModel.productListViewModels.addAll(productList);
            mViewProductViewModel.makeitId = kitchenID;
        }


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        mFragmentDishBinding.recyclerviewProducts.setLayoutManager(gridLayoutManager);
        mFragmentDishBinding.recyclerviewProducts.setAdapter(mKitchenProductsAdapter);

    }


    @Override
    public int getBindingVariable() {
        return BR.viewProductViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_view_fav_product;
    }

    @Override
    public ViewProductViewModel getViewModel() {
        return mViewProductViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void toastMessage(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }


    @Override
    public void otherKitchenDish(Long makeitId, Integer productId, Integer quantity, Integer price) {
        DialogChangeKitchen.newInstance().show(getSupportFragmentManager(), this, makeitId, productId, quantity, price);

    }


    @Override
    public void viewCart() {

        new Analytics().kitchenViewcart(AppConstants.CLICK_KITCHEN_VIEW_CART, kitchenID);
        Intent intent = MainActivity.newIntent(ViewProductActivity.this);
        intent.putExtra("cart", true);
        intent.putExtra("screenName", AppConstants.SCREEN_KITCHEN_DETAILS);
        startActivity(intent);
        finish();

    }

    @Override
    public void goBack() {
        onBackPressed();
    }


    @Override
    public void onResume() {
        super.onResume();
        registerWifiReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }


    @Override
    public void productNotAvailable(int quantity, String productname) {
        Toast.makeText(ViewProductActivity.this, "Only " + quantity + " Quantity of " + productname + " Available", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void confirmClick(boolean status) {


    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_KITCHEN_DETAILS, AppConstants.CLICK_BACK_BUTTON);
        super.onBackPressed();
    }


    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        MvvmApp.getInstance().registerReceiver(mWifiReceiver, filter);
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
        MvvmApp.getInstance().unregisterReceiver(mWifiReceiver);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AppConstants.INTERNET_ERROR_REQUEST_CODE) {
        }
    }

    public void startKitchenLoader() {
        //  mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        //    mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();
    }

    public void stopKitchenLoader() {

        //    mFragmentDishBinding.shimmerViewContainer.setVisibility(View.GONE);
        //   mFragmentDishBinding.shimmerViewContainer.stopShimmerAnimation();
    }

    @Override
    public void canceled() {

    }

    @Override
    public void dishRefresh() {
        mViewProductViewModel.totalCart();
    }


    @Override
    public void showToast(String msg) {

    }


}

