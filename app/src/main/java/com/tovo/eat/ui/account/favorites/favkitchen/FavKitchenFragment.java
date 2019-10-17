package com.tovo.eat.ui.account.favorites.favkitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentFavKitchenBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenNavigator;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

public class FavKitchenFragment extends BaseFragment<FragmentFavKitchenBinding, FavKitchenViewModel> implements KitchenNavigator, FavKitchenAdapter.LiveProductsAdapterListener, StartFilter {


    @Inject
    FavKitchenViewModel mKitchenViewModel;
    @Inject
    FavKitchenAdapter adapter;
    Analytics analytics;
    String  pageName=AppConstants.SCREEN_FAVOURITE_KITCHEN;
    FragmentFavKitchenBinding mFragmentKitchenBinding;

    public static FavKitchenFragment newInstance() {
        Bundle args = new Bundle();
        FavKitchenFragment fragment = new FavKitchenFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKitchenViewModel.setNavigator(this);
        adapter.setListener(this);
        analytics=new Analytics(getBaseActivity(),pageName);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentKitchenBinding = getViewDataBinding();
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentKitchenBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentKitchenBinding.recyclerviewOrders.setAdapter(adapter);
        subscribeToLiveData();

        mFragmentKitchenBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //save refresh click
                new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITE_KITCHEN,AppConstants.CLICK_REFRESH);
                //Start loader
                mFragmentKitchenBinding.loader.setVisibility(View.VISIBLE);
                //fetch fav kitchens
                mKitchenViewModel.fetchRepos();
            }
        });
    }

    @Override
    public int getBindingVariable() {
        return BR.kitchenViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_fav_kitchen;
    }

    @Override
    public FavKitchenViewModel getViewModel() {
        return mKitchenViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
    }



    @Override
    public void kitchenListLoaded() {
        mFragmentKitchenBinding.loader.setVisibility(View.GONE);
        mFragmentKitchenBinding.refreshList.setRefreshing(false);
    }

    @Override
    public void kitchenListLoading() {
        mFragmentKitchenBinding.loader.setVisibility(View.VISIBLE);
    }

    @Override
    public void filter() {
    }




    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }



    private void subscribeToLiveData() {
        mKitchenViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mKitchenViewModel.addKitchenItemsToList(kitchenItemViewModel));
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {
    }
    @Override
    public void offersItemClick(CouponListResponse.Result offers) {
    }

    @Override
    public void onItemClickData(Long kitchenId) {
        mKitchenViewModel.saveMakeitId(kitchenId);
        // save kitchen click to analytics
        new Analytics().sendClickData(AppConstants.SCREEN_FAVOURITE_KITCHEN,AppConstants.CLICK_KITCHEN_CLICK);
        //Open kitchen details
        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

    }

    @Override
    public void animateView(View view) {
    }
    @Override
    public void empty() {
        mKitchenViewModel.emptyKitchen.set(true);
    }

    @Override
    public void addFav(Integer id, String fav) {
        mKitchenViewModel.addFavourite(id, fav);
    }
    @Override
    public void removeDishFavourite(Integer favId) {
        mKitchenViewModel.removeFavourite(favId);
    }

    @Override
    public void applyFilter() {
        mFragmentKitchenBinding.loader.setVisibility(View.VISIBLE);
        mKitchenViewModel.fetchRepos();
    }
}

