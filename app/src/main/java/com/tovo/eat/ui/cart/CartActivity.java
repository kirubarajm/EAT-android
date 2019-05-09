package com.tovo.eat.ui.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityCartBinding;
import com.tovo.eat.ui.address.select.SelectSelectAddressListActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.CartListener;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.HomeTabFragment;
import com.tovo.eat.ui.orderplaced.OrderPlacedActivity;
import com.tovo.eat.ui.registration.RegistrationActivity;

import javax.inject.Inject;

public class CartActivity extends BaseFragment<ActivityCartBinding, CartViewModel> implements CartNavigator, CartDishAdapter.LiveProductsAdapterListener {


    /*  @Inject
      LinearLayoutManager mLayoutManager;*/
    @Inject
    CartDishAdapter adapter;
    @Inject
    CartViewModel mCartViewModel;
    CartListener cartListener;
    private ActivityCartBinding mActivityCartBinding;

    public static CartActivity newInstance() {
        Bundle args = new Bundle();
        CartActivity fragment = new CartActivity();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        cartListener = (CartListener) context;
        super.onAttach(context);
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

        return mCartViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // mActivityCartBinding = getViewDataBinding();
        mCartViewModel.setNavigator(this);
        adapter.setListener(this);


        if (mCartViewModel.getCartPojoDetails() == null) {

            ((MainActivity) getActivity()).openHome();
        }





        //   ((FilterActivity) getActivity()).setActionBarTitle("My Account");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivityCartBinding = getViewDataBinding();

        mCartViewModel.toolbarTitle.set(getString(R.string.cart));
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivityCartBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mActivityCartBinding.recyclerviewOrders.setAdapter(adapter);
        subscribeToLiveData();

        //mCartViewModel.fetchRepos();

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

    @Override
    public void paymentMode(String mode) {


        if (mode.equals(getString(R.string.cash_on_delivery))) {
            mCartViewModel.payment.set(true);
        } else if (mode.equals(getString(R.string.pay_online))) {

            mCartViewModel.payment.set(true);

        } else {
            mCartViewModel.payment.set(false);
        }


    }

    @Override
    public void selectAddress() {

        Intent intent = SelectSelectAddressListActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public boolean paymentStatus(String mode) {

        /*if (mode.equals(getString(R.string.cash_on_delivery))) {
            Toast.makeText(getBaseActivity(), mode, Toast.LENGTH_SHORT).show();
            return true;

        } else if (mode.equals(getString(R.string.pay_online))) {
            Toast.makeText(getBaseActivity(), mode, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(getBaseActivity(), "nothing selected", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return true;

    }

    @Override
    public void orderCompleted() {

       /* FragmentTransaction transaction =getBaseActivity().getSupportFragmentManager().beginTransaction();
        HomeTabFragment fragment = new HomeTabFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();*/


        Intent intent = OrderPlacedActivity.newIntent(getContext());
        startActivity(intent);

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emptyCart() {
        FragmentTransaction transaction = getBaseActivity().getSupportFragmentManager().beginTransaction();
        HomeTabFragment fragment = new HomeTabFragment();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();

    }

    @Override
    public void postRegistration() {

        Intent intent = RegistrationActivity.newIntent(getContext());
        startActivity(intent);

    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    private void subscribeToLiveData() {
        mCartViewModel.getDishItemsLiveData().observe(this,
                kitchenItemViewModel -> mCartViewModel.addDishItemsToList(kitchenItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();
        mCartViewModel.setAddressTitle();

        if (mCartViewModel.getCartPojoDetails() != null)
            mCartViewModel.fetchRepos();
    }

    @Override
    public void onItemClickData(CartDishResponse.Result blogUrl) {

    }

    @Override
    public void sendCart() {
        cartListener.checkCart();
    }

    @Override
    public void saveToCart(String cart) {
        mCartViewModel.saveToCartPojo(cart);
        cartListener.checkCart();
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
        if (mCartViewModel.getCartPojoDetails() != null)
            mCartViewModel.fetchRepos();
    }

}