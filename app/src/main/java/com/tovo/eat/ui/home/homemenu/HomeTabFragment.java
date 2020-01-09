package com.tovo.eat.ui.home.homemenu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
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
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentHomeBinding;
import com.tovo.eat.ui.account.favorites.FavouritesActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.cart.coupon.CouponListActivity;
import com.tovo.eat.ui.filter.FilterFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.MainActivity;
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
import com.tovo.eat.utilities.EndlessRecyclerOnScrollListener;
import com.tovo.eat.utilities.GpsUtils;
import com.tovo.eat.utilities.SingleShotLocationProvider;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.card.CardSliderLayoutManager;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.scroll.InfiniteScrollListener;
import com.tovo.eat.utilities.scroll.PaginationListener;
import com.tovo.eat.utilities.stack.StackLayoutManager;

import org.checkerframework.checker.units.qual.K;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.tovo.eat.utilities.scroll.PaginationListener.PAGE_START;

public class HomeTabFragment extends BaseFragment<FragmentHomeBinding, HomeTabViewModel> implements HomeTabNavigator,
        StartFilter, KitchenAdapter.LiveProductsAdapterListener, RegionsCardAdapter.LiveProductsAdapterListener, StoriesCardAdapter.StoriesAdapterListener, FilterCollectionAdapter.FilterCollectionAdapterListener {


    public static final String TAG = HomeTabFragment.class.getSimpleName();
    private static final int MAX_ITEMS_PER_REQUEST =6;
    private static final int NUMBER_OF_ITEMS = 100;
    private static final int SIMULATED_LOADING_TIME_IN_MS = 1500;
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
    private FragmentHomeBinding mFragmentHomeBinding;
    private int currentPosition;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;

    public static HomeTabFragment newInstance() {
        Bundle args = new Bundle();
        HomeTabFragment fragment = new HomeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }


    /*@Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Activity a;

        if (context instanceof Activity){
            a=(Activity) context;

            try {
                filterListener = (FilterListener ) a;
            } catch (ClassCastException e) {
                throw new ClassCastException(a.toString()
                        + " must implement MyInterface ");
            }

        }

    }*/

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
        ((MainActivity) getActivity()).selectHomeAddress();
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
        setUp();
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


        mFragmentHomeBinding.fullScroll.smoothScrollTo(0, 0);
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


        startStoriesLoader();
        startRegionLoader();
        startKitchenLoader();
        regionsResponse = new RegionsResponse();

        if (mHomeTabViewModel.isAddressAdded()) {

            if (mHomeTabViewModel.getDataManager().getAddressId() != 0L) {

                if (mHomeTabViewModel.getDataManager().getAppStartedAgain()) {

                    // startLocationTrackingForAddress();

                    showAddressAler();

                }
            }

            setUp();
            mHomeTabViewModel.loadAllApis();
            mHomeTabViewModel.favIcon.set(true);

        } else {
            ((MainActivity) getActivity()).startLocationTracking();
        }


        mFragmentHomeBinding.fullScroll.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                closeAddressAlert();

            }
        });




    }


    public void startLocationTrackingForAddress() {


        new GpsUtils(getBaseActivity()).turnGPSOn(new GpsUtils.onGpsListener() {
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


    public void getLocation() {


        SingleShotLocationProvider.requestSingleUpdate(getActivity(),
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        if (location != null)

                            if (distance(location.latitude, location.longitude, Double.parseDouble(mHomeTabViewModel.getDataManager().getCurrentLat()), Double.parseDouble(mHomeTabViewModel.getDataManager().getCurrentLng()), "K") > 1) {


                                showAddressAler();


                            }


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



        /*mFragmentHomeBinding.recyclerviewOrders.addOnScrollListener(new com.tovo.eat.utilities.scroll.EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {

                if ( !mHomeTabViewModel.paginationLoading.get()){
                    mHomeTabViewModel.paginationLoading.set(true);
                    Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    if (mHomeTabViewModel.getDataManager().isFilterApplied()) {
                        mHomeTabViewModel.fetchKitchenFilter();
                    } else {
                        mHomeTabViewModel.fetchKitchen();
                    }
                }

            }
        });*/
     //   createInfiniteScrollListener();


       /* mFragmentHomeBinding.recyclerviewOrders.addOnScrollListener(new PaginationListener(mLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if ( !mHomeTabViewModel.paginationLoading.get()){
                    mHomeTabViewModel.paginationLoading.set(true);
                    Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                    if (mHomeTabViewModel.getDataManager().isFilterApplied()) {
                        mHomeTabViewModel.fetchKitchenFilter();
                    } else {
                        mHomeTabViewModel.fetchKitchen();
                    }
                }
            }

            @Override
            public boolean isLastPage() {
                return false;
            }

            @Override
            public boolean isLoading() {
                return false;
            }

        });*/



        /*mFragmentHomeBinding.recyclerviewOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
              int  totalItemCount = 100;

               int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (!mHomeTabViewModel.paginationLoading.get() && totalItemCount!=0&& totalItemCount <= (lastVisibleItem + 6)) {

                    if ( !mHomeTabViewModel.paginationLoading.get()){
                        mHomeTabViewModel.paginationLoading.set(true);
                        Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                        if (mHomeTabViewModel.getDataManager().isFilterApplied()) {
                            mHomeTabViewModel.fetchKitchenFilter();
                        } else {
                            mHomeTabViewModel.fetchKitchen();
                        }
                    }
                }
            }
        });*/


       /* mFragmentHomeBinding.recyclerviewOrders.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if ( !mHomeTabViewModel.paginationLoading.get()) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mHomeTabViewModel.kitchenItemViewModels.size() - 1) {

                        if ( !mHomeTabViewModel.paginationLoading.get()){
                            mHomeTabViewModel.paginationLoading.set(true);
                               Toast.makeText(getContext(), "Loading...", Toast.LENGTH_SHORT).show();
                            if (mHomeTabViewModel.getDataManager().isFilterApplied()) {
                                mHomeTabViewModel.fetchKitchenFilter();
                            } else {
                                mHomeTabViewModel.fetchKitchen();
                            }
                        }

                    }
                }
            }
        });*/

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

                           if ( !mHomeTabViewModel.paginationLoading.get()){
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

    private void doApiCall() {

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    itemCount++;
                }

                // check weather is last page or not
                if (currentPage < totalPage) {

                } else {
                    isLastPage = true;
                }
                isLoading = false;

                mHomeTabViewModel.kitchenListLoading.set(false);

            }
        }, 1500);
    }


    @NonNull
    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(MAX_ITEMS_PER_REQUEST, mLayoutManager) {
            @Override
            public void onScrolledToEnd(final int firstVisibleItemPosition) {
                Toast.makeText(getContext(), "loading...", Toast.LENGTH_SHORT).show();

                //    mHomeTabViewModel.fetchKitchen();


               /* int start = ++page * MAX_ITEMS_PER_REQUEST;
                final boolean allItemsLoaded = start >= mHomeTabViewModel.kitchenItemViewModels.size();
                if (allItemsLoaded) {
                    Toast.makeText(getContext(), "loading...", Toast.LENGTH_SHORT).show();
                } else {
                    int end = start + MAX_ITEMS_PER_REQUEST;
                    mHomeTabViewModel.fetchKitchen();
                }*/
            }


        /*return new InfiniteScrollListener(20   , mLayoutManager) {
            @Override public void onScrolledToEnd(final int firstVisibleItemPosition) {
                if (!mHomeTabViewModel.kitchenListLoading.get()) {
                    mHomeTabViewModel.kitchenListLoading.set(true);
                    mHomeTabViewModel.fetchKitchen();
                }


                *//*int start = ++page * MAX_ITEMS_PER_REQUEST;
                final boolean allItemsLoaded = start >= items.size();
                if (allItemsLoaded) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    int end = start + MAX_ITEMS_PER_REQUEST;
                    final List<String> itemsLocal = getItemsToBeLoaded(start, end);
                    refreshView(recyclerView, new MyAdapter(itemsLocal), firstVisibleItemPosition);
                }*//*
            }*/
        };
    }

    @Override
    public void onResume() {
        super.onResume();

        String ss=mHomeTabViewModel.getDataManager().getCurrentUserName();
        String name;
       /* name = "Hi! " + mHomeTabViewModel.getDataManager().getCurrentUserName()==null?"":mHomeTabViewModel.getDataManager().getCurrentUserName() + ",";*/
        if (ss==null){
             name = "Hi! ";

        }else {
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


        List<KitchenResponse.Result> result=new ArrayList<>();
        KitchenResponse.Result kl=new KitchenResponse.Result();
        kl.setStory(story);
        KitchenResponse kitchenResponse=new KitchenResponse();
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
        Intent intent = RegionDetailsActivity.newIntent(getContext());
        intent.putExtra("image", region.getRegionDetailImage());
        intent.putExtra("id", region.getRegionid());
        intent.putExtra("tagline", region.getTagline());
        startActivity(intent);
    }

    @Override
    public void infinityCollectionDetailItemClick(KitchenResponse.CollectionDetail collection) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_KITCHEN);

        new Analytics().selectKitchen(AppConstants.ANALYTICYS_HOME_KITCHEN, collection.getMakeituserid());

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

                new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_REGION_CARD);
                Intent intent = RegionDetailsActivity.newIntent(getContext());
                intent.putExtra("image", mRegionList.getRegionDetailImage());
                intent.putExtra("id", mRegionList.getRegionid());
                intent.putExtra("tagline", mRegionList.getTagline());
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
            Intent intent = StoriesTabActivity.newIntent(getContext());
            intent.putExtra("position", pos);
            intent.putExtra("fullStories", storiesFullResponse);
            startActivity(intent);
        }

    }


    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_COLLECTION);


        Intent intent = SearchDishActivity.newIntent(getContext());
        intent.putExtra("cid", collection.getCid());
        intent.putExtra("title", collection.getName());
        startActivity(intent);

    }

    @Override
    public void offersItemClick(KitchenResponse.Coupon offers) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_COUPON);
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

        } else if (requestCode == AppConstants.GPS_REQUEST) {

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
        Intent intent = SearchDishActivity.newIntent(getContext());
        intent.putExtra("cid", collection.getCid());
        intent.putExtra("title", collection.getName() );
        startActivity(intent);
    }
}
