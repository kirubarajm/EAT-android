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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFragment;
import com.tovo.eat.ui.home.homemenu.story.StoriesCardAdapter;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.home.region.RegionsAdapter;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.ui.signup.namegender.RegionListAdapter;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.card.CardSliderLayoutManager;
import com.tovo.eat.utilities.card.CardSnapHelper;
import com.tovo.eat.utilities.card.DecodeBitmapTask;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeTabFragment extends BaseFragment<FragmentHomeBinding, HomeTabViewModel> implements HomeTabNavigator, LocationListener, StartFilter,KitchenAdapter.LiveProductsAdapterListener {


    public static final String TAG = HomeTabFragment.class.getSimpleName();

    @Inject
    HomeTabViewModel mHomeTabViewModel;
    
    @Inject
    KitchenAdapter adapter;
    @Inject
    RegionsCardAdapter regionListAdapter;

    @Inject
    StoriesCardAdapter storiesCardAdapter;


    DecodeBitmapTask decodeBitmapTask;

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
        adapter.setListener(this);
    }


    @Override
    public void onPause() {
        super.onPause();

/*
        if (isFinishing() && decodeBitmapTask != null) {
             decodeBitmapTask.cancel(true);
        }*/
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


        subscribeToLiveData();



        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentHomeBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentHomeBinding.recyclerviewOrders.setAdapter(adapter);



        LinearLayoutManager mLayoutManager3
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentHomeBinding.recyclerViewStory.setLayoutManager(mLayoutManager3);
        mFragmentHomeBinding.recyclerViewStory.setAdapter(storiesCardAdapter);




        // subscribeToLiveData();

       /* mFragmentHomeBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFragmentHomeBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                mFragmentHomeBinding.shimmerViewContainer.startShimmerAnimation();
                mKitchenViewModel.fetchRepos();
            }
        });*/


        mFragmentHomeBinding.recyclerviewOrders.setItemAnimator(new DefaultItemAnimator());


        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Could hide open views here if you wanted. //
            }
        };



        mFragmentHomeBinding.recyclerviewOrders.setOnScrollListener(onScrollListener);




        mFragmentHomeBinding.recyclerViewRegion.setAdapter(regionListAdapter);
        mFragmentHomeBinding.recyclerViewRegion.setHasFixedSize(true);





     //   mFragmentHomeBinding.recyclerViewRegion.addItemDecoration(new OverlapDecoration());



        mFragmentHomeBinding.recyclerViewRegion.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //onActiveCardChange();
                }
            }
        });

        CardSliderLayoutManager  layoutManger = (CardSliderLayoutManager)    mFragmentHomeBinding.recyclerViewRegion.getLayoutManager();





        new CardSnapHelper().attachToRecyclerView(   mFragmentHomeBinding.recyclerViewRegion);
        
        
        
        
        
        
       /* FragmentTransaction transaction = getFragmentManager().beginTransaction();
        KitchenFragment fragment = new KitchenFragment();
        transaction.replace(R.id.contentKitchen, fragment);
        transaction.commit();*/



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


    private void subscribeToLiveData() {
        mHomeTabViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mHomeTabViewModel.addKitchenItemsToList(kitchenItemViewModel));
        mHomeTabViewModel.getregionItemsLiveData().observe(this,
                regionItemViewModel -> mHomeTabViewModel.addRegionItemsToList(regionItemViewModel));
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

    @Override
    public void applyFilter() {

        mHomeTabViewModel.fetchRepos();


    }

    @Override
    public void onItemClickData(Integer kitchenId) {


        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);


    }

    @Override
    public void removeDishFavourite(Integer favId) {

    }

    @Override
    public void addFav(Integer id, String fav) {

    }
}
