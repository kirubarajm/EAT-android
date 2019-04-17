package com.tovo.eat.ui.home;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityMainBinding;
import com.tovo.eat.databinding.NavHeaderMainBinding;
import com.tovo.eat.ui.account.MyAccountFragment;
import com.tovo.eat.ui.address.select.SelectSelectAddressListActivity;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.cart.CartActivity;
import com.tovo.eat.ui.home.homemenu.FilterListener;
import com.tovo.eat.ui.home.homemenu.HomeTabFragment;
import com.tovo.eat.ui.track.OrderTrackingActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, CartListener {

    public FilterListener filterListener;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;
    private ActivityMainBinding mActivityMainBinding;
    //private SwipePlaceHolderView mCardsContainerView;
    private DrawerLayout mDrawer;
    private MainViewModel mMainViewModel;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;
    private ViewGroup mRrootLayout;
    private int _xDelta;
    private int _yDelta;

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
    public void openNavDrawer() {

        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            mDrawer.openDrawer(GravityCompat.START);
        }


    }

    @Override
    public void openCart() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CartActivity fragment = new CartActivity();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();

        mMainViewModel.toolbarTitle.set("Cart");
        mMainViewModel.titleVisible.set(true);

    }

    @Override
    public void openHome() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HomeTabFragment fragment = new HomeTabFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();

        mMainViewModel.toolbarTitle.set("Home");
        mMainViewModel.titleVisible.set(false);

    }

    @Override
    public void openExplore() {

        mMainViewModel.toolbarTitle.set("Explore");
        mMainViewModel.titleVisible.set(true);
    }

    @Override
    public void openAccount() {

       /* Intent intent = AddressListActivity.newIntent(MainActivity.this);
        startActivity(intent);*/


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MyAccountFragment fragment = new MyAccountFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();

        mMainViewModel.toolbarTitle.set("My Account");
        mMainViewModel.titleVisible.set(true);

    }

    @Override
    public void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void selectAddress() {
        Intent intent = SelectSelectAddressListActivity.newIntent(MainActivity.this);
        startActivity(intent);
    }

    @Override
    public void trackLiveOrder(Integer orderId) {

       // Toast.makeText(this, "Tracking is working", Toast.LENGTH_SHORT).show();

        Intent intent= OrderTrackingActivity.newIntent(MainActivity.this);
        startActivity(intent);

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {

        if (this.mDrawer.isDrawerOpen(GravityCompat.START)) {
            this.mDrawer.closeDrawer(GravityCompat.START);
        } else {


            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(HomeTabFragment.TAG);
            if (fragment == null) {
                super.onBackPressed();
            } else {
                onFragmentDetached(HomeTabFragment.TAG);
            }

        }

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
            unlockDrawer();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);

        mMainViewModel.liveOrders();


        checkAndRequestPermissions();


        ///mToolbar.setTitle("Home");
        setUp();


        Intent intent = getIntent();

        if (intent.getExtras() != null) {

            if (intent.getExtras().getBoolean("cart")) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                CartActivity fragment = new CartActivity();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
            } else {
                if (savedInstanceState == null) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    HomeTabFragment fragment = new HomeTabFragment();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();
                }
            }
        }else {

            if (savedInstanceState == null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                HomeTabFragment fragment = new HomeTabFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
            }


        }


        mMainViewModel.totalCart();

       /* mRrootLayout = (ViewGroup) findViewById(R.id.root);
        RelativeLayout relativeLayout =  mRrootLayout.findViewById(R.id.cart_view);

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(260,260);
        relativeLayout.setLayoutParams(layoutParams);
        relativeLayout.setOnTouchListener(this);*/
    }


    @Override
    protected void onResume() {
        super.onResume();

        mMainViewModel.totalCart();

        mMainViewModel.liveOrders();

        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }


        if (mMainViewModel.updateAddressTitle() != null) {
            mMainViewModel.addressTitle.set(mMainViewModel.updateAddressTitle());
        } else {

            mMainViewModel.addressTitle.set("Select Address");

        }

    }

    private void lockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void setUp() {
        mDrawer = mActivityMainBinding.drawerView;
        //   mToolbar = mActivityMainBinding.toolbar;
        mNavigationView = mActivityMainBinding.navigationView;
        //mCardsContainerView = mActivityMainBinding.cardsContainer;

        setSupportActionBar(mToolbar);
        //this.setActionBarTitle("Home");
//        mToolbar.setTitle("title");
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mMainViewModel.updateAppVersion(version);
        mMainViewModel.onNavMenuCreated();
        //setupCardContainerView();
        //subscribeToLiveData();
        //showAboutFragment();
    }



