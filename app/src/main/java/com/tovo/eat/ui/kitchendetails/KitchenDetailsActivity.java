package com.tovo.eat.ui.kitchendetails;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityKitchenDetailsBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishAdapter;
import com.tovo.eat.ui.home.kitchendish.KitchenDishResponse;
import com.tovo.eat.ui.kitchendetails.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.kitchendetails.dialog.DialogChangeKitchen;
import com.tovo.eat.utilities.swipe.ItemTouchHelperExtension;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class KitchenDetailsActivity extends BaseActivity<ActivityKitchenDetailsBinding, KitchenDetailsViewModel> implements KitchenDetailsNavigator, KitchenDishAdapter.LiveProductsAdapterListener, AddKitchenDishListener, HasSupportFragmentInjector, MenuKitchenInfoImageAdapter.MenuProductsAdapterListener {

    public ItemTouchHelperExtension mItemTouchHelper;
    public ItemTouchHelperExtension.Callback mCallback;
    @Inject
    KitchenDetailsViewModel mKitchenDetailsViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    KitchenDishAdapter adapter;
    @Inject
    MenuKitchenInfoImageAdapter kitchenCommonAdapter;
    @Inject
    InfoImageAdapter infoImageAdapter;
    ActivityKitchenDetailsBinding mFragmentDishBinding;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    Integer kitchenID;
    private float collapsedScale;
    private float expandedScale;

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
        adapter.setListener(this);
        kitchenCommonAdapter.setListener(this);
        mFragmentDishBinding = getViewDataBinding();


        setSupportActionBar(mFragmentDishBinding.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        /*backArrow.setColorFilter(getResources().getColor(R.color.md_grey_900), PorterDuff.Mode.SRC_ATOP);*/
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
        mFragmentDishBinding.toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));


        //   mFragmentDishBinding.kitchenSlider.init(new PicassoImageLoadingService(this));


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            kitchenID = intent.getExtras().getInt("kitchenId");

            mKitchenDetailsViewModel.fetchRepos(kitchenID);


            subscribeToLiveData();
        }


        setTitle(mKitchenDetailsViewModel.kitchenName.get());


        //  setTitle("Kitchen");



       /* mFragmentDishBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0 || verticalOffset <= mFragmentDishBinding.toolbar.getHeight()){
                    Toast.makeText(KitchenDetailsActivity.this, "collapsed", Toast.LENGTH_SHORT).show();


                }else if(!mFragmentDishBinding.toolbar.getTitle().equals(null)){
                    Toast.makeText(KitchenDetailsActivity.this, "un-collapsed", Toast.LENGTH_SHORT).show();
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


                    //   Toast.makeText(KitchenDetailsActivity.this, "Expanded", Toast.LENGTH_SHORT).show();

                    //   setTitle(" ");
                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));

                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                    // Toast.makeText(KitchenDetailsActivity.this, "collapsed", Toast.LENGTH_SHORT).show();
                    // setTitle("Kitchen");
                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
                    //  mFragmentDishBinding.toolbar.setVisibility(View.GONE);
                    // mFragmentDishBinding.image.setVisibility(View.GONE);

                } else {
                    //   Toast.makeText(KitchenDetailsActivity.this, "d", Toast.LENGTH_SHORT).show();
                    //   setTitle(" ");
                    //  mFragmentDishBinding.toolbar.setVisibility(View.VISIBLE);
                    // mFragmentDishBinding.image.setVisibility(View.VISIBLE);

                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
                }*//*

            }
        });
*/


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentDishBinding.recyclerviewOrders.setAdapter(adapter);





       /* mCallback = new ItemTouchHelperCallback();
        mItemTouchHelper = new ItemTouchHelperExtension(mCallback);
        mItemTouchHelper.attachToRecyclerView( mFragmentDishBinding.recyclerviewOrders);
        adapter.setItemTouchHelperExtension(mItemTouchHelper);*/

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

        subscribeToLiveData();


        mFragmentDishBinding.recyclerviewOrders.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mFragmentDishBinding.recyclerviewGalleryImage.setLayoutManager(gridLayoutManager);
        mFragmentDishBinding.recyclerviewGalleryImage.setAdapter(infoImageAdapter);




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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFragmentDishBinding.recyclerKitchenCommon.setLayoutManager(linearLayoutManager);
        mFragmentDishBinding.recyclerKitchenCommon.setAdapter(kitchenCommonAdapter);

        GridLayoutManager gridLayoutManager2 = new GridLayoutManager(getApplicationContext(), 3);
        mFragmentDishBinding.recyclerviewFoodBadges.setLayoutManager(gridLayoutManager2);
        mFragmentDishBinding.recyclerviewFoodBadges.setAdapter(infoImageAdapter);


        /*Animation aniSlide = AnimationUtils.loadAnimation(this,R.anim.zoom_in);
        mFragmentDishBinding.recyclerKitchenCommon.startAnimation(aniSlide);*/
    }

    private void subscribeToLiveDataKitchenImages() {
        mKitchenDetailsViewModel.getKitchenCommonImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addkitchenCommonImagesList(kitchenImagesViewModel));

        mKitchenDetailsViewModel.getFoodBadgesImages().observe(this,
                kitchenImagesViewModel -> mKitchenDetailsViewModel.addFoodBadgesImagesList(kitchenImagesViewModel));
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
    }

    @Override
    public void viewCart() {

        Intent intent = MainActivity.newIntent(KitchenDetailsActivity.this);
        intent.putExtra("cart", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void back() {
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

    @Override
    public void previousImage() {
        LinearLayoutManager ll = (LinearLayoutManager) mFragmentDishBinding.recyclerKitchenCommon.getLayoutManager();
        int firstVisiblePosition = ll.findFirstCompletelyVisibleItemPosition();
        int firstVisiblePosition1 = ll.getItemCount();
        Log.e("sdf",""+firstVisiblePosition1);
        if (firstVisiblePosition>0 && firstVisiblePosition<firstVisiblePosition1) {
            mFragmentDishBinding.recyclerKitchenCommon.smoothScrollToPosition(firstVisiblePosition - 1);
        }
    }

    @Override
    public void nextImage() {
        LinearLayoutManager ll = (LinearLayoutManager) mFragmentDishBinding.recyclerKitchenCommon.getLayoutManager();
        int firstVisiblePosition = ll.findFirstCompletelyVisibleItemPosition();
        mFragmentDishBinding.recyclerKitchenCommon.smoothScrollToPosition(firstVisiblePosition + 1);
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

    private void subscribeToLiveData() {
        mKitchenDetailsViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mKitchenDetailsViewModel.addDishItemsToList(kitchenItemViewModel));
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();

        mKitchenDetailsViewModel.fetchRepos(kitchenID);
    }

    @Override
    public void onItemClickData(KitchenDishResponse.Result blogUrl, View view) {

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
        mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();
        mKitchenDetailsViewModel.fetchRepos(kitchenID);

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
    public void onItemClicked(KitchenDishResponse.Kitchenmenuimage mBlog, String days) {

    }
}

