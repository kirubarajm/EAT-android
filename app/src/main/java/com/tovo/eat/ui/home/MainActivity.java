package com.tovo.eat.ui.home;

import android.Manifest;
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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
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
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityMainBinding;
import com.tovo.eat.ui.account.MyAccountFragment;
import com.tovo.eat.ui.address.select.SelectSelectAddressListActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.cart.CartActivity;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.dialog.DialogSelectAddress;
import com.tovo.eat.ui.home.homemenu.FilterListener;
import com.tovo.eat.ui.home.homemenu.HomeTabFragment;
import com.tovo.eat.ui.notification.FCMMeassagingService;
import com.tovo.eat.ui.notification.FirebaseDataReceiver;
import com.tovo.eat.ui.orderrating.OrderRatingActivity;
import com.tovo.eat.ui.search.SearchFragment;
import com.tovo.eat.ui.track.OrderTrackingActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.tovo.eat.utilities.nointernet.InternetListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, CartListener, StartFilter, InternetListener {

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1001;
    public FilterListener filterListener;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    String DialogTag = DialogSelectAddress.newInstance().getTag();
    boolean internetCheck = false;
    boolean doubleBackToExitPressedOnce = false;
    private MainViewModel mMainViewModel;

    FirebaseDataReceiver dataReceiver=new FirebaseDataReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            super.onReceive(context, intent);


            Bundle bundle = intent.getExtras();
            if (bundle == null) return;
            String pageid = bundle.getString("pageid");


            Toast.makeText(context, pageid, Toast.LENGTH_SHORT).show();
        }
    };



    /*BroadcastReceiver orderReciever=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


        }
    };

    class OrderReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // do your stuff here
        }
    }*/


    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
                if (internetCheck) {

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    HomeTabFragment fragment = new HomeTabFragment();
                    transaction.replace(R.id.content_main, fragment);
                    //  transaction.addToBackStack(HomeTabFragment.class.getSimpleName());
                    transaction.commit();
                    mMainViewModel.isHome.set(true);
                      mMainViewModel.isExplore.set(false);
                    mMainViewModel.isCart.set(false);
                    mMainViewModel.isMyAccount.set(false);
                }
            } else {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;
            }

        }
    };

    private ActivityMainBinding mActivityMainBinding;
    //private SwipePlaceHolderView mCardsContainerView;
    private DrawerLayout mDrawer;

    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private ViewGroup mRrootLayout;
    private int _xDelta;
    private int _yDelta;
    private BroadcastReceiver mNetworkReceiver;

    public static Intent newIntent(Context context) {
       /* Intent intent = new Intent(context, CartActivity.class);
        return intent;*/
        return new Intent(context, MainActivity.class);
    }


    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
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

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeTabFragment fragment = new HomeTabFragment();
        transaction.replace(R.id.content_main, fragment);
        //  transaction.addToBackStack(HomeTabFragment.class.getSimpleName());
        transaction.commit();

        mMainViewModel.toolbarTitle.set("Home");
        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isHome.set(true);
        mMainViewModel.isExplore.set(false);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isMyAccount.set(false);

    }

    @Override
    public void openExplore() {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SearchFragment fragment = new SearchFragment();
        transaction.replace(R.id.content_main, fragment);
        //  transaction.addToBackStack(HomeTabFragment.class.getSimpleName());
        transaction.commit();


        mMainViewModel.toolbarTitle.set("Explore");

        mMainViewModel.titleVisible.set(false);

        mMainViewModel.isHome.set(false);
        mMainViewModel.isExplore.set(true);
        mMainViewModel.isCart.set(false);
        mMainViewModel.isMyAccount.set(false);


    }

    @Override
    public void openAccount() {

       /* Intent intent = RegionDetailsActivity.newIntent(FilterActivity.this);
        startActivity(intent);*/

        if (mMainViewModel.checkInternet()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            MyAccountFragment fragment = new MyAccountFragment();
            transaction.replace(R.id.content_main, fragment);
            //  transaction.addToBackStack(MyAccountFragment.class.getSimpleName());
            transaction.commit();

            mMainViewModel.toolbarTitle.set("My Account");
            mMainViewModel.titleVisible.set(true);

        }


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
        Intent intent = SelectSelectAddressListActivity.newIntent(MainActivity.this);
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
    public void showOrderRating(Integer orderId) {

        Intent intent = OrderRatingActivity.newIntent(MainActivity.this);
        intent.putExtra("orderid",orderId);
        startActivity(intent);
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
            Fragment fragment = fragmentManager.findFragmentByTag(HomeTabFragment.TAG);
            if (fragment == null) {
                super.onBackPressed();
            } else {
                onFragmentDetached(HomeTabFragment.TAG);
            }*/

        //  showExitDialog();

       /* FragmentManager fm = getSupportFragmentManager();


      //  Fragment fragment = fm.findFragmentByTag(HomeTabFragment.TAG);

        Fragment fragment = fm.findFragmentById(R.id.content_main);




        if (fragment instanceof HomeTabFragment) {


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

        registerWifiReceiver();




        IntentFilter intentFilter= new IntentFilter("com.google.android.c2dm.intent.RECEIVE");
        registerReceiver(dataReceiver,intentFilter);




       /* IntentFilter intentFilter= new IntentFilter(AppConstants.FCM_RECEIVER_ORDER);
        registerReceiver(orderReciever,intentFilter);*/


      //  checkAndRequestPermissions();


        if (mMainViewModel.isAddressAdded()) {

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

        }else {
            openHome();

        }
    }

    public void statusUpdate() {
        mMainViewModel.totalCart();
        mMainViewModel.liveOrders();
        mMainViewModel.saveRequestData();
        mMainViewModel.getDataManager().setIsFav(false);

        if (mMainViewModel.isAddressAdded()) {
            mMainViewModel.addressTitle.set(mMainViewModel.updateAddressTitle());

        } else {

          /*  Fragment oldFragment = getSupportFragmentManager().findFragmentByTag(DialogSelectAddress.class.getSimpleName());
            if (oldFragment == null) {
                DialogSelectAddress.newInstance().show(getSupportFragmentManager(), TestActivity.this);
            }*/

         //   startLocationTracking();
            mMainViewModel.addressTitle.set("Current location");

           /* Intent intent = RegionDetailsActivity.newIntent(TestActivity.this);
            intent.putExtra("for", "new");
            startActivity(intent);*/


        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        statusUpdate();

        IntentFilter intentFilter= new IntentFilter("com.google.android.c2dm.intent.RECEIVE");
        registerReceiver(dataReceiver,intentFilter);

    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(dataReceiver);

    }

    private boolean checkAndRequestPermissions() {

        int writepermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int callpermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE);

        int locationpermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (locationpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (callpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 10001);
            } else {


            }
            return false;
        }
        return true;
    }


    @Override
    public void checkCart() {
        if (!mMainViewModel.totalCart()) {
            mMainViewModel.isHome.set(true);
              mMainViewModel.isExplore.set(false);
            mMainViewModel.isCart.set(false);
            mMainViewModel.isMyAccount.set(false);

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


    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    protected void unregisterNetworkChanges() {
        try {
            unregisterReceiver(mNetworkReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterWifiReceiver();
        unregisterReceiver(dataReceiver);
    }

    private void startLocationTracking() {


        if (checkPermissions()) {

            new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
                @Override
                public void gpsStatus(boolean isGPSEnable) {
                    // turn on GPS
                }

            });


        } else {
            requestPermissions();
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

            Snackbar snackbar = Snackbar.make(mActivityMainBinding.contentMain, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(getResources().getColor(R.color.white));
            View snackbarView = snackbar.getView();
            int snackbarTextId = android.support.design.R.id.snackbar_text;
            TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
            textView.setTextColor(getResources().getColor(R.color.white));
            snackbarView.setBackgroundColor(Color.parseColor("#2d77bd"));

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

            } else {
                Snackbar snackbar = Snackbar.make(mActivityMainBinding.contentMain, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE);
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


}