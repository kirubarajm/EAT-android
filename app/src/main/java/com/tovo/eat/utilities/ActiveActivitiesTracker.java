package com.tovo.eat.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.tovo.eat.data.prefs.AppPreferencesHelper;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

public class ActiveActivitiesTracker {
    public static int sActiveActivities = 0;

    public static void activityStarted()
    {
        if( sActiveActivities == 0 )
        {
            // TODO: Here is presumably "application level" resume



        }
        sActiveActivities++;
    }

    public static void activityStopped()
    {
        sActiveActivities--;
        if( sActiveActivities == 0 ){
            // TODO: Here is presumably "application level" pause


          /*  AppPreferencesHelper appPreferencesHelper=new AppPreferencesHelper(MvvmApp.getInstance(),AppConstants.PREF_NAME);
            appPreferencesHelper.setRatingAppStatus(false);

            if (appPreferencesHelper.getAddressId()==0) {
                appPreferencesHelper.setCurrentLat(0.0);
                appPreferencesHelper.setCurrentLng(0.0);
            }*/


            //unregisterWifiReceiver();
        }
    }



}