package com.tovo.eat.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentDishBinding;
import com.tovo.eat.databinding.FragmentSearchBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.CartListener;
import com.tovo.eat.ui.home.homemenu.dish.dialog.AddDishListener;
import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchen;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.home.region.list.RegionListActivity;
import com.tovo.eat.utilities.nointernet.InternetListener;

import javax.inject.Inject;

public class SearchFragment extends BaseFragment<FragmentSearchBinding, SearchViewModel> implements SearchNavigator, SearchAdapter.LiveProductsAdapterListener {

    @Inject
    SearchViewModel mSearchViewModel;
    /*  @Inject
      LinearLayoutManager mLayoutManager;*/
    @Inject
    SearchAdapter adapter;

    FragmentSearchBinding mFragmentSearchBinding;


    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchViewModel.setNavigator(this);
        adapter.setListener(this);
        //  ((TestActivity) getActivity()).setFilterListener(FavDishFragment.this);


        //  StartFilter myInterface =getInterface(StartFilter.class, this);


    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentSearchBinding = getViewDataBinding();
        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewSearch.setLayoutManager(mLayoutManager);
        mFragmentSearchBinding.recyclerviewSearch.setAdapter(adapter);
        // subscribeToLiveData();

        mFragmentSearchBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSearchViewModel.fetchRepos("");
            }
        });


        mFragmentSearchBinding.searchh.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchViewModel.fetchRepos(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                mSearchViewModel.fetchRepos(s);

                return true;
            }
        });


        ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_IMPLICIT_ONLY);






    }

    @Override
    public int getBindingVariable() {
        return BR.searchViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public SearchViewModel getViewModel() {
        return mSearchViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void toastMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void listLoaded() {

        mFragmentSearchBinding.refreshList.setRefreshing(false);
    }

    @Override
    public void listLoading() {
        mFragmentSearchBinding.refreshList.setRefreshing(true);
    }

    @Override
    public void searchList(String data) {


    }


    private void subscribeToLiveData() {
        mSearchViewModel.getSearchItemsLiveData().observe(this,
                searchItemViewModel -> mSearchViewModel.addSearchItemsToList(searchItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();

       /* mFragmentSearchBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentSearchBinding.shimmerViewContainer.startShimmerAnimation();*/


        //  ((TestActivity) getActivity()).statusUpdate();

        /*mFragmentSearchBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentSearchBinding.shimmerViewContainer.startShimmerAnimation();*/

        subscribeToLiveData();
    }

    @Override
    public void onItemClickData(SearchResponse.Result result) {


        switch (result.getType()) {


            case 1:


             break;
            case 2:


                Intent intent = KitchenDishActivity.newIntent(getContext());
                intent.putExtra("kitchenId", result.getId());
                startActivity(intent);
                break;


            case 3:
                Intent intent2 = RegionListActivity.newIntent(getContext());
                intent2.putExtra("id", result.getId());
                startActivity(intent2);
                break;


            default:

                return;


        }


    }


}

