package com.tovo.eat.ui.account.favorites.favdish;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentFavoritesDishBinding;
import com.tovo.eat.ui.base.BaseFragment;


import javax.inject.Inject;

public class FavoritesDishFragment extends BaseFragment<FragmentFavoritesDishBinding, FavoritesDishViewModel> implements FavoritesDishNavigator,
FavoriteDishAdapter.LiveProductsAdapterListener{

    @Inject
    FavoritesDishViewModel mPersonalViewModel;
    private FragmentFavoritesDishBinding mFragmentPersonalBinding;
    @Inject
    FavoriteDishAdapter mFavoriteDishAdapter;
    @Inject
    LinearLayoutManager mLayoutManager;

    public static FavoritesDishFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesDishFragment fragment = new FavoritesDishFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return BR.dishViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorites_dish;
    }

    @Override
    public FavoritesDishViewModel getViewModel() {
        return mPersonalViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPersonalViewModel.setNavigator(this);
        mFavoriteDishAdapter.setListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentPersonalBinding = getViewDataBinding();
        //mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //mFragmentPersonalBinding.recyclerviewDishes.setLayoutManager(new LinearLayoutManager(getContext()));
        //mFragmentPersonalBinding.recyclerviewDishes.setAdapter(mFavoriteDishAdapter);
        subscribeToLiveData();
        
    }

    private void subscribeToLiveData() {
        mPersonalViewModel.getDish().observe(this,
                favDishesItemViewModel -> mPersonalViewModel.addFavDishesToList(favDishesItemViewModel));
    }

    @Override
    public void onItemClickData(FavoriteDishResponse.Result blogUrl) {

    }

    @Override
    public void sendCart() {

    }

    @Override
    public void dishRefresh() {

    }

    @Override
    public void productNotAvailable() {

    }

    @Override
    public void addDishFavourite(Integer dishId, String fav) {

    }

    @Override
    public void removeDishFavourite(Integer favId) {

    }
}
