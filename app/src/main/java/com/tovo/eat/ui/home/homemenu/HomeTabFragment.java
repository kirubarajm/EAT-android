package com.tovo.eat.ui.home.homemenu;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipView;
import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentHomeBinding;
import com.tovo.eat.ui.account.favorites.FavouritesActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.cart.coupon.CouponListActivity;
import com.tovo.eat.ui.filter.FilterFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.ad.bottom.PromotionFragment;
import com.tovo.eat.ui.home.homemenu.collection.FilterCollectionAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.homemenu.story.StoriesCardAdapter;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.homemenu.story.storiesactivity.StoriesTabActivity;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.home.region.list.RegionDetailsActivity;
import com.tovo.eat.ui.home.region.title.RegionsCardTitleAdapter;
import com.tovo.eat.ui.home.region.viewmore.RegionListActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.ui.search.dish.SearchDishActivity;
import com.tovo.eat.ui.track.OrderTrackingActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.CustomTypefaceSpan;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.SingleShotLocationProvider;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.card.CardSliderLayoutManager;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.scroll.InfiniteScrollListener;
import com.tovo.eat.utilities.stack.StackLayoutManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;
import static com.tovo.eat.utilities.scroll.PaginationListener.PAGE_START;

public class HomeTabFragment extends BaseFragment<FragmentHomeBinding, HomeTabViewModel> implements HomeTabNavigator,
        StartFilter, KitchenAdapter.LiveProductsAdapterListener, RegionsCardAdapter.LiveProductsAdapterListener, StoriesCardAdapter.StoriesAdapterListener, FilterCollectionAdapter.FilterCollectionAdapterListener {

    boolean locationAlertShowing = false;
    public static final String TAG = HomeTabFragment.class.getSimpleName();
    private static final int MAX_ITEMS_PER_REQUEST = 6;
    private static final int NUMBER_OF_ITEMS = 100;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1500;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1201;
    @Inject
    HomeTabViewModel mHomeTabViewModel;
    @Inject
    KitchenAdapter adapter;
    @Inject
    RegionsCardAdapter regionListAdapter;
    @Inject
    FilterCollectionAdapter filterCollectionAdapter;
    @Inject
    RegionsCardTitleAdapter regionsCardTitleAdapter;
    @Inject
    StoriesCardAdapter storiesCardAdapter;
    Dialog locationDialog;
    RegionsResponse regionsResponse;
    CardSliderLayoutManager cardSliderLayoutManager;
    StoriesResponse storiesFullResponse;
    StackLayoutManager mStackLayoutManager;
    boolean regionCardClicked = false;
    ProgressDialog progressDialog;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_HOME;
    ToolTipView myToolTipView;
    LinearLayoutManager mLayoutManager;
    int page = 1;
    int itemCount = 0;
    FilterListener filterListener;
    List<KitchenResponse.Result> kicthenListAnalytics;
    List<RegionsResponse.Result> regionListAnalytics;
    ArrayList<String> serviceableKitchenListAnalytics;
    ArrayList<String> unServiceableKitchenListAnalytics;
    ArrayList<String> regionForListAnalytics;
    private FragmentHomeBinding mFragmentHomeBinding;
    private int currentPosition;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int pgId = 0;

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
        //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics
        ((MainActivity) getActivity()).selectHomeAddress();
    }

    public void metricsAppOpens(String screenName) {
        try {
            kicthenListAnalytics = new ArrayList<>();
            regionListAnalytics = new ArrayList<>();
            serviceableKitchenListAnalytics = new ArrayList<>();
            unServiceableKitchenListAnalytics = new ArrayList<>();
            regionForListAnalytics = new ArrayList<>();
            int serviceableCount = 0, unServiceableCount = 0, regionCount = 0;
            StringBuilder serviceableKitchenSb = new StringBuilder();
            StringBuilder unServiceableKitchenSb = new StringBuilder();
            StringBuilder regionSb = new StringBuilder();

//            kicthenListAnalytics = mHomeTabViewModel.kitchenItemViewModels;

            //     kicthenListAnalytics = mHomeTabViewModel.getKitchenItemsLiveData().getValue();

           /* mHomeTabViewModel.getKitchenItemsLiveData().observe(this,
                    kitchenItemViewModel -> mHomeTabViewModel.addTempKitchenItemsToList(kitchenItemViewModel));*/


            kicthenListAnalytics = mHomeTabViewModel.kitchenItemViewModelsTemp;

            regionListAnalytics = mHomeTabViewModel.regionItemViewModels;

            for (int i = 0; i < kicthenListAnalytics.size(); i++) {
                if (kicthenListAnalytics.get(i).getType() == 0) {
                    if (kicthenListAnalytics.get(i).getServiceablestatus()) {
                        serviceableKitchenListAnalytics.add(String.valueOf(kicthenListAnalytics.get(i).getMakeituserid()));
                        serviceableCount = serviceableKitchenListAnalytics.size();
                        serviceableKitchenSb.append(kicthenListAnalytics.get(i).getMakeituserid()).append(",");
                    } else {
                        unServiceableKitchenListAnalytics.add(String.valueOf(kicthenListAnalytics.get(i).getMakeituserid()));
                        unServiceableCount = unServiceableKitchenListAnalytics.size();
                        unServiceableKitchenSb.append(kicthenListAnalytics.get(i).getMakeituserid()).append(",");
                    }
                }
            }

            for (int j = 0; j < regionListAnalytics.size(); j++) {
                regionForListAnalytics.add(regionListAnalytics.get(j).getRegionname());
                regionCount = regionForListAnalytics.size();
                regionSb.append(regionListAnalytics.get(j).getRegionid()).append(",");
            }

            String regionList = regionSb.toString();
            String strServiceableKitchenSb = serviceableKitchenSb.toString();
            String strUnServiceableKitchenSb = unServiceableKitchenSb.toString();
            String addressTitle = mHomeTabViewModel.getDataManager().getCurrentAddressTitle();

            String strRegionList = "", strServiceableKitchen = "", strUnServiceableKitchen = "";
            if (regionList.length() > 0) {
                strRegionList = regionList.substring(0, regionList.length() - 1);
            }
            if (strServiceableKitchenSb.length() > 0) {
                strServiceableKitchen = strServiceableKitchenSb.substring(0, strServiceableKitchenSb.length() - 1);
            }
            if (strUnServiceableKitchenSb.length() > 0) {
                strUnServiceableKitchen = strUnServiceableKitchenSb.substring(0, strUnServiceableKitchenSb.length() - 1);
            }

            new Analytics().appOpensMetrics(screenName, serviceableCount, unServiceableCount, regionCount, addressTitle, /*screenName,*/
                    strServiceableKitchen, strUnServiceableKitchen, strRegionList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void metricsAppHome(String screenName) {
        try {
            kicthenListAnalytics = new ArrayList<>();
            regionListAnalytics = new ArrayList<>();
            serviceableKitchenListAnalytics = new ArrayList<>();
            unServiceableKitchenListAnalytics = new ArrayList<>();
            regionForListAnalytics = new ArrayList<>();
            int serviceableCount = 0, unServiceableCount = 0, regionCount = 0;
            StringBuilder serviceableKitchenSb = new StringBuilder();
            StringBuilder unServiceableKitchenSb = new StringBuilder();
            StringBuilder regionSb = new StringBuilder();

            //  kicthenListAnalytics = mHomeTabViewModel.kitchenItemViewModels;
            kicthenListAnalytics = mHomeTabViewModel.kitchenItemsLiveData.getValue();
            regionListAnalytics = mHomeTabViewModel.regionItemViewModels;

            for (int i = 0; i < kicthenListAnalytics.size(); i++) {
                if (kicthenListAnalytics.get(i).getType() == 0) {
                    if (kicthenListAnalytics.get(i).getServiceablestatus()) {
                        serviceableKitchenListAnalytics.add(String.valueOf(kicthenListAnalytics.get(i).getMakeituserid()));
                        serviceableCount = serviceableKitchenListAnalytics.size();
                        serviceableKitchenSb.append(kicthenListAnalytics.get(i).getMakeituserid()).append(",");
                    } else {
                        unServiceableKitchenListAnalytics.add(String.valueOf(kicthenListAnalytics.get(i).getMakeituserid()));
                        unServiceableCount = unServiceableKitchenListAnalytics.size();
                        unServiceableKitchenSb.append(kicthenListAnalytics.get(i).getMakeituserid()).append(",");
                    }
                }
            }

            for (int j = 0; j < regionListAnalytics.size(); j++) {
                regionForListAnalytics.add(regionListAnalytics.get(j).getRegionname());
                regionCount = regionForListAnalytics.size();
                regionSb.append(regionListAnalytics.get(j).getRegionid()).append(",");
            }

            String regionList = regionSb.toString();
            String strServiceableKitchenSb = serviceableKitchenSb.toString();
            String strUnServiceableKitchenSb = unServiceableKitchenSb.toString();
            String addressTitle = mHomeTabViewModel.getDataManager().getCurrentAddressTitle();

            String strRegionList = "", strServiceableKitchen = "", strUnServiceableKitchen = "";
            if (regionList.length() > 0) {
                strRegionList = regionList.substring(0, regionList.length() - 1);
            }
            if (strServiceableKitchenSb.length() > 0) {
                strServiceableKitchen = strServiceableKitchenSb.substring(0, strServiceableKitchenSb.length() - 1);
            }
            if (strUnServiceableKitchenSb.length() > 0) {
                strUnServiceableKitchen = strUnServiceableKitchenSb.substring(0, strUnServiceableKitchenSb.length() - 1);
            }

            new Analytics().appHomeMetrics(screenName, serviceableCount, unServiceableCount, regionCount, addressTitle, /*screenName,*/
                    strServiceableKitchen, strUnServiceableKitchen, strRegionList, pgId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void filter() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_FILTER);
        FilterFragment bottomSheetFragment = new FilterFragment();
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void favourites() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_FAVOURITES);


        Intent intent = FavouritesActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void disconnectGps() {
    }

    @Override
    public void loaded() {
        // setUp();
        mHomeTabViewModel.loadAllApis();
        mHomeTabViewModel.favIcon.set(true);

        //  stopLoader();
    }

    @Override
    public void regionsLoaded(RegionsResponse regionResponse) {
        this.regionsResponse = regionResponse;
        stopRegioneLoader();
        initCountryText();


    }

    @Override
    public void dataLoaded() {
        //stopLoader();
    }

    @Override
    public void goBack() {

    }

    @Override
    public void collectionLoaded() {


    }

    @Override
    public void kitchenLoaded() {
        stopKitchenLoader();
        mHomeTabViewModel.paginationLoading.set(false);



       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mHomeTabViewModel.paginationLoading.set(false);
            }
        },2000);*/

    }

    @Override
    public void getFullStories(StoriesResponse storiesResponse) {
        this.storiesFullResponse = storiesResponse;
        stopStorieLoader();
    }

    @Override
    public void trackLiveOrder(long orderId) {

        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_ORDER_TRACK);
        Intent intent = OrderTrackingActivity.newIntent(getContext());
        intent.putExtra("orderid", mHomeTabViewModel.getDataManager().getOrderId());
        startActivity(intent);

    }

    @Override
    public void closeAddressAlert() {
        if (myToolTipView != null) {
            myToolTipView.remove();
            myToolTipView = null;
            mHomeTabViewModel.getDataManager().appStartedAgain(false);
        }

    }

    @Override
    public void scrollToTop() {


        //    mFragmentHomeBinding.fullScroll.smoothScrollTo(0, 0);
    }

    @Override
    public void checkApiSuccessMetrics(int pageid) {
        this.pgId = pageid;
        if (pageid == 1) {
            metricsAppOpens("");
        } else {
            //   metricsAppHome("");
        }
    }

    @Override
    public void showPromotions(String url, boolean fullScreen, int type, int promotionid) {

        Bundle bundle = new Bundle();
        bundle.putInt(AppConstants.PROMOTION_TYPE, type);
        bundle.putInt(AppConstants.PROMOTION_ID, promotionid);
        bundle.putString(AppConstants.PROMOTION_URL, url);

        PromotionFragment bottomSheetFragment = new PromotionFragment();
        bottomSheetFragment.setArguments(bundle);
        bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void addressListLoaded(boolean available) {

        if (available) {

            LocationManager locationManager = (LocationManager) getBaseActivity().getSystemService(LOCATION_SERVICE);

           /* if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                getLocation();

            } else {
                showGetLocationAlert();
            }*/

            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            if (isGPSEnabled) {
                getLocation();
            } else {
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //     ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        // public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        // int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        requestPermissions();
                        return;
                    }
                    if (checkPermissions()) {
                        Location location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();
                            float[] results = new float[1];
                            double smallestDistance = Integer.MAX_VALUE;
                            int pos = 0;
                            double distance = 0.0;
                            for (int i = 0; i < mHomeTabViewModel.addressList.getResult().size(); i++) {
                                distance = distance(latitude, longitude, mHomeTabViewModel.addressList.getResult().get(i).getLat(),
                                        mHomeTabViewModel.addressList.getResult().get(i).getLon(), "K");
                                if (smallestDistance == -1 || distance < smallestDistance) {
                                    smallestDistance = distance;
                                    pos = i;
                                }
                                if (i == (mHomeTabViewModel.addressList.getResult().size() - 1)) {
                                    if (smallestDistance <= mHomeTabViewModel.addressList.getLocationRadius()) {
                                        mHomeTabViewModel.changeAddress(pos);
                                        showAddressAler();
                                    } else {
                                        showAddressAler();
                                    }
                                }
                            }
                        } else {
                            showGetLocationAlert();
                        }

                    } else {
                        requestPermissions();
                    }


                } else {
                    showGetLocationAlert();
                }
            }
           /* if (isNetworkEnabled) {
                // no network provider is enabled
                // Log.e(“Network-GPS”, “Disable”);
            } else {
                if (isNetworkEnabled) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    double latitude = location.getLatitude();
                                    double longitude = location.getLongitude();
                                    Toast.makeText(MainActivity.this, latitude + "\n" + longitude, Toast.LENGTH_SHORT).show();
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
                            });
                    // Log.e(“Network”, “Network”);
                    if (locationManager != null) {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        Location location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            Toast.makeText(MainActivity.this, latitude + "\n" + longitude, Toast.LENGTH_SHORT).show();


                        }
                    }
                }
            }*/

        } else {
            showAddressAler();

/*
            if (mHomeTabViewModel.isAddressAdded()) {
             //   setUp();
                mHomeTabViewModel.loadAllApis();
                mHomeTabViewModel.favIcon.set(true);

            } else {
                ((MainActivity) getActivity()).startLocationTracking();
            }*/
        }

    }

    @Override
    public void getMainLocation() {
        ((MainActivity) getActivity()).startLocationTracking();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeTabViewModel.setNavigator(this);
        mHomeTabViewModel.updateAddressTitle();
        adapter.setListener(this);
        regionListAdapter.setListener(this);
        storiesCardAdapter.setListener(this);
        filterCollectionAdapter.setListener(this);
        subscribeToLiveData();

        analytics = new Analytics(getActivity(), pageName);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHomeBinding = getViewDataBinding();


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);


        setUp();

        startStoriesLoader();
        startRegionLoader();
        startKitchenLoader();
        regionsResponse = new RegionsResponse();


        //  startLocationTrackingForAddress();

        if (mHomeTabViewModel.isAddressAdded()) {
            mHomeTabViewModel.loadAllApis();
            mHomeTabViewModel.favIcon.set(true);

        } else {
            ((MainActivity) getActivity()).startLocationTracking();
        }

        if (mHomeTabViewModel.getDataManager().getAppStartedAgain()) {
            mHomeTabViewModel.getAddressList();
        }
       /* if (mHomeTabViewModel.isAddressAdded()) {
            setUp();
            mHomeTabViewModel.loadAllApis();
            mHomeTabViewModel.favIcon.set(true);

        } else {
            ((MainActivity) getActivity()).startLocationTracking();
        }*/


        mFragmentHomeBinding.fullScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                // closeAddressAlert();

            }
        });

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               // scrollToView(   mFragmentHomeBinding.fullScroll,mFragmentHomeBinding.recyclerviewOrders);
                mFragmentHomeBinding.fullScroll.scrollTo(0,mFragmentHomeBinding.recyclerViewFilterCollection.getTop());

            }
        },10000);*/


    }


    public void startLocationTrackingForAddress() {

        if (checkPermissions()) {


            new GpsUtils(getContext(), AppConstants.GPS_NORMAL_REQUEST).turnGPSOn(new GpsUtils.onGpsListener() {
                @Override
                public void gpsStatus(boolean isGPSEnable) {
                    // turn on GPS
                    if (isGPSEnable) {
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

    public void showGetLocationAlert() {
        locationAlertShowing = true;
       /// if (mWifiReceiver != null)
          registerWifiReceiver();

        ToolTip toolTip = new ToolTip()
                .withContentView(LayoutInflater.from(getContext()).inflate(R.layout.tool_tip_getlocation, null))
                // .withText("Now delivering to "+mHomeTabViewModel.getDataManager().getCurrentAddress())
                .withColor(getResources().getColor(R.color.tracking_back))
                .withShadow()
                .withTextColor(Color.BLACK)
                .withAnimationType(ToolTip.AnimationType.FROM_TOP);
        myToolTipView = mFragmentHomeBinding.activityMainTooltipframelayout.showToolTipForView(toolTip, mFragmentHomeBinding.delAddress);

        TextView noThanks = myToolTipView.findViewById(R.id.noThanks);
        TextView enableLocation = myToolTipView.findViewById(R.id.enable);

        noThanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    mHomeTabViewModel.getDataManager().setAppStartedAgain(false);
                if (myToolTipView != null) {
                    myToolTipView.remove();
                    myToolTipView = null;
                    mHomeTabViewModel.getDataManager().appStartedAgain(false);
                }
                locationAlertShowing = false;
                if (mWifiReceiver != null)
                    getContext().unregisterReceiver(mWifiReceiver);
                showAddressAler();
/*
                if (mHomeTabViewModel.isAddressAdded()) {
                 //   setUp();
                    mHomeTabViewModel.loadAllApis();
                    mHomeTabViewModel.favIcon.set(true);

                } else {
                    ((MainActivity) getActivity()).startLocationTracking();
                }*/


            }
        });

        enableLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  mHomeTabViewModel.getDataManager().setAppStartedAgain(false);
                if (myToolTipView != null) {
                    myToolTipView.remove();
                    myToolTipView = null;
                    mHomeTabViewModel.getDataManager().appStartedAgain(false);
                }
                locationAlertShowing = false;
                if (mWifiReceiver != null)
                    getContext().unregisterReceiver(mWifiReceiver);
                if (checkPermissions()) {

                    new GpsUtils(getContext(), AppConstants.GPS_NORMAL_REQUEST).turnGPSOn(new GpsUtils.onGpsListener() {
                        @Override
                        public void gpsStatus(boolean isGPSEnable) {
                            if (isGPSEnable) {
                                getLocation();
                            }

                        }
                    });
                } else {
                    requestPermissions();
                }


            }
        });

    }

    public void showAddressAler() {

        ToolTip toolTip = new ToolTip()
                .withContentView(LayoutInflater.from(getContext()).inflate(R.layout.tool_tip_address, null))
                // .withText("Now delivering to "+mHomeTabViewModel.getDataManager().getCurrentAddress())
                .withColor(getResources().getColor(R.color.tracking_back))
                .withShadow()
                .withTextColor(Color.BLACK)
                .withAnimationType(ToolTip.AnimationType.FROM_TOP);
        myToolTipView = mFragmentHomeBinding.activityMainTooltipframelayout.showToolTipForView(toolTip, mFragmentHomeBinding.delAddress);

        TextView title = myToolTipView.findViewById(R.id.activity_main_redtv);

        String aTitle = mHomeTabViewModel.getDataManager().getCurrentAddressArea() == null ? "your location" : mHomeTabViewModel.getDataManager().getCurrentAddressArea();


        String sTitle = "Now showing kitchens around " + aTitle + ".\nClick to change location!";

        title.setText(sTitle);

        myToolTipView.setOnToolTipViewClickedListener(new ToolTipView.OnToolTipViewClickedListener() {
            @Override
            public void onToolTipViewClicked(ToolTipView toolTipView) {

                if (myToolTipView != null) {
                    myToolTipView.remove();
                    myToolTipView = null;
                    mHomeTabViewModel.getDataManager().appStartedAgain(false);
                }

                // selectAddress();
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (myToolTipView != null) {

                    myToolTipView.remove();
                    myToolTipView = null;
                    mHomeTabViewModel.getDataManager().appStartedAgain(false);
                }
            }
        }, 10000);


    }

    public double getRadius(double sLat, double sLng) {

        float[] results = new float[1];

        Location.distanceBetween(sLat,
                sLat,
                mHomeTabViewModel.addressList.getResult().get(0).getLat(),
                mHomeTabViewModel.addressList.getResult().get(0).getLon(), results);


        return results[0];

    }

    public void getLocation() {


        SingleShotLocationProvider.requestSingleUpdate(getActivity(),
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        float[] results = new float[1];
                        double smallestDistance = Integer.MAX_VALUE;

                        int pos = 0;

                        double distance = 0.0;

                        if (location != null)

                            for (int i = 0; i < mHomeTabViewModel.addressList.getResult().size(); i++) {

                                /*distance = getRadius(location.latitude,
                                        location.longitude);*/


                                distance = distance(location.latitude, location.longitude, mHomeTabViewModel.addressList.getResult().get(i).getLat(),
                                        mHomeTabViewModel.addressList.getResult().get(i).getLon(), "K");

                                if (smallestDistance == -1 || distance < smallestDistance) {
                                    smallestDistance = distance;
                                    pos = i;
                                }


                                if (i == (mHomeTabViewModel.addressList.getResult().size() - 1)) {
                                    //this is the last iteration of for loop


                                    int radius=mHomeTabViewModel.addressList.getLocationRadius();

                                    if (smallestDistance <=radius) {
                                        mHomeTabViewModel.changeAddress(pos);
                                        /*if (mHomeTabViewModel.isAddressAdded()) {
                                            //setUp();
                                            mHomeTabViewModel.loadAllApis();
                                            mHomeTabViewModel.favIcon.set(true);

                                        } else {
                                            ((MainActivity) getActivity()).startLocationTracking();
                                        }*/
                                        showAddressAler();
                                    } else {

                                        showAddressAler();

                                       /* if (mHomeTabViewModel.isAddressAdded()) {
                                           // setUp();
                                            mHomeTabViewModel.loadAllApis();
                                            mHomeTabViewModel.favIcon.set(true);

                                        } else {
                                            ((MainActivity) getActivity()).startLocationTracking();
                                        }*/
                                    }
                                }


                            }

                           /* if (distance(location.latitude, location.longitude, Double.parseDouble(mHomeTabViewModel.getDataManager().getCurrentLat()), Double.parseDouble(mHomeTabViewModel.getDataManager().getCurrentLng()), "K") > 1) {
                                showAddressAler();


                            }*/


                    }
                });


    }

    public void showLocationDialog() {
        locationDialog = new Dialog(getActivity());
        locationDialog.setCancelable(false);
        locationDialog.setContentView(R.layout.dialog_get_location);

        ButtonTextView text = locationDialog.findViewById(R.id.allowgps);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationDialog.dismiss();
                startLocationTrackingForAddress();

                new Analytics().sendClickData(pageName, AppConstants.CLICK_ALLOW_LOACTION);

            }
        });

        ButtonTextView dialogButton = locationDialog.findViewById(R.id.cancelgps);
        dialogButton.setVisibility(View.GONE);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        locationDialog.show();

    }

    public double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public double distance(double lat1, double lon1, double lat2, double lon2, String sr) {


        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (sr.equals("K")) {
            dist = dist * 1.609344;
        } else if (sr.equals("N")) {
            dist = dist * 0.8684;
        }
        return (dist);
    }


    private void setUp() {


        int size = 1;

        mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentHomeBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentHomeBinding.recyclerviewOrders.setAdapter(adapter);

        mFragmentHomeBinding.recyclerviewOrders.setNestedScrollingEnabled(false);

        mFragmentHomeBinding.fullScroll.setNestedScrollingEnabled(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mFragmentHomeBinding.fullScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {


                    /*//int hh = v.getMeasuredHeight();
                    //  int ff = mFragmentHomeBinding.recyclerviewOrders.getChildAt(0).getMeasuredHeight();
                    if (scrollY == (v.getMeasuredHeight() - 150)) {
                        //   Log.i(TAG, "BOTTOM SCROLL");
                        //   Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    }
                    if (scrollY > 2000) {
                        //   Log.i(TAG, "BOTTOM SCROLL");
                        //   Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                        mHomeTabViewModel.backToTop.set(true);

                    }else {
                        mHomeTabViewModel.backToTop.set(false);
                    }*/


                    if ((scrollY >= (mFragmentHomeBinding.fullScroll.getChildAt(mFragmentHomeBinding.fullScroll.getChildCount() - 1).getMeasuredHeight() - mFragmentHomeBinding.fullScroll.getMeasuredHeight())) &&
                            scrollY > oldScrollY) {


                        //   if (mHomeTabViewModel.pageid.get() + 1 > mHomeTabViewModel.pageCount) {

                        //  } else {

                        if (!mHomeTabViewModel.paginationLoading.get()) {
                            mHomeTabViewModel.paginationLoading.set(true);
                            //   Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                            if (mHomeTabViewModel.getDataManager().isFilterApplied()) {
                                mHomeTabViewModel.fetchKitchenFilter();
                            } else {
                                mHomeTabViewModel.fetchKitchen();
                            }
                        }


                        //  }


                    }


                }
            });
        }


        LinearLayoutManager mLayoutManagerTitle
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mLayoutManagerTitle.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentHomeBinding.recyclerViewRegionTitle.setLayoutManager(mLayoutManagerTitle);
        mFragmentHomeBinding.recyclerViewRegionTitle.setAdapter(regionsCardTitleAdapter);


        SnapHelper snapHelper = new PagerSnapHelper();////for single slider in recycler while swiping
        snapHelper.attachToRecyclerView(mFragmentHomeBinding.recyclerViewRegionTitle);

        LinearLayoutManager mLayoutManager3
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        mLayoutManager3.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentHomeBinding.recyclerViewStory.setLayoutManager(mLayoutManager3);
        mFragmentHomeBinding.recyclerViewStory.setAdapter(storiesCardAdapter);


        /*mFragmentHomeBinding.recyclerViewStory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager ll = (LinearLayoutManager) recyclerView.getLayoutManager();

                int firstVisiblePosition = ll.findFirstCompletelyVisibleItemPosition();

                try {

                    if (storiesFullResponse.getResult() != null)
                        mHomeTabViewModel.firstStory.set(String.valueOf(storiesFullResponse.getResult().get(firstVisiblePosition % storiesFullResponse.getResult().size()).getThumbTitle()));
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                } catch (Exception x) {
                    x.printStackTrace();
                }

            }
        });*/


        LinearLayoutManager collectionLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        collectionLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mFragmentHomeBinding.recyclerViewFilterCollection.setLayoutManager(collectionLayoutManager);
        mFragmentHomeBinding.recyclerViewFilterCollection.setAdapter(filterCollectionAdapter);


        mFragmentHomeBinding.recyclerViewRegionTitle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });


        mFragmentHomeBinding.recyclerViewRegion.setAdapter(regionListAdapter);
        mFragmentHomeBinding.recyclerViewRegion.setHasFixedSize(true);


        mStackLayoutManager = new StackLayoutManager();
        mStackLayoutManager.setItemOffset(70);

        mFragmentHomeBinding.recyclerViewRegion.setLayoutManager(mStackLayoutManager);


        mStackLayoutManager.setItemChangedListener(new StackLayoutManager.ItemChangedListener() {
            @Override
            public void onItemChanged(int position) {


                mFragmentHomeBinding.recyclerViewRegionTitle.smoothScrollToPosition(position);


                if (mHomeTabViewModel.regionResult.getResult() != null && mHomeTabViewModel.regionResult.getResult().size() > 0 && mHomeTabViewModel.regionResult.getResult().size() != position) {

                    mFragmentHomeBinding.rTitle.setVisibility(View.VISIBLE);
                    Animation aniFadeOut = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
                    mFragmentHomeBinding.rTitle.startAnimation(aniFadeOut);

                    aniFadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            mHomeTabViewModel.region1.set(mHomeTabViewModel.regionResult.getResult().get(position).getRegionname());
                            mHomeTabViewModel.slogan1.set(mHomeTabViewModel.regionResult.getResult().get(position).getTagline());

                            Animation aniFade = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
                            mFragmentHomeBinding.rTitle.startAnimation(aniFade);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                } else {

                    mFragmentHomeBinding.rTitle.setVisibility(View.INVISIBLE);
                }


            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();

        String ss = mHomeTabViewModel.getDataManager().getCurrentUserName();
        String name;
        /* name = "Hi! " + mHomeTabViewModel.getDataManager().getCurrentUserName()==null?"":mHomeTabViewModel.getDataManager().getCurrentUserName() + ",";*/
        if (ss == null) {
            name = "Hi! ";

        } else {
            name = "Hi! " + mHomeTabViewModel.getDataManager().getCurrentUserName() + ",";
        }


        String welcomeMessage = " pick \nyour home to eat!";

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Poppins-Medium.otf");
        Typeface font2 = Typeface.createFromAsset(getActivity().getAssets(), "Poppins-Regular.otf");
        SpannableStringBuilder SS = new SpannableStringBuilder(name + welcomeMessage);

        SS.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, name.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SS.setSpan(new CustomTypefaceSpan("", font), 0, name.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        SS.setSpan(new CustomTypefaceSpan("", font2), name.length() + 1, name.length() + welcomeMessage.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        mFragmentHomeBinding.welcomeText.setText(SS);


        mHomeTabViewModel.updateAddressTitle();


       /* if (mHomeTabViewModel.getDataManager().isFilterApplied()) {
            mHomeTabViewModel.fetchKitchenFilter();
        } else {0
            mHomeTabViewModel.fetchKitchen();
        }
*/
        mHomeTabViewModel.liveOrders();
        //     mHomeTabViewModel.storiesRefresh();
        regionCardClicked = false;

    }


    private void subscribeToLiveData() {
        mHomeTabViewModel.getregionItemsLiveData().observe(this,
                regionItemViewModel -> mHomeTabViewModel.addRegionItemsToList(regionItemViewModel));
       /* mHomeTabViewModel.getStoriesItemsImages().observe(this,
                regionItemViewModel -> mHomeTabViewModel.addStoriesImagesList(regionItemViewModel));*/
        mHomeTabViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mHomeTabViewModel.addKitchenItemsToList(kitchenItemViewModel));

        mHomeTabViewModel.getCollectionItemLiveData().observe(this,
                collectionItemViewModel -> mHomeTabViewModel.addCollectionItemsToList(collectionItemViewModel));

        mHomeTabViewModel.getCouponListItemsLiveData().observe(this,
                couponItemViewModel -> mHomeTabViewModel.addCouponItemsToList(couponItemViewModel));


    }


    @Override
    public void applyFilter() {
        mHomeTabViewModel.pageid.set(0);
        if (mHomeTabViewModel.getDataManager().isFilterApplied()) {
            mHomeTabViewModel.fetchKitchenFilter();
        } else {
            mHomeTabViewModel.fetchKitchen();
        }
    }

    @Override
    public void animateView(View view) {
    }

    @Override
    public void onItemClickData(Long kitchenId) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_KITCHEN);

        //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics
        new Analytics().selectKitchen(AppConstants.ANALYTICYS_HOME_KITCHEN, kitchenId);

        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mHomeTabViewModel.removeFavourite(favId);
    }

    @Override
    public void addFav(long id, String fav) {

        mHomeTabViewModel.addFavourite(id, fav);

    }

    @Override
    public void infinityStoryItemClick(List<KitchenResponse.Story> story, int position) {


        List<KitchenResponse.Result> result = new ArrayList<>();
        KitchenResponse.Result kl = new KitchenResponse.Result();
        kl.setStory(story);
        KitchenResponse kitchenResponse = new KitchenResponse();
        result.add(kl);
        kitchenResponse.setResult(result);

        mHomeTabViewModel.saveStory(kitchenResponse);

        if (story.get(position).getStories().size() > 0) {

            new Analytics().story(story.get(position).getStoryid(), story.get(position).getThumbTitle());

            new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_STORY);
            Intent intent = StoriesTabActivity.newIntent(getContext());
            intent.putExtra("position", position);
            intent.putExtra("fullStories", kitchenResponse);
            startActivity(intent);


        }
    }

    @Override
    public void regionCollectionItemClick(KitchenResponse.Region region) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_REGION_CARD);

        //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics

        Intent intent = RegionDetailsActivity.newIntent(getContext());
        intent.putExtra("image", region.getRegionDetailImage());
        intent.putExtra("id", region.getRegionid());
        intent.putExtra("tagline", region.getTagline());
        intent.putExtra("next_page", AppConstants.SCREEN_HOME);
        startActivity(intent);
    }

    @Override
    public void infinityCollectionDetailItemClick(KitchenResponse.CollectionDetail collection) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_KITCHEN);

        new Analytics().selectKitchen(AppConstants.ANALYTICYS_HOME_KITCHEN, collection.getMakeituserid());

        //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics


        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", collection.getMakeituserid());
        startActivity(intent);
    }

    private void initCountryText() {


        // setCountryText(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname(),true );
        //  setCountryTextSlogan(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname(),true );


        //  mFragmentHomeBinding.area1.setText(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname());
        // mFragmentHomeBinding.slogan1.setText(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname());
       /* if (mHomeTabViewModel.regionResult != null) {
            countryAnimDuration = 350;
            countryOffset1 = getResources().getDimensionPixelSize(R.dimen.left_offset);
            countryOffset2 = getResources().getDimensionPixelSize(R.dimen.card_width);
            // mFragmentHomeBinding.area1.setX(countryOffset1);
            // mFragmentHomeBinding.area2.setX(countryOffset2);
            mFragmentHomeBinding.area1.setText(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname());
            mFragmentHomeBinding.area2.setAlpha(0f);

            //   mFragmentHomeBinding.slogan1.setX(countryOffset1);
            //  mFragmentHomeBinding.slogan2.setX(countryOffset2);
            mFragmentHomeBinding.slogan1.setText(mHomeTabViewModel.regionResult.getResult().get(0).getTagline());
            mFragmentHomeBinding.slogan2.setAlpha(0f);
        }*/

    }

    /*private void setCountryText(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (mFragmentHomeBinding.area1.getAlpha() > mFragmentHomeBinding.area2.getAlpha()) {
            visibleText = mFragmentHomeBinding.area1;
            invisibleText = mFragmentHomeBinding.area2;
        } else {
            visibleText = mFragmentHomeBinding.area2;
            invisibleText = mFragmentHomeBinding.area1;
        }

        final int vOffset;
        if (left2right) {
            //  invisibleText.setX(0);
            vOffset = countryOffset2;
        } else {
            //  invisibleText.setX(countryOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);

        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 1f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", countryOffset1);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", vOffset);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(countryAnimDuration);
        animSet.start();
    }*/

  /*  private void setCountryTextSlogan(String text, boolean left2right) {
        final TextView invisibleText;
        final TextView visibleText;
        if (mFragmentHomeBinding.slogan1.getAlpha() > mFragmentHomeBinding.slogan2.getAlpha()) {
            visibleText = mFragmentHomeBinding.slogan1;
            invisibleText = mFragmentHomeBinding.slogan2;
        } else {
            visibleText = mFragmentHomeBinding.slogan2;
            invisibleText = mFragmentHomeBinding.slogan1;
        }

        final int vOffset;
        if (left2right) {
            //invisibleText.setX(0);
            vOffset = countryOffset2;
        } else {
            // invisibleText.setX(countryOffset2);
            vOffset = 0;
        }

        invisibleText.setText(text);
        final ObjectAnimator iAlpha = ObjectAnimator.ofFloat(invisibleText, "alpha", 0f);
        final ObjectAnimator vAlpha = ObjectAnimator.ofFloat(visibleText, "alpha", 0f);
        final ObjectAnimator iX = ObjectAnimator.ofFloat(invisibleText, "x", 0);
        final ObjectAnimator vX = ObjectAnimator.ofFloat(visibleText, "x", 0);

        final AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(iAlpha, vAlpha, iX, vX);
        animSet.setDuration(countryAnimDuration);
        animSet.start();
    }*/

    private void onActiveCardChange() {
        final int pos = cardSliderLayoutManager.getActiveCardPosition();
        if (pos == RecyclerView.NO_POSITION || pos == currentPosition) {
            return;
        }


        onActiveCardChange(pos);
    }

    private void onActiveCardChange(int pos) {

        final boolean left2right = pos < currentPosition;


        if (pos == mHomeTabViewModel.regionResult.getResult().size()) {
            mHomeTabViewModel.region1.set("");
            mHomeTabViewModel.slogan1.set("");
        } else {
            mHomeTabViewModel.region1.set(mHomeTabViewModel.regionResult.getResult().get(pos).getRegionname());
            mHomeTabViewModel.slogan1.set(mHomeTabViewModel.regionResult.getResult().get(pos).getTagline());
        }


        currentPosition = pos;
    }

    @Override
    public void onItemClickData(RegionsResponse.Result mRegionList, int position) {


        int activeCardPosition = mStackLayoutManager.getFirstVisibleItemPosition();


        if (activeCardPosition == RecyclerView.NO_POSITION) {
            return;
        }

        if (position == activeCardPosition) {
            if (!regionCardClicked) {
                regionCardClicked = true;

                //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics

                new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_REGION_CARD);
                Intent intent = RegionDetailsActivity.newIntent(getContext());
                intent.putExtra("image", mRegionList.getRegionDetailImage());
                intent.putExtra("id", mRegionList.getRegionid());
                intent.putExtra("tagline", mRegionList.getTagline());
                intent.putExtra("next_page", AppConstants.SCREEN_HOME);
                startActivity(intent);
                //   getActivity().overridePendingTransition(R.anim.rotate_out, R.anim.rotate_in);
            }

        } else if (position > activeCardPosition) {

        }

    }

    @Override
    public void showMore(Integer regionId) {

    }

    @Override
    public void viewMoreRegions() {


        int activeCardPosition = mStackLayoutManager.getFirstVisibleItemPosition();

        if (activeCardPosition == RecyclerView.NO_POSITION) {
            return;
        }

        if (mHomeTabViewModel.regionResult.getResult().size() == activeCardPosition) {

            //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics

            new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_REGION_EXPLORE_ALL);
            Intent intent = RegionListActivity.newIntent(getContext());
            startActivity(intent);

        } else if (mHomeTabViewModel.regionResult.getResult().size() > activeCardPosition) {
            mFragmentHomeBinding.recyclerViewRegion.smoothScrollToPosition(mHomeTabViewModel.regionResult.getResult().size());
            onActiveCardChange(mHomeTabViewModel.regionResult.getResult().size());
        }
    }

    @Override
    public void onItemClickData(StoriesResponse.Result result, int pos) {

        if (result.getStories().size() > 0) {

            new Analytics().story(result.getStoryid(), result.getThumbTitle());


            new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_STORY);

            //metricsAppOpens(AppConstants.SCREEN_STORIES);//////Metrics

            Intent intent = StoriesTabActivity.newIntent(getContext());
            intent.putExtra("position", pos);
            intent.putExtra("fullStories", storiesFullResponse);
            startActivity(intent);
        }

    }


    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_COLLECTION);

        //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics

        Intent intent = SearchDishActivity.newIntent(getContext());
        intent.putExtra("cid", collection.getCid());
        intent.putExtra("title", collection.getName());
        startActivity(intent);

    }

    @Override
    public void offersItemClick(KitchenResponse.Coupon offers) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_COUPON);
        //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics

        Intent intent = CouponListActivity.newIntent(getContext());
        intent.putExtra("clickable", true);
        startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.SELECT_ADDRESS_LIST_CODE) {
            startRegionLoader();
            startKitchenLoader();
            //  if (resultCode == RESULT_OK) {
            mHomeTabViewModel.updateAddressTitle();
            mHomeTabViewModel.fetchRepos(0);
            mHomeTabViewModel.fetchKitchen();
            //   }
        }
        if (requestCode == AppConstants.GPS_NORMAL_REQUEST) {
            if (resultCode == RESULT_OK) {
                new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_GPS_ON);
                getLocation();
            } else {

                showAddressAler();

                /*if (mHomeTabViewModel.isAddressAdded()) {
                    //setUp();
                    mHomeTabViewModel.loadAllApis();
                    mHomeTabViewModel.favIcon.set(true);

                } else {
                    ((MainActivity) getActivity()).startLocationTracking();
                }*/
            }
        }

    }


    public void startLoader() {


        if (!progressDialog.isShowing()) progressDialog.show();


       /* mFragmentHomeBinding.shimmer.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.shimmer.startShimmerAnimation();*/
    }

    public void stopLoader() {

        if (progressDialog.isShowing()) progressDialog.dismiss();

       /* mFragmentHomeBinding.shimmer.setVisibility(View.GONE);
        mFragmentHomeBinding.shimmer.stopShimmerAnimation();*/
    }


    public void startStoriesLoader() {
        mFragmentHomeBinding.storiesLoader.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.storiesLoader.startShimmerAnimation();
    }

    public void stopStorieLoader() {
        mFragmentHomeBinding.storiesLoader.setVisibility(View.GONE);
        mFragmentHomeBinding.storiesLoader.stopShimmerAnimation();
    }

    public void startRegionLoader() {

        mFragmentHomeBinding.regionLayout.setVisibility(View.GONE);

        mFragmentHomeBinding.regionLoader.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.regionLoader.startShimmerAnimation();
    }

    public void stopRegioneLoader() {
        mFragmentHomeBinding.regionLayout.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.regionLoader.setVisibility(View.GONE);
        mFragmentHomeBinding.regionLoader.stopShimmerAnimation();
    }

    public void startKitchenLoader() {
        mFragmentHomeBinding.kitchenLoader.setVisibility(View.VISIBLE);
        mFragmentHomeBinding.kitchenLoader.startShimmerAnimation();
    }

    public void stopKitchenLoader() {

        mFragmentHomeBinding.kitchenLoader.setVisibility(View.GONE);
        mFragmentHomeBinding.kitchenLoader.stopShimmerAnimation();
    }

    @Override
    public void filterCollectionItemClick(KitchenResponse.Collection collection) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_COLLECTION);
        //metricsAppOpens(AppConstants.SCREEN_HOME);//////Metrics

        Intent intent = SearchDishActivity.newIntent(getContext());
        intent.putExtra("cid", collection.getCid());
        intent.putExtra("title", collection.getName());
        startActivity(intent);
    }


    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(getContext(),
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
            TextView textView = snackbarView.findViewById(snackbarTextId);
            textView.setTextColor(getResources().getColor(R.color.white));
            snackbarView.setBackgroundColor((getResources().getColor(R.color.black)));

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


                new GpsUtils(getContext(), AppConstants.GPS_NORMAL_REQUEST).turnGPSOn(new GpsUtils.onGpsListener() {
                    @Override
                    public void gpsStatus(boolean isGPSEnable) {
                        if (isGPSEnable) {
                            getLocation();
                        }

                    }
                });

            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                /*  Log.i(TAG, "Permission granted.");*/
                showAddressAler();
                /*if (mHomeTabViewModel.isAddressAdded()) {
                    //setUp();
                    mHomeTabViewModel.loadAllApis();
                    mHomeTabViewModel.favIcon.set(true);

                } else {
                    ((MainActivity) getActivity()).startLocationTracking();
                }*/
            }
        }
    }

    BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (locationAlertShowing) {
                LocationManager locationManager = (LocationManager) getBaseActivity().getSystemService(LOCATION_SERVICE);
                boolean isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (isGPSEnabled) {
                    getLocation();
                    if (myToolTipView != null) {
                        myToolTipView.remove();
                        myToolTipView = null;
                        mHomeTabViewModel.getDataManager().appStartedAgain(false);
                    }
                    locationAlertShowing = false;
                    if (mWifiReceiver != null)
                        getContext().unregisterReceiver(mWifiReceiver);
                }
            }
        }
    };

    private void registerWifiReceiver() {
        /*IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);*/
        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        getContext().registerReceiver(mWifiReceiver, filter);

    }


}
