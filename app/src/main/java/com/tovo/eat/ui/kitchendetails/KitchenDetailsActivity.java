package com.tovo.eat.ui.kitchendetails;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityKitchenDetailsBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.ui.kitchendetails.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.kitchendetails.dialog.DialogChangeKitchen;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.TextJustification;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class KitchenDetailsActivity extends BaseActivity<ActivityKitchenDetailsBinding, KitchenDetailsViewModel> implements KitchenDetailsNavigator,
        FavoriteAdapter.LiveProductsAdapterListener, AddKitchenDishListener, HasSupportFragmentInjector,
        MenuKitchenInfoCommonImageAdapter.MenuProductsAdapterListener, SpecialitiesAdapter.LiveProductsAdapterListener, FoodBadgeAdapter.LiveProductsAdapterListener,
        TodaysMenuAdapter.LiveProductsAdapterListener {

    @Inject
    KitchenDetailsViewModel mKitchenDetailsViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    FavoriteAdapter mFavTodaysMenuAdapter;
    @Inject
    TodaysMenuAdapter mTodaysMenuAdapter;
    @Inject
    MenuKitchenInfoCommonImageAdapter kitchenCommonAdapter;
    @Inject
    FoodBadgeAdapter foodBadgesImageAdapter;
    @Inject
    SpecialitiesAdapter specialitiesAdapter;
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
        mFavTodaysMenuAdapter.setListener(this);
        mTodaysMenuAdapter.setListener(this);
        foodBadgesImageAdapter.setListener(this);
        specialitiesAdapter.setListener(this);
        kitchenCommonAdapter.setListener(this);
        mFragmentDishBinding = getViewDataBinding();


        startKitchenLoader();
        subscribeToLiveDataKitchenImages();

        analytics = new Analytics(this, pageName);



      //  mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
      //  mFragmentDishBinding.toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            kitchenID = intent.getExtras().getLong("kitchenId");
            mKitchenDetailsViewModel.makeitId = kitchenID;
            if (intent.getExtras().getInt("type") == 2) {
                mKitchenDetailsViewModel.info();
            }


        }

        setTitle(mKitchenDetailsViewModel.kitchenName.get());


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerFav.setLayoutManager(new LinearLayoutManager(this));
        mFragmentDishBinding.recyclerFav.setAdapter(mFavTodaysMenuAdapter);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerTodaysMenu.setLayoutManager(new LinearLayoutManager(this));
        mFragmentDishBinding.recyclerTodaysMenu.setAdapter(mTodaysMenuAdapter);

    }

    @Override
    public void update(List<KitchenDishResponse.Kitchenmenuimage> kitchenmenuimageArrayList) {



    }


    private void subscribeToLiveDataKitchenImages() {


        mKitchenDetailsViewModel.getKitchenCommonImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addkitchenCommonImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getFoodBadgesImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addFoodBadgesImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getSpeialItemsImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addSpecialItemsImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getFavTodaysMenuItemsImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addFavTodaysMenuItemsImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getFav1ItemsImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addFav1ImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getTodaysItemsImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addTodaysImagesList(kitchenImagesViewModel));
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
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void otherKitchenDish(Long makeitId, Integer productId, Integer quantity, Integer price) {
        DialogChangeKitchen.newInstance().show(getSupportFragmentManager(), this, makeitId, productId, quantity, price);

    }


    @Override
    public void dishListLoaded(KitchenDishResponse response) {
        stopKitchenLoader();
        if (response != null && response.getResult() != null && response.getResult().size() > 0) {
            mFavTodaysMenuAdapter.serviceable(response.getResult().get(0).isServiceableStatus());
            mTodaysMenuAdapter.serviceable(response.getResult().get(0).isServiceableStatus());
        }
    }


    @Override
    public void viewCart() {

        new Analytics().kitchenViewcart(AppConstants.CLICK_KITCHEN_VIEW_CART,kitchenID);

        Intent intent = MainActivity.newIntent(KitchenDetailsActivity.this);
        intent.putExtra("cart", true);
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
    public void onItemClickData(KitchenDishResponse.Productlist blogUrl, View view) {

        animateView(view);

    }

    public void animateView(View view) {
    }

    @Override
    public void sendCart() {

        mKitchenDetailsViewModel.totalCart();


    }

    @Override
    public void dishRefresh() {
    }

    @Override
    public void addDishFavourite(Integer favId, String fav) {

      //  mKitchenDetailsViewModel.addFavourite(favId);
    }


    @Override
    public void productNotAvailable(int quantity, String productname) {
        Toast.makeText(KitchenDetailsActivity.this, "Only " + quantity + " Quantity of " + productname + " Available", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mKitchenDetailsViewModel.removeFavourite(favId);
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

    @Override
    public void onSliderItemClicked(KitchenDishResponse.Kitchenmenuimage mBlog, String days) {

    }

    @Override
    public void onSpecialItemClickData(String url) {
        viewImage(url);


    }

    @Override
    public void onFoodBadgesItemClickData(String url) {
        viewImage(url);

    }

    public void viewImage(String url) {


        new Analytics().sendClickData(AppConstants.SCREEN_KITCHEN_DETAILS, AppConstants.CLICK_VIEW_IMAGE);

        final Dialog nagDialog = new Dialog(KitchenDetailsActivity.this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(true);
        nagDialog.setContentView(R.layout.preview_image);
        ButtonTextView btnClose = nagDialog.findViewById(R.id.btnIvClose);
        ImageView ivPreview = nagDialog.findViewById(R.id.iv_preview_image);
        if (url != null) {
           /* Glide.with(getApplicationContext()).load(url).placeholder(null)
                    .error(R.drawable.images_loading).into(ivPreview); */
            Glide.with(getApplicationContext()).load(url).into(ivPreview);
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                nagDialog.dismiss();
                new Analytics().sendClickData(AppConstants.SCREEN_KITCHEN_DETAILS, AppConstants.CLICK_CLOSE);
            }
        });
        nagDialog.show();
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

    }

    public void stopKitchenLoader() {


    }

    @Override
    public void canceled() {

    }
}

