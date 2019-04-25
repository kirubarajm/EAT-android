package com.tovo.eat.ui.home.homemenu.dish;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentDishBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.filter.FilterFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.CartListener;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.FilterListener;

import javax.inject.Inject;

public class DishFragment extends BaseFragment<FragmentDishBinding, DishViewModel> implements DishNavigator, DishAdapter.LiveProductsAdapterListener , FilterListener, StartFilter {

    @Inject
    DishViewModel mDishViewModel;
    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    DishAdapter adapter;

    FragmentDishBinding mFragmentDishBinding;


    CartListener cartListener;

    public static DishFragment newInstance() {
        Bundle args = new Bundle();
        DishFragment fragment = new DishFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        cartListener=(CartListener)context;

        super.onAttach(context);
    }

   /* public static <T> getInterface(StartFilter interfaceClass, Fragment thisFragment) {
           final Fragment parent = thisFragment.getParentFragment();
           if (parent != null && interfaceClass.isAssignableFrom(parent)) {
               return interfaceClass.c(parent);
           }

           final Activity activity = thisFragment.getActivity();
           if (activity != null && interfaceClass.isAssignableFrom(activity)) {
               return interfaceClass.cast(activity);
           }

           return null;
       }*/


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDishViewModel.setNavigator(this);
        adapter.setListener(this);
        ((MainActivity) getActivity()).setFilterListener(DishFragment.this);


      //  StartFilter myInterface =getInterface(StartFilter.class, this);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentDishBinding = getViewDataBinding();
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentDishBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentDishBinding.recyclerviewOrders.setAdapter(adapter);
        subscribeToLiveData();

        mFragmentDishBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
        mFragmentDishBinding.refreshList.setRefreshing(false);
    }

    @Override
    public void addressAdded() {




    }

    @Override
    public void noAddressFound() {
        Toast.makeText(getContext(), "Please Add your address", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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
        mDishViewModel.getKitchenItemsLiveData().observe(this,
               kitchenItemViewModel -> mDishViewModel.addDishItemsToList(kitchenItemViewModel));
    }




    @Override
    public void onResume() {
        super.onResume();

        ((MainActivity)getActivity()).statusUpdate();

       mDishViewModel.fetchRepos();
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
        subscribeToLiveData();
    }

    @Override
    public void productNotAvailable() {

        Toast.makeText(getContext(), "Entered quantity not available now", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void addDishFavourite(Integer dishId, String fav) {
        mDishViewModel.addFavourite(dishId,fav);
    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mDishViewModel.removeFavourite(favId);
    }


    @Override
    public void filterList() {


        mDishViewModel.fetchRepos();


    }

    @Override
    public void applyFilter() {
        mDishViewModel.fetchRepos();

    }
}

