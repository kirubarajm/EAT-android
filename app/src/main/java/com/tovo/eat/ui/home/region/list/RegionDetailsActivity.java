package com.tovo.eat.ui.home.region.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityRegionListBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;

import javax.inject.Inject;

public class RegionDetailsActivity extends BaseActivity<ActivityRegionListBinding, RegionDetailsViewModel> implements RegionDetailsNavigator, KitchenAdapter.LiveProductsAdapterListener {

    @Inject
    RegionDetailsViewModel mRegionDetailsViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    KitchenAdapter adapter;

    ActivityRegionListBinding mActivityRegionListBinding;

    public static Intent newIntent(Context context) {

        return new Intent(context, RegionDetailsActivity.class);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityRegionListBinding = getViewDataBinding();
        mRegionDetailsViewModel.setNavigator(this);
        adapter.setListener(this);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            mRegionDetailsViewModel.fetchRepos(intent.getExtras().getInt("id"));
            subscribeToLiveData();
        }

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityRegionListBinding.recyclerviewList.setLayoutManager(new LinearLayoutManager(this));
        mActivityRegionListBinding.recyclerviewList.setAdapter(adapter);


        mActivityRegionListBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRegionDetailsViewModel.fetchRepos(intent.getExtras().getInt("id"));
            }
        });


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
    public void listLoaded() {
        mActivityRegionListBinding.refreshList.setRefreshing(false);

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
    public void onItemClickData(Integer kitchenId) {


        Intent intent = KitchenDishActivity.newIntent(RegionDetailsActivity.this);
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

