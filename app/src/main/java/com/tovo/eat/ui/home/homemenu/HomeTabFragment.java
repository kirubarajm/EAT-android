package com.tovo.eat.ui.home.homemenu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentHomeBinding;
import com.tovo.eat.ui.account.favorites.FavouritesActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.cart.coupon.CouponListActivity;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.filter.FilterFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.MainActivity;
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
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.card.CardSliderLayoutManager;
import com.tovo.eat.utilities.stack.StackLayoutManager;

import javax.inject.Inject;

import static android.app.Activity.RESULT_OK;

public class HomeTabFragment extends BaseFragment<FragmentHomeBinding, HomeTabViewModel> implements HomeTabNavigator,
        StartFilter, KitchenAdapter.LiveProductsAdapterListener, RegionsCardAdapter.LiveProductsAdapterListener, StoriesCardAdapter.StoriesAdapterListener {


    public static final String TAG = HomeTabFragment.class.getSimpleName();

    @Inject
    HomeTabViewModel mHomeTabViewModel;
    @Inject
    KitchenAdapter adapter;
    @Inject
    RegionsCardAdapter regionListAdapter;
    @Inject
    RegionsCardTitleAdapter regionsCardTitleAdapter;
    @Inject
    StoriesCardAdapter storiesCardAdapter;

    RegionsResponse regionsResponse;
    CardSliderLayoutManager cardSliderLayoutManager;
    StoriesResponse storiesFullResponse;
    StackLayoutManager mStackLayoutManager;
    boolean regionCardClicked = false;
    ProgressDialog progressDialog;
    Analytics analytics;
    String pageName = AppConstants.SCREEN_HOME;
    private FragmentHomeBinding mFragmentHomeBinding;
    private int currentPosition;

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
        ((MainActivity) getActivity()).selectHomeAddress();
    }
    @Override
    public void filter() {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_FILTER);
        FilterFragment bottomSheetFragment = new FilterFragment();
        bottomSheetFragment.show(getBaseActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
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
    }

    @Override
    public void getFullStories(StoriesResponse storiesResponse) {
        this.storiesFullResponse = storiesResponse;
        stopStorieLoader();
    }

    @Override
    public void trackLiveOrder(Integer orderId) {

        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_ORDER_TRACK);
        Intent intent = OrderTrackingActivity.newIntent(getContext());
        startActivity(intent);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeTabViewModel.setNavigator(this);
        mHomeTabViewModel.updateAddressTitle();
        adapter.setListener(this);
        regionListAdapter.setListener(this);
        storiesCardAdapter.setListener(this);
        subscribeToLiveData();


        analytics = new Analytics(getActivity(), pageName);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

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
            setUp();
            mHomeTabViewModel.loadAllApis();
            mHomeTabViewModel.favIcon.set(true);

        } else {
            ((MainActivity) getActivity()).startLocationTracking();
        }


    }


    private void setUp() {

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentHomeBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentHomeBinding.recyclerviewOrders.setAdapter(adapter);

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

        mFragmentHomeBinding.recyclerViewStory.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });


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
        if (mHomeTabViewModel.getDataManager().getFilterSort() != null) {

            mHomeTabViewModel.fetchKitchenFilter();
        } else {
            mHomeTabViewModel.fetchKitchen();
        }
        mHomeTabViewModel.liveOrders();
        mHomeTabViewModel.storiesRefresh();
        regionCardClicked = false;


    }


    private void subscribeToLiveData() {
        mHomeTabViewModel.getregionItemsLiveData().observe(this,
                regionItemViewModel -> mHomeTabViewModel.addRegionItemsToList(regionItemViewModel));
        mHomeTabViewModel.getStoriesItemsImages().observe(this,
                regionItemViewModel -> mHomeTabViewModel.addStoriesImagesList(regionItemViewModel));
        mHomeTabViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mHomeTabViewModel.addKitchenItemsToList(kitchenItemViewModel));

        mHomeTabViewModel.getCollectionItemLiveData().observe(this,
                collectionItemViewModel -> mHomeTabViewModel.addCollectionItemsToList(collectionItemViewModel));

        mHomeTabViewModel.getCouponListItemsLiveData().observe(this,
                couponItemViewModel -> mHomeTabViewModel.addCouponItemsToList(couponItemViewModel));


    }


    @Override
    public void applyFilter() {

    }

    @Override
    public void animateView(View view) {
    }

    @Override
    public void onItemClickData(Integer kitchenId) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_KITCHEN);

        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mHomeTabViewModel.removeFavourite(favId);
    }

    @Override
    public void addFav(Integer id, String fav) {

        mHomeTabViewModel.addFavourite(id, fav);

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
        intent.putExtra("title", collection.getHeading() + " " + collection.getSubheading());
        startActivity(intent);

    }

    @Override
    public void offersItemClick(CouponListResponse.Result offers) {
        new Analytics().sendClickData(AppConstants.SCREEN_HOME, AppConstants.CLICK_COUPON);
        Intent intent = CouponListActivity.newIntent(getContext());
        intent.putExtra("clickable", true);
        startActivity(intent);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.SELECT_ADDRESS_LIST_CODE) {

            if (resultCode == RESULT_OK) {
                mHomeTabViewModel.updateAddressTitle();
                mHomeTabViewModel.fetchRepos(0);
                mHomeTabViewModel.fetchKitchen();
            }

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

}
