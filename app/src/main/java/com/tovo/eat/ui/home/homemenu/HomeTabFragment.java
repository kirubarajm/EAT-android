package com.tovo.eat.ui.home.homemenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentHomeBinding;
import com.tovo.eat.ui.account.favorites.FavoritesTabActivity;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.address.select.SelectSelectAddressListActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.filter.FilterFragment;
import com.tovo.eat.ui.home.MainActivity;

import javax.inject.Inject;

public class HomeTabFragment extends BaseFragment<FragmentHomeBinding, HomeTabViewModel> implements HomeTabNavigator {


    public static final String TAG = HomeTabFragment.class.getSimpleName();

    @Inject
    HomeTabViewModel mHomeTabViewModel;
    @Inject
    HomeTabAdapter mHomeTabAdapter;

    private FragmentHomeBinding mFragmentHomeBinding;

    public static HomeTabFragment newInstance() {
        Bundle args = new Bundle();
        HomeTabFragment fragment = new HomeTabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.homeTabViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public HomeTabViewModel getViewModel() {
        return mHomeTabViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void selectAddress() {
        Intent intent = AddressListActivity.newIntent(getContext());
        startActivity(intent);
    }

    @Override
    public void filter() {
        FilterFragment bottomSheetFragment = new FilterFragment();
        bottomSheetFragment.show(getBaseActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    @Override
    public void favourites() {

       /* FragmentTransaction transaction = getFragmentManager().beginTransaction();
        FavoritesTabActivity fragment = new FavoritesTabActivity();
        transaction.replace(R.id.content_main, fragment);
        transaction.commitNow();*/

       Intent intent=FavoritesTabActivity.newIntent(getContext());
       startActivity(intent);


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHomeTabViewModel.setNavigator(this);
        mHomeTabViewModel.updateAddressTitle();
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentHomeBinding = getViewDataBinding();
        setUp();
    }

    private void setUp() {
        mHomeTabAdapter.setCount(2);
        mFragmentHomeBinding.myaccViewPager.setAdapter(mHomeTabAdapter);
        mFragmentHomeBinding.myaccTabLayout.addTab(mFragmentHomeBinding.myaccTabLayout.newTab().setText("Kitchen"));
        mFragmentHomeBinding.myaccTabLayout.addTab(mFragmentHomeBinding.myaccTabLayout.newTab().setText("Dish"));
        mFragmentHomeBinding.myaccViewPager.setOffscreenPageLimit(mFragmentHomeBinding.myaccTabLayout.getTabCount());
        mFragmentHomeBinding.myaccViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mFragmentHomeBinding.myaccTabLayout));
        mFragmentHomeBinding.myaccTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentHomeBinding.myaccViewPager.setCurrentItem(tab.getPosition());

                mHomeTabViewModel.setCurrentFragment(tab.getPosition());



            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


                mHomeTabViewModel.setCurrentFragment(0);

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mHomeTabViewModel.updateAddressTitle();


    }


}
