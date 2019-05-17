package com.tovo.eat.ui.home.region;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentRegionBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;

import javax.inject.Inject;

public class RegionFragment extends BaseFragment<FragmentRegionBinding, RegionViewModel> implements RegionNavigator, RegionsAdapter.LiveProductsAdapterListener {

    @Inject
    RegionViewModel mRegionViewModel;
    /* @Inject
     LinearLayoutManager mLayoutManager;*/
    @Inject
    RegionsAdapter adapter;

    FragmentRegionBinding mFragmentRegionBinding;

    public static RegionFragment newInstance() {
        Bundle args = new Bundle();
        RegionFragment fragment = new RegionFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRegionViewModel.setNavigator(this);
        adapter.setListener(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentRegionBinding = getViewDataBinding();


        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentRegionBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentRegionBinding.recyclerviewOrders.setAdapter(adapter);
        // subscribeToLiveData();

        mFragmentRegionBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFragmentRegionBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                mFragmentRegionBinding.shimmerViewContainer.startShimmerAnimation();
                mRegionViewModel.fetchRepos();
            }
        });


    }

    @Override
    public int getBindingVariable() {
        return BR.regionViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_region;
    }

    @Override
    public RegionViewModel getViewModel() {
        return mRegionViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    @Override
    public void kitchenListLoaded() {

        mFragmentRegionBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentRegionBinding.shimmerViewContainer.stopShimmerAnimation();

        mFragmentRegionBinding.refreshList.setRefreshing(false);
    }

    @Override
    public void kitchenListLoading() {
        mFragmentRegionBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentRegionBinding.shimmerViewContainer.startShimmerAnimation();
    }


    private void subscribeToLiveData() {
        mRegionViewModel.getregionItemsLiveData().observe(this,
                regionItemViewModel -> mRegionViewModel.addKitchenItemsToList(regionItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();

        mFragmentRegionBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentRegionBinding.shimmerViewContainer.stopShimmerAnimation();


        ((MainActivity) getActivity()).statusUpdate();


        kitchenListLoading();
        mRegionViewModel.fetchRepos();

        subscribeToLiveData();
    }


    @Override
    public void onItemClickData(Integer kitchenId) {

        mRegionViewModel.saveMakeitId(kitchenId);

        Intent intent = KitchenDishActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

    }

    @Override
    public void showMore(Integer regionId) {


        Toast.makeText(getContext(), "Show more clicked", Toast.LENGTH_SHORT).show();


    }


}

