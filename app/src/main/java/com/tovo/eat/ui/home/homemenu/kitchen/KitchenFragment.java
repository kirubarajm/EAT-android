package com.tovo.eat.ui.home.homemenu.kitchen;

import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;


import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentKitchenBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;

import javax.inject.Inject;

public class KitchenFragment extends BaseFragment<FragmentKitchenBinding, KitchenViewModel> implements KitchenNavigator, KitchenAdapter.LiveProductsAdapterListener {


    @Inject
    KitchenViewModel mKitchenViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    KitchenAdapter adapter;

    FragmentKitchenBinding mFragmentKitchenBinding;

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

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentKitchenBinding = getViewDataBinding();
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentKitchenBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentKitchenBinding.recyclerviewOrders.setAdapter(adapter);
        subscribeToLiveData();

        mFragmentKitchenBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
    public void gotoJobCompleted() {


    }

    @Override
    public void gotoInJobCompleted() {

    }

    @Override
    public void kitchenListLoaded() {
        mFragmentKitchenBinding.refreshList.setRefreshing(false);
    }






   /* @Override
    public void gotoOrderView() {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        ViewFragment fragment = new VideoActivity();
        transaction.setCustomAnimations(R.anim.slide_left, R.anim.slide_right);
        transaction.replace(R.id.frame_food, fragment);
        transaction.commit();


    }*/

    private void subscribeToLiveData() {
        mKitchenViewModel.getKitchenItemsLiveData().observe(this,
               kitchenItemViewModel -> mKitchenViewModel.addKitchenItemsToList(kitchenItemViewModel));
    }




    @Override
    public void onResume() {
        super.onResume();
    //    mKitchenViewModel.fetchRepos();
    }


    @Override
    public void onItemClickData(Integer kitchenId) {

        Intent intent= KitchenDishActivity.newIntent(getContext());
        intent.putExtra("kitchenId",kitchenId);
        startActivity(intent);


    }

    @Override
    public void addFav(Integer id, String fav) {





    }
}

