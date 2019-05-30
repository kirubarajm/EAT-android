package com.tovo.eat.ui.home.homemenu.kitchen;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.FragmentKitchenBinding;
import com.tovo.eat.ui.base.BaseFragment;
import com.tovo.eat.ui.filter.StartFilter;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.FilterListener;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.utilities.SwipeController;
import com.tovo.eat.utilities.SwipeControllerActions;

import javax.inject.Inject;

public class KitchenFragment extends BaseFragment<FragmentKitchenBinding, KitchenViewModel> implements KitchenNavigator, KitchenAdapter.LiveProductsAdapterListener, StartFilter {


    @Inject
    KitchenViewModel mKitchenViewModel;
   /* @Inject
    LinearLayoutManager mLayoutManager;*/
    @Inject
    KitchenAdapter adapter;

    FragmentKitchenBinding mFragmentKitchenBinding;

    public static KitchenFragment newInstance() {
        Bundle args = new Bundle();
        KitchenFragment fragment = new KitchenFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mKitchenViewModel.setNavigator(this);
        adapter.setListener(this);

      //  mKitchenViewModel.fetchRepos();


       // ((TestActivity) getActivity()).setFilterListener(FavKitchenFragment.this);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFragmentKitchenBinding = getViewDataBinding();


        LinearLayoutManager mLayoutManager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFragmentKitchenBinding.recyclerviewOrders.setLayoutManager(mLayoutManager);
        mFragmentKitchenBinding.recyclerviewOrders.setAdapter(adapter);
       // subscribeToLiveData();

        mFragmentKitchenBinding.refreshList.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
                mFragmentKitchenBinding.shimmerViewContainer.startShimmerAnimation();
                mKitchenViewModel.fetchRepos();
            }
        });


        mFragmentKitchenBinding.recyclerviewOrders.setItemAnimator(new DefaultItemAnimator());


        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                // Could hide open views here if you wanted. //
            }
        };



        mFragmentKitchenBinding.recyclerviewOrders.setOnScrollListener(onScrollListener);


      //  mFragmentKitchenBinding.recyclerviewOrders.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));







        



       /* SwipeController  swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {

            }
        });

        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(   mFragmentKitchenBinding.recyclerviewOrders);

        mFragmentKitchenBinding.recyclerviewOrders.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });*/






    }

    @Override
    public int getBindingVariable() {
        return BR.kitchenViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_kitchen;
    }

    @Override
    public KitchenViewModel getViewModel() {
        return mKitchenViewModel;
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
    public void kitchenListLoaded() {

        mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentKitchenBinding.shimmerViewContainer.stopShimmerAnimation();

        mFragmentKitchenBinding.refreshList.setRefreshing(false);
    }

    @Override
    public void kitchenListLoading() {
        mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentKitchenBinding.shimmerViewContainer.startShimmerAnimation();
    }

    @Override
    public void addressAdded1() {

    }

    @Override
    public void noAddressFound1() {
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
        mKitchenViewModel.getKitchenItemsLiveData().observe(this,
                kitchenItemViewModel -> mKitchenViewModel.addKitchenItemsToList(kitchenItemViewModel));
    }


    @Override
    public void onResume() {
        super.onResume();

        mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.GONE);
        mFragmentKitchenBinding.shimmerViewContainer.stopShimmerAnimation();


     /*   mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentKitchenBinding.shimmerViewContainer.startShimmerAnimation();
*/
        ((MainActivity) getActivity()).statusUpdate();


        kitchenListLoading();
       mKitchenViewModel.fetchRepos();

       subscribeToLiveData();
    }


    @Override
    public void onItemClickData(Integer kitchenId) {

        mKitchenViewModel.saveMakeitId(kitchenId);

        Intent intent = KitchenDishActivity.newIntent(getContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);

    }


    @Override
    public void addFav(Integer id, String fav) {

        mKitchenViewModel.addFavourite(id, fav);
    }


    @Override
    public void removeDishFavourite(Integer favId) {
        mKitchenViewModel.removeFavourite(favId);
    }

    /*@Override
    public void filterList() {
        mKitchenViewModel.fetchRepos();
    }*/

    @Override
    public void applyFilter() {
        mFragmentKitchenBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mFragmentKitchenBinding.shimmerViewContainer.startShimmerAnimation();


        mKitchenViewModel.fetchRepos();
    }


}

