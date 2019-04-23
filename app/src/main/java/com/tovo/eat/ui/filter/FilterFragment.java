package com.tovo.eat.ui.filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;

import com.tovo.eat.databinding.FragmentFilterBinding;
import com.tovo.eat.databinding.FragmentMyAccountBinding;
import com.tovo.eat.ui.account.favorites.FavoritesTabActivity;
import com.tovo.eat.ui.account.feedbackandsupport.FeedbackAndSupportActivity;
import com.tovo.eat.ui.account.orderhistory.historylist.OrderHistoryActivity;
import com.tovo.eat.ui.account.referrals.ReferralsActivity;
import com.tovo.eat.ui.address.list.AddressListActivity;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;

import javax.inject.Inject;


public class FilterFragment extends BaseFragment<FragmentFilterBinding, FilterViewModel> implements FilterNavigator {


    public static final String TAG = FilterFragment.class.getSimpleName();
    @Inject
    FilterViewModel mFilterViewModel;

    FragmentFilterBinding mFragmentFilterBinding;

    @Inject
    FilterAdapter adapter;
    @Inject
    LinearLayoutManager mLayoutManager;




    public static FilterFragment newInstance() {
        Bundle args = new Bundle();
        FilterFragment fragment = new FilterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getBindingVariable() {
        return BR.filterViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_filter;
    }

    @Override
    public FilterViewModel getViewModel() {
        //  mFoodMenuViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FoodMenuViewModel.class);
        return mFilterViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentFilterBinding = getViewDataBinding();
        mFilterViewModel.setNavigator(this);




        //   ((FilterActivity) getActivity()).setActionBarTitle("My Account");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // mFragmentMyAccountBinding = getViewDataBinding();


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentFilterBinding.recyclerviewFilters.setLayoutManager(mLayoutManager);
        mFragmentFilterBinding.recyclerviewFilters.setAdapter(adapter);

        mFilterViewModel.toolbarTitle.set(getString(R.string.my_account));
    }

    @Override
    public void onResume() {
        super.onResume();

        //  ((FilterActivity) getActivity()).setActionBarTitle("My Account");

    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void clearFilters() {

    }


}
