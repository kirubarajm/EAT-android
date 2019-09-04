package com.tovo.eat.ui.home;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.dialog.DialogSelectAddress;
import com.tovo.eat.ui.home.homemenu.FilterListener;
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
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.tovo.eat.utilities.nointernet.InternetListener;

import org.json.JSONObject;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, CartListener, StartFilter, InternetListener, LocationListener, PaymentListener, PaymentResultListener {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1001;
    public FilterListener filterListener;
    protected LocationManager locationManager;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    String DialogTag = DialogSelectAddress.newInstance().getTag();
    boolean internetCheck = false;
    boolean doubleBackToExitPressedOnce = false;
    ProgressDialog progressDialog;

    boolean cart = false;
    String pageid = "";


    FusedLocationProviderClient fusedLocationClient;

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //   if (mMainViewModel.isAddressAdded()) {
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
    //private SwipePlaceHolderView mCardsContainerView;
    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private ViewGroup mRrootLayout;
    private int _xDelta;
    private int _yDelta;
    private BroadcastReceiver mNetworkReceiver;
   /* private GoogleApiClient mGoogleApiClient;
    private GoogleApiClient.ConnectionCallbacks mLocationRequestCallback = new GoogleApiClient
            .ConnectionCallbacks() {

        @Override
        public void onConnected(Bundle bundle) {
            LocationRequest request = new LocationRequest();
            request.setInterval(1);
            request.setFastestInterval(1);
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            try {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                        request, MainActivity.this::onLocationChanged);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onConnectionSuspended(int reason) {
            // TODO: Handle gracefully
        }

    };*/

    public static Intent newIntent(Context context) {
       /* Intent intent = new Intent(context, CartActivity.class);
        return intent;*/
        return new Intent(context, MainActivity.class);
    }

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
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

            }
        });

        ButtonTextView dialogButton = locationDialog.findViewById(R.id.cancelgps);
        dialogButton.setVisibility(View.GONE);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* locationDialog.dismiss();
                finish();
