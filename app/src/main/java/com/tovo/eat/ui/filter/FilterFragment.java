package com.tovo.eat.ui.filter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentFilterBinding;
import com.tovo.eat.ui.base.BaseBottomSheetFragment;
import com.tovo.eat.ui.home.homemenu.FilterListener;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;


public class FilterFragment extends BaseBottomSheetFragment<FragmentFilterBinding, FilterViewModel> implements FilterNavigator, FilterAdapter.FiltersAdapterListener {


    public static final String TAG = FilterFragment.class.getSimpleName();
    @Inject
    FilterViewModel mFilterViewModel;

    FragmentFilterBinding mFragmentFilterBinding;

    @Inject
    FilterAdapter adapter;
    @Inject
    LinearLayoutManager mLayoutManager;
    StartFilter startFilter;
    Analytics analytics;
    String  pageName= AppConstants.SCREEN_KITCHEN_FILTER;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StartFilter) {
            //init the listener
            startFilter = (StartFilter) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement InteractionListener");
        }

    }

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
        return mFilterViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilterViewModel.setNavigator(this);
        adapter.setListener(this);
        analytics=new Analytics(getActivity(),pageName);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentFilterBinding = getViewDataBinding();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentFilterBinding.recyclerviewFilters.setLayoutManager(mLayoutManager);
        mFragmentFilterBinding.recyclerviewFilters.setAdapter(adapter);
        mFilterViewModel.sort();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void clearFilters() {
        new Analytics().sendClickData(pageName,AppConstants.CLICK_CLEAR);
        startFilter.applyFilter();
        dismiss();

    }

    @Override
    public void applyFilter() {
        new Analytics().sendClickData(pageName,AppConstants.CLICK_APPLY);
        dismiss();
        startFilter.applyFilter();

    }


    @Override
    public void onItemClickData(Integer id) {

    }

    @Override
    public void addToFilter(Integer id) {
        mFilterViewModel.addToFilter(id);
    }

    @Override
    public void removeFromFilter(Integer id) {
        mFilterViewModel.removeFromFilter(id);
    }

    @Override
    public Integer getSelectedOption() {
        return mFilterViewModel.getSelectedOptions();
    }
}
