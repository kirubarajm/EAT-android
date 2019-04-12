package com.tovo.eat.ui.cart;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityCartBinding;
import com.tovo.eat.ui.account.MyAccountFragment;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;

import org.json.JSONException;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public class CartActivity extends BaseFragment<ActivityCartBinding, CartViewModel> implements CartNavigator,CartDishAdapter.LiveProductsAdapterListener {


    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    CartDishAdapter adapter;


    private ActivityCartBinding mActivityCartBinding;

    @Inject
    CartViewModel mCartViewModel;


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

        return mCartViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }


    public static CartActivity newInstance() {
        Bundle args = new Bundle();
        CartActivity fragment = new CartActivity();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // mActivityCartBinding = getViewDataBinding();
        mCartViewModel.setNavigator(this);
        adapter.setListener(this);

        //   ((MainActivity) getActivity()).setActionBarTitle("My Account");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityCartBinding = getViewDataBinding();

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
        mCartViewModel.getDishItemsLiveData().observe(this,
                kitchenItemViewModel -> mCartViewModel.addDishItemsToList(kitchenItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();
        //  mDishViewModel.fetchRepos();
    }

    @Override
    public void onItemClickData(CartDishResponse.Result blogUrl) {

    }

    @Override
    public void sendCart() {

    }

    @Override
    public void saveToCart(String cart) {

        mCartViewModel.saveToCartPojo(cart);
    }


    @Override
    public String getCartData() {
        return mCartViewModel.getCartPojoDetails();
    }

    @Override
    public void productNotAvailable() {
        Toast.makeText(getContext(), "Entered quantity not available now", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void reloadCart() {
        subscribeToLiveData();
    }
}