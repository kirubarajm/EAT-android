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

    FilterListener filterListener;



    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }



    /*public static <T> getInterface(Class<T> interfaceClass, Fragment thisFragment) {
        final Fragment parent = thisFragment.getParentFragment();
        if (parent != null && interfaceClass.isAssignableFrom(parent)) {
            return interfaceClass.cast(parent);
        }

        final Activity activity = thisFragment.getActivity();
        if (activity != null && interfaceClass.isAssignableFrom(activity)) {
            return interfaceClass.cast(activity);
        }

        return null;
    }*/
    public final StartFilter getContract() {
        return startFilter;
    }

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



      /*  try {
            startFilter=(StartFilter)context;
        } catch (ClassCastException e) {
            throw new IllegalStateException(context.getClass().getSimpleName()
                    + " does not implement " + getClass().getSimpleName() + "'s contract interface.", e);
        }
        super.onAttach(context);*/

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
        //  mFoodMenuViewModel = ViewModelProviders.of(this, mViewModelFactory).get(FoodMenuViewModel.class);
        return mFilterViewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFilterViewModel.setNavigator(this);

        adapter.setListener(this);



      //  startFilter=(StartFilter)getContext();



        //   ((FilterActivity) getActivity()).setActionBarTitle("My Account");

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentFilterBinding = getViewDataBinding();

        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentFilterBinding.recyclerviewFilters.setLayoutManager(mLayoutManager);
        mFragmentFilterBinding.recyclerviewFilters.setAdapter(adapter);

        //  mFilterViewModel.toolbarTitle.set(getString(R.string.my_account));

        mFilterViewModel.sort();

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
        startFilter.applyFilter();
        dismiss();

    }

    @Override
    public void applyFilter() {
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
