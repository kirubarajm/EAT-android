package com.tovo.eat.ui.kitchendetails;

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
import android.text.Html;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityKitchenDetailsBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.kitchendetails.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.kitchendetails.dialog.DialogChangeKitchen;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class KitchenDetailsActivity extends BaseActivity<ActivityKitchenDetailsBinding, KitchenDetailsViewModel> implements KitchenDetailsNavigator,
        AddKitchenDishListener, HasSupportFragmentInjector, KitchenProductsAdapter.LiveProductsAdapterListener {

    @Inject
    KitchenDetailsViewModel mKitchenDetailsViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    KitchenProductsAdapter mKitchenProductsAdapter;
    @Inject
    KitchenHeaderAdapter mKitchenHeaderAdapter;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    ActivityKitchenDetailsBinding mFragmentDishBinding;
    Long kitchenID;
    int totalCount;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_KITCHEN_DETAILS;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(KitchenDetailsActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }
        }
    };

    private TextView[] dots;

    public static Intent newIntent(Context context) {

        return new Intent(context, KitchenDetailsActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKitchenDetailsViewModel.setNavigator(this);
        mFragmentDishBinding = getViewDataBinding();
        mKitchenProductsAdapter.setListener(this);

        startKitchenLoader();
        subscribeToLiveDataKitchenImages();

        analytics = new Analytics(this, pageName);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            kitchenID = intent.getExtras().getLong("kitchenId");
            mKitchenDetailsViewModel.makeitId = kitchenID;
        }

        setTitle(mKitchenDetailsViewModel.kitchenName.get());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mFragmentDishBinding.recyclerviewKitchenHeader.setLayoutManager(gridLayoutManager);
        mFragmentDishBinding.recyclerviewKitchenHeader.setAdapter(mKitchenHeaderAdapter);


        //GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);

        LinearLayoutManager mProductLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mProductLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerviewProducts.setLayoutManager(mProductLayoutManager);
        mFragmentDishBinding.recyclerviewProducts.setAdapter(mKitchenProductsAdapter);
    }


    private void subscribeToLiveDataKitchenImages() {


        mKitchenDetailsViewModel.getProductListLiveData().observe(this,
                productsViewModel -> mKitchenDetailsViewModel.addProductsToList(productsViewModel));

        mKitchenDetailsViewModel.getHeaderItemLiveData().observe(this,
                headersViewModel -> mKitchenDetailsViewModel.addHeadersToList(headersViewModel));


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
    public int getBindingVariable() {
        return BR.kitchenDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kitchen_details;
    }

    @Override
    public KitchenDetailsViewModel getViewModel() {
        return mKitchenDetailsViewModel;
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
    public void dishListLoaded(KitchenDetailsResponse response) {
        stopKitchenLoader();
        String strContent1 = response.getResult().get(0).getKitchen_page_header_content1();
        String strContent2 = response.getResult().get(0).getKitchen_page_header_content2();

        if (strContent1 != null)
            mFragmentDishBinding.headerContent1.setText(Html.fromHtml(strContent1));
        if (strContent2 != null)
            mFragmentDishBinding.headerContent2.setText(Html.fromHtml(strContent2));
    }

    @Override
    public void viewCart() {

        new Analytics().kitchenViewcart(AppConstants.CLICK_KITCHEN_VIEW_CART, kitchenID);
        Intent intent = MainActivity.newIntent(KitchenDetailsActivity.this);
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
    public void loadError() {
        stopKitchenLoader();
    }


    @Override
    public void onResume() {
        super.onResume();
        mKitchenDetailsViewModel.fetchRepos(kitchenID);
        registerWifiReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }


    @Override
    public void productNotAvailable(int quantity, String productname) {
        Toast.makeText(KitchenDetailsActivity.this, "Only " + quantity + " Quantity of " + productname + " Available", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void confirmClick(boolean status) {

        if (true) {
            mKitchenDetailsViewModel.fetchRepos(kitchenID);
        }

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
        mKitchenDetailsViewModel.totalCart();
    }


    @Override
    public void showToast(String msg) {

    }


}

