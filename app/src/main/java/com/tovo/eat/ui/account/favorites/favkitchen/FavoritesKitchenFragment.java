package com.tovo.eat.ui.account.favorites.favkitchen;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentFavoritesDishBinding;
import com.tovo.eat.databinding.FragmentFavoritesKitchenBinding;
import com.tovo.eat.ui.base.BaseFragment;

import javax.inject.Inject;

public class FavoritesKitchenFragment extends BaseFragment<FragmentFavoritesKitchenBinding, FavoritesKitchenViewModel> implements FavoritesKitchenNavigator {

    @Inject
    FavoritesKitchenViewModel mPersonalViewModel;
    private FragmentFavoritesKitchenBinding mFragmentFavoritesKitchenBinding;

    public static FavoritesKitchenFragment newInstance() {
        Bundle args = new Bundle();
        FavoritesKitchenFragment fragment = new FavoritesKitchenFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getBindingVariable() {
        return BR.favKitchenViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_favorites_kitchen;
    }

    @Override
    public FavoritesKitchenViewModel getViewModel() {
        return mPersonalViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentFavoritesKitchenBinding = getViewDataBinding();
        mPersonalViewModel.setNavigator(this);
        //subscribeToLiveData();
    }

/*
    private void subscribeToLiveData() {
        mPersonalViewModel.getPersonal().observe(this,
                menuProductsItemViewModel -> mPersonalViewModel.addPersonToList(menuProductsItemViewModel));
    }
*/

}
