package com.tovo.eat.ui.search;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentSearchBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.home.CartListener;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.dish.dialog.AddDishListener;
import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchen;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.ui.search.dish.SearchDishAdapter;
import com.tovo.eat.utilities.AppConstants;
import com.tovo.eat.utilities.analytics.Analytics;

import java.util.List;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public class SearchFragment extends BaseFragment<FragmentSearchBinding, SearchViewModel> implements SearchNavigator, SearchAdapter.LiveProductsAdapterListener, SearchDishAdapter.LiveProductsAdapterListener, KitchenAdapter.LiveProductsAdapterListener, AddDishListener {

    @Inject
    SearchViewModel mSearchViewModel;
    /*  @Inject
      LinearLayoutManager mLayoutManager;*/
    @Inject
    SearchAdapter adapter;

    @Inject
    SearchDishAdapter dishAdapter;
    @Inject
    KitchenAdapter kitchenAdapter;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    String searchText;


    SearchResponse.Result searchClicked = new SearchResponse.Result();


    FragmentSearchBinding mFragmentSearchBinding;

    CartListener cartListener;


    Analytics analytics;
    String pageName = AppConstants.SCREEN_SEARCH;

    ObservableList<SearchResponse.Result> searchItemViewModels;

    public static SearchFragment newInstance() {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        cartListener = (CartListener) context;
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchViewModel.setNavigator(this);
        adapter.setListener(this);
        dishAdapter.setListener(this,pageName);
        kitchenAdapter.setListener(this);
        //  ((TestActivity) getActivity()).setFilterListener(FavDishFragment.this);


        analytics = new Analytics(getActivity(), pageName);
        //  XfactorListner myInterface =getInterface(XfactorListner.class, this);


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

        mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
        mFragmentSearchBinding.before.setVisibility(View.VISIBLE);

        mFragmentSearchBinding.searchh.requestFocusFromTouch();


        mFragmentSearchBinding.searchh.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                new Analytics().sendClickData(AppConstants.SCREEN_SEARCH, AppConstants.CLICK_CLOSE);


                mFragmentSearchBinding.recyclerviewDish.setVisibility(View.GONE);
                mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
                mFragmentSearchBinding.searchRegion.setVisibility(View.GONE);
                mFragmentSearchBinding.before.setVisibility(View.VISIBLE);
                return false;
            }
        });


        mFragmentSearchBinding.searchh.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mFragmentSearchBinding.regionEmpty.setVisibility(View.GONE);
                if (s.length() > 1) {
                    mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.VISIBLE);
                    mFragmentSearchBinding.sear.setVisibility(View.VISIBLE);
                    mFragmentSearchBinding.recyclerviewDish.setVisibility(View.GONE);
                    mFragmentSearchBinding.searchRegion.setVisibility(View.GONE);
                    searchText = s;
                    mSearchViewModel.fetchRepos(s);
                } else {
                    mFragmentSearchBinding.recyclerviewDish.setVisibility(View.GONE);
                    mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
                    mFragmentSearchBinding.searchRegion.setVisibility(View.GONE);
                    mFragmentSearchBinding.before.setVisibility(View.VISIBLE);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                mFragmentSearchBinding.regionEmpty.setVisibility(View.GONE);

                if (s.length() > 1) {
                    mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.VISIBLE);
                    mFragmentSearchBinding.sear.setVisibility(View.VISIBLE);
                    mFragmentSearchBinding.recyclerviewDish.setVisibility(View.GONE);
                    mFragmentSearchBinding.searchRegion.setVisibility(View.GONE);
                    searchText = s;
                    mSearchViewModel.fetchRepos(s);
                } else {
                    mFragmentSearchBinding.recyclerviewDish.setVisibility(View.GONE);
                    mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
                    mFragmentSearchBinding.searchRegion.setVisibility(View.GONE);
                    mFragmentSearchBinding.before.setVisibility(View.VISIBLE);

                }

                return true;
            }

        });


        //dish search

        LinearLayoutManager dLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewDish.setLayoutManager(dLayoutManager);
        mFragmentSearchBinding.recyclerviewDish.setAdapter(dishAdapter);


        // region search
        LinearLayoutManager rLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentSearchBinding.recyclerviewRegion.setLayoutManager(rLayoutManager);
        mFragmentSearchBinding.recyclerviewRegion.setAdapter(kitchenAdapter);


       /* ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                toggleSoftInput(InputMethodManager.SHOW_FORCED,
                        InputMethodManager.HIDE_NOT_ALWAYS);*/


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
        mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.VISIBLE);
        mFragmentSearchBinding.before.setVisibility(View.GONE);

        //   mFragmentSearchBinding.refreshList.setRefreshing(false);


        //  kitchenAdapter.serviceable(mSearchViewModel.kitchenResponse.getResult());


    }

    @Override
    public void listLoading() {
        //  mFragmentSearchBinding.refreshList.setRefreshing(true);
    }

    @Override
    public void searchList(String data) {


    }

    @Override
    public void noResults() {
        mFragmentSearchBinding.searchRegion.setVisibility(View.GONE);
        mFragmentSearchBinding.regionEmpty.setVisibility(View.VISIBLE);

    }


    private void subscribeToLiveData() {
        mSearchViewModel.getSearchItemsLiveData().observe(this,
                searchItemViewModel -> mSearchViewModel.addSearchItemsToList(searchItemViewModel));

        mSearchViewModel.getDishItemsLiveData().observe(this,
                searchItemViewModel -> mSearchViewModel.addDishItemsToList(searchItemViewModel));


        mSearchViewModel.getkitchenListItemsLiveData().observe(this,
                kitchensListItemViewModel -> mSearchViewModel.addKitchenItemsToList(kitchensListItemViewModel));

    }


    @Override
    public void onResume() {
        super.onResume();
        cartListener.checkCart();
       /* mFragmentSearchBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentSearchBinding.shimmerViewContainer.startShimmerAnimation();*/


        //  ((TestActivity) getActivity()).statusUpdate();

        /*mFragmentSearchBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentSearchBinding.shimmerViewContainer.startShimmerAnimation();*/

        subscribeToLiveData();
    }

    @Override
    public void onItemClickData(SearchResponse.Result result) {
        searchClicked = result;


        new Analytics().sendClickData(AppConstants.SCREEN_SEARCH, result.getName());


        switch (result.getType()) {

            case 1:

                new Analytics().sendClickData(AppConstants.SCREEN_SEARCH, result.getName());
                mSearchViewModel.fetchDishes(result.getName());
                new Analytics().search("DISH", searchText, result.getName());
                mFragmentSearchBinding.recyclerviewDish.setVisibility(View.VISIBLE);
                mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
                mFragmentSearchBinding.searchRegion.setVisibility(View.GONE);
                mFragmentSearchBinding.sear.setVisibility(View.GONE);
                mFragmentSearchBinding.searchh.clearFocus();

                metricsSearch(AppConstants.SCREEN_SEARCH);

              /*  mFragmentSearchBinding.searchh.clearFocus();
                mFragmentSearchBinding.searchh.setQuery("",false);*/


               /* Intent intent = SearchDishActivity.newIntent(getContext());
                intent.putExtra("search", searchText);
                startActivity(intent);*/

                break;
            case 2:

                new Analytics().search("HOME", searchText, result.getName());

                new Analytics().selectKitchen(AppConstants.ANALYTICYS_SEARCH_KITCHEN, Long.valueOf(result.getId()));

                metricsSearch(AppConstants.SCREEN_KITCHEN_DETAILS);
                Intent intent1 = KitchenDetailsActivity.newIntent(getContext());
                intent1.putExtra("kitchenId", Long.valueOf(result.getId()));
                startActivity(intent1);
                break;

            case 3:

                new Analytics().search("REGION", searchText, result.getName());

                mSearchViewModel.fetchRegionKitchens(result.getName(), result.getId());

                mFragmentSearchBinding.recyclerviewDish.setVisibility(View.GONE);
                mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
                mFragmentSearchBinding.sear.setVisibility(View.GONE);
                mFragmentSearchBinding.searchRegion.setVisibility(View.VISIBLE);
                mFragmentSearchBinding.searchh.clearFocus();

                metricsSearch(AppConstants.SCREEN_SEARCH);
               /* mFragmentSearchBinding.searchh.clearFocus();
                mFragmentSearchBinding.searchh.setQuery("",false);*/

                /*Intent intent2 = RegionDetailsActivity.newIntent(getContext());
                intent2.putExtra("id", result.getId());
                startActivity(intent2);*/
                break;


            default:

                return;
        }
    }

    public void metricsSearch(String nextPage) {
        try {
            searchItemViewModels = new ObservableArrayList<>();
            searchItemViewModels = mSearchViewModel.getSearchListAnalytics();
            StringBuilder dishSb = new StringBuilder();StringBuilder kitchenSb = new StringBuilder();StringBuilder regionSb = new StringBuilder();
            int dishCount = 0, kitchenCount = 0, regionCount = 0;
            for (int i = 0; i < searchItemViewModels.size(); i++) {
                if (searchItemViewModels.get(i).getType() == 1) {
                    dishCount++;
                    dishSb.append(searchItemViewModels.get(i).getName()).append(",");
                } else if (searchItemViewModels.get(i).getType() == 2) {
                    kitchenCount++;
                    kitchenSb.append(searchItemViewModels.get(i).getName()).append(",");
                } else if (searchItemViewModels.get(i).getType() == 3) {
                    regionCount++;
                    regionSb.append(searchItemViewModels.get(i).getName()).append(",");
                }
            }
            String strRegion = regionSb.toString();
            String strKitchen = kitchenSb.toString();
            String strDish = dishSb.toString();
            String Region="",Kitchen="",Dish="";

            if (!strRegion.equals(""))
            {
                Region  = strRegion.substring(0, strRegion.length() - 1);
            }
            if (!strKitchen.equals(""))
            {
                Kitchen  = strKitchen.substring(0, strKitchen.length() - 1);
            } if (!strDish.equals(""))
            {
                Dish  = strDish.substring(0, strDish.length() - 1);
            }
            new Analytics().searchMetrics("",searchText, regionCount, kitchenCount, dishCount, searchClicked.getType(),
                    searchClicked.getId(), nextPage,Region ,Kitchen ,Dish);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {

    }

    @Override
    public void offersItemClick(KitchenResponse.Coupon offers) {

    }

    @Override
    public void onItemClickData(Long kitchenId) {

        new Analytics().sendClickData(AppConstants.SCREEN_SEARCH, AppConstants.CLICK_VIEW_KITCHEN);

        metricsSearch(AppConstants.SCREEN_KITCHEN_DETAILS);
        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

        mFragmentSearchBinding.searchh.clearFocus();
    }

    @Override
    public void animateView(View view) {

    }

    @Override
    public void showMore(Long regionId) {
        new Analytics().sendClickData(AppConstants.SCREEN_SEARCH, AppConstants.CLICK_VIEW_MENU);

        metricsSearch(AppConstants.SCREEN_KITCHEN_DETAILS);
        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", regionId);
        startActivity(intent);

    }

    @Override
    public void onItemClickData(DishResponse.Result blogUrl) {

    }

    @Override
    public void sendCart() {
        //   mSearchViewModel.totalCart();

        cartListener.checkCart();

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

    @Override
    public void addFav(long id, String fav) {

    }

    @Override
    public void infinityStoryItemClick(List<KitchenResponse.Story> story, int position) {

    }

    @Override
    public void regionCollectionItemClick(KitchenResponse.Region collection) {

    }

    @Override
    public void infinityCollectionDetailItemClick(KitchenResponse.CollectionDetail collection) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void otherKitchenDish(Long makeitId, Integer productId, Integer quantity, Integer price) {

        DialogChangeKitchen fragment = new DialogChangeKitchen();
        fragment.setTargetFragment(this, 0);
        fragment.setCancelable(false);

        DialogChangeKitchen.newInstance(fragment).show(getFragmentManager(), getBaseActivity(), makeitId, productId, quantity, price);

        mFragmentSearchBinding.searchh.clearFocus();

    }

    @Override
    public void confirmClick(boolean status) {
        subscribeToLiveData();


        if (status) {
            new Analytics().sendClickData(AppConstants.SCREEN_SEARCH, AppConstants.CLICK_CHANGE_KITCHEN_YES);
        } else {
            new Analytics().sendClickData(AppConstants.SCREEN_SEARCH, AppConstants.CLICK_CHANGE_KITCHEN_NO);

        }


    }

    @Override
    public void onStop() {
        super.onStop();
        mFragmentSearchBinding.searchh.clearFocus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentSearchBinding.searchh.clearFocus();
    }

    @Override
    public void onPause() {
        super.onPause();
        mFragmentSearchBinding.searchh.clearFocus();
    }


}

