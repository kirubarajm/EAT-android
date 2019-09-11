package com.tovo.eat.ui.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentSearchBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.cart.coupon.CouponListResponse;
import com.tovo.eat.ui.home.CartListener;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.dish.dialog.AddDishListener;
import com.tovo.eat.ui.home.homemenu.dish.dialog.DialogChangeKitchen;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenResponse;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.kitchendetails.KitchenDetailsActivity;
import com.tovo.eat.ui.search.dish.SearchDishAdapter;
import com.tovo.eat.utilities.analytics.Analytics;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;

public class SearchFragment extends BaseFragment<FragmentSearchBinding, SearchViewModel> implements SearchNavigator, SearchAdapter.LiveProductsAdapterListener, SearchDishAdapter.LiveProductsAdapterListener, KitchenAdapter.LiveProductsAdapterListener , AddDishListener {

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


    FragmentSearchBinding mFragmentSearchBinding;

    CartListener cartListener;


    Analytics analytics;
    String  pageName="Search";




    @Override
    public void onAttach(Context context) {
        cartListener=(CartListener)context;
        super.onAttach(context);
    }


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
        dishAdapter.setListener(this);
        kitchenAdapter.setListener(this);
        //  ((TestActivity) getActivity()).setFilterListener(FavDishFragment.this);


        analytics=new Analytics(getActivity(), pageName);
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

        mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
        mFragmentSearchBinding.before.setVisibility(View.VISIBLE);

        mFragmentSearchBinding.searchh.requestFocusFromTouch();



        mFragmentSearchBinding.searchh.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
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

                mSearchViewModel.fetchDishes(result.getName());


                new Analytics().search("DISH",result.getName());


                mFragmentSearchBinding.recyclerviewDish.setVisibility(View.VISIBLE);
                mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
                mFragmentSearchBinding.searchRegion.setVisibility(View.GONE);
                mFragmentSearchBinding.sear.setVisibility(View.GONE);
                mFragmentSearchBinding.searchh.clearFocus();

              /*  mFragmentSearchBinding.searchh.clearFocus();
                mFragmentSearchBinding.searchh.setQuery("",false);*/


               /* Intent intent = SearchDishActivity.newIntent(getContext());
                intent.putExtra("search", searchText);
                startActivity(intent);*/

                break;
            case 2:

                new Analytics().search("HOME",result.getName());

                Intent intent1 = KitchenDetailsActivity.newIntent(getContext());
                intent1.putExtra("kitchenId", result.getId());
                startActivity(intent1);
                break;


            case 3:

                new Analytics().search("REGION",result.getName());

                mSearchViewModel.fetchRegionKitchens(result.getName(), result.getId());

                mFragmentSearchBinding.recyclerviewDish.setVisibility(View.GONE);
                mFragmentSearchBinding.recyclerviewSearch.setVisibility(View.GONE);
                mFragmentSearchBinding.sear.setVisibility(View.GONE);
                mFragmentSearchBinding.searchRegion.setVisibility(View.VISIBLE);
                mFragmentSearchBinding.searchh.clearFocus();

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


    @Override
    public void collectionItemClick(KitchenResponse.Collection collection) {

    }

    @Override
    public void offersItemClick(CouponListResponse.Result offers) {

    }

    @Override
    public void onItemClickData(Integer kitchenId) {

        Intent intent = KitchenDetailsActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

        mFragmentSearchBinding.searchh.clearFocus();
    }

    @Override
    public void animateView(View view) {

    }

    @Override
    public void showMore(Integer regionId) {

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
    public void addFav(Integer id, String fav) {

    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price) {

        DialogChangeKitchen fragment = new DialogChangeKitchen();
        fragment.setTargetFragment(this, 0);
        fragment.setCancelable(false);

        DialogChangeKitchen.newInstance(fragment).show(getFragmentManager(), getBaseActivity(),makeitId,productId,quantity,price);

        mFragmentSearchBinding.searchh.clearFocus();

    }

    @Override
    public void confirmClick(boolean status) {
        subscribeToLiveData();
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

