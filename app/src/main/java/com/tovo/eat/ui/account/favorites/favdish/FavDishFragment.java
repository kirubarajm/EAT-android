package com.tovo.eat.ui.account.favorites.favdish;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentDishBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishNavigator;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.dish.DishViewModel;
import com.tovo.eat.ui.home.homemenu.dish.dialog.AddDishListener;
import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchen;
import com.tovo.eat.utilities.analytics.Analytics;
import com.tovo.eat.utilities.nointernet.InternetListener;

import javax.inject.Inject;

public class FavDishFragment extends BaseFragment<FragmentDishBinding, DishViewModel> implements DishNavigator, DishAdapter.LiveProductsAdapterListener, StartFilter, AddDishListener {

    @Inject
    DishViewModel mDishViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    DishAdapter adapter;

    FragmentDishBinding mFragmentDishBinding;
    Analytics analytics;
    String  pageName="Favourite dishes";


    CartFavListener cartListener;

    InternetListener internetListener;

    public static FavDishFragment newInstance() {
        Bundle args = new Bundle();
        FavDishFragment fragment = new FavDishFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        cartListener = (CartFavListener) context;
        super.onAttach(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDishViewModel.setNavigator(this);
        adapter.setListener(this);

        analytics=new Analytics(getBaseActivity(),pageName);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentDishBinding = getViewDataBinding();

        setUp();


    }


    public void setUp() {

        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentDishBinding.recyclerviewOrders.setAdapter(adapter);
        subscribeToLiveData();
        mFragmentDishBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


              mFragmentDishBinding.loader.setVisibility(View.VISIBLE);

                mDishViewModel.fetchRepos();
            }
        });

    }


    @Override
    public int getBindingVariable() {
        return BR.dishViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_dish;
    }

    @Override
    public DishViewModel getViewModel() {
        return mDishViewModel;
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
    public void dishListLoaded() {
        mFragmentDishBinding.loader.setVisibility(View.GONE);
        mFragmentDishBinding.refreshList.setRefreshing(false);
    }

    @Override
    public void addressAdded() {


    }

    @Override
    public void noAddressFound() {

        toastMessage("Please Add your address");
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dishLoading() {
        mFragmentDishBinding.loader.setVisibility(View.VISIBLE);
    }

    private void subscribeToLiveData() {
        mDishViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mDishViewModel.addDishItemsToList(kitchenItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();

       /* mFragmentDishBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentDishBinding.shimmerViewContainer.startShimmerAnimation();*/


//        ((TestActivity) getActivity()).statusUpdate();

        // mDishViewModel.fetchRepos();
    }

    @Override
    public void onItemClickData(DishResponse.Result blogUrl) {


    }

    @Override
    public void sendCart() {

        cartListener.checkCart();

    }

    @Override
    public void dishRefresh() {


        mFragmentDishBinding.loader.setVisibility(View.VISIBLE);

        mDishViewModel.fetchRepos();
    }

    @Override
    public void productNotAvailable() {

        toastMessage("Entered quantity not available now");


    }

    @Override
    public void addDishFavourite(Integer dishId, String fav) {
        mDishViewModel.addFavourite(dishId, fav);
    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mDishViewModel.removeFavourite(favId);
    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void favChanged() {
        mDishViewModel.fetchRepos();
    }

    @Override
    public void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price) {

        DialogChangeKitchen fragment = new DialogChangeKitchen();
        fragment.setTargetFragment(this, 0);
        fragment.setCancelable(false);

        DialogChangeKitchen.newInstance(fragment).show(getFragmentManager(), getBaseActivity(), makeitId, productId, quantity, price);

    }

    @Override
    public void empty() {
        mDishViewModel.emptyDish.set(true);
    }

    @Override
    public void applyFilter() {
        mFragmentDishBinding.loader.setVisibility(View.VISIBLE);
        mDishViewModel.fetchRepos();

    }

    @Override
    public void confirmClick(boolean status) {
        mFragmentDishBinding.loader.setVisibility(View.VISIBLE);
        mDishViewModel.fetchRepos();
        cartListener.checkCart();
    }
}

