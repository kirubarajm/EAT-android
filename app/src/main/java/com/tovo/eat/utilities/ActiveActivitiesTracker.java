package com.tovo.eat.utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

public class ActiveActivitiesTracker {
    public static int sActiveActivities = 0;

    public static void activityStarted()
    {
        if( sActiveActivities == 0 )
        {
            // TODO: Here is presumably "application level" resume
            registerWifiReceiver();


        }
        sActiveActivities++;
    }

    public static void activityStopped()
    {
        sActiveActivities--;
        if( sActiveActivities == 0 )
        {
            // TODO: Here is presumably "application level" pause
            unregisterWifiReceiver();
        }
    }

    private static void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        MvvmApp.getInstance().registerReceiver(mWifiReceiver, filter);
    }


    private static boolean checkWifiConnect() {
        ConnectivityManager manager = (ConnectivityManager) MvvmApp.getInstance(). getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        ConnectivityManager cm =
                (ConnectivityManager) MvvmApp.getInstance() .getSystemService(Context.CONNECTIVITY_SERVICE);

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

    static BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {






            } else {


                Intent inIntent=InternetErrorFragment.newIntent(MvvmApp.getInstance());
                inIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MvvmApp.getInstance().startActivity(inIntent);



               /* FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                InternetErrorFragment fragment = new InternetErrorFragment();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                internetCheck = true;*/
            }

        }
    };
    private static void unregisterWifiReceiver() {
        MvvmApp.getInstance().  unregisterReceiver(mWifiReceiver);
    }


}