*/
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
        stopLoader();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CartActivity fragment = new CartActivity();
        transaction.replace(R.id.content_main, fragment);
        //  transaction.addToBackStack(CartActivity.class.getSimpleName());
        transaction.commit();

        mMainViewModel.toolbarTitle.set("Cart");
        mMainViewModel.titleVisible.set(true);

        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(true);
        mMainViewModel.isMyAccount.set(false);

    }

    @Override
    public void disConnectGPS() {


    }

    @Override
    public void openHome() {
        stopLoader();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeTabFragment fragment = new HomeTabFragment();
        transaction.replace(R.id.content_main, fragment);
        //  transaction.addToBackStack(StoriesPagerFragment.class.getSimpleName());
        transaction.commitAllowingStateLoss();

        mMainViewModel.toolbarTitle.set("Home");
        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isHome.set(true);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isMyAccount.set(false);

    }

    @Override
    public void openExplore() {
        stopLoader();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        transaction.replace(R.id.content_main, fragment);
        //  transaction.addToBackStack(StoriesPagerFragment.class.getSimpleName());
        transaction.commit();


        mMainViewModel.toolbarTitle.set("Explore");

        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(true);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isMyAccount.set(false);


    }

    @Override
    public void paymentStausChanged() {
        mMainViewModel.liveOrders();
    }

    @Override
    public void paymentPending(int orderid, String brandname, int price, String products) {

        Bundle bundle = new Bundle();
        bundle.putInt("orderid", orderid);
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

        //  if (!progressDialog.isShowing()) progressDialog.show();


        // mActivityMainBinding.loading.setVisibility(View.VISIBLE);

    }

    public void stopLoader() {
        mActivityMainBinding.loading.setVisibility(View.GONE);
        mActivityMainBinding.loading.stopShimmerAnimation();
        // if (progressDialog.isShowing()) progressDialog.dismiss();
        // mActivityMainBinding.loading.setVisibility(View.GONE);
    }

    @Override
    public void openAccount() {
        stopLoader();


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MyAccountFragment fragment = new MyAccountFragment();
        transaction.replace(R.id.content_main, fragment);
        //  transaction.addToBackStack(MyAccountFragment.class.getSimpleName());
        transaction.commit();
        mMainViewModel.toolbarTitle.set("My Account");
        mMainViewModel.titleVisible.set(true);


    }

    Fragment getCurrentFragment() {
        Fragment currentFragment = getSupportFragmentManager()
                .findFragmentById(R.id.content_main);
        return currentFragment;
    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        /*Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        View toastView = toast.getView(); // This'll return the default View of the Toast.

        *//* And now you can get the TextView of the default View of the Toast. *//*
        TextView toastMessage = (TextView) toastView.findViewById(android.R.id.message);
        toastMessage.setTextSize(12);
        toastMessage.setTextColor(Color.WHITE);
       // toastMessage.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.ic_fly, 0, 0, 0);
        toastMessage.setGravity(Gravity.CENTER);
        toastView.setBackgroundColor(Color.BLACK);
        toast.show();*/

      /*  new CustomToast(this, msg);
        CustomToast.show();*/

    }

    @Override
    public void selectAddress() {
        Intent intent = SelectAddressListActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }

    @Override
    public void trackLiveOrder(Integer orderId) {

        // Toast.makeText(this, "Tracking is working", Toast.LENGTH_SHORT).show();
        if (mMainViewModel.checkInternet()) {
            Intent intent = OrderTrackingActivity.newIntent(MainActivity.this);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Please check your internet...", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void showOrderRating(Integer orderId, String brandname) {

     /*   Intent intent = OrderCanceledBottomFragment.newIntent(MainActivity.this);
        intent.putExtra("orderid", orderId);
        startActivity(intent);*/


        Bundle bundle = new Bundle();
        bundle.putInt("orderid", orderId);
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
        if (doubleBackToExitPressedOnce) {
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

           /* FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(StoriesPagerFragment.TAG);
            if (fragment == null) {
                super.onBackPressed();
            } else {
                onFragmentDetached(StoriesPagerFragment.TAG);
            }*/

        //  showExitDialog();

       /* FragmentManager fm = getSupportFragmentManager();


      //  Fragment fragment = fm.findFragmentByTag(StoriesPagerFragment.TAG);

        Fragment fragment = fm.findFragmentById(R.id.content_main);




        if (fragment instanceof StoriesPagerFragment) {


                if (doubleBackToExitPressedOnce) {
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


        }else {

            if (fm.getBackStackEntryCount() > 0) {
                Log.i("TestActivity", "popping backstack");
                *//*fm.popBackStack();*//*
                fm.popBackStack();
            } else {

                if (doubleBackToExitPressedOnce) {
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


        }*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        HomeTabFragment homeTabFragment = new HomeTabFragment();

/*
        Fragment fragment = (Fragment) getSupportFragmentManager().findFragmentByTag(homeTabFragment.getTag());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        openHome();*/


        if (requestCode == AppConstants.GPS_REQUEST) {


            if (resultCode == RESULT_OK) {

                getLocation();
               /* new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getLocation();
                    }
                }, 5000);*/


            } else {
                showLocationDialog();
            }


        } else if (requestCode == AppConstants.HOME_ADDRESS_CODE) {
            openHome();
        } else if (requestCode == AppConstants.INTERNET_ERROR_REQUEST_CODE) {
            /* openCurrentFragment();*/
        }
    }


    public void openCurrentFragment() {
        if (mMainViewModel.isHome.get()) {
            openHome();
        } else if (mMainViewModel.isExplore.get()) {
            openExplore();
        } else if (mMainViewModel.isCart.get()) {
            openCart();
        } else if (mMainViewModel.isMyAccount.get()) {
            openAccount();
        } else {
            openHome();
        }


    }


    public void selectHomeAddress() {

        Intent intent = SelectAddressListActivity.newIntent(MainActivity.this);
        startActivityForResult(intent, AppConstants.HOME_ADDRESS_CODE);
    }


    public void saveFcmToken() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            //   Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();

                        mMainViewModel.saveToken(token);


                    }
                });


    }

    private void showExitDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Do you want to exit?");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //SharedPreference sharedPreference = new SharedPreference(KitchenActivity.this);
                        //sharedPreference.clearSession();
                        //Intent i = new Intent(this, LoginActivity.class);
                        //startActivity(i);
                        //clearSharedPreference();
                        finish();

                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
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


        saveFcmToken();

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


        if (mMainViewModel.getDataManager().getAddressId() == 0) {
            mMainViewModel.getDataManager().setCurrentLat(0.0);
            mMainViewModel.getDataManager().setCurrentLng(0.0);
            startLoader();
            startLocationTracking();
        }

