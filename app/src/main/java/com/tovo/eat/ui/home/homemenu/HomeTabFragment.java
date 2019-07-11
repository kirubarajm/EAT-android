package com.tovo.eat.ui.home.homemenu;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.rahuljanagouda.statusstories.StatusStoriesActivity;
import com.tovo.eat.BR;
import com.tovo.eat.BuildConfig;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentHomeBinding;
import com.tovo.eat.ui.account.favorites.FavouritesActivity;
import com.tovo.eat.ui.account.favorites.tab.FavoritesTabActivity;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.filter.FilterFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.story.StoriesActivity;
import com.tovo.eat.ui.home.homemenu.story.StoriesCardAdapter;
import com.tovo.eat.ui.home.homemenu.story.StoriesResponse;
import com.tovo.eat.ui.home.region.RegionsResponse;
import com.tovo.eat.ui.home.region.list.RegionDetailsActivity;
import com.tovo.eat.ui.home.region.viewmore.RegionListActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.card.CardSliderLayoutManager;
import com.tovo.eat.utilities.card.CardSnapHelper;
import com.tovo.eat.utilities.card.DecodeBitmapTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HomeTabFragment extends BaseFragment<FragmentHomeBinding, HomeTabViewModel> implements HomeTabNavigator, LocationListener,
        StartFilter, KitchenAdapter.LiveProductsAdapterListener, RegionsCardAdapter.LiveProductsAdapterListener,StoriesCardAdapter.StoriesAdapterListener {


    public static final String TAG = HomeTabFragment.class.getSimpleName();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 1001;
    @Inject
    HomeTabViewModel mHomeTabViewModel;
    @Inject
    KitchenAdapter adapter;
    @Inject
    RegionsCardAdapter regionListAdapter;
    @Inject
    StoriesCardAdapter storiesCardAdapter;
    DecodeBitmapTask decodeBitmapTask;
    RegionsResponse regionsResponse;
    CardSliderLayoutManager cardSliderLayoutManager;
    private FragmentHomeBinding mFragmentHomeBinding;
    private ImageSwitcher mapSwitcher;
    private TextSwitcher temperatureSwitcher;
    private TextSwitcher placeSwitcher;
    private TextSwitcher clockSwitcher;
    private TextSwitcher descriptionsSwitcher;
    private View greenDot;
    private int countryOffset1;
    private int countryOffset2;
    private long countryAnimDuration;
    private int currentPosition;
    private DecodeBitmapTask decodeMapBitmapTask;
    private DecodeBitmapTask.Listener mapLoadListener;


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
        FavoritesActivity fragment = new FavoritesActivity();
        transaction.replace(R.id.content_main, fragment);
        transaction.commitNow();*/
        Intent intent = FavouritesActivity.newIntent(getContext());
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
    public void storiesLoaded(RegionsResponse regionResponse) {
        this.regionsResponse = regionResponse;
        initCountryText();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeTabViewModel.setNavigator(this);
        mHomeTabViewModel.updateAddressTitle();
        adapter.setListener(this);
        regionListAdapter.setListener(this);
        storiesCardAdapter.setListener(this);
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

        if (mHomeTabViewModel.isAddressAdded()) {
            setUp();
            mFragmentHomeBinding.shimmerViewContainer.setVisibility(View.GONE);
            mFragmentHomeBinding.shimmerViewContainer.stopShimmerAnimation();
            mHomeTabViewModel.favIcon.set(true);

        } else {
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


        mFragmentHomeBinding.recyclerViewStory.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                LinearLayoutManager ll = (LinearLayoutManager) recyclerView.getLayoutManager();

                int firstVisiblePosition = ll.findFirstCompletelyVisibleItemPosition();

                Toast.makeText(getContext(), "Position : " + firstVisiblePosition, Toast.LENGTH_SHORT).show();
                try {

                    if (regionsResponse.getResult() != null)
                        mHomeTabViewModel.firstRegion.set(regionsResponse.getResult().get(firstVisiblePosition).getRegionname());
                } catch (NullPointerException ne) {
                    ne.printStackTrace();
                } catch (Exception x) {
                    x.printStackTrace();
                }

            }
        });


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
                    onActiveCardChange();
                } else {

                    cardSliderLayoutManager = (CardSliderLayoutManager) recyclerView.getLayoutManager();

                    int firstVisiblePosition = cardSliderLayoutManager.getActiveCardPosition();


                    //   Toast.makeText(getContext(), "Position : "+ firstVisiblePosition, Toast.LENGTH_SHORT).show();
                }
            }
        });

        cardSliderLayoutManager = (CardSliderLayoutManager) mFragmentHomeBinding.recyclerViewRegion.getLayoutManager();


        new CardSnapHelper().attachToRecyclerView(mFragmentHomeBinding.recyclerViewRegion);
        
        
        
        
        
        
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
        mHomeTabViewModel.getStoriesItemsImages().observe(this,
                regionItemViewModel -> mHomeTabViewModel.addStoriesImagesList(regionItemViewModel));
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

        mHomeTabViewModel. fetchKitchen();


    }

    @Override
    public void animateView(View view) {
        Animation shake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        view.startAnimation(shake);
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

    private void initCountryText() {


        // setCountryText(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname(),true );
        //  setCountryTextSlogan(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname(),true );


        //  mFragmentHomeBinding.area1.setText(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname());
        // mFragmentHomeBinding.slogan1.setText(mHomeTabViewModel.regionResult.getResult().get(0).getRegionname());


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


    }

    private void setCountryText(String text, boolean left2right) {
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
    }

    private void setCountryTextSlogan(String text, boolean left2right) {
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
    }

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
            mFragmentHomeBinding.area1.setText("");
            mFragmentHomeBinding.slogan1.setText("");
        } else {
            mFragmentHomeBinding.area1.setText(mHomeTabViewModel.regionResult.getResult().get(pos).getRegionname());
            mFragmentHomeBinding.slogan1.setText(mHomeTabViewModel.regionResult.getResult().get(pos).getTagline());
        }

        //   setCountryText(mHomeTabViewModel.regionResult.getResult().get(pos).getRegionname(), left2right);
        //  setCountryTextSlogan(mHomeTabViewModel.regionResult.getResult().get(pos).getRegionname(), left2right);

        currentPosition = pos;
    }

    @Override
    public void onItemClickData( RegionsResponse.Result mRegionList, int position) {


        final CardSliderLayoutManager lm = (CardSliderLayoutManager) mFragmentHomeBinding.recyclerViewRegion.getLayoutManager();

        if (lm.isSmoothScrolling()) {
            return;
        }

        final int activeCardPosition = lm.getActiveCardPosition();
        if (activeCardPosition == RecyclerView.NO_POSITION) {
            return;
        }

        if (position == activeCardPosition) {

            Intent intent = RegionDetailsActivity.newIntent(getContext());
            intent.putExtra("image", mRegionList.getRegionDetailImage());
            intent.putExtra("id", mRegionList.getRegionid());
            intent.putExtra("tagline", mRegionList.getTagline());
            startActivity(intent);


        } else if (position > activeCardPosition) {
            mFragmentHomeBinding.recyclerViewRegion.smoothScrollToPosition(position);
            onActiveCardChange();
        }


    }

    @Override
    public void showMore(Integer regionId) {

    }

    @Override
    public void viewMoreRegions() {


        final CardSliderLayoutManager lm = (CardSliderLayoutManager) mFragmentHomeBinding.recyclerViewRegion.getLayoutManager();

        if (lm.isSmoothScrolling()) {
            return;
        }

        final int activeCardPosition = lm.getActiveCardPosition();
        if (activeCardPosition == RecyclerView.NO_POSITION) {
            return;
        }

        if (mHomeTabViewModel.regionResult.getResult().size() == activeCardPosition) {


            Intent intent= RegionListActivity.newIntent(getContext());
         startActivity(intent);




        } else if (mHomeTabViewModel.regionResult.getResult().size() > activeCardPosition) {
            mFragmentHomeBinding.recyclerViewRegion.smoothScrollToPosition(mHomeTabViewModel.regionResult.getResult().size());
              onActiveCardChange(mHomeTabViewModel.regionResult.getResult().size());


        }


    }

    @Override
    public void onItemClickData(StoriesResponse.Result result) {
        Intent intent = StatusStoriesActivity.newIntent(getContext());
        intent.putExtra("stories", (Serializable) result.getStories());
        startActivity(intent);
    }

    private class OnCardClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final CardSliderLayoutManager lm = (CardSliderLayoutManager) mFragmentHomeBinding.recyclerViewRegion.getLayoutManager();

            if (lm.isSmoothScrolling()) {
                return;
            }

            final int activeCardPosition = lm.getActiveCardPosition();
            if (activeCardPosition == RecyclerView.NO_POSITION) {
                return;
            }

            final int clickedPosition = mFragmentHomeBinding.recyclerViewRegion.getChildAdapterPosition(view);
            if (clickedPosition == activeCardPosition) {

            } else if (clickedPosition > activeCardPosition) {
                mFragmentHomeBinding.recyclerViewRegion.smoothScrollToPosition(clickedPosition);
                onActiveCardChange(clickedPosition);
            }
        }
    }
}
