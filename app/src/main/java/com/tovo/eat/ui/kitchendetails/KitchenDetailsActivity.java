package com.tovo.eat.ui.kitchendetails;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityKitchenDetailsBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.ui.kitchendetails.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.kitchendetails.dialog.DialogChangeKitchen;
import com.tovo.eat.ui.kitchendetails.viewimage.ViewImageActivity;
import com.tovo.eat.utilities.MvvmApp;
import com.tovo.eat.utilities.fonts.poppins.ButtonTextView;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.tovo.eat.utilities.swipe.ItemTouchHelperExtension;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class KitchenDetailsActivity extends BaseActivity<ActivityKitchenDetailsBinding, KitchenDetailsViewModel> implements KitchenDetailsNavigator,
        FavoriteAdapter.LiveProductsAdapterListener, AddKitchenDishListener, HasSupportFragmentInjector,
        MenuKitchenInfoCommonImageAdapter.MenuProductsAdapterListener, SpecialitiesAdapter.LiveProductsAdapterListener, FoodBadgeAdapter.LiveProductsAdapterListener,
        TodaysMenuAdapter.LiveProductsAdapterListener {

    public ItemTouchHelperExtension mItemTouchHelper;
    public ItemTouchHelperExtension.Callback mCallback;
    @Inject
    KitchenDetailsViewModel mKitchenDetailsViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    FavoriteAdapter mFavTodaysMenuAdapter;
    @Inject
    TodaysMenuAdapter mTodaysMenuAdapter;
    @Inject
    MenuKitchenInfoCommonImageAdapter kitchenCommonAdapter;
    @Inject
    FoodBadgeAdapter foodBadgesImageAdapter;
    @Inject
    SpecialitiesAdapter specialitiesAdapter;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    ActivityKitchenDetailsBinding mFragmentDishBinding;
    Integer kitchenID;
    int firstVisiblePosition;
    int totalCount;
    TextView rowTextView;
    TextView[] myTextViews;
    private float collapsedScale;
    private float expandedScale;
    private TextView[] dots;
    private int[] layouts;

    public static Intent newIntent(Context context) {
       /* Intent intent = new Intent(context, CartActivity.class);
        return intent;*/
        return new Intent(context, KitchenDetailsActivity.class);
    }

    private static void scalePhotoImage(ImageView photoView, float scale) {

        Drawable photo = photoView.getDrawable();
        int bitmapWidth = 0;
        int bitmapHeight = 0;


        try {
            bitmapWidth = photo.getIntrinsicWidth();
            bitmapHeight = photo.getIntrinsicHeight();
        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }

        float offsetX = (photoView.getWidth() - bitmapWidth) / 2F;
        float offsetY = (photoView.getHeight() - bitmapHeight) / 2F;

        float centerX = photoView.getWidth() / 2F;
        float centerY = photoView.getHeight() / 2F;

        Matrix imageMatrix = new Matrix();
        imageMatrix.setScale(scale, scale, centerX, centerY);
        imageMatrix.preTranslate(offsetX, offsetY);

        photoView.setImageMatrix(imageMatrix);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* KitchenDishSubBinding mBinding = DataBindingUtil
                .setContentView(this, R.layout.kitchen_dish_sub);*/

        mKitchenDetailsViewModel.setNavigator(this);
        mFavTodaysMenuAdapter.setListener(this);
        mTodaysMenuAdapter.setListener(this);
        foodBadgesImageAdapter.setListener(this);
        specialitiesAdapter.setListener(this);
        kitchenCommonAdapter.setListener(this);
        mFragmentDishBinding = getViewDataBinding();
/*
        setSupportActionBar(mFragmentDishBinding.toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

       /* Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        *//*backArrow.setColorFilter(getResources().getColor(R.color.md_grey_900), PorterDuff.Mode.SRC_ATOP);*//*
        getSupportActionBar().setHomeAsUpIndicator(backArrow);*/

        mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
        mFragmentDishBinding.toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        //   mFragmentDishBinding.kitchenSlider.init(new PicassoImageLoadingService(this));


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            kitchenID = intent.getExtras().getInt("kitchenId");
            mKitchenDetailsViewModel.fetchRepos(kitchenID);

            if (intent.getExtras().getInt("type")==2){
                mKitchenDetailsViewModel.info();
            }


        }

        setTitle(mKitchenDetailsViewModel.kitchenName.get());


        //  setTitle("Kitchen");



       /* mFragmentDishBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0 || verticalOffset <= mFragmentDishBinding.toolbar.getHeight()){
                    Toast.makeText(FavouritesActivity.this, "collapsed", Toast.LENGTH_SHORT).show();


                }else if(!mFragmentDishBinding.toolbar.getTitle().equals(null)){
                    Toast.makeText(FavouritesActivity.this, "un-collapsed", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

/*
        mFragmentDishBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            //private State state;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                int maxScroll = appBarLayout.getTotalScrollRange();
                int bitmapWidth = 0;
                int bitmapHeight=0 ;

                float scrollPercent = (float) Math.abs(verticalOffset) / (float) maxScroll;

                if (collapsedScale == 0) {

                    Drawable photo = mFragmentDishBinding.photoView.getDrawable();
                    try {
                         bitmapWidth = photo.getIntrinsicWidth();
                         bitmapHeight = photo.getIntrinsicHeight();
                    } catch (NullPointerException ne) {
                        ne.printStackTrace();
                    }

                    collapsedScale = (float) mFragmentDishBinding.photoView.getWidth() / (float) bitmapWidth;
                    expandedScale = (float) mFragmentDishBinding.photoView.getHeight() / (float) bitmapHeight;

                    scalePhotoImage(mFragmentDishBinding.photoView, expandedScale);

                } else {

                    scalePhotoImage(mFragmentDishBinding.photoView, collapsedScale + (expandedScale - collapsedScale) * (1f - scrollPercent));
                }











*/
/*
                if (verticalOffset == 0) {


                    //   Toast.makeText(FavouritesActivity.this, "Expanded", Toast.LENGTH_SHORT).show();

                    //   setTitle(" ");
                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));

                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                    // Toast.makeText(FavouritesActivity.this, "collapsed", Toast.LENGTH_SHORT).show();
                    // setTitle("Kitchen");
                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
                    //  mFragmentDishBinding.toolbar.setVisibility(View.GONE);
                    // mFragmentDishBinding.image.setVisibility(View.GONE);

                } else {
                    //   Toast.makeText(FavouritesActivity.this, "d", Toast.LENGTH_SHORT).show();
                    //   setTitle(" ");
                    //  mFragmentDishBinding.toolbar.setVisibility(View.VISIBLE);
                    // mFragmentDishBinding.image.setVisibility(View.VISIBLE);

                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
                }*//*

            }
        });
*/








       /* mCallback = new ItemTouchHelperCallback();
        mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.attachToRecyclerView( mFragmentDishBinding.recyclerviewOrders);
        mFavTodaysMenuAdapter.setItemTouchHelperExtension(mItemTouchHelper);*/

/*

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getDragDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return super.getDragDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                // when user swipe thr recyclerview item to right remove item from avorite list
                if (direction == ItemTouchHelper.RIGHT) {
                  //  favAdapter.addToFav(viewHolder.getAdapterPosition(), false);



                }
                // when user swipe thr recyclerview item to left remove item from avorite list
                else if (direction == ItemTouchHelper.LEFT) {
                    */
        /*favAdapter.addToFav(viewHolder.getAdapterPosition(), true);*//*


                }




            }
        }).attachToRecyclerView( mFragmentDishBinding.recyclerviewOrders);
*/

        // mKitchenDetailsViewModel.fetchRepos();

       /* mFragmentDishBinding.recyclerviewOrders.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            }
        });*/

       /* mFragmentDishBinding.recyclerviewOrders.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {




                return false;
            }
        });
*/

      /*  mFragmentDishBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mKitchenDetailsViewModel.fetchRepos();
            }
        });*/

        /*AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                   // showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                   // hideOption(R.id.action_info);
                }
            }
        });*/

/*

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {




            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(   mFragmentDishBinding.recyclerviewOrders);

        mFragmentDishBinding.recyclerviewOrders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
*/
        subscribeToLiveDataKitchenImages();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerFav.setLayoutManager(new LinearLayoutManager(this));
        mFragmentDishBinding.recyclerFav.setAdapter(mFavTodaysMenuAdapter);


      //  mFragmentDishBinding.recyclerFav.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerTodaysMenu.setLayoutManager(new LinearLayoutManager(this));
        mFragmentDishBinding.recyclerTodaysMenu.setAdapter(mTodaysMenuAdapter);
      //  mFragmentDishBinding.recyclerTodaysMenu.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mFragmentDishBinding.recyclerSpecialities.setLayoutManager(gridLayoutManager);
        mFragmentDishBinding.recyclerSpecialities.setAdapter(specialitiesAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFragmentDishBinding.recyclerKitchenCommonSlider.setLayoutManager(linearLayoutManager);
        mFragmentDishBinding.recyclerKitchenCommonSlider.setAdapter(kitchenCommonAdapter);
        SnapHelper snapHelper = new PagerSnapHelper();////for single slider in recycler while swiping
        snapHelper.attachToRecyclerView(mFragmentDishBinding.recyclerKitchenCommonSlider);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 3);
        mFragmentDishBinding.recyclerviewFoodBadges.setLayoutManager(gridLayoutManager2);
        mFragmentDishBinding.recyclerviewFoodBadges.setAdapter(foodBadgesImageAdapter);
    }

    @Override
    public void update(int count) {
        totalCount = count;
        dots = new TextView[totalCount];
        mFragmentDishBinding.layoutDots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText("-");
            dots[i].setTextSize(35);
            dots[i].setPadding(0, 0, 30, 0);
            dots[i].setTextColor(Color.DKGRAY);
            mFragmentDishBinding.layoutDots.addView(dots[i]);
        }
        if (dots.length > 0)
            dots[0].setTextColor(Color.YELLOW);

        mFragmentDishBinding.recyclerKitchenCommonSlider.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager ll = (LinearLayoutManager) mFragmentDishBinding.recyclerKitchenCommonSlider.getLayoutManager();
                int currentFirstVisible = ll.findFirstVisibleItemPosition();
                addBottomDots(currentFirstVisible);
            }
        });
    }

    private void addBottomDots(int currentPage) {
        try {
            dots = new TextView[totalCount];
            mFragmentDishBinding.layoutDots.removeAllViews();
            for (int i = 0; i < dots.length; i++) {
                dots[i] = new TextView(this);
                dots[i].setText("-");
                dots[i].setTextSize(35);
                dots[i].setPadding(0, 0, 30, 0);
                dots[i].setTextColor(Color.DKGRAY);
                mFragmentDishBinding.layoutDots.addView(dots[i]);
            }

            if (dots.length > 0)
                dots[currentPage].setTextColor(Color.YELLOW);

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    private void subscribeToLiveDataKitchenImages() {
        mKitchenDetailsViewModel.getKitchenCommonImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addkitchenCommonImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getFoodBadgesImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addFoodBadgesImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getSpeialItemsImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addSpecialItemsImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getFavTodaysMenuItemsImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addFavTodaysMenuItemsImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getFav1ItemsImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addFav1ImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getTodaysItemsImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addTodaysImagesList(kitchenImagesViewModel));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public int getBindingVariable() {
        return BR.kitchenDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_kitchen_details;
    }

    @Override
    public KitchenDetailsViewModel getViewModel() {
        return mKitchenDetailsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void toastMessage(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price) {
        DialogChangeKitchen.newInstance().show(getSupportFragmentManager(), this, makeitId, productId, quantity, price);

    }

    @Override
    public void dishListLoaded(KitchenDishResponse response) {
        mFragmentDishBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentDishBinding.shimmerViewContainer.stopShimmerAnimation();

        if (response!=null&&response.getResult().size()>0){
            mFavTodaysMenuAdapter.serviceable(response.getResult().get(0).isServiceableStatus());
            mTodaysMenuAdapter.serviceable(response.getResult().get(0).isServiceableStatus());

        }


    }

    @Override
    public void viewCart() {

        Intent intent = MainActivity.newIntent(KitchenDetailsActivity.this);
        intent.putExtra("cart", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void goBack() {
        finish();
    }



    @Override
    public void animChanges(boolean status) {

/*
        if (status){


          animFade(mFragmentDishBinding.menu,mFragmentDishBinding.about);


        }else{
           animFade(mFragmentDishBinding.about,mFragmentDishBinding.menu);
        }*/
    }

    public void animFade(View view, View view2) {

        Transition transition = new Fade();
        transition.setDuration(6000);
        transition.addTarget(R.id.image);
        TransitionManager.beginDelayedTransition(mFragmentDishBinding.parent, transition);
        view2.setVisibility(View.GONE);


        transition.setDuration(6000);
        transition.addTarget(R.id.image);
        TransitionManager.beginDelayedTransition(mFragmentDishBinding.parent, transition);
        view.setVisibility(View.VISIBLE);






       /* view.animate()
                .translationY(view.getHeight())
                .alpha(0.0f)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {

                        super.onAnimationEnd(animation);
                        view.setVisibility(View.GONE);
                    }
                });*/
      /*  TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);*/
        /*  view.setVisibility(View.GONE);*/






        /*view.setVisibility(View.VISIBLE);
        view.setAlpha(0.0f);
        view.animate()
                .setDuration(500)
                .alpha(1.0f)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        view.animate().setListener(null);
                    }
                })
        ;

        view2.animate()
                .setDuration(500)
                .translationY(view2.getHeight());*/


    }

    @Override
    public void onResume() {
        super.onResume();
      /*  mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();*/

        //mKitchenDetailsViewModel.fetchRepos(kitchenID);

        registerWifiReceiver();

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterWifiReceiver();
    }

    @Override
    public void onItemClickData(KitchenDishResponse.Productlist blogUrl, View view) {

        animateView(view);

    }

    public void animateView(View view) {
        Animation shake = AnimationUtils.loadAnimation(KitchenDetailsActivity.this, R.anim.shake);
        view.startAnimation(shake);
    }


    @Override
    public void sendCart() {

        mKitchenDetailsViewModel.totalCart();


    }

    @Override
    public void dishRefresh() {
       /* mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();

        mKitchenDetailsViewModel.fetchRepos();*/
    }

    @Override
    public void addDishFavourite(Integer favId, String fav) {

        mKitchenDetailsViewModel.addFavourite(favId);
    }

    @Override
    public void productNotAvailable() {
        Toast.makeText(KitchenDetailsActivity.this, "Entered quantity not available now", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mKitchenDetailsViewModel.removeFavourite(favId);
    }

    @Override
    public void confirmClick(boolean status) {


        if (true) {
            mKitchenDetailsViewModel.fetchVegProducts();
        }

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onSliderItemClicked(KitchenDishResponse.Kitchenmenuimage mBlog, String days) {

    }

    @Override
    public void onSpecialItemClickData(String url) {

        /*Intent intent= ViewImageActivity.newIntent(KitchenDetailsActivity.this);
        intent.putExtra("image",url);
        startActivity(intent);*/


        viewImage(url);


    }

    @Override
    public void onFoodBadgesItemClickData(String url) {
        /*Intent intent= ViewImageActivity.newIntent(KitchenDetailsActivity.this);
        intent.putExtra("image",url);
        startActivity(intent);*/
       viewImage(url);

    }



    public void viewImage(String url){
        final Dialog nagDialog = new Dialog(KitchenDetailsActivity.this, android.R.style.Theme_DeviceDefault_NoActionBar_Fullscreen);
        nagDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        nagDialog.setCancelable(true);
        nagDialog.setContentView(R.layout.preview_image);
        ButtonTextView btnClose =nagDialog.findViewById(R.id.btnIvClose);
        ImageView ivPreview = (ImageView) nagDialog.findViewById(R.id.iv_preview_image);
        if (url != null) {
            Glide.with(getApplicationContext()).load(url).placeholder(null)
                    .error(R.drawable.images_loading).into(ivPreview);
        }
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                nagDialog.dismiss();
            }
        });
        nagDialog.show();
    }




    private void registerWifiReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        MvvmApp.getInstance().registerReceiver(mWifiReceiver, filter);
    }


    private  boolean checkWifiConnect() {
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

     BroadcastReceiver mWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //   if (mMainViewModel.isAddressAdded()) {
            if (checkWifiConnect()) {
            } else {
                Intent inIntent= InternetErrorFragment.newIntent(MvvmApp.getInstance());
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
    private  void unregisterWifiReceiver() {
        MvvmApp.getInstance().  unregisterReceiver(mWifiReceiver);
    }


}

