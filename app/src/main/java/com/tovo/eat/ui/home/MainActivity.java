package com.tovo.eat.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityMainBinding;
import com.tovo.eat.ui.account.MyAccountFragment;
import com.tovo.eat.ui.account.feedbackandsupport.support.replies.RepliesActivity;
import com.tovo.eat.ui.address.select.SelectAddressListActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.cart.CartActivity;
import com.tovo.eat.ui.cart.xfactoralert.XfactorListner;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.ad.bottom.PromotionFragment;
import com.tovo.eat.ui.home.homemenu.HomeTabFragment;
import com.tovo.eat.ui.notification.FirebaseDataReceiver;
import com.tovo.eat.ui.orderrating.OrderRatingActivity;
import com.tovo.eat.ui.pendingpayment.PaymentListener;
import com.tovo.eat.ui.pendingpayment.PendingPaymentAlert;
import com.tovo.eat.ui.search.SearchFragment;
import com.tovo.eat.ui.track.OrderTrackingActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.SingleShotLocationProvider;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.tovo.eat.utilities.nointernet.InternetListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, CartListener, StartFilter, InternetListener, LocationListener, PaymentListener, PaymentResultListener, XfactorListner, InstallStateUpdatedListener, OnSuccessListener<AppUpdateInfo> {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1001;
    private static final int RC_APP_UPDATE = 55669;
    public static String FLEXIBLE_UPDATE = "flexible_update";
    protected LocationManager locationManager;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    boolean doubleBackToExitPressedOnce = false;
    ProgressDialog progressDialog;
    boolean cart = false;
    String pageid = "";
    Analytics analytics;
    String pageName = AppConstants.SCREEN_HOME;
    double clatitude;
    double clongitude;
    InstallStateUpdatedListener installStateUpdatedListener;
    int getAddressCount = 0;
    AppUpdateManager appUpdateManager;
    AppUpdateInfo appUpdateInfo;

    Snackbar snackbar;

    boolean downloading;


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!checkWifiConnect()) {
                Intent inIntent = InternetErrorFragment.newIntent(MainActivity.this);
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(inIntent, AppConstants.INTERNET_ERROR_REQUEST_CODE);
            }

        }
    };
    Dialog locationDialog;
    private MainViewModel mMainViewModel;
    private ActivityMainBinding mActivityMainBinding;
    FirebaseDataReceiver dataReceiver = new FirebaseDataReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);
            try {
                Bundle bundle = intent.getExtras();
                if (bundle == null) return;
                String pageid = bundle.getString("pageid");

                if (pageid != null)
                    if (pageid.equals("8") || pageid.equals("7")) {
                        mMainViewModel.liveOrders();
                        openHome();
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }


    public void showLocationDialog() {
        locationDialog = new Dialog(this);
        locationDialog.setCancelable(false);
        locationDialog.setContentView(R.layout.dialog_get_location);

        ButtonTextView text = locationDialog.findViewById(R.id.allowgps);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationDialog.dismiss();
                startLocationTracking();

                new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_ALLOW_LOACTION);

            }
        });

        ButtonTextView dialogButton = locationDialog.findViewById(R.id.cancelgps);
        dialogButton.setVisibility(View.GONE);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        locationDialog.show();

    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void openCart() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_CART);
        stopLoader();
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CartActivity fragment = new CartActivity();
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(CartActivity.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.toolbarTitle.set("Cart");
        mMainViewModel.titleVisible.set(true);

        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(true);
        mMainViewModel.isMyAccount.set(false);
        mMainViewModel.updateAvailable.set(false);
    }

    @Override
    public void disConnectGPS() {


    }

    @Override
    public void openHome() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_GO_HOME);
        stopLoader();
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HomeTabFragment fragment = new HomeTabFragment();
            transaction.replace(R.id.content_main, fragment);

            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commitAllowingStateLoss();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.toolbarTitle.set("Home");
        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isHome.set(true);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isMyAccount.set(false);


        if (mMainViewModel.update.get()) {
            if (!mMainViewModel.isLiveOrder.get()) {
                mMainViewModel.updateAvailable.set(true);
            }
        }

    }

    @Override
    public void openExplore() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_SEARCH);
        stopLoader();
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SearchFragment fragment = new SearchFragment();
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(StoriesPagerFragment22.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }

        mMainViewModel.toolbarTitle.set("Explore");

        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(true);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isMyAccount.set(false);

        mMainViewModel.updateAvailable.set(false);


    }

    @Override
    public void paymentStausChanged() {
        // mMainViewModel.liveOrders();
    }

    @Override
    public void paymentPending(Long orderid, String brandname, int price, String products) {

        Bundle bundle = new Bundle();
        bundle.putLong("orderid", orderid);
        bundle.putString("brandname", brandname);
        bundle.putString("products", products);
        bundle.putInt("price", price);
        PendingPaymentAlert bottomSheetFragment = new PendingPaymentAlert();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.setCancelable(false);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());

    }

    public void startLoader() {
        mActivityMainBinding.loading.setVisibility(View.VISIBLE);
        mActivityMainBinding.loading.startShimmerAnimation();


    }

    public void stopLoader() {
        mActivityMainBinding.loading.setVisibility(View.GONE);
        mActivityMainBinding.loading.stopShimmerAnimation();

    }

    @Override
    public void openAccount() {
        stopLoader();
        try {
            new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_MY_ACCOUNT);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MyAccountFragment fragment = new MyAccountFragment();
            transaction.replace(R.id.content_main, fragment);
            // transaction.addToBackStack(MyAccountFragment.class.getSimpleName());
            transaction.commit();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        mMainViewModel.toolbarTitle.set("My Account");
        mMainViewModel.titleVisible.set(true);
        mMainViewModel.updateAvailable.set(false);
    }

    @Override
    public void selectAddress() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_SELECT_ADDRESS);
        Intent intent = SelectAddressListActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }

    @Override
    public void trackLiveOrder(long orderId) {
        if (mMainViewModel.checkInternet()) {
            new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_ORDER_TRACK);
            Intent intent = OrderTrackingActivity.newIntent(MainActivity.this);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please check your internet...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void retryPaymentForSamePrderID() {

        int price = mMainViewModel.liveOrderResponsePojo.getResult().get(0).getPrice();
        Long orderId = mMainViewModel.liveOrderResponsePojo.getResult().get(0).getOrderid();


        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_PAYMENT_RETRY);


        final Activity activity = this;

        final Checkout co = new Checkout();

        // co.setImage(R.mipmap.ic_launcher);

        co.setFullScreenDisable(true);
        try {
            JSONObject options = new JSONObject();
            options.put("name", getString(R.string.app_name));
            options.put("description", getString(R.string.orderid) + orderId);
            options.put("currency", "INR");
            options.put("amount", price * 100);
            options.put("customer_id", mMainViewModel.getDataManager().getRazorpayCustomerId());
            JSONObject ReadOnly = new JSONObject();
            ReadOnly.put("email", "true");
            ReadOnly.put("contact", "true");
            options.put("readonly", ReadOnly);

            co.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
        }


    }

    @Override
    public void showOrderRating(Long orderId, String brandname) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_ORDER_RATING);
        Bundle bundle = new Bundle();
        bundle.putLong("orderid", orderId);
        bundle.putString("brandname", brandname);
        OrderRatingActivity bottomSheetFragment = new OrderRatingActivity();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.setCancelable(false);
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());


    }


    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_BACK_BUTTON);
        if (doubleBackToExitPressedOnce) {
            new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_EXIT_APP);
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }


   /* public void inAppUpdate() {


        mAppUpdateManager = AppUpdateManagerFactory.create(this);

         installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                if (mAppUpdateManager != null && installStateUpdatedListener!=null) {
                    mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                }

            } else {

            }
        };


        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE, MainActivity.this, RC_APP_UPDATE);


                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                popupSnackbarForCompleteUpdate();
            } else {
                // Log.e(TAG, "checkForAppUpdateAvailability: something else");
            }
        });


    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == AppConstants.GPS_REQUEST) {
            if (resultCode == RESULT_OK) {
                new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_GPS_ON);
                getLocation();
            } else {
                new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_GPS_NO_THANKS);
                showLocationDialog();
            }
        } else if (requestCode == AppConstants.HOME_ADDRESS_CODE) {

            //if (resultCode == RESULT_OK)
            //   openHome();

            HomeTabFragment homeTabFragment = (HomeTabFragment)
                    getSupportFragmentManager()
                            .findFragmentById(R.id.content_main);

            homeTabFragment.applyFilter();


        } else if (requestCode == AppConstants.INTERNET_ERROR_REQUEST_CODE) {
        } else if (requestCode == RC_APP_UPDATE) {
            if (resultCode != RESULT_OK) {
                //  Log.e(TAG, "onActivityResult: app download failed");
            }
        }
    }

    /*private void popupSnackbarForCompleteUpdate() {

        if (mAppUpdateManager != null) {
            mAppUpdateManager.completeUpdate();
        }

        *//*Snackbar snackbar =
                Snackbar.make(
                        findViewById(R.id.root),
                        "New app is ready!",
                        Snackbar.LENGTH_INDEFINITE);

        snackbar.setAction("Install", view -> {
            if (mAppUpdateManager != null) {
                mAppUpdateManager.completeUpdate();
            }
        });

        // snackbar.setActionTextColor(getResources().getColor(R.color.install_color));
        snackbar.show();*//*
    }*/


    public void selectHomeAddress() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_SELECT_ADDRESS);
        Intent intent = SelectAddressListActivity.newIntent(MainActivity.this);
        startActivityForResult(intent, AppConstants.HOME_ADDRESS_CODE);
    }


    public void saveFcmToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        mMainViewModel.saveToken(token);
                    }
                });
    }

    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);


       /* String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        telephonyManager.getDeviceId();*/


        appUpdateManager = AppUpdateManagerFactory.create(this);

        //   updatePopup(getString(R.string.update_available), getString(R.string.update), false, true);
        // inAppUpdate();
        //  updateUIalert();

        analytics = new Analytics(this, pageName);

        saveFcmToken();

        //  updateUIalert();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(true);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            cart = intent.getExtras().getBoolean("cart");
            if (null != intent.getExtras().getString("pageid")) {
                pageid = intent.getExtras().getString("pageid");
            }
        }


        if (!mMainViewModel.isAddressAdded()) {
            startLoader();
            startLocationTracking();
        }


        mMainViewModel.liveOrders();





        /*Freshchat.setImageLoader(com.freshchat.consumer.sdk.j.af.aw(MainActivity.this));
        FreshchatConfig freshchatConfig=new FreshchatConfig("378cbc0d-a29a-4853-8a00-a7833858dc97","d7a5250c-a166-40bb-a5c4-b94b2788b52c");
       *//* freshchatConfig.setCameraCaptureEnabled(true);
        freshchatConfig.setGallerySelectionEnabled(true);*//*
        Freshchat.getInstance(getApplicationContext()).init(freshchatConfig);




        FreshchatUser freshUser=Freshchat.getInstance(getApplicationContext()).getUser();

        freshUser.setFirstName("John");
        freshUser.setLastName("Doe");
        freshUser.setEmail("john.doe.1982@mail.com");
        freshUser.setPhone("+91", "9790987495");

//Call setUser so that the user information is synced with Freshchat's servers
        try {
            Freshchat.getInstance(getApplicationContext()).setUser(freshUser);
        } catch (MethodNotAllowedException e) {
            e.printStackTrace();
        }
        *//* Set any custom metadata to give agents more context, and for segmentation for marketing or pro-active messaging *//*
        Map<String, String> userMeta = new HashMap<String, String>();
        userMeta.put("userLoginType", "Facebook");
        userMeta.put("city", "SpringField");
        userMeta.put("age", "22");
        userMeta.put("userType", "premium");
        userMeta.put("numTransactions", "5");
        userMeta.put("usedWishlistFeature", "yes");

//Call setUserProperties to sync the user properties with Freshchat's servers
        try {
            Freshchat.getInstance(getApplicationContext()).setUserProperties(userMeta);
        } catch (MethodNotAllowedException e) {
            e.printStackTrace();
        }

        //Freshchat.setImageLoader(new CustomImageLoader());

       *//* String tag = "order_queries";
        String msgText = "User has trouble with order #1234";
        FreshchatMessage FreshchatMessage = new FreshchatMessage().setTag(tag).setMessage(msgText);
        Freshchat.sendMessage(getApplicationContext(), FreshchatMessage);*//*


        List<String> tags = new ArrayList<>();
        tags.add("order_123");
        ConversationOptions convOptions = new ConversationOptions()
                .filterByTags(tags, "Order Queries");
        Freshchat.showConversations(getApplicationContext(),convOptions);*/

        // Freshchat.showConversations(getApplicationContext());
    }


    public void updateUIalert() {
        AppUpdateManager mAppUpdateManager = AppUpdateManagerFactory.create(this);

        mAppUpdateManager.registerListener(installStateUpdatedListener);

        mAppUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {

            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

                try {
                    mAppUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.FLEXIBLE, MainActivity.this, RC_APP_UPDATE);


                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }

            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                // popupSnackbarForCompleteUpdate();
            } else {
                /// Log.e(TAG, "checkForAppUpdateAvailability: something else");
            }
        });


        installStateUpdatedListener = state -> {
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                //    popupSnackbarForCompleteUpdate();
            } else if (state.installStatus() == InstallStatus.INSTALLED) {
                if (mAppUpdateManager != null) {
                    mAppUpdateManager.unregisterListener(installStateUpdatedListener);
                }

            } else {
                //  Log.i(TAG, "InstallStateUpdatedListener: state: " + state.installStatus());
            }
        };
    }


    public void startLocationTracking() {

        if (checkPermissions()) {

            new GpsUtils(MainActivity.this).turnGPSOn(new GpsUtils.onGpsListener() {
                @Override
                public void gpsStatus(boolean isGPSEnable) {
                    // turn on GPS
                    if (isGPSEnable) {
                        /*mGoogleApiClient = new GoogleApiClient.Builder(MainActivity.this)
                                .addConnectionCallbacks(mLocationRequestCallback)
                                .addApi(LocationServices.API)
                                .build();
                        mGoogleApiClient.connect();*/

                        getLocation();


                    } else {
                        //  turnOnGps();
                        showLocationDialog();
                    }
                }

            });


        } else {
            requestPermissions();
        }
    }

    public void statusUpdate() {
        mMainViewModel.totalCart();
        mMainViewModel.saveRequestData();
        mMainViewModel.getDataManager().setIsFav(false);
//        mMainViewModel.liveOrders();

    }


    @Override
    public void checkCart() {
        if (!mMainViewModel.totalCart()) {
           /* mMainViewModel.isHome.set(true);
            mMainViewModel.isExplore.set(false);
            mMainViewModel.isCart.set(false);
            mMainViewModel.isMyAccount.set(false);*/

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public void applyFilter() {
        //    openHome();

        HomeTabFragment homeTabFragment = (HomeTabFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.content_main);

        homeTabFragment.applyFilter();


    }

    @Override
    public void isInternet(boolean available) {
        if (available) {
            openHome();

        }
    }

    public void internetcheck(boolean status) {
        if (status) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HomeTabFragment fragment = new HomeTabFragment();
            transaction.replace(R.id.content_main, fragment);
            transaction.commit();
            mMainViewModel.isHome.set(true);
            mMainViewModel.isExplore.set(false);
            mMainViewModel.isCart.set(false);
            mMainViewModel.isMyAccount.set(false);

        } else {

        }

    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }

    private void unregisterWifiReceiver() {
        unregisterReceiver(mWifiReceiver);
    }

    private boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

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

    @Override
    public void onDestroy() {
        super.onDestroy();

        mMainViewModel.getDataManager().appStartedAgain(false);
        try {
            unregisterReceiver(dataReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            //   Log.i(TAG, "Displaying permission rationale to provide additional context.");

            Snackbar snackbar = Snackbar.make(mActivityMainBinding.root, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(getResources().getColor(R.color.white));
            View snackbarView = snackbar.getView();
            int snackbarTextId = android.support.design.R.id.snackbar_text;
            TextView textView = snackbarView.findViewById(snackbarTextId);
            textView.setTextColor(getResources().getColor(R.color.white));
            snackbarView.setBackgroundColor((getResources().getColor(R.color.black)));

            snackbar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_PERMISSIONS_REQUEST_CODE);
                }
            });
            snackbar.show();

        } else {


            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        /*  Log.i(TAG, "onRequestPermissionResult");*/
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                /*   Log.i(TAG, "User interaction was cancelled.");*/
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                /*  Log.i(TAG, "Permission granted.");*/

                startLocationTracking();


            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                /*  Log.i(TAG, "Permission granted.");*/

                Snackbar snackbar = Snackbar.make(mActivityMainBinding.root, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE);
                snackbar.setActionTextColor(getResources().getColor(R.color.white));
                View snackbarView = snackbar.getView();
                int snackbarTextId = android.support.design.R.id.snackbar_text;
                TextView textView = snackbarView.findViewById(snackbarTextId);
                textView.setTextColor(getResources().getColor(R.color.white));
                snackbarView.setBackgroundColor(Color.parseColor("#2d77bd"));

                snackbar.setAction(R.string.settings, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setAction(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",
                                BuildConfig.APPLICATION_ID, null);
                        intent.setData(uri);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                snackbar.show();


            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {


        if (location != null) {
          /*  if (mGoogleApiClient != null)
                mGoogleApiClient.disconnect();*/
            mMainViewModel.currentLatLng(location.getLatitude(), location.getLongitude());
            openHome();
            locationManager.removeUpdates(this);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void paymentRetry() {


        mMainViewModel.retry();

    }

    @Override
    public void paymentCancel() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_PAYMENT_CANCEL);
        mMainViewModel.paymentSuccess("Canceled", 2);

    }


    @Override
    public void onPaymentSuccess(String s) {

        mMainViewModel.paymentSuccess(s, 1);

    }

    @Override
    public void onPaymentError(int i, String s) {
        mMainViewModel.paymentSuccess("Canceled", 2);
    }


    public void getLocation() {


        SingleShotLocationProvider.requestSingleUpdate(MainActivity.this,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

                        mMainViewModel.currentLatLng(location.latitude, location.longitude);
                        getAddressFromLocation(location.latitude, location.longitude);

                        /*mMainViewModel.currentLatLng(13.09342957, 80.17434692);
                        getAddressFromLocation(13.09342957, 80.17434692);*/

                       /* if (mMainViewModel.isAddressAdded()) {
                            if (cart) {
                                mMainViewModel.gotoCart();
                            } else if (pageid.equals("") && pageid.equals("9")) {

                                Intent repliesIntent = RepliesActivity.newIntent(MainActivity.this);
                                startActivity(repliesIntent);
                            } else {

                                if (mMainViewModel.isHome.get()) {


                                } else if (mMainViewModel.isMyAccount.get()) {


                                } else if (mMainViewModel.isExplore.get()) {


                                } else if (mMainViewModel.isCart.get()) {


                                } else {

                                    getAddressFromLocation(location.latitude, location.longitude);
                                   // openHome();
                                }


                            }

                        }*/

                    }
                });


    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();

        appUpdateManager.registerListener(this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(this);


        statusUpdate();

        IntentFilter intentFilter = new IntentFilter("com.google.android.c2dm.intent.RECEIVE");
        registerReceiver(dataReceiver, intentFilter);
        registerWifiReceiver();

        if (mMainViewModel.isAddressAdded()) {
            if (cart) {
                mMainViewModel.gotoCart();
            } else if (pageid.equals("") && pageid.equals("9")) {

                Intent repliesIntent = RepliesActivity.newIntent(MainActivity.this);
                startActivity(repliesIntent);
            } else {

                if (mMainViewModel.isHome.get()) {


                } else if (mMainViewModel.isMyAccount.get()) {


                } else if (mMainViewModel.isExplore.get()) {


                } else if (mMainViewModel.isCart.get()) {


                } else {

                    openHome();
                }

            }

        }

       /* mAppUpdateManager
                .getAppUpdateInfo()
                .addOnSuccessListener(
                        appUpdateInfo -> {

                            if (appUpdateInfo.updateAvailability()
                                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                                // If an in-app update is already running, resume the update.
                                try {
                                    mAppUpdateManager.startUpdateFlowForResult(
                                            appUpdateInfo,
                                            AppUpdateType.IMMEDIATE,
                                            this,
                                            RC_APP_UPDATE);
                                } catch (IntentSender.SendIntentException e) {
                                    e.printStackTrace();
                                }
                            }
                        });*/

    }

    @Override
    protected void onPause() {
        super.onPause();

        cart = false;
        pageid = "";


        try {
            unregisterReceiver(dataReceiver);
            unregisterWifiReceiver();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    private void getAddressFromLocation(double latitude, double longitude) {

        clatitude = latitude;
        clongitude = longitude;
        new AsyncTaskAddress().execute(latitude, longitude);

    }

    @Override
    public void goHome() {
        openHome();
    }

    @Override
    public void canceled() {

    }

    @Override
    public void onStateUpdate(InstallState installState) {
        int installStatus = installState.installStatus();
        switch (installStatus) {
            case InstallStatus.DOWNLOADING:
                // updateTextView.setText(R.string.downloading_text);
                //  updateButton.setVisibility(View.INVISIBLE);
                if (downloading) {
                    updatePopup("Downloading...", "", false, false, false);
                    downloading = false;
                }


                break;
            case InstallStatus.DOWNLOADED:
                //   updateTextView.setText(R.string.downloaded_text);
                //  updateButton.setVisibility(View.INVISIBLE);
                //   aboutUsText.setText(R.string.success_text);
                //  placeholderText.setText(R.string.flexible_update_downloaded);
                //  popupSnackbarForCompleteUpdate();
                downloading = false;
                updatePopup(getString(R.string.download_completed), getString(R.string.install), false, true, false);

                break;
            case InstallStatus.FAILED:
                //   updateTextView.setText(R.string.download_failed_text);
                updatePopup(getString(R.string.download_failed), getString(R.string.retry), true, true, false);
                break;
            case InstallStatus.CANCELED:
                //  updateTextView.setText(R.string.download_canceled_text);

                break;
        }
    }

    @Override
    public void onSuccess(AppUpdateInfo appUpdateInfo) {
        this.appUpdateInfo = appUpdateInfo;
        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            // If the update is downloaded but not installed,
            // notify the user to complete the update.
            //   popupSnackbarForCompleteUpdate();

            //  updatePopup(getString(R.string.download_completed),getString(R.string.install),false);

        } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {

            /*if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)){

                Toast.makeText(this, "flexible", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "update available", Toast.LENGTH_SHORT).show();
            }*/

            /* && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)*/
            //show flexible update available UI and on response from that, start flexible update

            //   updatePopup(getString(R.string.update_available), getString(R.string.update), false, true);
            this.appUpdateInfo = appUpdateInfo;
        } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADING) {


        }
    }

    private void startUpdate(AppUpdateInfo appUpdateInfo, int appUpdateType) {
        try {
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo,
                    appUpdateType,
                    MainActivity.this,
                    RC_APP_UPDATE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    /* Displays the snackbar notification and call to action. */
    private void popupSnackbarForCompleteUpdate() {
       /* Snackbar snackbar =
                Snackbar.make(
                       mActivityMainBinding.root,
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUpdateManager.completeUpdate();
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.eat_color));
        snackbar.show();*/

    }


    @Override
    public void update(boolean update, boolean forceupdate) {
        if (update)
            updatePopup(getString(R.string.update_available), getString(R.string.update), false, update, forceupdate);
    }

    @Override
    public void showPromotions(String url, boolean fullScreen, int type) {
        PromotionFragment bottomSheetFragment = new PromotionFragment();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }


    public void updatePopup(String message, String action, boolean error, boolean isUpdate, boolean forceUpdate) {

        mActivityMainBinding.update.startShimmerAnimation();


        if (mMainViewModel.isHome.get()) {
            if (!mMainViewModel.isLiveOrder.get()) {
                mMainViewModel.updateAvailable.set(isUpdate);
                mMainViewModel.updateTitle.set(message);
                mMainViewModel.updateAction.set(action);
                mMainViewModel.enableLater.set(forceUpdate);
                mMainViewModel.update.set(isUpdate);
            }
        } else {
            mMainViewModel.updateAvailable.set(false);
        }

        if (error) {
            mActivityMainBinding.action.setTextColor(getResources().getColor(R.color.red));
        } else {
            mActivityMainBinding.action.setTextColor(getResources().getColor(R.color.green));
        }

        mActivityMainBinding.action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (action.equals(getString(R.string.install))) {

                    appUpdateManager.completeUpdate();

                } else if (action.equals(getString(R.string.update))) {

                    if (appUpdateInfo != null && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);
                        downloading = true;
                        mMainViewModel.updateAvailable.set(false);
                    } else {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        finish();

                    }


                   /* if (snackbar != null)
                        if (snackbar.isShown())
                            snackbar.dismiss();*/

                    //  updatePopup("Downloading...", "", false);
                } else if (action.isEmpty()) {
                    //  startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);
                } else if (action.equals(getString(R.string.retry))) {
                    if (appUpdateInfo != null && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);
                        downloading = true;
                        mMainViewModel.updateAvailable.set(false);
                    } else {
                        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                        finish();

                    }

                } else {
                    return;
                }

            }
        });
        mActivityMainBinding.later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainViewModel.updateAvailable.set(false);
                mMainViewModel.update.set(false);
            }
        });





      /*  if (snackbar != null)
            if (snackbar.isShown())
                snackbar.dismiss();

        LayoutInflater mInflater = LayoutInflater.from(MainActivity.this);
        snackbar = Snackbar.make(mActivityMainBinding.root, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();
        TextView textView = (TextView) layout.findViewById(android.support.design.R.id.snackbar_text);
        textView.setVisibility(View.INVISIBLE);

        View snackView = mInflater.inflate(R.layout.update_snackbar, null);
        TextView textViewTop = (TextView) snackView.findViewById(R.id.name);
        textViewTop.setText(message);

        TextView textViewAction = (TextView) snackView.findViewById(R.id.action);
        textViewAction.setText(action);

        TextView textViewLater = (TextView) snackView.findViewById(R.id.later);

        if (isUpdate) {
            textViewLater.setVisibility(View.VISIBLE);
        }

        if (error) {
            textViewAction.setTextColor(getResources().getColor(R.color.red));
        }


        textViewAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (action.equals(getString(R.string.install))) {

                    appUpdateManager.completeUpdate();

                } else if (action.equals(getString(R.string.update))) {
                    startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);
                    downloading = true;
                    if (snackbar != null)
                        if (snackbar.isShown())
                            snackbar.dismiss();

                    //  updatePopup("Downloading...", "", false);
                } else if (action.isEmpty()) {
                    //  startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);
                } else if (action.equals(getString(R.string.retry))) {
                    startUpdate(appUpdateInfo, AppUpdateType.FLEXIBLE);

                } else {
                    return;
                }

            }
        });
        textViewLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (snackbar != null)
                    if (snackbar.isShown())
                        snackbar.dismiss();

            }
        });




        layout.setPadding(0, 0, 0, 0);
        layout.addView(snackView, 0);
        snackbar.show();*/
    }

    private class AsyncTaskAddress extends AsyncTask<Double, Address, Address> {


        @Override
        protected Address doInBackground(Double... doubles) {
            Geocoder geocoder = new Geocoder(MainActivity.this, Locale.ENGLISH);

            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(doubles[0], doubles[1], 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses != null)
                if (addresses.size() > 0) {
                    Address fetchedAddress = addresses.get(0);

                    return fetchedAddress;

                }

            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Address fetchedAddress) {
            super.onPostExecute(fetchedAddress);
            if (fetchedAddress != null) {
                String address = fetchedAddress.getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = fetchedAddress.getLocality();
                String state = fetchedAddress.getAdminArea();
                String country = fetchedAddress.getCountryName();
                String postalCode = fetchedAddress.getPostalCode();
                String knownName = fetchedAddress.getFeatureName();
                mMainViewModel.getDataManager().saveFirstLocation(address, fetchedAddress.getSubLocality(), city);
                openHome();
            } else {
                //openHome();
                getAddressCount++;
                if (getAddressCount < 3) {
                    new AsyncTaskAddress().execute(clatitude, clatitude);
                } else {
                    openHome();
                }
            }

        }
    }


}