//        Intent intent = getIntent();
//        if (mMainViewModel.isAddressAdded()) {
//            if (intent.getExtras() != null) {
//                if (intent.getExtras().getBoolean("cart")) {
//                    mMainViewModel.gotoCart();
//                } else if (null != intent.getExtras().getString("pageid") && intent.getExtras().getString("pageid").equals("9")) {
//
//                    Intent repliesIntent = RepliesActivity.newIntent(MainActivity.this);
//                    startActivity(repliesIntent);
//                } else {
//                    openHome();
//                }
//            }else {
//                openHome();
//            }
//        }else {
//            startLoader();
//            startLocationTracking();
//
//        }



        /*if (intent.getExtras() != null) {
            if (intent.getExtras().getBoolean("cart")) {

                if (mMainViewModel.isAddressAdded()) {
                    mMainViewModel.gotoCart();
                } else {
                    openHome();
                }
            } else if (null != intent.getExtras().getString("pageid") && intent.getExtras().getString("pageid").equals("9")) {

                Intent repliesIntent = RepliesActivity.newIntent(MainActivity.this);
                startActivity(repliesIntent);


            } else {
                if (mMainViewModel.getDataManager().getAddressId() == 0) {
                    mMainViewModel.getDataManager().setCurrentLat(0.0);
                    mMainViewModel.getDataManager().setCurrentLng(0.0);
                    startLoader();
                    startLocationTracking();
                } else {

                    openHome();
                }

            }
        } else {
            if (mMainViewModel.getDataManager().getAddressId() == 0) {
                mMainViewModel.getDataManager().setCurrentLat(0.0);
                mMainViewModel.getDataManager().setCurrentLng(0.0);
                startLoader();
                startLocationTracking();
            } else {

                openHome();
            }

        }*/






       /* IntentFilter intentFilter= new IntentFilter(AppConstants.FCM_RECEIVER_ORDER);
        registerReceiver(orderReciever,intentFilter);*/

        //  checkAndRequestPermissions();


       /* if (mMainViewModel.isAddressAdded()) {

            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                if (intent.getExtras().getBoolean("cart")) {
                    mMainViewModel.gotoCart();
                } else {

                    openHome();

                }
            } else {


                openHome();

            }

        } else {


            Intent intent = getIntent();
            if (intent.getExtras() != null) {
                if (intent.getExtras().getBoolean("cart")) {
                    mMainViewModel.gotoCart();
                } else {

                    startLoader();
                    startLocationTracking();

                }
            } else {

                startLoader();
                startLocationTracking();

            }

        }*/

        // startLocationTracking();


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

        /*if (mMainViewModel.isAddressAdded()) {
            mMainViewModel.addressTitle.set(mMainViewModel.updateAddressTitle());

        } else {
            mMainViewModel.addressTitle.set("Current location");
            startLocationTracking();
        }*/
        mMainViewModel.liveOrders();

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

        openHome();
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
            TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
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
                TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
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
        int price = mMainViewModel.liveOrderResponsePojo.getResult().get(0).getPrice();
        int orderId = mMainViewModel.liveOrderResponsePojo.getResult().get(0).getOrderid();


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
    public void paymentCancel() {
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
                    @Override public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {

                        mMainViewModel.currentLatLng(location.latitude, location.longitude);
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

                    }
                });




       /* fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            mMainViewModel.currentLatLng(location.getLatitude(), location.getLongitude());
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

                        } else {

                            getLocation();

                          //  getlocationUsingDefault();
                        }
                    }

                });
*/
    }



    public void getlocationUsingDefault(){

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AppConstants.GPS_REQUEST);


            }

               locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3, 1, this);

            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);

          if( locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)!=null){
              Location ll=  locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
              mMainViewModel.currentLatLng(ll.getLatitude(), ll.getLongitude());
              openHome();
             /* locationManager.removeUpdates(this);*/

          }



        }

    }






    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
       /* if (mMainViewModel.getDataManager().getAddressId() == 0) {
            mMainViewModel.getDataManager().setCurrentLat(0.0);
            mMainViewModel.getDataManager().setCurrentLng(0.0);
        }*/

        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();

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

/*
        if (!mMainViewModel.isAddressAdded()) {
            startLoader();
            startLocationTracking();
        }*/

        /*if (mMainViewModel.getDataManager().getAddressId()==0){
            startLocationTracking();
        }*/
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
}