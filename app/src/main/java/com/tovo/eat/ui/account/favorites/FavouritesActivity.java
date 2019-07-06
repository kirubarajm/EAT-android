package com.tovo.eat.ui.account.favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.tovo.eat.BR;
import com.tovo.eat.R;
import com.tovo.eat.databinding.ActivityFavouritesBinding;
import com.tovo.eat.ui.account.favorites.dialog.AddKitchenDishListener;
import com.tovo.eat.ui.account.favorites.dialog.DialogChangeKitchen;
import com.tovo.eat.ui.account.favorites.favdish.CartFavListener;
import com.tovo.eat.ui.account.favorites.favdish.FavDishFragment;
import com.tovo.eat.ui.account.favorites.favkitchen.FavKitchenFragment;
import com.tovo.eat.ui.base.BaseActivity;
import com.tovo.eat.ui.home.MainActivity;
import com.tovo.eat.ui.home.homemenu.dish.DishAdapter;
import com.tovo.eat.ui.home.homemenu.dish.DishFragment;
import com.tovo.eat.ui.home.homemenu.dish.DishResponse;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenAdapter;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFavRequest;
import com.tovo.eat.ui.home.homemenu.kitchen.KitchenFragment;
import com.tovo.eat.utilities.nointernet.InternetErrorFragment;
import com.tovo.eat.utilities.swipe.ItemTouchHelperExtension;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class FavouritesActivity extends BaseActivity<ActivityFavouritesBinding, FavouritesViewModel> implements FavouritesNavigator, CartFavListener, HasSupportFragmentInjector {


    @Inject
    FavouritesViewModel mFavouritesViewModel;
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;


    ActivityFavouritesBinding mActivityFavouritesBinding;

    Integer kitchenID;

    public static Intent newIntent(Context context) {
       /* Intent intent = new Intent(context, CartActivity.class);
        return intent;*/
        return new Intent(context, FavouritesActivity.class);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavouritesViewModel.setNavigator(this);
        mActivityFavouritesBinding = getViewDataBinding();
        kitchen();
    }



    @Override
    public int getBindingVariable() {
        return BR.favouritesViewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_favourites;
    }

    @Override
    public FavouritesViewModel getViewModel() {
        return mFavouritesViewModel;
    }

    @Override
    public void handleError(Throwable throwable) {

    }

    @Override
    public void toastMessage(String msg) {

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    public void viewCart() {

        Intent intent = MainActivity.newIntent(FavouritesActivity.this);
        intent.putExtra("cart", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void back() {
        finish();
    }


    @Override
    public void dish() {


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FavDishFragment fragment = new FavDishFragment();
        transaction.replace(R.id.favourite_container, fragment);
        transaction.commit();



    }

    @Override
    public void kitchen() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FavKitchenFragment fragment = new FavKitchenFragment();
        transaction.replace(R.id.favourite_container, fragment);
        transaction.commit();
    }

    @Override
    public void goBack() {
        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
     /*   mActivityFavouritesBinding.shimmerViewContainer.setVisibility(View.VISIBLE);
        mActivityFavouritesBinding.shimmerViewContainer.startShimmerAnimation();*/
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void checkCart() {
        mFavouritesViewModel.totalCart();
    }
}

