package com.tovo.eat.ui.home.homemenu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentHomeBinding;
import com.tovo.eat.ui.account.favorites.FavoritesTabActivity;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.address.select.SelectSelectAddressListActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.filter.FilterFragment;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.utilities.GpsUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeTabFragment extends BaseFragment<FragmentHomeBinding, HomeTabViewModel> implements HomeTabNavigator, LocationListener {


    public static final String TAG = HomeTabFragment.class.getSimpleName();

    @Inject
    HomeTabViewModel mHomeTabViewModel;
    @Inject
    HomeTabAdapter mHomeTabAdapter;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1001;
    private FragmentHomeBinding mFragmentHomeBinding;
    private GoogleApiClient mGoogleApiClient;
    private GoogleApiClient.ConnectionCallbacks mLocationRequestCallback = new GoogleApiClient
            .ConnectionCallbacks() {

        @Override
        public void onConnected(Bundle bundle) {
            LocationRequest request = new LocationRequest();
            request.setInterval(1000);
            request.setFastestInterval(1000);
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


            try {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                        request, HomeTabFragment.this::onLocationChanged);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }

        @Override
        public void onConnectionSuspended(int reason) {
            // TODO: Handle gracefully
        }


    };
    public static HomeTabFragment newInstance() {
        Bundle args = new Bundle();
        HomeTabFragment fragment = new HomeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.homeTabViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeTabViewModel getViewModel() {
        return mHomeTabViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void selectAddress() {
        Intent intent = AddressListActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void filter() {
        FilterFragment bottomSheetFragment = new FilterFragment();
        bottomSheetFragment.show(getBaseActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void favourites() {

       /* FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FavoritesTabActivity fragment = new FavoritesTabActivity();
        transaction.replace(R.id.content_main, fragment);
        transaction.commitNow();*/

       Intent intent=FavoritesTabActivity.newIntent(getContext());
       startActivity(intent);


    }

    @Override
    public void disconnectGps() {
        mGoogleApiClient.disconnect();
    }

    @Override
    public void loaded() {
        setUp();
        mFragmentHomeBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentHomeBinding.shimmerViewContainer.stopShimmerAnimation();
        mHomeTabViewModel.favIcon.set(true);




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeTabViewModel.setNavigator(this);
        mHomeTabViewModel.updateAddressTitle();
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHomeBinding = getViewDataBinding();



        mFragmentHomeBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.shimmerViewContainer.startShimmerAnimation();

        if (mHomeTabViewModel.isAddressAdded()){
            setUp();
            mFragmentHomeBinding.shimmerViewContainer.setVisibility(View.GONE);
            mFragmentHomeBinding.shimmerViewContainer.stopShimmerAnimation();
            mHomeTabViewModel.favIcon.set(true);

        }else {
           startLocationTracking();
        }


    }

    private void setUp() {
        mHomeTabAdapter.setCount(2);
        mFragmentHomeBinding.myaccViewPager.setAdapter(mHomeTabAdapter);
        mFragmentHomeBinding.myaccTabLayout.addTab(mFragmentHomeBinding.myaccTabLayout.newTab().setText("Kitchen"));
        mFragmentHomeBinding.myaccTabLayout.addTab(mFragmentHomeBinding.myaccTabLayout.newTab().setText("Region"));
        mFragmentHomeBinding.myaccViewPager.setOffscreenPageLimit(mFragmentHomeBinding.myaccTabLayout.getTabCount());
        mFragmentHomeBinding.myaccViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mFragmentHomeBinding.myaccTabLayout));
        mFragmentHomeBinding.myaccTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentHomeBinding.myaccViewPager.setCurrentItem(tab.getPosition());

                mHomeTabViewModel.setCurrentFragment(tab.getPosition());



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


                mHomeTabViewModel.setCurrentFragment(0);

            }
        });


        for (int i = 0; i < mFragmentHomeBinding.myaccTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mFragmentHomeBinding.myaccTabLayout.getTabAt(i);
            RelativeLayout relativeLayout = (RelativeLayout)
                    LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, mFragmentHomeBinding.myaccTabLayout, false);

            TextView tabTextView = (TextView) relativeLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(relativeLayout);
            /*tab.select();*/
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mHomeTabViewModel.updateAddressTitle();


    }


    private boolean checkAndRequestPermissions() {

        int writepermission = ContextCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        int callpermission = ContextCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.CALL_PHONE);

        int locationpermission = ContextCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_FINE_LOCATION);

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



    private void startLocationTracking() {


        if (checkPermissions()) {

            new GpsUtils(getBaseActivity()).turnGPSOn(new GpsUtils.onGpsListener() {
                @Override
                public void gpsStatus(boolean isGPSEnable) {
                    // turn on GPS
                    if (isGPSEnable) {
                        mGoogleApiClient = new GoogleApiClient.Builder(getBaseActivity())
                                .addConnectionCallbacks(mLocationRequestCallback)
                                .addApi(LocationServices.API)
                                .build();
                        mGoogleApiClient.connect();
                    } else {
                        startLocationTracking();
                    }
                }

            });


        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getBaseActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(getBaseActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION);
        if (shouldProvideRationale) {
            //   Log.i(TAG, "Displaying permission rationale to provide additional context.");

            Snackbar snackbar = Snackbar.make(mFragmentHomeBinding.homemain, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE);
            snackbar.setActionTextColor(getResources().getColor(R.color.white));
            View snackbarView = snackbar.getView();
            int snackbarTextId = android.support.design.R.id.snackbar_text;
            TextView textView = (TextView) snackbarView.findViewById(snackbarTextId);
            textView.setTextColor(getResources().getColor(R.color.white));
            snackbarView.setBackgroundColor(Color.parseColor("#2d77bd"));

            snackbar.setAction("Ok", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(getBaseActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_PERMISSIONS_REQUEST_CODE);
                }
            });
            snackbar.show();

        } else {


            ActivityCompat.requestPermissions(getBaseActivity(),
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
                Snackbar snackbar = Snackbar.make(mFragmentHomeBinding.homemain, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE);
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
        mHomeTabViewModel.currentLatLng(location.getLatitude(), location.getLongitude());
        mHomeTabViewModel.favIcon.set(true);

    }

}
