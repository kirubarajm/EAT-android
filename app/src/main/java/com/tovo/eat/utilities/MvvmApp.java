/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.tovo.eat.utilities;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.di.component.DaggerAppComponent;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;


/**
 * Created by amitshekhar on 07/07/17.
 */

public class MvvmApp extends Application implements HasActivityInjector {

    public static final String TAG = MvvmApp.class
            .getSimpleName();
    /*@Inject
    CalligraphyConfig mCalligraphyConfig;*/
    private static MvvmApp mInstance;
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {


            } else {


                startActivity(new Intent(InternetErrorFragment.newIntent(MvvmApp.getInstance())));



               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }

        }
    };
    private RequestQueue mRequestQueue;

    public static synchronized MvvmApp getInstance() {
        return mInstance;
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
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
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        FirebaseAnalytics.getInstance(this);


        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);
        appPreferencesHelper.setRatingAppStatus(true);


        Crashlytics.getInstance();


        //AppLogger.init();

        /*AndroidNetworking.initialize(getApplicationContext());
        if (BuildConfig.DEBUG) {
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
        }*/

        //CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }


 public Map<String, String>  setHeaders(String version) {

     HashMap<String, String> headers = new HashMap<>();
     headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
     //  headers.put("Content-Type", "application/json; charset=utf-8");
     headers.put("accept-version",version);
     headers.put("apptype",AppConstants.APP_TYPE_ANDROID);
     //  headers.put("Authorization","Bearer");
     AppPreferencesHelper preferencesHelper=new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);
     headers.put("Authorization","Bearer "+preferencesHelper.getApiToken());

     // headers.put("token",preferencesHelper.getApiToken());


     return  headers;


    }


    public int getVersionCode() {
        return BuildConfig.VERSION_CODE;

       /*
        try {
            PackageInfo pInfo =getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;

            return pInfo.getLongVersionCode();


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/

    }

    public String getVersionName() {
        // return BuildConfig.VERSION_CODE;

        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;

            return version;


        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
    }


    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public boolean onCheckNetWork() {
        boolean isOnline = NetworkUtils.isNetworkConnected(this);
        if (!isOnline)
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        return isOnline;

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        AppPreferencesHelper appPreferencesHelper = new AppPreferencesHelper(MvvmApp.getInstance(), AppConstants.PREF_NAME);
        appPreferencesHelper.setRatingAppStatus(false);

        if (appPreferencesHelper.getAddressId() == 0) {
            appPreferencesHelper.setCurrentLat(0.0);
            appPreferencesHelper.setCurrentLng(0.0);
        }

    }
    /*public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }*/

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

}
