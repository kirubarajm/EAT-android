package com.tovo.eat.ui.home.homemenu.kitchen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentKitchenBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.filter.FilterFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.utilities.analytics.Analytics;

import java.util.List;

import javax.inject.Inject;

public class KitchenFragment extends BaseFragment<FragmentKitchenBinding, KitchenViewModel> implements KitchenNavigator, KitchenAdapter.LiveProductsAdapterListener, StartFilter {


    @Inject
    KitchenViewModel mKitchenViewModel;
    @Inject
    KitchenAdapter adapter;

    FragmentKitchenBinding mFragmentKitchenBinding;


    Analytics analytics;
    String pageName = "Kitchen list";


    public static KitchenFragment newInstance() {
        Bundle args = new Bundle();
        KitchenFragment fragment = new KitchenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKitchenViewModel.setNavigator(this);
        adapter.setListener(this);

        analytics = new Analytics(getActivity(), pageName);

        //  mKitchenViewModel.fetchRepos();

        // ((TestActivity) getActivity()).setFilterListener(FavKitchenFragment.this);
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
        // subscribeToLiveData();

        mFragmentKitchenBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                mFragmentKitchenBinding.shimmerViewContainer.startShimmerAnimation();
                mKitchenViewModel.fetchRepos();
            }
        });


        mFragmentKitchenBinding.recyclerviewOrders.setItemAnimator(new DefaultItemAnimator());


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

        mFragmentKitchenBinding.recyclerviewOrders.setOnScrollListener(onScrollListener);


    }

    @Override
    public int getBindingVariable() {
        return BR.kitchenViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kitchen;
    }

    @Override
    public KitchenViewModel getViewModel() {
        return mKitchenViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void kitchenListLoaded() {

        mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentKitchenBinding.shimmerViewContainer.stopShimmerAnimation();

        mFragmentKitchenBinding.refreshList.setRefreshing(false);
    }

    @Override
    public void kitchenListLoading() {
        mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentKitchenBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void filter() {

        FilterFragment bottomSheetFragment = new FilterFragment();
        bottomSheetFragment.show(getBaseActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());

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

        mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentKitchenBinding.shimmerViewContainer.stopShimmerAnimation();

        ((MainActivity) getActivity()).statusUpdate();


        kitchenListLoading();
        mKitchenViewModel.fetchRepos();

        subscribeToLiveData();
    }


    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {

    }

    @Override
    public void offersItemClick(KitchenResponse.Coupon offers) {

    }

    @Override
    public void onItemClickData(Long kitchenId) {

        mKitchenViewModel.saveMakeitId(kitchenId);

        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

    }

    @Override
    public void animateView(View view) {

    }


    @Override
    public void addFav(long id, String fav) {

        mKitchenViewModel.addFavourite(id, fav);
    }

    @Override
    public void infinityStoryItemClick(List<KitchenResponse.Story> story, int position) {

    }

    @Override
    public void regionCollectionItemClick(KitchenResponse.Region collection) {

    }

    @Override
    public void infinityCollectionDetailItemClick(KitchenResponse.CollectionDetail collection) {

    }


    @Override
    public void removeDishFavourite(Integer favId) {
        mKitchenViewModel.removeFavourite(favId);
    }

    @Override
    public void applyFilter() {
        mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentKitchenBinding.shimmerViewContainer.startShimmerAnimation();

        mKitchenViewModel.fetchRepos();
    }


}

