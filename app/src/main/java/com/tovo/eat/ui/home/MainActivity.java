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
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityMainBinding;
import com.tovo.eat.databinding.NavHeaderMainBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.cart.CartActivity;
import com.tovo.eat.ui.home.homemenu.HomeTabFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector, CartListener {

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


    public static Intent newIntent(Context context) {
       /* Intent intent = new Intent(context, CartActivity.class);
        return intent;*/
        return new Intent(context, MainActivity.class);
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
        Intent intent = CartActivity.newIntent(MainActivity.this);
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
        //super.onBackPressed();

      /*  FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() == 0) {
            //fm.popBackStack();
            showExitDialog();
            // SharedPreference sharedPreference = new SharedPreference(this);
            // sharedPreference.clearSession();
            // finish();
        } else {

            super.onBackPressed();
        }*/
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


        checkAndRequestPermissions();


        ///mToolbar.setTitle("Home");
        setUp();

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            HomeTabFragment fragment = new HomeTabFragment();
            transaction.replace(R.id.content_main, fragment);
            transaction.commit();
        }

        mMainViewModel.totalCart();

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
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
        // .replace(R.id.frame_food,new TrainingFragment(),null)
        // .addToBackStack(null).commit();

     *//*   FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TrainingFragment fragment = new TrainingFragment();
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
        DishFragment fragment = new DishFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }*/

    /*private void showTrainingFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.clRootView, TrainingFragment.newInstance(), TrainingFragment.TAG)
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


/*
        int totalCart = mMainViewModel.totalCart();
        Log.e("Cart", String.valueOf(totalCart));


        if (totalCart==0){




        }else{




        }*/


    }
}