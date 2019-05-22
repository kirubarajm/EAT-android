package com.tovo.eat.ui.home.kitchendish;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentKitchenDishBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.home.kitchendish.dialog.DialogChangeKitchen;
import com.tovo.eat.utilities.MainSliderAdapter;
import com.tovo.eat.utilities.PicassoImageLoadingService;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class KitchenDishActivity extends BaseActivity<FragmentKitchenDishBinding, KitchenDishViewModel> implements KitchenDishNavigator, KitchenDishAdapter.LiveProductsAdapterListener, AddKitchenDishListener, HasSupportFragmentInjector {

    @Inject
    KitchenDishViewModel mKitchenDishViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    KitchenDishAdapter adapter;

    FragmentKitchenDishBinding mFragmentDishBinding;


    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;





    Integer kitchenID;





    public static Intent newIntent(Context context) {
       /* Intent intent = new Intent(context, CartActivity.class);
        return intent;*/
        return new Intent(context, KitchenDishActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* KitchenDishSubBinding mBinding = DataBindingUtil
                .setContentView(this, R.layout.kitchen_dish_sub);*/


        mKitchenDishViewModel.setNavigator(this);
        adapter.setListener(this);
        mFragmentDishBinding = getViewDataBinding();


        setSupportActionBar(mFragmentDishBinding.toolbar);






        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        /*backArrow.setColorFilter(getResources().getColor(R.color.md_grey_900), PorterDuff.Mode.SRC_ATOP);*/
        getSupportActionBar().setHomeAsUpIndicator(backArrow);

        mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
        mFragmentDishBinding.toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));





        mFragmentDishBinding.kitchenSlider.init(new PicassoImageLoadingService(this));

        mFragmentDishBinding.kitchenSlider.setAdapter(new MainSliderAdapter());






        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            kitchenID=intent.getExtras().getInt("kitchenId");

            mKitchenDishViewModel.fetchRepos(kitchenID);


            subscribeToLiveData();
        }











        //  setTitle("Kitchen");



       /* mFragmentDishBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(verticalOffset == 0 || verticalOffset <= mFragmentDishBinding.toolbar.getHeight()){
                    Toast.makeText(KitchenDishActivity.this, "collapsed", Toast.LENGTH_SHORT).show();


                }else if(!mFragmentDishBinding.toolbar.getTitle().equals(null)){
                    Toast.makeText(KitchenDishActivity.this, "un-collapsed", Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        mFragmentDishBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            //private State state;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {


                    //   Toast.makeText(KitchenDishActivity.this, "Expanded", Toast.LENGTH_SHORT).show();

                //   setTitle(" ");
                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));

                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                    // Toast.makeText(KitchenDishActivity.this, "collapsed", Toast.LENGTH_SHORT).show();
                  // setTitle("Kitchen");
                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
                    //  mFragmentDishBinding.toolbar.setVisibility(View.GONE);
                    // mFragmentDishBinding.image.setVisibility(View.GONE);

                } else {
                 //   Toast.makeText(KitchenDishActivity.this, "d", Toast.LENGTH_SHORT).show();
                 //   setTitle(" ");
                    //  mFragmentDishBinding.toolbar.setVisibility(View.VISIBLE);
                    // mFragmentDishBinding.image.setVisibility(View.VISIBLE);

                    mFragmentDishBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
                }
            }
        });


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentDishBinding.recyclerviewOrders.setAdapter(adapter);

        // mKitchenDishViewModel.fetchRepos();

        subscribeToLiveData();

      /*  mFragmentDishBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mKitchenDishViewModel.fetchRepos();
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
        return BR.kitchenDishViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kitchen_dish;
    }

    @Override
    public KitchenDishViewModel getViewModel() {
        return mKitchenDishViewModel;
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
    public void dishListLoaded() {
        //mFragmentDishBinding.refreshList.setRefreshing(false);


        mFragmentDishBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void viewCart() {

        Intent intent = MainActivity.newIntent(KitchenDishActivity.this);
        intent.putExtra("cart", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void back() {
        finish();
    }


    private void subscribeToLiveData() {
        mKitchenDishViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mKitchenDishViewModel.addDishItemsToList(kitchenItemViewModel));


      /*  mKitchenDishViewModel.getDishItemFullViewModels().observe(this,
                kitchenItemViewModel2 -> mKitchenDishViewModel.addKitchenDishItemsToList(kitchenItemViewModel2));*/

    }


    @Override
    public void onResume() {
        super.onResume();
        mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();

        mKitchenDishViewModel.fetchRepos(kitchenID);
    }

    @Override
    public void onItemClickData(KitchenDishResponse.Result blogUrl) {

    }

    @Override
    public void sendCart() {

        mKitchenDishViewModel.totalCart();


    }

    @Override
    public void dishRefresh() {
       /* mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();

        mKitchenDishViewModel.fetchRepos();*/
    }

    @Override
    public void addDishFavourite(Integer favId, String fav) {

        mKitchenDishViewModel.addFavourite(favId);
    }

    @Override
    public void productNotAvailable() {
        Toast.makeText(KitchenDishActivity.this, "Entered quantity not available now", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mKitchenDishViewModel.removeFavourite(favId);
    }


    @Override
    public void confirmClick(boolean status) {
        mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();
        mKitchenDishViewModel.fetchRepos(kitchenID);

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

