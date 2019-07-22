package com.tovo.eat.ui.home.region.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityRegionDetailsBinding;
import com.tovo.eat.databinding.ActivityRegionListBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;

import javax.inject.Inject;

public class RegionDetailsActivity extends BaseActivity<ActivityRegionDetailsBinding, RegionDetailsViewModel> implements RegionDetailsNavigator, KitchenAdapter.LiveProductsAdapterListener {

    @Inject
    RegionDetailsViewModel mRegionDetailsViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    KitchenAdapter adapter;

    ActivityRegionDetailsBinding mActivityRegionListBinding;

    public static Intent newIntent(Context context) {

        return new Intent(context, RegionDetailsActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegionListBinding = getViewDataBinding();
        mRegionDetailsViewModel.setNavigator(this);
        adapter.setListener(this);

        setSupportActionBar(mActivityRegionListBinding.toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        /*backArrow.setColorFilter(getResources().getColor(R.color.md_grey_900), PorterDuff.Mode.SRC_ATOP);*/
        getSupportActionBar().setHomeAsUpIndicator(backArrow);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {

            mRegionDetailsViewModel.detailImageUrl.set(intent.getExtras().getString("image"));
            mRegionDetailsViewModel.tagline.set(intent.getExtras().getString("tagline"));
            mRegionDetailsViewModel.fetchRepos(intent.getExtras().getInt("id"));
            subscribeToLiveData();
        }


        setTitle(mRegionDetailsViewModel.regionName.get());
      //  mActivityRegionListBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
        mActivityRegionListBinding.toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));

        mActivityRegionListBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            //private State state;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {


                    //   Toast.makeText(FavouritesActivity.this, "Expanded", Toast.LENGTH_SHORT).show();

                    //   setTitle(" ");
                    mActivityRegionListBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));

                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {

                    // Toast.makeText(FavouritesActivity.this, "collapsed", Toast.LENGTH_SHORT).show();
                    // setTitle("Kitchen");
                    mActivityRegionListBinding.toolbarLayout.setCollapsedTitleTextColor(Color.rgb(0, 0, 0));
                    //  mFragmentDishBinding.toolbar.setVisibility(View.GONE);
                    // mFragmentDishBinding.image.setVisibility(View.GONE);

                } else {
                    //   Toast.makeText(FavouritesActivity.this, "d", Toast.LENGTH_SHORT).show();
                    //   setTitle(" ");
                    //  mFragmentDishBinding.toolbar.setVisibility(View.VISIBLE);
                    // mFragmentDishBinding.image.setVisibility(View.VISIBLE);

                    mActivityRegionListBinding.toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.transparent));
                }
            }
        });


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRegionListBinding.recyclerviewOrders.setLayoutManager(new LinearLayoutManager(this));
        mActivityRegionListBinding.recyclerviewOrders.setAdapter(adapter);

/*
        mActivityRegionListBinding.r.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRegionDetailsViewModel.fetchRepos(intent.getExtras().getInt("id"));
            }
        });*/


    }


    @Override
    public int getBindingVariable() {
        return BR.regionDetailsViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_region_details;
    }

    @Override
    public RegionDetailsViewModel getViewModel() {
        return mRegionDetailsViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

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
    public void listLoaded() {
        //mActivityRegionListBinding.refreshList.setRefreshing(false);

    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }



    private void subscribeToLiveData() {
        mRegionDetailsViewModel.getkitchenListItemsLiveData().observe(this,
               kitchensListItemViewModel -> mRegionDetailsViewModel.addDishItemsToList(kitchensListItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {

    }

    @Override
    public void onItemClickData(Integer kitchenId) {


        Intent intent = KitchenDetailsActivity.newIntent(RegionDetailsActivity.this);
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);
    }

    @Override
    public void animateView(View view) {

    }

    @Override
    public void removeDishFavourite(Integer favId) {

    }

    @Override
    public void addFav(Integer id, String fav) {

    }
}

