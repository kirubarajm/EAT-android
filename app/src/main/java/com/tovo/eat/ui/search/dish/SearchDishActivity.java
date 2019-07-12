package com.tovo.eat.ui.search.dish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivitySearchDishBinding;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.kitchendish.KitchenDishActivity;
import com.tovo.eat.ui.home.kitchendish.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.home.kitchendish.dialog.DialogChangeKitchen;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class SearchDishActivity extends BaseActivity<ActivitySearchDishBinding, SearchDishViewModel> implements SearchDishNavigator, SearchDishAdapter.LiveProductsAdapterListener, HasSupportFragmentInjector, AddKitchenDishListener {


    public ActivitySearchDishBinding mActivitySearchDishBinding;
    @Inject
    public SearchDishViewModel mSearchDishViewModel;

    @Inject
    LinearLayoutManager mLayoutManager;
    @Inject
    SearchDishAdapter adapter;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    String searchText;


    public static Intent newIntent(Context context) {

        return new Intent(context, SearchDishActivity.class);
    }


    @Override
    public int getBindingVariable() {
        return BR.searchDishViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_dish;
    }

    @Override
    public SearchDishViewModel getViewModel() {

        return mSearchDishViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void goBack() {
        finish();
    }

    @Override
    public void listLoaded() {
        mActivitySearchDishBinding.recyclerviewList.setVisibility(View.VISIBLE);

    }

    @Override
    public void viewCart() {
        Intent intent = MainActivity.newIntent(SearchDishActivity.this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("cart", true);
        startActivity(intent);
        finish();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySearchDishBinding = getViewDataBinding();
        mSearchDishViewModel.setNavigator(this);
        adapter.setListener(this);


        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mActivitySearchDishBinding.recyclerviewList.setLayoutManager(mLayoutManager);
        mActivitySearchDishBinding.recyclerviewList.setAdapter(adapter);
        subscribeToLiveData();


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.getExtras().getString("search") != null) {
                searchText = intent.getExtras().getString("search");
                mSearchDishViewModel.searched.set(searchText);
                mSearchDishViewModel.fetchRepos(searchText);
            }
        }

        mSearchDishViewModel.totalCart();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    private void subscribeToLiveData() {
        mSearchDishViewModel.getDishItemsLiveData().observe(this,
                searchItemViewModel -> mSearchDishViewModel.addDishItemsToList(searchItemViewModel));
    }

    @Override
    public void onItemClickData(DishResponse.Result blogUrl) {

    }

    @Override
    public void sendCart() {

        mSearchDishViewModel.totalCart();
    }

    @Override
    public void dishRefresh() {

    }

    @Override
    public void productNotAvailable() {

        Toast.makeText(getApplicationContext(), "Entered quantity not available now", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void addDishFavourite(Integer dishId, String fav) {
        mSearchDishViewModel.addFavourite(dishId, fav);
    }

    @Override
    public void removeDishFavourite(Integer favId) {
        mSearchDishViewModel.removeFavourite(favId);
    }

    @Override
    public void showToast(String msg) {

        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void otherKitchenDish(Integer makeitId, Integer productId, Integer quantity, Integer price) {


        DialogChangeKitchen fragment = new DialogChangeKitchen();
        fragment.setCancelable(false);


        DialogChangeKitchen.newInstance().show(getSupportFragmentManager(), this, makeitId, productId, quantity, price);


      /*  DialogRefundAlert fragment = new DialogRefundAlert();
        fragment.setTargetFragment(this, 0);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations(R.rotate_out.fade_in, R.rotate_out.fade_in);
        fragment.show(ft, "UploadDialogFragment");
        fragment.setCancelable(false);*/
    }

    @Override
    public void confirmClick(boolean status) {

        mSearchDishViewModel.fetchRepos(searchText);
        mSearchDishViewModel.totalCart();

    }

    @Override
    public void onItemClickData(Integer kitchenId) {

        Intent intent = KitchenDishActivity.newIntent(getApplicationContext());
        intent.putExtra("kitchenId", kitchenId);
        startActivity(intent);


    }

    @Override
    public void showMore(Integer regionId) {

    }
}