/*
    private void setupCardContainerView() {
        int screenWidth = ScreenUtils.getScreenWidth(this);
        int screenHeight = ScreenUtils.getScreenHeight(this);

        mCardsContainerView.getBuilder()
                .setDisplayViewCount(3)
                .setHeightSwipeDistFactor(10)
                .setWidthSwipeDistFactor(5)
                .setSwipeDecor(new SwipeDecor()
                        .setViewWidth((int) (0.90 * screenWidth))
                        .setViewHeight((int) (0.75 * screenHeight))
                        .setPaddingTop(20)
                        .setSwipeRotationAngle(10)
                        .setRelativeScale(0.01f));

        mCardsContainerView.addItemRemoveListener(count -> {
            if (count == 0) {
                // reload the contents again after 1 sec delay
                new Handler(getMainLooper()).postDelayed(() -> {
                    //Reload once all the cards are removed
                    mMainViewModel.loadQuestionCards();
                }, 800);
            } else {
                mMainViewModel.removeQuestionCard();
            }
        });
    }
*/

    private void setupNavMenu() {
        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, mActivityMainBinding.navigationView, false);
        mActivityMainBinding.navigationView.addHeaderView(navHeaderMainBinding.getRoot());
        navHeaderMainBinding.setViewModel(mMainViewModel);

       /* mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                           // showMenuFragment();
                            return true;
                        case R.id.nav_to_hub:
                          //  showHubFragment();
                            //RateUsDialog.newInstance().show(getSupportFragmentManager());
                            return true;
                        case R.id.sales:
                            //startActivity(FeedActivity.newIntent(KitchenActivity.this));
                          //  showSalesFragment();
                            return true;
                      *//*  case R.id.training:
                            showTrainingFragment();
                            //startActivity(new Intent(this,ForgotPasswordActivity.class));
                            return true;*//*
                        case R.id.faq:
                          // showFaqsFragment();
                            //startActivity(new Intent(this,ForgotPasswordActivity.class));
                            return true;
                        case R.id.log_out:
                          //  logOut();
                            //startActivity(new Intent(this,ForgotPasswordActivity.class));
                            return true;
                        default:
                            return false;
                    }
                });*/
    }


   /* public void logOut() {
        mMainViewModel.logout();
        Intent loginIntent = SignInActivityMain.newIntent(CartActivity.this);
        startActivity(loginIntent);
        finish();
    }*/


   /* private void showMenuFragment() {
        //lockDrawer();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuFragment fragment = new MenuFragment();
        transaction.replace(R.id.frame_food, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


        //getSupportFragmentManager()
        // .beginTransaction()
        // .replace(R.id.frame_food,new MyAccountFragment(),null)
        // .addToBackStack(null).commit();

     *//*   FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MyAccountFragment fragment = new MyAccountFragment();
        transaction.replace(R.id.frame_food, fragment);
        transaction.commit();*//*

    }*/

    private void showHubFragment() {


        Toast.makeText(this, "Functionality not available", Toast.LENGTH_SHORT).show();


        /*lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.frame_food, ViewInventoryFragment.newInstance(), ViewInventoryFragment.TAG)
                .commit();*/
        //getSupportFragmentManager()
        // .beginTransaction()
        // .replace(R.id.frame_food,new ViewInventoryFragment(),null)
        // .addToBackStack(null).commit();

     /*   FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ViewInventoryFragment fragment = new ViewInventoryFragment();
        transaction.replace(R.id.frame_food, fragment);
        transaction.addToBackStack(null);
        transaction.commit();*/
    }

   /* private void showSalesFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        KitchenDishActivity fragment = new KitchenDishActivity();
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    /*private void showTrainingFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.clRootView, MyAccountFragment.newInstance(), MyAccountFragment.TAG)
                .commit();
    }*/


    /*
        private void subscribeToLiveData() {
            mMainViewModel.getQuestionCardData().observe(this, questionCardDatas -> mMainViewModel.setQuestionDataList(questionCardDatas));
        }
    */
    private void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    public void setActionBarTitle(String title) {
        try {
            mToolbar.setTitle(title);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

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
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 10001) {

            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++) {

                    if (permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "storage granted");

                        }
                    } else if (permissions[i].equals(Manifest.permission.ACCESS_FINE_LOCATION)) {
                        if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                            Log.e("msg", "location granted");

                        }
                    }

                }

            }
        }
    }

    @Override
    public void checkCart() {
        mMainViewModel.totalCart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }



    /*public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                        .getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);
                break;
        }
        mRrootLayout.invalidate();
        return true;
    }*/


}