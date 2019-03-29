package com.tovo.eat.ui.cart;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityCartBinding;
import com.tovo.eat.ui.base.BaseActivity;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public class CartActivity extends BaseActivity<ActivityCartBinding, CartViewModel> implements CartNavigator {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    ViewModelProvider.Factory mViewModelFactory;

    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    CartDishAdapter adapter;


    private ActivityCartBinding mActivityCartBinding;


    private CartViewModel mCartViewModel;

    public static Intent newIntent(Context context) {
       /* Intent intent = new Intent(context, CartActivity.class);
        return intent;*/
        return new Intent(context, CartActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.cartViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_cart;
    }

    @Override
    public CartViewModel getViewModel() {
        mCartViewModel = ViewModelProviders.of(this, mViewModelFactory).get(CartViewModel.class);
        return mCartViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCartBinding = getViewDataBinding();
        mCartViewModel.setNavigator(this);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCartBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mActivityCartBinding.recyclerviewOrders.setAdapter(adapter);
        subscribeToLiveData();

        mCartViewModel.fetchRepos();

    }


    @Override
    public void gotoJobCompleted() {

    }

    @Override
    public void gotoInJobCompleted() {

    }

    @Override
    public void dishListLoaded() {
        //   mFragmentDishBinding.refreshList.setRefreshing(false);
    }


    private void subscribeToLiveData() {
        mCartViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mCartViewModel.addDishItemsToList(kitchenItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();
        //  mDishViewModel.fetchRepos();
    }

}