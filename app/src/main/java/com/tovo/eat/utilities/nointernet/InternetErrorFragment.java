package com.tovo.eat.utilities.nointernet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentNoInternetBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.utilities.MvvmApp;

import javax.inject.Inject;


public class InternetErrorFragment extends BaseActivity<FragmentNoInternetBinding, InternetErrorViewModel> implements InternetErrorNavigator {


    public static final String TAG = InternetErrorFragment.class.getSimpleName();
    @Inject
    InternetErrorViewModel mInternetErrorViewModel;

    FragmentNoInternetBinding mFragmentNoInternetBinding;


    InternetListener internetListener;


    /* @Override
     public void onAttach(Context context) {
         internetListener = (InternetListener) context;
         super.onAttach(context);
     }*/
    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {


                finish();


            }

        }
    };

    public static Intent newIntent(Context context) {
        return new Intent(context, InternetErrorFragment.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentNoInternetBinding = getViewDataBinding();
        mInternetErrorViewModel.setNavigator(this);


    }

    @Override
    public int getBindingVariable() {
        return BR.internetErrorViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_no_internet;
    }

    @Override
    public InternetErrorViewModel getViewModel() {
        //  mFoodMenuViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FoodMenuViewModel.class);
        return mInternetErrorViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void retry() {
        // internetListener.isInternet(mInternetErrorViewModel.checkInternet());

        if (mInternetErrorViewModel.checkInternet()) finish();


    }

    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mWifiReceiver, filter);
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
        unregisterReceiver(mWifiReceiver);
    }


    @Override
    protected void onStart() {
        super.onStart();
        registerWifiReceiver();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterWifiReceiver();
    }
}
